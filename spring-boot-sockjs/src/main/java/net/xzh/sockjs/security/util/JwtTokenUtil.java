package net.xzh.sockjs.security.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import cn.hutool.core.util.IdUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * JwtToken工具类
 *
 */
public class JwtTokenUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	private static final String CLAIM_KEY_USERNAME = "sub"; // JWT主题声明键
	private static final String CLAIM_KEY_CREATED = "created"; // 创建时间声明键

	@Value("${token.secret}")
	private String secret; // JWT签名密钥

	@Value("${token.expiration}")
	private Long expiration; // token过期时间（秒）

	/**
	 * 根据负载生成JWT token
	 * 
	 * @param claims 负载数据
	 * @return 生成的JWT token字符串
	 */
	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims) // 设置负载
				.setExpiration(generateExpirationDate()) // 设置过期时间
				.signWith(SignatureAlgorithm.HS512, secret) // 使用HS512算法和密钥签名
				.compact(); // 生成token字符串
	}

	/**
	 * 从token中解析JWT负载
	 * 
	 * @param token JWT token字符串
	 * @return 解析出的Claims对象，解析失败返回null
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret) // 设置签名密钥
					.parseClaimsJws(token) // 解析token
					.getBody(); // 获取负载
		} catch (Exception e) {
			LOGGER.warn("JWT格式验证失败:{}", token);
		}
		return claims;
	}

	/**
	 * 生成token过期时间
	 * 
	 * @return 过期时间Date对象
	 */
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	/**
	 * 从token中提取用户名
	 * 
	 * @param token JWT token字符串
	 * @return 用户名，提取失败返回null
	 */
	public String getUserNameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject(); // 从主题声明中获取用户名
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * 验证token有效性
	 * 
	 * @param token       客户端传入的token
	 * @param userDetails 从数据库中查询的用户信息
	 * @return true-有效，false-无效
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		String username = getUserNameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	/**
	 * 判断token是否已过期
	 * 
	 * @param token JWT token字符串
	 * @return true-已过期，false-未过期
	 */
	private boolean isTokenExpired(String token) {
		Date expiredDate = getExpiredDateFromToken(token);
		return expiredDate.before(new Date());
	}

	/**
	 * 从token中获取过期时间
	 * 
	 * @param token JWT token字符串
	 * @return 过期时间Date对象
	 */
	private Date getExpiredDateFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getExpiration();
	}

	/**
	 * 根据用户信息生成token
	 * 
	 * @param userDetails 用户详细信息
	 * @return 生成的JWT token字符串
	 */
	public String generateToken(UserDetails userDetails) {
		String token = IdUtil.simpleUUID(); // 生成唯一标识
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername()); // 设置用户名
		claims.put(CLAIM_KEY_CREATED, token); // 设置创建标识
		return generateToken(claims);
	}

	/**
	 * 判断token是否可刷新（未过期时可刷新）
	 * 
	 * @param token JWT token字符串
	 * @return true-可刷新，false-不可刷新
	 */
	public boolean canRefresh(String token) {
		return !isTokenExpired(token);
	}

	/**
	 * 刷新token（更新创建时间）
	 * 
	 * @param token 原token
	 * @return 新token，原token过期时返回null
	 */
	public String refreshToken(String token) {
		if (!canRefresh(token)) {
			return null;
		}
		Claims claims = getClaimsFromToken(token);
		claims.put(CLAIM_KEY_CREATED, new Date()); // 更新创建时间为当前时间
		return generateToken(claims);
	}
}