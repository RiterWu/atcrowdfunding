package junit.test;

import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.manager.service.UserService;
import com.riter.atcrowdfunding.utils.MD5Util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDemo {

    public static void main(String[] args) {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring*.xml");
        UserService userService = ioc.getBean(UserService.class);

        for (int i = 1; i <= 100; i++) {
            User user = new User();
            user.setLoginacct("test" + i);
            user.setUserpswd(MD5Util.digest("159"));
            user.setUsername("md" + i);
            user.setEmail("test" + i + "@atRiter.com");
            user.setCreatetime("2020-02-05 18:46:35");
            userService.save(user);
        }
    }
}
