package net.xzh.netty.client;

import java.io.Serializable;

import lombok.Data;

@Data
public class Protocol implements Serializable {

	private static final long serialVersionUID = 3370817125198091232L;
	private long id;
	private String content;

	public Protocol(long id, String content) {
		this.id = id;
		this.content = content;
	}
}