import com.qianfeng.springboot.UserApplication;
import com.qianfeng.springboot.po.UserInfo;
import com.qianfeng.springboot.service.Impl.UserServiceImpl;
import com.qianfeng.springboot.utils.MD5Utils;


import com.qianfeng.springboot.utils.RandomUtils;
import org.apache.shiro.crypto.hash.Md2Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class UserApplicationTests {
    @Autowired
    private UserServiceImpl userService;
    @org.junit.Test
    public void test(){
        String md5 = MD5Utils.md5("123", "张三");
        System.out.println(md5);
    }

    @Test
    public void test1(){
        String s = RandomUtils.generateStringOfNum(6);
        System.out.println(s);
    }

    @Test
    public void  test2(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_name("张三");
        userInfo.setUser_password("123");
        userInfo.setPhone("1379");
        userService.insertUser(userInfo);
    }
}
