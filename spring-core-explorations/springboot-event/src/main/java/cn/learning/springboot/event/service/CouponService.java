package cn.learning.springboot.event.service;

import cn.learning.springboot.event.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CouponService {


    @EventListener
    public void addCoupon(UserRegisterEvent event) {
        log.info("[addCoupon][给用户({}) 发放优惠劵]", event.getUsername());
    }

}
