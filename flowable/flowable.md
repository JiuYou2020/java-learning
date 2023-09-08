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
                     flowable:class="cn.learning.flowable.quickstart.CallExternalSystemDelegate"/>
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
                     flowable:class="cn.learning.flowable.quickstart.CallExternalSystemDelegate"/>
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