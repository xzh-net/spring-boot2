package net.xzh.sftp;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.xzh.sftp.config.SftpConfiguration;

@SpringBootTest
class SftpGatewayTest {

    @Autowired
    private SftpConfiguration.SftpGateway sftpGateway;

    @Test
    void contextLoads() {

    }
    @Test
    void testListFiles(){
        sftpGateway.listFile("/").stream().forEach((f)->{
            System.out.println(f);
        });
    }

    @Test
    void testUpload(){
        sftpGateway.upload(new File("D:\\tomcat.keystore"));
    }

    @Test
    void testDownload(){
        List<File> downloadFiles = sftpGateway.downloadFiles("/");
        downloadFiles.stream().forEach((f)->{
            System.out.println(f.getName());
        });
    }


}
