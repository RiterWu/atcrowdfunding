package junit.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestActivity {

    ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");

    ProcessEngine processEngine = (ProcessEngine)ioc.getBean("processEngine");

    // 1.创建流程引擎，创建23张表
    @Test
    public void createDatabase(){

        System.out.println("ProcessEngine = "+processEngine);

    }

    // 2.部署流程定义
    @Test
    public void deploy(){

        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deploy = repositoryService.createDeployment().addClasspathResource("myProcess1.bpmn").deploy();

        System.out.println("deploy = "+deploy);
    }

    // 3.查询部署流程定义
    @Test
    public void queryDeploy(){

        RepositoryService repositoryService = processEngine.getRepositoryService();

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        List<ProcessDefinition> processDefinitions = processDefinitionQuery.list(); // 查询所有流程定义

        System.out.println("================================================");
        for (ProcessDefinition p : processDefinitions) {
            System.out.println("id = " + p.getId());
            System.out.println("key = " + p.getKey());
            System.out.println("name = " + p.getName());
            System.out.println("version = " + p.getVersion());
        }
        System.out.println("================================================");

        long count = processDefinitionQuery.count();    // 流程定义的数量
        System.out.println("count = " + count);

    }

    // 3启动流程实例
    @Test
    public void startProcessInstance() {
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        System.out.println("processInstance = " + processInstance);

    }

}
