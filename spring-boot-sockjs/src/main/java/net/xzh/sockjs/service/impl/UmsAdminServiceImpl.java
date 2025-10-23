package net.xzh.sockjs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollUtil;
import net.xzh.sockjs.common.exception.Asserts;
import net.xzh.sockjs.common.exception.BusinessException;
import net.xzh.sockjs.common.model.ResultCode;
import net.xzh.sockjs.entity.UmsAdmin;
import net.xzh.sockjs.entity.UmsAdminExample;
import net.xzh.sockjs.entity.UmsResource;
import net.xzh.sockjs.mapper.UmsAdminMapper;
import net.xzh.sockjs.mapper.UmsAdminRoleRelationDao;
import net.xzh.sockjs.security.domain.LoginUser;
import net.xzh.sockjs.security.util.JwtTokenUtil;
import net.xzh.sockjs.service.UmsAdminCacheService;
import net.xzh.sockjs.service.UmsAdminService;

/**
 * UmsAdminService实现类
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UmsAdminMapper adminMapper;
	@Autowired
	private UmsAdminRoleRelationDao adminRoleRelationDao;
	@Autowired
	private UmsAdminCacheService adminCacheService;

	@Override
	public String login(String username, String password) {
		String token = null;
		// 密码需要客户端加密后传递
		try {
			UserDetails userDetails = loadUserByUsername(username);
			if (!passwordEncoder.matches(password, userDetails.getPassword())) {
				Asserts.fail("密码不正确");
			}
			if (!userDetails.isEnabled()) {
				Asserts.fail("帐号已被禁用");
			}
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			token = jwtTokenUtil.generateToken(userDetails);
		} catch (AuthenticationException e) {
			throw new BusinessException(ResultCode.FAILED);
		}
		return token;
	}

	@Override
	public UmsAdmin getItem(Long id) {
		return adminMapper.selectByPrimaryKey(id);
	}

	@Override
	public String refreshToken(String oldToken) {
		return jwtTokenUtil.refreshToken(oldToken);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		// 获取用户信息
		UmsAdmin admin = getAdminByUsername(username);
		if (admin != null) {
			List<UmsResource> resourceList = getResourceList(admin.getId());
			return new LoginUser(admin, resourceList);
		}
		throw new UsernameNotFoundException("用户名或密码错误");
	}

	@Override
	public List<UmsResource> getResourceList(Long adminId) {
		List<UmsResource> resourceList = adminCacheService.getResourceList(adminId);
		if (CollUtil.isNotEmpty(resourceList)) {
			return resourceList;
		}
		resourceList = adminRoleRelationDao.getResourceList(adminId);
		if (CollUtil.isNotEmpty(resourceList)) {
			adminCacheService.setResourceList(adminId, resourceList);
		}
		return resourceList;
	}

	@Override
	public UmsAdmin getAdminByUsername(String username) {
		UmsAdmin admin = adminCacheService.getAdmin(username);
		if (admin != null)
			return admin;
		UmsAdminExample example = new UmsAdminExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<UmsAdmin> adminList = adminMapper.selectByExample(example);
		if (adminList != null && adminList.size() > 0) {
			admin = adminList.get(0);
			adminCacheService.setAdmin(admin);
			return admin;
		}
		return null;
	}
}
