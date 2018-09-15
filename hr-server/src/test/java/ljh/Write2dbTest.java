package ljh;

import com.handge.hr.auth.shiro.ShiroEncryptor;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.repository.mapper.EntityAccountMapper;
import com.handge.hr.domain.repository.mapper.EntityRoleMapper;
import com.handge.hr.domain.repository.pojo.EntityAccount;
import com.handge.hr.domain.repository.pojo.EntityRole;
import com.handge.hr.server.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Liujuhao
 * @date 2018/8/13.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class Write2dbTest {

    @Autowired
    EntityAccountMapper entityAccountMapper;

    @Autowired
    EntityRoleMapper entityRoleMapper;

    @Test
    public void test() {
        List<EntityAccount> entityAccounts = entityAccountMapper.selectAll();

        entityAccounts.forEach(entityAccount -> {
            if (StringUtils.isEmpty(entityAccount.getPassword())) {
                String pwd = ShiroEncryptor.securityTransform("123456", entityAccount.getUsername());
                entityAccount.setPassword(pwd);
                entityAccountMapper.updateByPrimaryKey(entityAccount);
            }
        });
    }

    @Test
    public void test2() {
        EntityRole role = entityRoleMapper.getRoleById("21a343d9ad054215b3a5cbcbce59d91d");
        System.out.println(role);
    }
}
