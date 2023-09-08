# 1. 工作流概述

## 1.1 概念

工作流(Workflow)，就是通过计算机**对业务流程自动化执行管理**。它主要解决的是“使在多个参与者之间按照某种预定义的规则自动进行传递文档、信息或任务的过程，从而实现某个预期的业务目标，或者促使此目标的实现”。

> 工作流的作用是对业务流程进行自动化管理,归根结底,工作流只是协助业务管理流程,**即使没有工作流业务系统也可以开发运行，只不过有了工作流可以更好的管理业务流程，提高系统的可扩展性。**

## 1.2 具体应用

1. 以正在开发中的CCPC来举例,我们对赛事的报名就可以看做是一个工作流,在学生报名参赛时,需要经过报名,审核,缴费,缴费审核,完成报名这5个阶段
2. 关键业务流程：订单、报价处理、合同审核、客户电话处理、供应链管理等
3. 行政管理类:出差申请、加班申请、请假申请、用车申请、各种办公用品申请、购买申请、日报周报等凡是原来手工流转处理的行政表单。
4. 人事管理类：员工培训安排、绩效考评、职位变动处理、员工档案信息管理等。
5. 财务相关类：付款请求、应收款处理、日常报销处理、出差报销、预算和计划申请等。
6. 客户服务类：客户信息管理、客户投诉、请求处理、售后服务管理等。
7. 特殊服务类：ISO系列对应流程、质量管理对应流程、产品数据信息管理、贸易公司报关处理、物流公司货物跟踪处理等各种通过表单逐步手工流转完成的任务均可应用工作流软件自动规范地实施。

## 1.3 实现方式

在没有专门的工作流引擎之前，我们之前为了实现流程控制，通常的做法就是**采用状态字段的值来跟踪流程的变化情况。**这样不用角色的用户，通过状态字段的取值来决定记录是否显示。 eg:我们在CCPC中采用status字段来跟踪报名参赛的流程

针对有权限可以查看的记录，当前用户根据自己的角色来决定审批是否合格的操作。如果合格将状态字段设置一个值，来代表合格；当然如果不合格也需要设置一个值来代表不合格的情况。

这是一种最为原始的方式。通过状态字段虽然做到了流程控制，但是**当我们的流程发生变更的时候，这种方式所编写的代码也要进行调整**。(**Hard Coding**)

那么有没有专业的方式来实现工作流的管理呢？并且可以做到业务流程变化之后，我们的程序可以不用改变，如果可以实现这样的效果，那么我们的业务系统的适应能力就得到了极大提升。请接着看...





# 2. Flowable工作流引擎

> Flowable is a light-weight business process engine written in Java. The Flowable process engine allows you to deploy BPMN 2.0 process definitions (an industry XML standard for defining processes), creating process instances of those process definitions, running queries, accessing active or historical process instances and related data, plus much more. 							--[官方文档](https://www.flowable.com/open-source/docs/bpmn/ch02-GettingStarted)
>
> 翻译:Flowable是一个用Java编写的轻量级业务流程引擎。Flowable流程引擎允许您部署BPMN 2. 0流程定义（定义流程的行业XML标准），创建这些流程定义的流程实例，运行查询，访问活动或历史流程实例和相关数据等等。

## 2.1 BPMN 2.0介绍

> 详细规范请参考[官方文档](https://www.flowable.com/open-source/docs/bpmn/ch07a-BPMN-Introduction)

BPMN 2.0是行业广泛接受的一种XML标准。在Flowable术语中,我们称之为流程定义。从一个流程定义中,可以启动多个流程实例。可以把流程定义看作是流程执行的蓝图。

![getting.started.bpmn.process](https://www.flowable.com/open-source/docs/assets/bpmn/getting.started.bpmn.process.png)

以这张图举例:

- 左边的圆圈为**开始事件**,是流程实例的起点

- 第一个矩形为**用户任务**,这是执行流程中的第一个步骤,在此例中,经理需要去批准或拒绝请求
- 根据经理的决定,**独占网关**(带有X字的菱形)会将流程实例路由到审批路径或拒绝路径
- 如果获得批准，我们必须在某个外部系统(即编写一个外部类)中注册请求，然后再次为原始员工执行用户任务，通知他们该结果。 当然，这可以替换为电子邮件。
- 如果拒绝,则会向员工发送电子邮件,通知他们
- 右边的加粗的圆圈是**结束事件**,是流程实例的结束点

# 3. 入门示例(java api)

> 源码位于:[learning/flowable/flowable-quickstart at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/flowable/flowable-quickstart)
>
> 在源码中,使用tudo标记了代码的编写流程,因此,我们可以通过idea的工具去查找编写流程,如下:
>
> ![image-20230908001711964](C:/Users/%E9%87%91%E6%9C%A8/AppData/Roaming/Typora/typora-user-images/image-20230908001711964.png)

1. 建模流程定义(有些流程定义是可以使用可视化建模工具建模的,但是这里使用xml以属性bpmn 2.0 及其概念)

将以下 XML 保存在名为 *holiday-request.bpmn20.xml* 的文件中，该文件位于 *src/main/resources* 文件夹中。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:flowable="http://flowable.org/bpmn"
             typeLanguage="http://www.w3.org/2001/XMLSchema"
             expressionLanguage="http://www.w3.org/1999/XPath"
             targetNamespace="http://www.flowable.org/processdef">
    <!--    流程定义的编写-->
    <!--id为holidayRequest,isExecutable为true表示该流程可以被执行-->
    <process id="holidayRequest" name="Holiday Request" isExecutable="true">
        <!--startEvent为开始节点,流程的开始-->
        <startEvent id="startEvent"/>

        <!--sequenceFlow为连线,sourceRef为开始节点的id,targetRef为下一个节点的id-->
        <sequenceFlow sourceRef="startEvent" targetRef="approveTask"/>
        <!--userTask为用户任务,即需要用户来完成的任务,需要指定id和name-->
        <userTask id="approveTask" name="Approve or reject request"/>
        <sequenceFlow sourceRef="approveTask" targetRef="decision"/>

        <!--exclusiveGateway为排他网关,即判断条件,需要指定id-->
        <exclusiveGateway id="decision"/>
        <!--sequenceFlow为连线,sourceRef为排他网关的id,targetRef为下一个节点的id,conditionExpression为条件表达式,即判断条件-->
        <sequenceFlow sourceRef="decision" targetRef="externalSystemCall">
            <!-- 这里是判断条件,${approved}表示流程变量approved的值,如果approved为true,则流程走向externalSystemCall,否则走向sendRejectionMail
             此处作为表达式编写的条件是${approved}形式,是${approved==true}的简写-->
            <conditionExpression xsi:type="tFormalExpression">
                <![CDATA[
          ${approved}
        ]]>
            </conditionExpression>
        </sequenceFlow>
        <sequenceFlow sourceRef="decision" targetRef="sendRejectionMail">
            <conditionExpression xsi:type="tFormalExpression">
                <![CDATA[
          ${!approved}
        ]]>
            </conditionExpression>
        </sequenceFlow>
        <!--serviceTask为服务任务,这里是==true时的步骤即需要调用服务来完成的任务,需要指定id,name和class,这里的class为自定义的类-->
        <serviceTask id="externalSystemCall" name="Enter holidays in external system"
                     flowable:class="cn.learning.CallExternalSystemDelegate"/>
        <sequenceFlow sourceRef="externalSystemCall" targetRef="holidayApprovedTask"/>
        <!--userTask为用户任务,即需要用户来完成的任务,这里表达管理人员统一审核,需要指定id和name-->
        <userTask id="holidayApprovedTask" name="Holiday approved"/>
        <sequenceFlow sourceRef="holidayApprovedTask" targetRef="approveEnd"/>
        <!--serviceTask为服务任务,这里是==false时的步骤即需要调用服务来完成的任务,需要指定id,name和class,这里的class为自定义的类-->
        <serviceTask id="sendRejectionMail" name="Send out rejection email"
                     flowable:class="org.flowable.SendRejectionMail"/>
        <sequenceFlow sourceRef="sendRejectionMail" targetRef="rejectEnd"/>
        <!--endEvent为结束节点,流程的结束-->
        <endEvent id="approveEnd"/>

        <endEvent id="rejectEnd"/>

    </process>

</definitions>
```

虽然长度有点让人生畏,但是都有比较详细的注释,可以结合 [BPMN 2.0介绍](# 2.1 BPMN 2.0介绍) 中给出的图片一起食用~

2. 现在,我们有了BPMN 2.0 XML文件,我们需要将其**部署**到引擎中,这意味着

- 进程引擎会将 XML 文件存储在数据库中，以便在需要时可以检索它
- 进程定义被解析为内部的可执行对象模型，以便可以从中启动*进程实例*。(这里就像mybatis将mapper.xml解析为MapperStatement对象)

若要*将*进程定义部署到可流动引擎，需要使用*RepositoryService*，可以从*进程引擎*对象中检索该*服务*。

```Java
public class HolidayRequest {
    public static void main(String[] args) {
        //这段代码主要作用就是使用Flowable提供的API完成流程引擎的配置、创建工作,为后续的流程部署与执行等操作做准备。
        //通过构建ProcessEngine对象,我们就可以进一步地使用Flowable的流程引擎来开发业务流程应用程序了。
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration().setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1").setJdbcUsername("sa").setJdbcPassword("").setJdbcDriver("org.h2.Driver").setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();
        //先去完成日志文件的设置和流程图的编写,再接下来看下面的内容
        //进程引擎会将xml文件存储到数据库中,以便需要时获取,接下来将流程部署到引擎中,即使用数据库服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("holiday-request.bpmn20.xml").deploy();
        //现在，我们可以通过 API 查询流程定义来验证引擎是否知道流程定义（并了解一些有关 API 的信息）。
        //这是通过 RepositoryService 创建一个新的 ProcessDefinitionQuery 对象来完成的。
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        System.out.println("流程定义如下 : " + processDefinition.getName());
        //现在，我们已将流程定义部署到流程引擎，因此可以使用此流程定义作为“蓝图”启动流程实例
        //要启动流程实例，我们需要提供一些初始流程变量。通常，当流程被自动触发时，将通过呈现给用户的表单或通过REST API获得这些信息。
        //在本例中，我们将保持简单，并使用java. util. Scanner类在命令行上简单地输入一些数据
        Scanner scanner = new Scanner(System.in);

        System.out.println("你的名字是?");
        String employee = scanner.nextLine();

        System.out.println("你需要请假多少天?");
        Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

        System.out.println("你为什么需要请假?");
        String description = scanner.nextLine();
        //接下来，我们可以通过RuntimeService启动一个流程实例。收集的数据作为java. util. Map实例传递，其中key是将用于稍后检索变量的标识符。
        //使用key启动流程实例。此键与BPMN 2. 0 XML文件中设置的id属性匹配，在本例中为holidayRequest。除了使用密钥之外，还有许多启动流程实例的方法
        //当流程实例启动时，会创建一个执行并放入start事件中。从那里开始，这个执行遵循序列流程到用户任务，以获得管理员的批准，并执行用户任务行为。
        //此行为将在数据库中创建一个任务，以后可以通过查询找到该任务。用户任务处于等待状态，引擎将停止执行任何进一步操作，返回API调用。
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
    }
}

```

到这里,启动流程实例时，将创建一个**执行**并放入启动事件中。此执行遵循工作流到用户任务以供经理批准，并*执行*用户任务行为。此行为将在数据库中创建一个任务，稍后可以查询找到该任务。用户任务是一种*等待状态*，工作流引擎将停止执行任何进一步的操作，并返回 API 调用。

> ps:事务性
>
> 1. 在Flowable中,数据库事务对保证数据一致性和解决并发问题起着至关重要的作用。
> 2. 当调用Flowable API时,默认情况下所有操作都是同步的,在同一事务中执行。也就是当方法返回时,事务会启动并提交。
> 3. 当启动一个流程实例时,从流程开始到下一个等待状态(第一个用户任务)会在一个数据库事务中完成。当引擎执行到用户任务时,状态会持久化到数据库,事务提交,API调用返回。
> 4. 在Flowable中继续执行流程实例时,总是从前一个等待状态到下一个等待状态会有一个数据库事务。一旦持久化,数据可以在数据库中停留很长时间,直到有API调用继续推进流程实例。注意在此等待状态下不消耗计算和内存资源。
> 5. 在本例中,当第一个用户任务完成时,从用户任务通过排他网关(自动逻辑)到第二个用户任务会在一个事务中完成。通过另一条路径直接结束流程也是如此。

3. 在现实的应用程序中,将有一个用户界面,员工和经理可以在其中登录并查看他们的任务列表,有了这些,他们可以对这些流程执行具体的操作如批准请假,等等.在此示例中，我们将通过执行通常位于驱动 UI 的服务调用后面的 API 调用来模拟任务列表。

我们尚未为用户任务配置分配。我们希望第一个任务转到“经理”组，第二个用户任务分配给假期的原始请求者。为此，请将*候选组*属性添加到第一个任务：

```xml
<userTask id="approveTask" name="Approve or reject request" flowable:candidateGroups="managers"/>
```

以及第二个任务的*被分派人*属性，如下所示。请注意，我们没有使用像上面的“managers”值这样的静态值，而是基于我们在启动流程实例时传递的流程变量的动态赋值：

```xml
<userTask id="holidayApprovedTask" name="Holiday approved" flowable:assignee="${employee}"/>
```

再在HolidayRequest继续编写代码

```Java
//todo 10.
        //获取实际的任务列表,通过TaskService创建一个新的TaskQuery对象,并使用taskCandidateGroup方法过滤出候选组为managers的任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("你有 " + tasks.size() + " 个任务:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
        }
        //使用任务标识符,我们现在可以获取特定任务的详细信息,并将其打印到控制台
        System.out.println("你想要处理哪个任务?");
        int taskIndex = Integer.parseInt(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee") + " 想要请假 " + processVariables.get("nrOfHolidays") + " 天,你是否同意?");
        //todo 11.
        //现在，我们可以使用TaskService完成任务。实际上,这通常意味着表单是由用户去提交的,然后,表单中的数据将作为流程传递变量使用
        //在这里,我们将通过在任务完成时传递带有“approved”变量的映射来模拟这一点（注意,名称很重要，因为它稍后会在序列流的条件中使用！
        boolean approved = "y".equalsIgnoreCase(scanner.nextLine());
        variables = new HashMap<>();
        variables.put("approved", approved);
        taskService.complete(task.getId(), variables);
        //任务已经完成,并根据"已批准"流程变量选择离开独占网关的序列流程
```

这样,就完成了经理同意审核这一网关,此时,需要路由到后面的操作,也就是请求获得批准时将执行的自动逻辑,还记得我们写的xml语句吗?

```xml
<serviceTask id="externalSystemCall" name="Enter holidays in external system"
                     flowable:class="cn.learning.CallExternalSystemDelegate"/>
```

它会路由到`CallExternalSystemDelegate`类去执行后面的操作

```Java
public class CallExternalSystemDelegate implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        System.out.println("为 " + execution.getVariable("employee") + " 申请了 " + execution.getVariable("nrOfHolidays") + " 天假期。");
    }
}
```

这时,我们可以看到这样的效果

![image-20230907235512928](C:/Users/%E9%87%91%E6%9C%A8/AppData/Roaming/Typora/typora-user-images/image-20230907235512928.png)

当然,使用`Flowable`还有一个很方便的地方,就是我们可以去获取流程实例的**审核数据**和**历史数据**

```Java
//查询历史数据
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).finished().orderByHistoricActivityInstanceEndTime().asc().list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId() + " 花了 " + activity.getDurationInMillis() + " 毫秒");
        }
```

可以看到如下的效果

![image-20230907235555192](C:/Users/%E9%87%91%E6%9C%A8/AppData/Roaming/Typora/typora-user-images/image-20230907235555192.png)



# 4. SpringBoot集成Flowable

> 源码GitHub地址:
>
> ```powershell
> # 项目目录:
> PS D:\JAVA\Project\private\learning\flowable\flowable-springboot\src> tree /F
> 卷 Data 的文件夹 PATH 列表
> 卷序列号为 8632-9174
> D:.
> └─main
>     ├─java
>     │  └─cn
>     │      └─learning
>     │          └─flowable
>     │              └─springboot
>         │
>         ├─processess
>         │      vacationRequest.bpmn20.xml
>         │
>         ├─sql
>         │      flowable.sql
>         │
>         └─templates
>                 list.html
>                 search.html
>                 vacation.html
> ```

## 一、pom中引入Flowable相关框架

具体依赖,父项pom文件依赖管理以及依赖版本管理

```xml
<properties>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <!--         flowable-springboot      -->
    <springboot.version>2.7.14</springboot.version>
    <flowable.springboot.version>6.8.0</flowable.springboot.version>
    <mysql.version>8.0.11</mysql.version>
    <mybatis.version>2.2.2</mybatis.version>
    <lombok.version>1.18.8</lombok.version>
</properties>
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${springboot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>org.flowable</groupId>
            <artifactId>flowable-spring-boot-starter</artifactId>
            <version>${flowable.springboot.version}</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis.version}</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

子项目(即该项目)具体依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- 工作流flowable架包 -->
    <dependency>
        <groupId>org.flowable</groupId>
        <artifactId>flowable-spring-boot-starter</artifactId>
    </dependency>
    <!-- mysql数据库连接架包 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <!-- mybatis ORM 架包 -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
    </dependency>
    <!-- thymeleaf架包 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## 二、相关配置文件

### 1.application.yml配置文件

```yml
server:
  port: 8081
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/flowable?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver

logging:
  level:
    org:
    flowable: debug

# 业务流程设计的表自动创建
flowable:
  database-schema-update: true
  async-executor-activate: false
```

### 2.审批流程xml文件，默认放置在resources下的processess文件夹下

名称为`vacationRequest.bpmn20.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:flowable="http://flowable.org/bpmn"
             typeLanguage="http://www.w3.org/2001/XMLSchema" expressionLanguage="http://www.w3.org/1999/XPath"
             targetNamespace="http://www.activiti.org/processdef">
    <!-- -请假条流程图 -->
    <process id="vacationRequest" name="请假条流程" isExecutable="true">
        <!-- -流程的开始 -->
        <startEvent id="startEvent"/>
        <sequenceFlow sourceRef="startEvent" targetRef="approveTask"/>
        <!-- -流程的节点 -->
        <userTask id="approveTask" name="开始请假" flowable:candidateGroups="managers"/>
        <!-- -流程节点间的线条，上一个节点和下一个节点-->
        <sequenceFlow sourceRef="approveTask" targetRef="decision"/>
        <!-- -排他性网关 -->
        <exclusiveGateway id="decision"/>
        <!-- -同意时 -->
        <sequenceFlow sourceRef="decision" targetRef="holidayApprovedTask">
            <conditionExpression xsi:type="tFormalExpression">
                <![CDATA[${approved}]]>
            </conditionExpression>
        </sequenceFlow>
        <!-- -拒绝时 -->
        <sequenceFlow sourceRef="decision" targetRef="rejectEnd">
            <conditionExpression xsi:type="tFormalExpression">
                <![CDATA[${!approved}]]>
            </conditionExpression>
        </sequenceFlow>
        <!-- -外部服务 -->
         <serviceTask id="externalSystemCall" name="Enter holidays in external system"
                     flowable:class="org.javaboy.flowable02.flowable.Approve"/>
        <sequenceFlow sourceRef="externalSystemCall" targetRef="holidayApprovedTask"/>

        <userTask id="holidayApprovedTask" flowable:assignee="${employee}" name="同意请假"/>
        <sequenceFlow sourceRef="holidayApprovedTask" targetRef="approveEnd"/>

         <serviceTask id="rejectLeave" name="Send out rejection email"
                     flowable:class="org.javaboy.flowable02.flowable.Reject"/>
        <sequenceFlow sourceRef="rejectLeave" targetRef="rejectEnd"/>

        <endEvent id="approveEnd"/>

        <endEvent id="rejectEnd"/>
        <!-- -流程的结束 -->
    </process>
</definitions>
```

### 3. flowable.sql文件

请执行下面语句:smile:

```sql
create database if not exists flowable default character set utf8 collate utf8_bin;
```



## 三、控制层代码块



```java
package cn.learning.flowable.springboot.controller;

import cn.learning.flowable.springboot.pojo.ResponseBean;
import cn.learning.flowable.springboot.pojo.VacationApproveVo;
import cn.learning.flowable.springboot.pojo.VacationRequestVo;
import cn.learning.flowable.springboot.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * {@code @Author: } JiuYou2020
 * <br>
 * {@code @Date: } 2023/9/8
 * <br>
 * {@code @Description: } 请假流程测试
 */
@RequestMapping("vacation")
@RestController
public class VacationController {
    @Autowired
    VacationService vacationService;

    /**
     * 请假条新增页面
     *
     * @return ModelAndView
     */
    @GetMapping("/add")
    public ModelAndView add() {
        return new ModelAndView("vacation");
    }

    /**
     * 请假条审批列表
     *
     * @return ModelAndView
     */
    @GetMapping("/aList")
    public ModelAndView aList() {
        return new ModelAndView("list");
    }

    /**
     * 请假条查询列表
     *
     * @return ModelAndView
     */
    @GetMapping("/sList")
    public ModelAndView sList() {
        return new ModelAndView("search");
    }

    /**
     * 请假请求方法
     *
     * @param vacationRequestVO 请假请求参数
     * @return ModelAndView
     */
    @PostMapping
    public ResponseBean askForLeave(@RequestBody VacationRequestVo vacationRequestVO) {
        return vacationService.askForLeave(vacationRequestVO);
    }

    /**
     * 获取待审批列表
     *
     * @param identity 用户名
     * @return ResponseBean
     */
    @GetMapping("/list")
    public ResponseBean leaveList(String identity) {
        return vacationService.leaveList(identity);
    }

    /**
     * 拒绝或同意请假
     *
     * @param vacationVO 请假请求参数
     * @return ResponseBean
     */
    @PostMapping("/handler")
    public ResponseBean askForLeaveHandler(@RequestBody VacationApproveVo vacationVO) {
        return vacationService.askForLeaveHandler(vacationVO);
    }

    /**
     * 请假查询
     *
     * @param name 请假人
     * @return ResponseBean
     */
    @GetMapping("/search")
    public ResponseBean searchResult(String name) {
        return vacationService.searchResult(name);
    }
}
```

## 四、Service层，请假条新增、审批、查询的业务处理



```java
package cn.learning.flowable.springboot.service;

import cn.learning.flowable.springboot.pojo.ResponseBean;
import cn.learning.flowable.springboot.pojo.VacationApproveVo;
import cn.learning.flowable.springboot.pojo.VacationInfo;
import cn.learning.flowable.springboot.pojo.VacationRequestVo;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * {@code @Author: } JiuYou2020
 * <br>
 * {@code @Date: } 2023/9/8
 * <br>
 * {@code @Description: } 请假流程测试
 */
@Service
public class VacationService {
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @PostConstruct
    public void init() {
        //部署流程
        repositoryService.createDeployment().addClasspathResource("processess/vacationRequest.bpmn20.xml").deploy();
    }

    /**
     * 申请请假
     *
     * @param vacationRequestVO 请假请求参数
     * @return ResponseBean
     */
    @Transactional
    public ResponseBean askForLeave(VacationRequestVo vacationRequestVO) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", vacationRequestVO.getName());
        variables.put("days", vacationRequestVO.getDays());
        variables.put("reason", vacationRequestVO.getReason());
        try {
            //指定业务流程
            runtimeService.startProcessInstanceByKey("vacationRequest", vacationRequestVO.getName(), variables);
            return ResponseBean.ok("已提交请假申请");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBean.error("提交申请失败");
    }

    /**
     * 审批列表
     *
     * @param identity 审批人
     * @return ResponseBean
     */
    public ResponseBean leaveList(String identity) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(identity).list();
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            Map<String, Object> variables = taskService.getVariables(task.getId());
            variables.put("id", task.getId());
            list.add(variables);
        }
        return ResponseBean.ok("加载成功", list);
    }

    /**
     * 操作审批
     *
     * @param vacationVO 审批参数
     * @return ResponseBean
     */
    public ResponseBean askForLeaveHandler(VacationApproveVo vacationVO) {
        try {
            boolean approved = vacationVO.getApprove();
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("approved", approved);
            variables.put("employee", vacationVO.getName());
            Task task = taskService.createTaskQuery().taskId(vacationVO.getTaskId()).singleResult();
            taskService.complete(task.getId(), variables);
            if (approved) {
                //如果是同意，还需要继续走一步
                Task t = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                taskService.complete(t.getId());
            }
            return ResponseBean.ok("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBean.error("操作失败");
    }

    /**
     * 请假列表
     *
     * @param name 请假人
     * @return ResponseBean
     */
    public ResponseBean searchResult(String name) {
        List<VacationInfo> vacationInfos = new ArrayList<>();
        List<HistoricProcessInstance> historicProcessInstances = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(name)
                .finished()
                .orderByProcessInstanceEndTime()
                .desc()
                .list();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            VacationInfo vacationInfo = new VacationInfo();
            Date startTime = historicProcessInstance.getStartTime();
            Date endTime = historicProcessInstance.getEndTime();
            List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(historicProcessInstance.getId())
                    .list();
            for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
                String variableName = historicVariableInstance.getVariableName();
                Object value = historicVariableInstance.getValue();
                if ("reason".equals(variableName)) {
                    vacationInfo.setReason((String) value);
                } else if ("days".equals(variableName)) {
                    vacationInfo.setDays(Integer.parseInt(value.toString()));
                } else if ("approved".equals(variableName)) {
                    vacationInfo.setStatus((Boolean) value);
                } else if ("name".equals(variableName)) {
                    vacationInfo.setName((String) value);
                }
            }
            vacationInfo.setStartTime(startTime);
            vacationInfo.setEndTime(endTime);
            vacationInfos.add(vacationInfo);
        }
        return ResponseBean.ok("ok", vacationInfos);
    }
}
```

## 五、POJO相关类



```java
package cn.learning.flowable.springboot.pojo;

import lombok.Data;
/**
 * 响应类
 * @Date
 */
@Data
public class ResponseBean {
  
    private Integer status;
    
    private String msg;
    
    private Object data;

    public static ResponseBean ok(String msg, Object data) {
        return new ResponseBean(200, msg, data);
    }


    public static ResponseBean ok(String msg) {
        return new ResponseBean(200, msg, null);
    }


    public static ResponseBean error(String msg, Object data) {
        return new ResponseBean(500, msg, data);
    }


    public static ResponseBean error(String msg) {
        return new ResponseBean(500, msg, null);
    }

    private ResponseBean() {
    }

    private ResponseBean(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}



package cn.learning.flowable.springboot.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 请假条DO
 * @Date
 */
@Data
public class VacationInfo {

  private String name;
  
  private Date startTime;
  
  private Date endTime;
  
  private String reason;
  
  private Integer days;
  
  private Boolean status;
}


package cn.learning.flowable.springboot.pojo;

import lombok.Data;

/**
 * 请假条申请
 * @Date
 */
@Data
public class VacationRequestVo {

    private String name;
    
    private Integer days;
    
    private String reason;
}


package cn.learning.flowable.springboot.pojo;

import lombok.Data;

/**
 * 请假条审批
 *
 * @Date 2023/9/8
 */
@Data
public class VacationApproveVo {

    private String taskId;

    private Boolean approve;

    private String name;
}
```

## 六、页面代码，页面文件放在resources的templates文件夹下

### 1.提交请假条申请页面vacation.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>提交请假条申请页面</title>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <!-- Import style -->
    <link rel="stylesheet" href="https://unpkg.com/element-plus/dist/index.css"/>
    <script src="https://unpkg.com/vue@3"></script>
    <!-- Import component library -->
    <script src="//unpkg.com/element-plus"></script>
</head>
<body>
<div id="app">
    <h1>开始一个请假流程</h1>
    <table>
        <tr>
            <td>请输入姓名：</td>
            <td>
                <el-input type="text" v-model="afl.name"/>
            </td>
        </tr>
        <tr>
            <td>请输入请假天数：</td>
            <td>
                <el-input type="text" v-model="afl.days"/>
            </td>
        </tr>
        <tr>
            <td>请输入请假理由：</td>
            <td>
                <el-input type="text" v-model="afl.reason"/>
            </td>
        </tr>
    </table>
    <el-button type="primary" @click="submit">提交请假申请</el-button>
</div>
<script>
    Vue.createApp(
        {
            data() {
                return {
                    afl: {
                        name: 'test',
                        days: 3,
                        reason: '测试'
                    }
                }
            },
            methods: {
                submit() {
                    let _this = this;
                    axios.post('/vacation', this.afl)
                        .then(function (response) {
                            if (response.data.status == 200) {
                                //提交成功
                                _this.$message.success(response.data.msg);
                            } else {
                                //提交失败
                                _this.$message.error(response.data.msg);
                            }
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                }
            }
        }
    ).use(ElementPlus).mount('#app')
</script>
</body>
</html>
```

### 2.审批请假条页面list.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>审批请假条页面</title>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <!-- Import style -->
    <link rel="stylesheet" href="https://unpkg.com/element-plus/dist/index.css"/>
    <script src="https://unpkg.com/vue@3"></script>
    <!-- Import component library -->
    <script src="//unpkg.com/element-plus"></script>
</head>
<body>
<div id="app">
    <div>
        <div>请选择你的身份：</div>
        <div>
            <el-select name="" id="" v-model="identity" @change="initTasks">
                <el-option :value="iden" v-for="(iden,index) in identities" :key="index" :label="iden"></el-option>
            </el-select>
            <el-button type="primary" @click="initTasks">刷新一下</el-button>
        </div>

    </div>
    <el-table border strip :data="tasks">
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="days" label="请假天数"></el-table-column>
        <el-table-column prop="reason" label="请假原因"></el-table-column>
        <el-table-column lable="操作">
            <template #default="scope">
                <el-button type="primary" @click="approveOrReject(scope.row.id,true,scope.row.name)">批准</el-button>
                <el-button type="danger" @click="approveOrReject(scope.row.id,false,scope.row.name)">拒绝</el-button>
            </template>
        </el-table-column>
    </el-table>
</div>
<script>
    Vue.createApp(
        {
            data() {
                return {
                    tasks: [],
                    identities: [
                        'managers'
                    ],
                    identity: ''
                }
            },
            methods: {
                initTasks() {
                    let _this = this;
                    axios.get('/vacation/list?identity=' + this.identity)
                        .then(function (response) {
                            _this.tasks = response.data.data;
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                },
                approveOrReject(taskId, approve,name) {
                    let _this = this;
                    axios.post('/vacation/handler', {taskId: taskId, approve: approve,name:name})
                        .then(function (response) {
                            _this.$message.success("审批成功");
                            _this.initTasks();

                        })
                        .catch(function (error) {
                            _this.$message.error("操作失败");
                            console.log(error);
                        });
                }
            }
        }
    ).use(ElementPlus).mount('#app')
</script>
</body>
</html>
```

### 3.已审批请假条查询页面search.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>已审批请假条查询页面</title>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <!-- Import style -->
    <link rel="stylesheet" href="https://unpkg.com/element-plus/dist/index.css"/>
    <script src="https://unpkg.com/vue@3"></script>
    <!-- Import component library -->
    <script src="//unpkg.com/element-plus"></script>
</head>
<body>
<div id="app">
    <div style="margin-top: 50px">
        <el-input v-model="name" style="width: 300px" placeholder="请输入用户名"></el-input>
        <el-button type="primary" @click="search">查询</el-button>
    </div>

    <div>
        <el-table border strip :data="historyInfos">
            <el-table-column prop="name" label="姓名"></el-table-column>
            <el-table-column prop="startTime" label="提交时间"></el-table-column>
            <el-table-column prop="endTime" label="审批时间"></el-table-column>
            <el-table-column prop="reason" label="事由"></el-table-column>
            <el-table-column prop="days" label="天数"></el-table-column>
            <el-table-column label="状态">
                <template #default="scope">
                    <el-tag type="success" v-if="scope.row.status">已通过</el-tag>
                    <el-tag type="danger" v-else>已拒绝</el-tag>
                </template>
            </el-table-column>
        </el-table>
    </div>
</div>
<script>
    Vue.createApp(
        {
            data() {
                return {
                    historyInfos: [],
                    name: 'zhangsan'
                }
            },
            methods: {
                search() {
                    let _this = this;
                    axios.get('/vacation/search?name=' + this.name)
                        .then(function (response) {
                            if (response.data.status == 200) {
                                _this.historyInfos=response.data.data;
                            } else {
                                _this.$message.error(response.data.msg);
                            }
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                }
            }
        }
    ).use(ElementPlus).mount('#app')
</script>
</body>
</html>
```

## 七、启动并测试

![image-20230908140522244](C:/Users/%E9%87%91%E6%9C%A8/AppData/Roaming/Typora/typora-user-images/image-20230908140522244.png)

### **1.第一次运行，系统会自动创建flowable需要数据表结构**

![image-20230908140815081](C:/Users/%E9%87%91%E6%9C%A8/AppData/Roaming/Typora/typora-user-images/image-20230908140815081.png)

### **2.输入url地址：localhost:8081/vacation/add，建立几个请假条**

![image-20230908140929994](C:/Users/%E9%87%91%E6%9C%A8/AppData/Roaming/Typora/typora-user-images/image-20230908140929994.png)

### 3.请假条建立好了，审批处理一下

![image-20230908141001778](C:/Users/%E9%87%91%E6%9C%A8/AppData/Roaming/Typora/typora-user-images/image-20230908141001778.png)

**注意：第一次运行这个demo，权限暂且不管，角色也先写死，先把demo跑起来再说。四个请假条两个通过，两个拒绝，操作完成后，在待审批列表不在出现**

### **4.作为请假人，查询一下自己提交的假条审批了.**

![image-20230908141045975](C:/Users/%E9%87%91%E6%9C%A8/AppData/Roaming/Typora/typora-user-images/image-20230908141045975.png)

 **通过查询结果得知，两个通过，两个拒绝。至此，一个简单的请假条审批流程走完了！！！**

## **八、API梳理说明**

### 1.FormService

表单数据的管理； **是可选服务**，也就是说Flowable没有它也能很好地运行，而不必牺牲任何功能。这个服务引入了开始表单(start form)与任务表单(task form)的概念。 开始表单是在流程实例启动前显示的表单，

而任务表单是用户完成任务时显示的表单。Flowable可以在BPMN 2.0流程定义中定义这些表单。表单服务通过简单的方式暴露这些数据。再次重申，表单不一定要嵌入流程定义，因此这个服务是可选的

```java
formService.getStartFormKey() // 获取表单key
formService.getRenderedStartForm()  // 查询表单json（无数据）
```

### 2.RepositiryService

提供了在**编辑和发布审批流程**的api。主要是模型管理和流程定义的业务api

这个服务**提供了管理与控制部署(deployments)与流程定义(process definitions)的操作**

查询引擎现有的部署与流程定义。 暂停或激活部署中的某些流程，或整个部署。暂停意味着不能再对它进行操作，激活刚好相反，重新使它可以操作。 获取各种资源，比如部署中保存的文件，

或者引擎自动生成的流程图。 获取POJO版本的流程定义。它可以用Java而不是XML的方式查看流程。

```java
1.提供了带条件的查询模型流程定义的api
repositoryService.createXXXQuery()
例如：
repositoryService.createModelQuery().list() 模型查询 
repositoryService.createProcessDefinitionQuery().list() 流程定义查询

repositoryService.createXXXXQuery().XXXKey(XXX) （查询该key是否存在）

2.提供一大波模型与流程定义的通用方法
模型相关
repositoryService.getModel()  （获取模型）
repositoryService.saveModel()  （保存模型）
repositoryService.deleteModel() （删除模型）
repositoryService.createDeployment().deploy(); （部署模型）
repositoryService.getModelEditorSource()  （获得模型JSON数据的UTF8字符串）
repositoryService.getModelEditorSourceExtra()  （获取PNG格式图像）

3.流程定义相关
repositoryService.getProcessDefinition(ProcessDefinitionId);  获取流程定义具体信息
repositoryService.activateProcessDefinitionById() 激活流程定义
repositoryService.suspendProcessDefinitionById()  挂起流程定义
repositoryService.deleteDeployment()  删除流程定义
repositoryService.getProcessDiagram()获取流程定义图片流
repositoryService.getResourceAsStream()获取流程定义xml流
repositoryService.getBpmnModel(pde.getId()) 获取bpmn对象（当前进行到的那个节点的流程图使用）

4.流程定义授权相关
repositoryService.getIdentityLinksForProcessDefinition() 流程定义授权列表
repositoryService.addCandidateStarterGroup()新增组流程授权
repositoryService.addCandidateStarterUser()新增用户流程授权
repositoryService.deleteCandidateStarterGroup() 删除组流程授权
repositoryService.deleteCandidateStarterUser()  删除用户流程授权
```

### 3.RuntimeService

处理正在运行的流程

```java
runtimeService.createProcessInstanceBuilder().start() 发起流程
runtimeService.deleteProcessInstance() 删除正在运行的流程
runtimeService.suspendProcessInstanceById() 挂起流程定义
runtimeService.activateProcessInstanceById() 激活流程实例
runtimeService.getVariables(processInstanceId); 获取表单中填写的值
runtimeService.getActiveActivityIds(processInstanceId)获取以进行的流程图节点 （当前进行到的那个节点的流程图使用）
runtimeService.createChangeActivityStateBuilder().moveExecutionsToSingleActivityId(executionIds, endId).changeState(); 终止流程
```

### 4.HistoryService

在用户发起审批后，会生成流程实例。historyService为处理流程实例的api，但是其中包括了已完成的和未完成的流程实例； 如果是处理正在运行的流程实例，请使用runtimeService；

**暴露Flowable引擎收集的所有历史数据。**当执行流程时，引擎会保存许多数据（可配置），例如流程实例启动时间、谁在执行哪个任务、完成任务花费的事件、每个流程实例的执行路径，等等。**这个服务主要提供查询这些数据的能力**

```java
historyService.createHistoricProcessInstanceQuery().list() 查询流程实例列表（历史流程,包括未完成的）
historyService.createHistoricProcessInstanceQuery().list().foreach().getValue() 可以获取历史中表单的信息
historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult(); 根绝id查询流程实例
historyService.deleteHistoricProcessInstance() 删除历史流程
historyService.deleteHistoricTaskInstance(taskid); 删除任务实例
historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list()  流程实例节点列表 （当前进行到的那个节点的流程图使用）
    
flowable 有api查看act_hi_varinst里面的数据吗
HistoricVariableInstanceQuery query = historyService.createHistoricVariableInstanceQuery().processInstanceId(instance.getId());
HistoricVariableInstance operate = query.variableName("operate").singleResult();
if (operate.getValue().toString().equals("1")) { 　　　　　　　　　　　　
    // 表示流程同意　　　　　　　　　　
}
```

### 5.TaskService

对流程实例的各个节点的审批处理

流转的节点审批

```java
taskService.createTaskQuery().list() 待办任务列表
taskService.createTaskQuery().taskId(taskId).singleResult();  待办任务详情
taskService.saveTask(task); 修改任务
taskService.setAssignee() 设置审批人
taskService.addComment() 设置审批备注
taskService.complete() 完成当前审批
taskService.getProcessInstanceComments(processInstanceId); 查看任务详情（也就是都经过哪些人的审批，意见是什么）
taskService.delegateTask(taskId, delegater); 委派任务
taskService.claim(taskId, userId);认领任务
taskService.unclaim(taskId); 取消认领
taskService.complete(taskId, completeVariables); 完成任务

任务授权
taskService.addGroupIdentityLink()新增组任务授权
taskService.addUserIdentityLink() 新增人员任务授权
taskService.deleteGroupIdentityLink() 删除组任务授权
taskService.deleteUserIdentityLink() 删除人员任务授权
```

### 6.ManagementService



```java
主要是执行自定义命令

managementService.executeCommand(new classA())  执行classA的内部方法 

在自定义的方法中可以使用以下方法获取repositoryService

ProcessEngineConfiguration processEngineConfiguration =
            CommandContextUtil.getProcessEngineConfiguration(commandContext);
RepositoryService repositoryService = processEngineConfiguration.getRepositoryService();

也可以获得流程定义方法集合

ProcessEngineConfigurationImpl processEngineConfiguration =
            CommandContextUtil.getProcessEngineConfiguration(commandContext);
        ProcessDefinitionEntityManager processDefinitionEntityManager =
            processEngineConfiguration.getProcessDefinitionEntityManager();
如 findById/findLatestProcessDefinitionByKey/findLatestProcessDefinitionByKeyAndTenantId 等。
```

### 7.IdentityService

**用于身份信息获取和保存，这里主要是获取身份信息**

用于管理（创建，更新，删除，查询……）组与用户。请注意，Flowable实际上在运行时并不做任何用户检查。

例如任务可以分派给任何用户，而引擎并不会验证系统中是否存在该用户。这是因为Flowable有时要与LDAP、Active Directory等服务结合使用

```java
identityService.createUserQuery().userId(userId).singleResult();  获取审批用户的具体信息
identityService.createGroupQuery().groupId(groupId).singleResult(); 获取审批组的具体信息
```

### 8.DynamicBpmnService

可用于修改流程定义中的部分内容，而不需要重新部署它。例如可以修改流程定义中一个用户任务的办理人设置，或者修改一个服务任务中的类名。

## 九、数据库表说明(34张表)

| **表分类**      | **表名**              | **表说明**                   |
| --------------- | --------------------- | ---------------------------- |
| 一般数据(2)     | ACT_GE_BYTEARRAY      | 通用的流程定义和流程资源     |
|                 | ACT_GE_PROPERTY       | 系统相关属性                 |
| 流程历史记录(8) | ACT_HI_ACTINST        | 历史的流程实例               |
|                 | ACT_HI_ATTACHMENT     | 历史的流程附件               |
|                 | ACT_HI_COMMENT        | 历史的说明性信息             |
|                 | ACT_HI_DETAIL         | 历史的流程运行中的细节信息   |
|                 | ACT_HI_IDENTITYLINK   | 历史的流程运行过程中用户关系 |
|                 | ACT_HI_PROCINST       | 历史的流程实例               |
|                 | ACT_HI_TASKINST       | 历史的任务实例               |
|                 | ACT_HI_VARINST        | 历史的流程运行中的变量信息   |
| 用户用户组表(9) | ACT_ID_BYTEARRAY      | 二进制数据表                 |
|                 | ACT_ID_GROUP          | 用户组信息表                 |
|                 | ACT_ID_INFO           | 用户信息详情表               |
|                 | ACT_ID_MEMBERSHIP     | 人与组关系表                 |
|                 | ACT_ID_PRIV           | 权限表                       |
|                 | ACT_ID_PRIV_MAPPING   | 用户或组权限关系表           |
|                 | ACT_ID_PROPERTY       | 属性表                       |
|                 | ACT_ID_TOKEN          | 系统登录日志表               |
|                 | ACT_ID_USER           | 用户表                       |
| 流程定义表(3)   | ACT_RE_MODEL          | 模型信息                     |
|                 | ACT_RE_DEPLOYMENT     | 部署单元信息                 |
|                 | ACT_RE_PROCDEF        | 已部署的流程定义             |
| 运行实例表(10)  | ACT_RU_DEADLETTER_JOB | 正在运行的任务表             |
|                 | ACT_RU_EVENT_SUBSCR   | 运行时事件                   |
|                 | ACT_RU_EXECUTION      | 运行时流程执行实例           |
|                 | ACT_RU_HISTORY_JOB    | 历史作业表                   |
|                 | ACT_RU_IDENTITYLINK   | 运行时用户关系信息           |
|                 | ACT_RU_JOB            | 运行时作业表                 |
|                 | ACT_RU_SUSPENDED_JOB  | 暂停作业表                   |
|                 | ACT_RU_TASK           | 运行时任务表                 |
|                 | ACT_RU_TIMER_JOB      | 定时作业表                   |
|                 | ACT_RU_VARIABLE       | 运行时变量表                 |
| 其他表(2)       | ACT_EVT_LOG           | 事件日志表                   |
|                 | ACT_PROCDEF_INFO      | 流程定义信息                 |
|                 |                       |                              |







参考:

[1].[官方文档](https://www.flowable.com/open-source/docs/bpmn/ch02-GettingStarted)

[2].[遇见你真好的博客](https://www.cnblogs.com/songweipeng/p/17119106.html)