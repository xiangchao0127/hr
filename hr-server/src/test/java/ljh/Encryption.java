package ljh;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.*;

/**
 * @author Liujuhao
 * @date 2018/8/9.
 */
public class Encryption {

    /**
     * 加密方式
     */
    static private String SECURITY_METHOD = "md5";

    /**
     * 迭代执行次数
     */
    static private int ITERATION_TIMES = 1000;

    @org.junit.Test
    public void test() {

        String clearText = "123456";
        String username = "025";

        Object result = new SimpleHash(SECURITY_METHOD, clearText, username, ITERATION_TIMES);
        System.out.println(result.toString());
    }
}