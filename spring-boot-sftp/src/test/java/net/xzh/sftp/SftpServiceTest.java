package net.xzh.sftp;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import net.xzh.sftp.service.SftpService;


@SpringBootTest
public class SftpServiceTest {

    @Resource
    private SftpService sftpService;

    @Test
    void testExistFile() {
        boolean existFile = sftpService.existFile("/upload/tomcat.keystore");
        System.out.println(existFile);
    }


    @Test
    void listFileTest() {
        sftpService.listALLFile("/upload").stream().forEach(System.out::println);
    }

    @Test
    void upload2PathTest() throws IOException {
        byte[] bytes = FileUtils.readFileToByteArray(new File("D:\\tomcat.keystore"));
        sftpService.mkdir("/upload/pro");
        sftpService.upload(bytes, UUID.randomUUID().toString().concat(".keystore"), "/upload/pro");
    }

    @Test
    void testDownLoad() throws Exception {
        sftpService.downloadFile("/upload/tomcat.keystore", "D:\\tmp\\tomcat2222.keystore");
//        sftpService.uploadFile(new File("D:\\tmp\\cc.js"));
//        InputStream inputStream = sftpService.readFile("/upload/cc.js");
//        IOUtils.copy(inputStream, new FileOutputStream(new File("D:\\tmp\\" + UUID.randomUUID() + ".js")));
    }

    @Test
    void uploadFile() {
        sftpService.uploadFile(new File("D:\\tomcat.keystore"));
    }

    @Test
    void nsltTest() {
        Arrays.asList(sftpService.nlstFile("/upload").split(",")).stream().forEach(System.out::println);
    }
}
