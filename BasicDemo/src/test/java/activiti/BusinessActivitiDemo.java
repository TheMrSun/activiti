package activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * @author Slience
 * @version 1.0
 */
public class BusinessActivitiDemo {

    /**
     * 启动流程实例,并带有业务id
     *
     * @throws Exception
     */
    @Test
    public void testStartProcess() throws Exception {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        //此处可以增加businessKey
        ProcessInstance leave = runtimeService.startProcessInstanceByKey("leave","1001");

        System.out.println("流程id定义:" + leave.getProcessDefinitionId());
        System.out.println("流程实例id:" + leave.getId());
        System.out.println("当前活动id:" + leave.getActivityId());
        System.out.println("流程业务id=" + leave.getBusinessKey());

    }

    /**
     * 挂起所有实例
     */
    @Test
    public void suspendAllProcessInstances(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("leave")
                .singleResult();

        boolean suspended = processDefinition.isSuspended();

        String id = processDefinition.getId();

        if(suspended){
            repositoryService.activateProcessDefinitionById(id,true,null);
            System.out.println("流程id=" + id + "已经激活");
        } else{
            repositoryService.suspendProcessDefinitionById(id,true,null);
            System.out.println("流程id" + id + "已经被挂起");
        }

    }

    /**
     * 挂起单个实例
     */
    @Test
    public void suspendSingleProcessInstance(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId("2501").singleResult();

        boolean suspended = processInstance.isSuspended();
        String id = processInstance.getId();

        if(suspended){
            runtimeService.activateProcessInstanceById(id);
            System.out.println("流程实例id" + id + "已经激活");
        }else{
            runtimeService.suspendProcessInstanceById(id);
            System.out.println("流程实例id" + id + "已经被挂起");
        }

    }
}
