package workflowTest;

import com.handge.hr.domain.entity.manage.web.request.workflow.*;
import com.handge.hr.manage.service.api.workflow.IProject;
import com.handge.hr.manage.service.api.workflow.ITask;
import com.handge.hr.server.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaJianfu on 2018/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class WorkflowTest {

    @Autowired
    IProject project;
    @Autowired
    ITask task;

    @Test
    public void addProjectTest() {
        ArrayList<AddProjectParam> list = new ArrayList<>();
        AddProjectParam param = new AddProjectParam();
        param.setName("hr智能系统");
        param.setType("A");
        param.setPrincipal("16723fbdbca441d49828f589a791924e");
        param.setPlanStartTime("2018-01-17 18:00:12");
        param.setPlanEndTime("2018-08-20 18:00:12");
        param.setRemark("好项目");
        param.setDescription("这是个好项目");
        list.add(param);
        project.addProject(list);
    }

    @Test
    public void modifyProjectTest() {
        AddProjectParam param = new AddProjectParam();
        param.setId("f0c3b6f8df474d01b479b8d2e99e029f");
        param.setName("智能1号系统");
        param.setPlanEndTime("2018-09-20 18:00:12");
        param.setPlanStartTime("2018-01-17 18:00:12");
        param.setDescription("这真是个好项目");
        project.modifyProject(param);
    }

    @Test
    public void receivedProjectTest() {
        List<ReceivedProjectParam> list = new ArrayList<>();
        ReceivedProjectParam param = new ReceivedProjectParam();
        param.setId("4d03f16d3d454a13a752214fc546aae5");
        list.add(param);
        project.receivedProject(list);
    }

    @Test
    public void terminatedProjectTest(){
        List<TerminatedProjectParam> list=new ArrayList<>();
        TerminatedProjectParam param=new TerminatedProjectParam();
        param.setId("f0c3b6f8df474d01b479b8d2e99e029f");
        param.setReason("不想要了");
        list.add(param);
        project.terminatedProject(list);
    }
    @Test
    public void deleteProjectTest() {
        DeleteProjectParam param = new DeleteProjectParam();
        List<String> list = new ArrayList<>();
        list.add("43e80d62506b45438f28a4bbfbf84721");
        list.add("a886874bb77146148dd737ca3df41fd2");
        param.setIds(list);
        project.deleteProject(param);
    }

    @Test
    public void addTaskTest() {
        List<AddTaskParam> list = new ArrayList<>();
        AddTaskParam param = new AddTaskParam();
        param.setProjectId("f0c3b6f8df474d01b479b8d2e99e029f");
        param.setParentTaskId("11e3240f7f804251b02f96228b69bb09");
        param.setType("B");
        param.setTaskName("二级任务01");
        param.setContent("111101");
        param.setReceiver("76e185c955c441a4af3a359fa85ec4b5");
        //param.setDepartmentId("3bb21abc91b14ac3bf8aab3b920e4d36");
        param.setPlanEndTime("2018-10-20 18:00:12");
        param.setPlanStartTime("2018-02-17 18:00:12");
        param.setDifficult("2");
        param.setUrgency("3");
        param.setWorkload("3");
        list.add(param);
        AddTaskParam param1 = new AddTaskParam();
        param1.setProjectId("f0c3b6f8df474d01b479b8d2e99e029f");
        param1.setParentTaskId("11e3240f7f804251b02f96228b69bb09");
        param1.setType("B");
        param1.setTaskName("二级任务02");
        param1.setContent("22202");
        param1.setReceiver("b408ea4d438448e58a4ebf62a26ce05d");
        //param.setDepartmentId("0a0535d9d5374c98a6182b85e026c5d7");
        param1.setPlanEndTime("2018-10-20 18:00:12");
        param1.setPlanStartTime("2018-02-17 18:00:12");
        param1.setDifficult("2");
        param1.setUrgency("3");
        param1.setWorkload("3");
        list.add(param1);
        task.addTask(list);

    }

    @Test
    public void receiveTaskTest() {
        List<ReceiveTaskParam> list = new ArrayList<>();
        ReceiveTaskParam param = new ReceiveTaskParam();
        param.setTaskId("59d7f4ac69c84236ac81db7cd748c429");
        list.add(param);
        ReceiveTaskParam param1 = new ReceiveTaskParam();
        param1.setTaskId("b021981a182b41a288f6bc1662490e4a");
        list.add(param1);
        task.receiveTask(list);
    }

    @Test
    public void QCDistributionTest() {
        List<QcDistributionParam> list = new ArrayList<>();
        QcDistributionParam param = new QcDistributionParam();
        param.setId("afbeb927ca51439191aef1aa36447e2a");
        param.setQcPersonId("d185f1cc517f4a2bb76a5f2760850ab7");
        list.add(param);
       /* QcDistributionParam param1=new QcDistributionParam();
        param1.setTaskId("c5f6f1a4f0264078bd5a0eb78063c2fa");
        param1.setIsQC("0");
        list.add(param1);*/
        task.taskQcDistribution(list);
    }

    @Test
    public void submitTaskTest() {
        List<SubmitTaskParam> list = new ArrayList<>();
        SubmitTaskParam param = new SubmitTaskParam();
        param.setTaskId("afbeb927ca51439191aef1aa36447e2a");
        list.add(param);
        task.submitTask(list);
    }

    @Test
    public void QCEvaluateTest() {
        QcEvaluateParam param = new QcEvaluateParam();
        param.setTaskId("afbeb927ca51439191aef1aa36447e2a");
        param.setIsPassed("1");
        task.qcEvaluate(param);
    }

    @Test
    public void TaskEvaluateTest() {
        TaskEvaluateParam param = new TaskEvaluateParam();
        param.setTaskId("c5f6f1a4f0264078bd5a0eb78063c2fa");
        task.taskEvaluate(param);
    }

    @Test
    public void deleteTest() {
        DeleteTaskParam param = new DeleteTaskParam();
        List<String> list = new ArrayList<>();
        list.add("61b2f4d787e746ca955b5bb05cb4bc1d");
        list.add("a71738f11f6b4203a8d72573b027298f");
        list.add("c5f6f1a4f0264078bd5a0eb78063c2fa");
        param.setIds(list);
        task.deleteTask(param);
    }

    @Test
    public void terminatedTest() {
        List<TerminatedTaskParam> list = new ArrayList<>();
        TerminatedTaskParam param = new TerminatedTaskParam();
        param.setTaskId("61b2f4d787e746ca955b5bb05cb4bc1d");
        param.setReason("好啊好啊");
        list.add(param);
        task.terminatedTask(list);
    }

    @Test
    public void totalTest1() {
        List<AddTaskParam> listAddTaskParam = new ArrayList<>();
        AddTaskParam addTaskParam = new AddTaskParam();
        addTaskParam.setProjectId("4d03f16d3d454a13a752214fc546aae5");
        //param.setParentTaskId("61b2f4d787e746ca955b5bb05cb4bc1d");
        addTaskParam.setType("B");
        addTaskParam.setTaskName("一级任务");
        addTaskParam.setContent("111101");
        //addTaskParam.setReceiver("76e185c955c441a4af3a359fa85ec4b5");
        addTaskParam.setDepartmentId("3bb21abc91b14ac3bf8aab3b920e4d36");
        addTaskParam.setPlanEndTime("2018-10-20 18:00:12");
        addTaskParam.setPlanStartTime("2018-02-17 18:00:12");
        addTaskParam.setDifficult("2");
        addTaskParam.setUrgency("3");
        addTaskParam.setWorkload("3");
        listAddTaskParam.add(addTaskParam);
        task.addTask(listAddTaskParam);
    }

    @Test
    public void totalTest2() {
        String taskId = "b3fedc1da07a4f43a763b0d50345bede";

        List<ReceiveTaskParam> listReceiveTaskParam = new ArrayList<>();
        ReceiveTaskParam receiveTaskParam = new ReceiveTaskParam();
        receiveTaskParam.setTaskId(taskId);
        listReceiveTaskParam.add(receiveTaskParam);
        task.receiveTask(listReceiveTaskParam);

        List<QcDistributionParam> listQcDistributionParam = new ArrayList<>();
        QcDistributionParam qcDistributionParam = new QcDistributionParam();
        qcDistributionParam.setId(taskId);
        qcDistributionParam.setQcPersonId("d185f1cc517f4a2bb76a5f2760850ab7");
        listQcDistributionParam.add(qcDistributionParam);
//        QcDistributionParam qcDistributionParamNew=new QcDistributionParam();
//        qcDistributionParamNew.setTaskId(taskId);
//        qcDistributionParamNew.setIsQC("0");
//        listQcDistributionParam.add(qcDistributionParamNew);
        task.taskQcDistribution(listQcDistributionParam);

        List<SubmitTaskParam> listSubmitTaskParam = new ArrayList<>();
        SubmitTaskParam submitTaskParam = new SubmitTaskParam();
        submitTaskParam.setTaskId(taskId);
        listSubmitTaskParam.add(submitTaskParam);
        task.submitTask(listSubmitTaskParam);

        QcEvaluateParam qcEvaluateParam = new QcEvaluateParam();
        qcEvaluateParam.setTaskId(taskId);
        qcEvaluateParam.setIsPassed("1");
        task.qcEvaluate(qcEvaluateParam);

        TaskEvaluateParam taskEvaluateParam = new TaskEvaluateParam();
        taskEvaluateParam.setTaskId(taskId);
        task.taskEvaluate(taskEvaluateParam);
    }

}
