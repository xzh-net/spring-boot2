package net.xzh.security.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollUtil;
import net.xzh.security.domain.AdminUserDetails;
import net.xzh.security.domain.UmsResource;
import net.xzh.security.model.UmsAdmin;
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
    private List<AdminUserDetails> adminUserDetailsList = new ArrayList<>();
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
    private void init(){
        adminUserDetailsList.add(AdminUserDetails.builder()
                .username("admin")
                .password(passwordEncoder.encode("123456"))
                .authorityList(CollUtil.toList("1:brand:create","2:brand:update","3:brand:delete","4:brand:list","5:brand:listAll"))
                .build());
        adminUserDetailsList.add(AdminUserDetails.builder()
                .username("xzh")
                .password(passwordEncoder.encode("123456"))
                .authorityList(CollUtil.toList("4:brand:list"))
                .build());
        
        resourceList.add(UmsResource.builder()
                .id(1L)
                .name("brand:create")
                .url("/brand/create")
                .build());
        resourceList.add(UmsResource.builder()
                .id(2L)
                .name("brand:update")
                .url("/brand/update/**")
                .build());
        resourceList.add(UmsResource.builder()
                .id(3L)
                .name("brand:delete")
                .url("/brand/delete/**")
                .build());
        resourceList.add(UmsResource.builder()
                .id(4L)
                .name("brand:list")
                .url("/brand/list")
                .build());
        resourceList.add(UmsResource.builder()
                .id(5L)
                .name("brand:listAll")
                .url("/brand/listAll")
                .build());
    }
    @Override
    public AdminUserDetails getAdminByUsername(String username) {
        List<AdminUserDetails> findList = adminUserDetailsList.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(findList)){
            return findList.get(0);
        }
        return null;
    }

    @Override
    public List<UmsResource> getResourceList() {
        return resourceList;
    }
    
	public UmsAdmin register(UmsAdmin umsAdminParam) {
		 adminUserDetailsList.add(AdminUserDetails.builder()
	                .username(umsAdminParam.getUsername())
	                .password(passwordEncoder.encode(umsAdminParam.getPassword()))
	                .authorityList(CollUtil.toList("4:brand:list"))
	                .build());
		return umsAdminParam;
	}
    

    @Override
    public String login(String username, String password) {
    	Authentication authentication = null;
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
		authentication = authenticationManager.authenticate(authenticationToken);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return jwtTokenUtil.generateToken(userDetails);
    }
}
