package net.xzh.sftp;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;


@SpringBootTest
public class SftpRemoteFileTemplateTest {

    @Resource
    private SftpRemoteFileTemplate sftpRemoteFileTemplate;

    @Test
    void existsTest() {
        sftpRemoteFileTemplate.execute(session -> {
            boolean exists = session.exists("/upload/tomcat.keystore");
            System.out.println(exists);
            return exists;
        });
    }
}