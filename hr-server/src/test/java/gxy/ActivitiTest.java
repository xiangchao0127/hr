package gxy;


import com.handge.hr.manage.activiti.ActivitiServer;
import com.handge.hr.server.Application;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ActivitiTest {

    @Autowired
    ActivitiServer activitiServer;
    @Autowired
    ProcessEngine processEngine;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;

    /** 测试生成Activiti的表结构
     */
    @Test
    public void createTable() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        ProcessEngine engine = configuration.buildProcessEngine();
    }

//    @Rule
//    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"processes/my-process.bpmn20.xml"})
    public void test() {
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("my-process");
        assertNotNull(processInstance);

        List<Task> task = processEngine.getTaskService().createTaskQuery().list();
        assertEquals("Activiti is awesome!", task.get(0).getName());
    }

    @Test
    public void taskProcess() {
        processEngine.getRepositoryService().createDeployment().addClasspathResource("com/handge/hr/manage/template/taskProcess1.bpmn20.xml").deploy();

        Map<String, Object> variables1 = new HashMap<>();
        variables1.put("userID", "bumen"); // 设置分配任务task的受理人变量
        variables1.put("receiverID", "e1"); // 设置确认接收/完成任务task的受理人变量
        variables1.put("QCID", "QCLeader"); // 设置QC分配task的受理人变量
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("taskProcess1", "task1", variables1);
        String processId = processInstance.getId();
        System.out.println("流程创建成功，当前流程实例ID："+processId);

        //分配任务
        List<Task> list = taskService.createTaskQuery().taskAssignee("bumen").processInstanceId(processId).list();
        printTask(list);
        taskService.complete(list.get(0).getId());
        System.out.println("分配任务已完成，此时流程已流转到确认接收\n");

        //确认接收
        List<Task> list2 = taskService.createTaskQuery().taskAssignee("e1").processInstanceId(processId).list();
        printTask(list2);
        taskService.complete(list2.get(0).getId());
        System.out.println("确认接受已完成，此时流程已流转到完成任务/QC分配\n");

        //QC分配
//        List<Task> list3 = taskService.createTaskQuery().taskAssignee("QCLeader").processInstanceId(processId).list();
//        printTask(list3);
//        Map<String, Object> variables2 = new HashMap<>();
//        variables2.put("qcID", "qc1"); // 设置QC评价task的受理人变量
//        variables2.put("hasQC", 1); // 设置是否有QC
//        taskService.complete(list3.get(0).getId(), variables2);
//        System.out.println("QC分配已完成，此时流程已流转到是否有QC\n");

        //完成任务
        List<Task> list4 = taskService.createTaskQuery().taskAssignee("e1").processInstanceId(processId).list();
        printTask(list4);
        Map<String, Object> variables3 = new HashMap<>();
        variables3.put("isFinished", 1); // 设置是否已完成
        variables3.put("isFirst", 1); // 设置是否已完成
        taskService.complete(list4.get(0).getId(), variables3);
        System.out.println("完成任务已完成，此时流程已流转到是否有QC\n");

        //QC评价
//        List<Task> list5 = taskService.createTaskQuery().taskAssignee("qc1").processInstanceId(processId).list();
//        printTask(list5);
//        Map<String, Object> variables4 = new HashMap<>();
//        variables4.put("isPassed", 0); // 设置是否已通过
//        taskService.complete(list5.get(0).getId(), variables4);
//        System.out.println("QC评价已完成，此时流程已流转到是否通过\n");
//
//        //QC不通过再次完成任务
//        List<Task> list41 = taskService.createTaskQuery().taskAssignee("e1").processInstanceId(processId).list();
//        printTask(list41);
//        Map<String, Object> variables31 = new HashMap<>();
//        variables31.put("isFinished", 1); // 设置是否已完成
//        variables31.put("isFirst", 0); // 设置是否已完成
//        taskService.complete(list41.get(0).getId(), variables31);
//        System.out.println("完成任务已完成，此时流程已流转到是否有QC\n");

//        //QC再评价
//        List<Task> list51 = taskService.createTaskQuery().taskAssignee("qc1").processInstanceId(processId).list();
//        printTask(list51);
//        Map<String, Object> variables41 = new HashMap<>();
//        variables41.put("isPassed", 1); // 设置是否已通过
//        taskService.complete(list51.get(0).getId(), variables41);
//        System.out.println("QC评价已完成，此时流程已流转到是否通过\n");
//
//        //任务评价
//        List<Task> list6 = taskService.createTaskQuery().taskAssignee("bumen").processInstanceId(processId).list();
//        printTask(list6);
//        taskService.complete(list6.get(0).getId());
//        System.out.println("任务评价已完成，整个流程完成");
    }

    public String complete(String businessKey, String assignee, HashMap<String, Object> var) {
        String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult().getProcessInstanceId();
        List<Task> list41 = taskService.createTaskQuery().taskAssignee(assignee).processInstanceId(processInstanceId).list();
        printTask(list41);

        taskService.complete(list41.get(0).getId(), var);

        return list41.get(0).getName();
    }

    @Test
    //修改任务受理人
    public void modifyReceiver() {
        String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey("task1").singleResult().getProcessInstanceId();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskName("确认接收").singleResult();
        if (task == null) {
            task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskName("完成任务").singleResult();
        }
        task.setAssignee("newAssignee");
        taskService.saveTask(task);
    }

    @Test
    //确认接收之后，修改QC受理人
    public void modifyQC() {
        String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey("task1").singleResult().getProcessInstanceId();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskName("QC评价").singleResult();
        if (task != null) {
            task.setAssignee("newQC");
            taskService.saveTask(task);
        } else {
            runtimeService.setVariable(processInstanceId, "qcID", "newQC");
        }

    }

    @Test
    //在未确认接收时，取消流程
    public void cancelProcess() {
        String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey("task1").singleResult().getProcessInstanceId();
        List<Execution> list = runtimeService.createExecutionQuery().signalEventSubscriptionName("取消流程").list();
        list.forEach(execution -> {
            if (execution.getProcessInstanceId().equals(processInstanceId)) {
                runtimeService.signalEventReceived("取消流程", execution.getId());
                System.out.println("流程" + processInstanceId + "已取消！！！");
            }
        });

    }

    @Test
    //触发qc分配
    public void qcFenPei() {
        String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey("task1").singleResult().getProcessInstanceId();
        List<Execution> list = runtimeService.createExecutionQuery().signalEventSubscriptionName("触发qc分配").list();
        list.forEach(execution -> {
            if (execution.getProcessInstanceId().equals(processInstanceId)) {
                runtimeService.signalEventReceived("触发qc分配", execution.getId());
                System.out.println("触发qc分配已触发！！！");
            }
        });

    }

    public void printTask(List<Task> list){
        if(list != null && list.size() > 0){
            for(org.activiti.engine.task.Task task:list){
                System.out.println("任务ID："+task.getId());
                System.out.println("任务的办理人："+task.getAssignee());
                System.out.println("任务名称："+task.getName());
                System.out.println("任务的创建时间："+task.getCreateTime());
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                System.out.println("##########################################");
            }
        }
    }


    @Test
    public void complete() {
//        taskService.setVariable("222528", "isPassed", "1");
        taskService.complete("245052");
    }
}
