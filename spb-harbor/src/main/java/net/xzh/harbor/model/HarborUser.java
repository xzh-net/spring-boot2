package net.xzh.harbor.model;

import io.swagger.annotations.ApiModelProperty;

public class HarborUser {
	
	@ApiModelProperty(value = "用户名")
	private String username;
	@ApiModelProperty(value = "密码")
	private String password;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "全名")
	private String realname;
	@ApiModelProperty(value = "注释")
	private String comment;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
