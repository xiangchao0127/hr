package xc;

import com.handge.hr.domain.repository.mapper.WorkflowProjectMapper;
import com.handge.hr.domain.repository.mapper.WorkflowTaskMapper;
import com.handge.hr.manage.service.api.workflow.ICompletion;
import com.handge.hr.server.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class CompletionRuleTest {
    @Autowired
    WorkflowTaskMapper workflowTaskMapper;
    @Autowired
    WorkflowProjectMapper workflowProjectMapper;
    @Autowired
    ICompletion iCompletion;

    /**
     * 项目完成度测试
     */
    @Test
    public void testProjectCompletionRule() {
        String projectCompletion = iCompletion.getProjectCompletion("859ad00281c9489e805d8f7fcd7c5e55");
        System.out.println(projectCompletion);
    }

    /**
     * 任务完成度测试
     */
    @Test
    public void testTaskCompletionRule() {
        String taskCompletion = iCompletion.getTaskCompletion("81f527d20ab5462b902c99301de3f3a0");
        System.out.println(taskCompletion);
    }
}
