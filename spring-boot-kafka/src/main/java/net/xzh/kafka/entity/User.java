package net.xzh.kafka.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2464136242282376807L;

	private Integer userId;

    private String userName;
    

}
