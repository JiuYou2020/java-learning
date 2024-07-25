package cn.jiuyou2020.config;

import cn.jiuyou2020.constant.OrderStatus;
import cn.jiuyou2020.constant.OrderStatusChangeEvent;
import cn.jiuyou2020.entity.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

/**
 * 订单状态机配置
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderStatusChangeEvent> {

    /**
     * 配置状态
     *
     * @param states 该对象提供了一个简单的API，用于配置状态机的状态
     * @throws Exception 异常
     */
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states) throws Exception {
        states.withStates().initial(OrderStatus.WAIT_PAYMENT).states(EnumSet.allOf(OrderStatus.class));
    }

    /**
     * 配置状态转换事件关系
     *
     * @param transitions 该对象提供了一个简单的API，用于配置状态机的状态转换事件关系
     * @throws Exception 异常
     */
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
        transitions
                // 1.支付，待支付->待发货,如果触发PAYED事件，状态从WAIT_PAYMENT变为WAIT_DELIVER，并触发cn.jiuyou2020.OrderStateListenerImpl.payTransition方法
                .withExternal().source(OrderStatus.WAIT_PAYMENT).target(OrderStatus.WAIT_DELIVER).event(OrderStatusChangeEvent.PAYED).and()
                // 2.发货，待发货->待收货,如果触发DELIVERY事件，状态从WAIT_DELIVER变为WAIT_RECEIVE，并触发cn.jiuyou2020.OrderStateListenerImpl.deliverTransition方法
                .withExternal().source(OrderStatus.WAIT_DELIVER).target(OrderStatus.WAIT_RECEIVE).event(OrderStatusChangeEvent.DELIVERY).and()
                // 3.收货，待收货->完成,如果触发RECEIVED事件，状态从WAIT_RECEIVE变为FINISH，并触发cn.jiuyou2020.OrderStateListenerImpl.receiveTransition方法
                .withExternal().source(OrderStatus.WAIT_RECEIVE).target(OrderStatus.FINISH).event(OrderStatusChangeEvent.RECEIVED);
    }

    /**
     * 持久化配置
     * 在实际使用中，可以配合Redis等进行持久化操作
     *
     * @return DefaultStateMachinePersister
     */
    @Bean
    public DefaultStateMachinePersister persister() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<Object, Object, Order>() {
            @Override
            public void write(StateMachineContext<Object, Object> context, Order order) throws Exception {
                //此处并没有进行持久化操作
            }

            @Override
            public StateMachineContext<Object, Object> read(Order order) throws Exception {
                //此处直接获取Order中的状态，其实并没有进行持久化读取操作
                return new DefaultStateMachineContext(order.getStatus(), null, null, null);
            }
        });
    }
}

