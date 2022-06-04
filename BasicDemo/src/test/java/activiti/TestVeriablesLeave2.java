package activiti;

import activiti.leave2.Evection;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Slience
 * @version 1.0
 */
public class TestVeriablesLeave2 {

    /**
     * leave2 增加判断条件，部署流程测试
     */
    @Test
    public void testDeployments() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deploy = repositoryService.createDeployment()
                .name("出差流程申请")
                .addClasspathResource("bpmn/leave2.bpmn20.xml")
                .addClasspathResource("bpmn/leave2.png")
                .deploy();

        System.out.println("部署流程id=" + deploy.getId());
        System.out.println("流程部署name=" + deploy.getName());
    }

    /**
     * 删除流程部署
     */
    @Test
    public void deleteDeployment() {
        String deploymentId = "10001";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        repositoryService.deleteDeployment(deploymentId, true);
        //有true 删除部署及其实例
        //repositoryService.deleteDeployment(deploymentId,true);
    }

    /**
     * 启动流程实例 - 2d
     *
     * @throws Exception
     */
    @Test
    public void testStartProcess() throws Exception {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        String key = "leave2";

        Map<String, Object> variables = new HashMap<String, Object>();

        Evection evection = new Evection();

        evection.setNum(2d);

        variables.put("evection", evection);
        variables.put("assignee0", "李员工");
        variables.put("assignee1", "王经理");
        variables.put("assignee2", "杨总");
        variables.put("assignee3", "张财务");

        ProcessInstance leave2 = runtimeService.startProcessInstanceByKey(key, variables);

        System.out.println("流程id定义:" + leave2.getProcessDefinitionId());
        System.out.println("流程实例id:" + leave2.getId());
        System.out.println("当前活动id:" + leave2.getActivityId());

    }
//    流程id定义:leave2:1:12504
//    流程实例id:15001
//    当前活动id:null

    /**
     * 启动第二个实例 3d
     *
     * @throws Exception
     */
    @Test
    public void testStartProcess2() throws Exception {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        String key = "leave2";

        Map<String, Object> variables = new HashMap<String, Object>();

        Evection evection = new Evection();

        evection.setNum(3d);

        variables.put("evection", evection);
        variables.put("assignee0", "李员工1");
        variables.put("assignee1", "王经理1");
        variables.put("assignee2", "杨总1");
        variables.put("assignee3", "张财务1");

        ProcessInstance leave2 = runtimeService.startProcessInstanceByKey(key, variables);

        System.out.println("流程id定义:" + leave2.getProcessDefinitionId());
        System.out.println("流程实例id:" + leave2.getId());
        System.out.println("当前活动id:" + leave2.getActivityId());

    }
//    流程id定义:leave2:1:12504
//    流程实例id:17501
//    当前活动id:null

    /**
     * 查询任务
     */
    @Test
    public void queryTask() {

        String key = "leave2";

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        List<Task> list = taskService.createTaskQuery().processDefinitionKey(key).list();

        for (Task task : list) {

            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getAssignee());

        }
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask() {
        String assignee = "杨总1";

        Map<String,Object> map = new HashMap<>();
        map.put("assignee3","roy");

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        TaskService taskService = processEngine.getTaskService();

        Task task = taskService.createTaskQuery().processDefinitionKey("leave2")
                .taskAssignee(assignee).singleResult();

        taskService.complete(task.getId(),map);
    }



}
