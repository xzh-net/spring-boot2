package net.xzh.sftp.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "sftp")
public class SftpProperties {
	private String host;
	private Integer port;
	private String password;
	private String username;
	private String remoteDir;
	private String localDir;
	private String privateKey;
	private String passphrase;
	
}
