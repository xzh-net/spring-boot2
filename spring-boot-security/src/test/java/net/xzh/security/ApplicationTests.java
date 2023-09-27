package net.xzh.security;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Administrator
 * @version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
    public void contextLoads() {
    }
	
    @Test
    public void testBCrypt(){

        //对密码进行加密
        String hashpw = BCrypt.hashpw("admin", BCrypt.gensalt());
        System.out.println(hashpw);

        //校验密码
        boolean checkpw = BCrypt.checkpw("admin", "$2a$10$CKAEYLHkyZb8VvYnysmyp.6sc2RXcACF/H8u43DI6CFIR8eC95cdK");
        System.out.println(checkpw);
    }
}
