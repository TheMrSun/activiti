package activiti;


import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.util.List;

/**
 * @author Slience
 * @version 1.0
 */
public class LeaveActivitiDemo {

    /**
     * 流程部署
     */
    @Test
    public void testDeployment() {
        //创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //将bpmn流程转化到数据库
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/leave.bpmn20.xml")
                .addClasspathResource("bpmn/leave.png")
                .name("请假流程申请")
                .deploy();
        System.out.println("流程部署id" + deployment.getId());
        System.out.println("流程部署name" + deployment.getName());

    }

    /**
     * 启动流程实例
     *
     * @throws Exception
     */
    @Test
    public void testStartProcess() throws Exception {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        //此处可以增加businessKey
        ProcessInstance leave = runtimeService.startProcessInstanceByKey("leave");

        System.out.println("流程id定义:" + leave.getProcessDefinitionId());
        System.out.println("流程实例id:" + leave.getId());
        System.out.println("当前活动id:" + leave.getActivityId());

    }
//    流程id定义:leave:1:4
//    流程实例id:2501
//    当前活动id:null

    /**
     * 查询当前个人待执行的任务
     */
    @Test
    public void testFindPersonTaskList() {
        //String assignee = "worker";
        String assignee = "manage";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        TaskService taskService = processEngine.getTaskService();

        List<Task> list = taskService.createTaskQuery().processDefinitionKey("leave")
                .taskAssignee(assignee)
                .list();
        for (Task task : list) {
            System.out.println("流程id定义" + task.getProcessDefinitionId());
            System.out.println("流程实例id" + task.getProcessInstanceId());
            System.out.println("任务id" + task.getId());
            System.out.println("任务负责人" + task.getAssignee());
            System.out.println("任务名称" + task.getName());
        }
    }
//    流程id定义leave:1:4
//    流程实例id2501
//    任务id2505
//    任务负责人worker
//    任务名称提交请假申请

//    流程id定义leave:1:4
//    流程实例id2501
//    任务id5002
//    任务负责人manage
//    任务名称部门经理审批

    /**
     * 完成任务
     */
    @Test
    public void completeTask() {
        String assignee = "worker";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        TaskService taskService = processEngine.getTaskService();

        Task task = taskService.createTaskQuery().processDefinitionKey("leave")
                .taskAssignee(assignee).singleResult();

        taskService.complete(task.getId());
    }

    /**
     * 查询定义流程
     */
    @Test
    public void queryProcessDefinition() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        List<ProcessDefinition> definitionList = processDefinitionQuery.processDefinitionKey("leave")
                .orderByProcessDefinitionVersion()
                .desc()
                .list();

        for (ProcessDefinition processDefinition : definitionList) {
            System.out.println("流程定义id=" + processDefinition.getId());
            System.out.println("流程定义name=" + processDefinition.getName());
            System.out.println("流程定义key=" + processDefinition.getKey());
            System.out.println("流程定义version=" + processDefinition.getVersion());
            System.out.println("流程部署id=" + processDefinition.getDeploymentId());
        }
    }

//    流程定义id=leave:1:4
//    流程定义name=leave
//    流程定义key=leave
//    流程定义version=1
//    流程部署id=1

    /**
     * 当前流程中运行实例
     */
    @Test
    public void queryProcessInstance() {
        String processDefinitionKey = "leave";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey)
                .list();
        for (ProcessInstance processInstance : list) {
            System.out.println("流程实例id=" + processInstance.getProcessInstanceId());
            System.out.println("所属流程定义id=" + processInstance.getProcessDefinitionId());
            System.out.println("是否执行完成=" + processInstance.isEnded());
            System.out.println("是否暂停=" + processInstance.isSuspended());
            System.out.println("当前活动标识=" + processInstance.getActivityId());
            System.out.println("业务关键字=" + processInstance.getBusinessKey());
        }
    }

//    流程实例id=2501
//    所属流程定义id=leave:1:4
//    是否执行完成=false
//    是否暂停=false
//    当前活动标识=null
//    业务关键字=null

    /**
     * 删除流程部署
     */
    @Test
    public void deleteDeployment(){
        String deploymentId = "1";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        repositoryService.deleteDeployment(deploymentId);
        //有true 删除部署及其实例
        //repositoryService.deleteDeployment(deploymentId,true);
    }

    /**
     * 查询bpmn信息
     */
    @Test
    public void queryBpmnFile() throws IOException {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave").singleResult();

        String deploymentId = processDefinition.getDeploymentId();

        InputStream pngInput = repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());

        InputStream bpmnInput = repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());

        File file_png = new File("D:\\Test_work\\Activiti\\activiti-demo\\BasicDemo\\src\\main\\resources\\getResource\\leave.png");
        File file_bpmn = new File("D:\\Test_work\\Activiti\\activiti-demo\\BasicDemo\\src\\main\\resources\\getResource\\leave.bpmn20.xml");
        FileOutputStream pngOutput = new FileOutputStream(file_png);
        FileOutputStream bpmnOutput = new FileOutputStream(file_bpmn);

        IOUtils.copy(pngInput,pngOutput);
        IOUtils.copy(bpmnInput,bpmnOutput);

        pngOutput.close();
        bpmnOutput.close();

    }

    /**
     * 查询历史信息
     */
    @Test
    public void findHistoryInfo(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();

        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();

        instanceQuery.processInstanceId("2501");
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();

        List<HistoricActivityInstance> list = instanceQuery.list();

        for(HistoricActivityInstance hi : list){
            System.out.println(hi.getActivityId());
            System.out.println(hi.getActivityName());
            System.out.println(hi.getProcessDefinitionId());
            System.out.println(hi.getProcessInstanceId());
        }


    }




}
