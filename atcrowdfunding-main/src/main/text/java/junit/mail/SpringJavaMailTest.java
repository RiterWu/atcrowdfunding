package junit.mail;

import com.riter.atcrowdfunding.utils.DesUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SpringJavaMailTest {

    public static void main(String[] args) {
        // 使用java程序发送mail
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");

        JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) ioc.getBean("sendMail");

        javaMailSender.setDefaultEncoding("UTF-8");

        MimeMessage mail = javaMailSender.createMimeMessage();  // 一封情书

        MimeMessageHelper helper = new MimeMessageHelper(mail);

        try {
            // 邮件标题
            helper.setSubject("告白书");

            StringBuilder content = new StringBuilder();

            String param = "ILY";
            param = DesUtil.encrypt(param, "qwertyuiop");

            content.append("<a href='http://www.atcrowdfunding.com/test/act.do?p=" + param + "'>惊喜链接</a>");

            helper.setText(content.toString(), true);
            helper.setFrom("admin@riter.com");
            helper.setTo("test@riter.com");

            javaMailSender.send(mail);

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
