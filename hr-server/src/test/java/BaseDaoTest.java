import com.handge.hr.behavior.common.repository.IBaseDAO;
import com.handge.hr.domain.entity.behavior.web.response.professional.ProfessionalAccomplishmentResult;
import com.handge.hr.server.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liuqian
 * @date 2018/8/8
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BaseDaoTest {
    @Autowired
    IBaseDAO baseDAO;
    /**
     * ip是否存在模型库中
     */
    @Test
    public void testIsExistInLib(){
        ArrayList<String> ips = new ArrayList<>();
        ips.add("172.18.199.77");
        ips.add("172.18.199.80");
        ips.add("172.18.199.42");
        boolean flag = baseDAO.isExistInLib(ips, "2018-05");
        System.out.println(flag);
    }

    /**
     * 通过部门名称获取部门员工工号
     */
    @Test
    public void testGetNumbersByDepName(){
        List<String> numbers = baseDAO.getNumbersByDepName("总经办");
        numbers.forEach(System.out::println);
    }

    /**
     * 通过员工工号获得员工姓名
     */
    @Test
    public void testGetEmployeeNameByNumber(){
        String name = baseDAO.getEmployeeNameByNumber("002");
        System.out.println(name);
    }

    /**
     * 通过员工工号查找该部门所有ip
     */
    @Test
    public void testGetDepIpsByNo(){
        List<String> ips = baseDAO.getDepIpsByNo("003");
        ips.forEach(System.out::println);
    }

    /**
     * 查询模型结果
     */
    @Test
    public void testGetProfessionalAccomplishmentResults(){
        List<ProfessionalAccomplishmentResult> list = baseDAO.getProfessionalAccomplishmentResults("201805", true);
        list.forEach(o ->{
            System.out.println(o.toString());
        });
    }

    /**
     * 通过员工工号查找部门名称及部门人数
     */
    @Test
    public void testGetDepInfoByNo(){
        String[] depInfoByNo = baseDAO.getDepInfoByNo("003");
        System.out.println("部门名称："+depInfoByNo[0]+" 部门人数："+depInfoByNo[1]);
    }

    /**
     * 通过员工工号查找对应的IP
     */
    @Test
    public void testGetIpsByNo(){
        List<String> ips = baseDAO.getIpsByNo("003");
        ips.forEach(System.out::println);
    }

    /**
     * 获取配置参数的值
     */
    @Test
    public void testGetConfigParam(){
        Map<String, Object> configParam = baseDAO.getConfigParam();
        System.out.println(configParam);
    }

    /**
     * 获取国家基本信息的映射表
     */
    @Test
    public void testGetInfoOfCountry(){
        Map<String, Object[]> infoOfCountry = baseDAO.getInfoOfCountry();
        System.out.println(infoOfCountry.toString());
    }

    /**
     * 获取国家起始IP为key的映射表
     */
    @Test
    public void testGetIpRegionOfCountry(){
        Map<Long, Object[]> ipRegionOfCountry = baseDAO.getIpRegionOfCountry();
        for (Map.Entry<Long, Object[]> entry:ipRegionOfCountry.entrySet()){
            System.out.println(entry.getKey()+"|"+entry.getValue()[0]+"|"+entry.getValue()[1]);
        }
    }
}
