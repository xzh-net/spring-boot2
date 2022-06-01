package net.xzh.activiti.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.xzh.activiti.common.utils.TreeUtil;
import net.xzh.activiti.mapper.MenuMapper;
import net.xzh.activiti.model.Menu;

@Service
public class MenuService {

    @Resource
    private MenuMapper menuMapper;


    /**
     * 获取所有的树形菜单 (admin 账户拥有所有权限.)
     */
    public List<Menu> selectMenuTree() {
        List<Menu> menus=menuMapper.selectAll();
        return toTree(menus);
    }

    /**
     * 转换为树形结构
     */
    private List<Menu> toTree(List<Menu> menuList) {
        return TreeUtil.toTree(menuList, "menuId", "parentId", "children", Menu.class);
    }

}