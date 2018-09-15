import com.handge.hr.domain.entity.manage.web.request.pointcard.ListPointCardParam;
import com.handge.hr.domain.entity.manage.web.response.pointcard.PointCardInfo;
import com.handge.hr.domain.repository.mapper.PerformancePointItemMapper;
import com.handge.hr.manage.service.api.workflow.IPointCard;
import com.handge.hr.server.Application;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author liuqian
 * @date 2018/8/17
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PermissionTest {
    @Autowired
    IPointCard pointCard;
    @Autowired
    PerformancePointItemMapper itemMapper;
    @Test
    public void test(){
        //指定ini配置文件创建一个SecurityManager工厂
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("D:\\InstallSoftware\\IDEA\\IntelliJ IDEA 2017.2.3\\idea PROJECT\\hr\\hr-server\\src\\main\\resources\\shiro-permission.ini");
        //获取SecurityManager实例并将实例绑定给SecurityUtils（全局设置，设置一次即可）
        SecurityManager instance = factory.getInstance();
        SecurityUtils.setSecurityManager(instance);

        //获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //创建用户名/密码身份验证token
        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");

        try {
            //登录（即身份验证）
            subject.login(token);
        } catch (AuthenticationException e) {
            //身份验证失败
            e.printStackTrace();
        }
        System.out.println("认证状态："+subject.isAuthenticated());

        /*boolean ishasRole = subject.hasRole("role3");
        System.out.println("单个角色判断" + ishasRole);
        boolean hasAllRoles = subject.hasAllRoles(Arrays.asList("role1", "role2", "role3"));
        System.out.println("多个角色判断" + hasAllRoles);*/
        // subject.checkRole("role13");

        boolean isPermitted = subject.isPermitted("user:CREATE:1");
        System.out.println("单个权限判断" + isPermitted);

        /*boolean isPermittedAll = subject.isPermittedAll("user:CREATE:1", "user:delete");
        System.out.println("多个权限判断" + isPermittedAll);
        subject.checkPermission("user:CREATE:1");*/
    }

    @Test
    public void test1(){
        ListPointCardParam pointCardParam = new ListPointCardParam();
        pointCardParam.setOrigin("1");
        List<PointCardInfo> cardInfos = pointCard.listPointCard(pointCardParam);
        cardInfos.forEach(o -> {
            System.out.println(o.toString());
            System.out.println("=====================");
        });
        /*Object options = itemMapper.getOptions();
        Gson gson = new Gson();
        Map<String,String> map = gson.fromJson(options.toString(), Map.class);
        for(Map.Entry<String,String> entry:map.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }*/
        /*PerformancePointItem pointItem = new PerformancePointItem();
        pointItem.setId(4);
        PerformancePointItem performancePointItem = itemMapper.selectOne(pointItem);
        Object options = performancePointItem.getOptions();
        Gson gson = new Gson();
        Map<String,String> map = gson.fromJson(options.toString(), Map.class);
        for (Map.Entry<String,String> entry:map.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }*/
    }
}
