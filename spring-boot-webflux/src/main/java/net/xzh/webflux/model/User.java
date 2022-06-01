package net.xzh.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version: 1.0
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private Long id;
	private String nickName;
	private Integer age;
}
