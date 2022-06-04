package com.activiti.springbootactiviti;

import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * @author Slience
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootActivitiDemo {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;

    @Autowired
    private DataSource dataSource;



    @Test
    public void testStartProcess(){
        System.out.println(dataSource);
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave");
//        System.out.println("流程定义id=" + processInstance.getProcessDefinitionId());
//        System.out.println("流程实例id=" + processInstance.getId());
//        System.out.println("流程活动id=" + processInstance.getActivityId());
    }

}
