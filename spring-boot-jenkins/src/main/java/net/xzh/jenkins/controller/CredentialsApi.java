package net.xzh.jenkins.controller;

import java.io.IOException;

import com.offbytwo.jenkins.client.JenkinsHttpClient;


/**
 * 用户，凭证，域管理（凭证操作必须安装Credentials Binding插件）
 * jenkins-client对凭证管理支持比较弱，建议使用REST API方式
 *
 */
public class CredentialsApi{

    private JenkinsHttpClient jenkinsHttpClient;
    
    CredentialsApi() {
        jenkinsHttpClient = JenkinsConnect.getHttpClient();
    }
    
    /**
     * 创建域
     */
    public void createDomain() {
        try {
            String data="<com.cloudbees.plugins.credentials.domains.Domain>\r\n"
            		+ "  <name>www.xuzhihao.net</name>\r\n"
            		+ "  <description>这是一个测试域</description>\r\n"
            		+ "  <specifications/>\r\n"
            		+ "</com.cloudbees.plugins.credentials.domains.Domain>\r\n";
			jenkinsHttpClient.post_xml("/credentials/store/system/createDomain",data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 删除域
     */
    public void removeDomain() {
        try {
            String domain = "www.xuzhihao.net";
            jenkinsHttpClient.post("/credentials/store/system/domain/"+domain+"/doDelete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 删除凭证
     * @param domain
     * @param credentialsId
     */
	public void removeCredentialsById(String domain,String credentialsId) {
		if("".equals(domain)) {
        	domain="_";
        }
		try {
			jenkinsHttpClient.post("/credentials/store/system/domain/"+domain+"/credential/"+credentialsId+"/doDelete");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
    /**
     * 创建 UsernamePassword 凭证
     * URL路径: /credentials/store/{store}/domain/{domain}/createCredentials
     * {store}: 凭证存储类型（默认 system 表示系统级存储）
	 * {domain}: 域名（_ 表示全局域，自定义域需替换名称）
     * @param domain 域
     */
    public void createCredentialsByUsernamePassword(String domain) {
    	if("".equals(domain)) {
        	domain="_";
        }
    	String id=System.currentTimeMillis()+"";
        try {
            String data = "<com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl>\r\n"
            		+ "  <scope>GLOBAL</scope>\r\n"
            		+ "  <id>"+id+"</id>\r\n"
            		+ "  <description>这是一个用户名密码凭证"+id+"</description>\r\n"
            		+ "  <username>admin</username>\r\n"
            		+ "  <password>123456</password>\r\n"
            		+ "</com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl>\r\n";
            jenkinsHttpClient.post_xml("/credentials/store/system/domain/"+domain+"/createCredentials",data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建 Secret text 凭证（sonarqube扫描）
     */
    public void createCredentialsBySecretText(String domain) {
    	if("".equals(domain)) {
        	domain="_";
        }
    	String id=System.currentTimeMillis()+"";
        try {
            String data = "<org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl>\r\n"
            		+ "  <scope>GLOBAL</scope>\r\n"
            		+ "  <id>"+id+"</id>\r\n"
            		+ "  <description>这是一个字符串凭证"+id+"</description>\r\n"
            		+ "  <secret>cd4d40ff116457d7bf9c88bef05f8bb881b4fd43</secret>\r\n"
            		+ "</org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl>\r\n";
            jenkinsHttpClient.post_xml("/credentials/store/system/domain/"+domain+"/createCredentials",data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 创建 SSH Username with private key 凭证（发布远程机器）
     */
    
    public void createCredentialsBySSHPrivateKey(String domain) {
    	if("".equals(domain)) {
        	domain="_";
        }
    	String id=System.currentTimeMillis()+"";
        try {
            String data = "<com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey>\r\n"
            		+ "  <scope>GLOBAL</scope>\r\n"
            		+ "  <id>"+id+"</id>\r\n"
            		+ "  <description>这是一个私钥凭证"+id+"</description>\r\n"
            		+ "  <username>root</username>\r\n"
            		+ "  <usernameSecret>false</usernameSecret>\r\n"
            		+ "  <passphrase>123456</passphrase>\r\n"
            		+ "  <privateKeySource class=\"com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey$DirectEntryPrivateKeySource\">\r\n"
            		+ "    <privateKey>$(cat ~/.ssh/id_rsa)</privateKey>\r\n"
            		+ "  </privateKeySource>\r\n"
            		+ "</com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey>\r\n";
            jenkinsHttpClient.post_xml("/credentials/store/system/domain/"+domain+"/createCredentials",data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public static void main(String[] args) {
    	CredentialsApi credentialsApi = new CredentialsApi();
    	//创建域
//    	credentialsApi.createDomain();
    	//删除域
//    	credentialsApi.removeDomain();
    	//创建全局用户名密码凭证
//    	credentialsApi.createCredentialsByUsernamePassword("");
    	//创建域下用户名密码凭证
//    	credentialsApi.createCredentialsByUsernamePassword("www.xuzhihao.net");
    	//删除凭证
//    	credentialsApi.removeCredentialsById("","凭证id");
    	//创建字符串凭证
//    	credentialsApi.createCredentialsBySecretText("");
    	//创建私钥凭证
    	credentialsApi.createCredentialsBySSHPrivateKey("");
    	
    }
    
    
}
