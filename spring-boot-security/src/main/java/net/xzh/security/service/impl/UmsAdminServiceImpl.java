package net.xzh.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollUtil;
import net.xzh.security.common.exception.BusinessException;
import net.xzh.security.common.model.ResultCode;
import net.xzh.security.domain.UmsResource;
import net.xzh.security.model.UmsAdmin;
import net.xzh.security.security.domain.LoginUser;
import net.xzh.security.security.util.JwtTokenUtil;
import net.xzh.security.service.UmsAdminService;

/**
 * 用户管理
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
	/**
	 * 存放默认用户信息
	 */
	private List<LoginUser> accountList = new ArrayList<>();
	/**
	 * 存放默认资源信息
	 */
	private List<UmsResource> resourceList = new ArrayList<>();

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Resource
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	private void init() {
		accountList.add(LoginUser.builder().username("admin").password(passwordEncoder.encode("123456"))
				.permissions(CollUtil.newHashSet("1:brand:create", "2:brand:update", "3:brand:delete", "4:brand:list",
						"5:brand:listAll", "6:admin:resetPwd"))
				.build());

		accountList.add(LoginUser.builder().username("xzh").password(passwordEncoder.encode("123456"))
				.permissions(CollUtil.newHashSet("4:brand:list")).build());

		resourceList.add(UmsResource.builder().id(1L).name("brand:create").url("/brand/create").build());
		resourceList.add(UmsResource.builder().id(2L).name("brand:update").url("/brand/update/**").build());
		resourceList.add(UmsResource.builder().id(3L).name("brand:delete").url("/brand/delete/**").build());
		resourceList.add(UmsResource.builder().id(4L).name("brand:list").url("/brand/list").build());
		resourceList.add(UmsResource.builder().id(5L).name("brand:listAll").url("/brand/listAll").build());
		resourceList.add(UmsResource.builder().id(6L).name("admin:resetPwd").url("/admin/resetPwd/**").build());
	}

	@Override
	public LoginUser getLoginByUsername(String username) {
		List<LoginUser> findList = accountList.stream().filter(item -> item.getUsername().equals(username))
				.collect(Collectors.toList());
		if (CollUtil.isNotEmpty(findList)) {
			return findList.get(0);
		}
		return null;
	}

	@Override
	public List<UmsResource> getResourceList() {
		return resourceList;
	}

	public UmsAdmin register(UmsAdmin umsAdminParam) {
		accountList.add(LoginUser.builder().username(umsAdminParam.getUsername())
				.password(passwordEncoder.encode(umsAdminParam.getPassword()))
				.permissions(CollUtil.newHashSet("4:brand:list")).build());
		return umsAdminParam;
	}

	@Override
	public String login(String username, String password) {
		try {
			// 1. 创建认证令牌
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
					password);
			// 2. 验证用户名密码
			// 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			// 3. 认证成功后设置安全上下文
			SecurityContextHolder.getContext().setAuthentication(authentication);
			// 4. 获取用户详情并生成token
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return jwtTokenUtil.generateToken(userDetails);
		} catch (BadCredentialsException e) {
			// 账号密码错误
			throw new BusinessException(ResultCode.A0005);
		}
	}

	@Override
	public String login2(String username, String password) {
		UserDetails userDetails = getLoginByUsername(username);
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BusinessException(ResultCode.A0005);
		}
		if (!userDetails.isEnabled()) {
			throw new BusinessException(ResultCode.A0005);
		}
		// 1. 创建认证令牌
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		// 2. 认证成功后设置安全上下文
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtTokenUtil.generateToken(userDetails);
	}
}
