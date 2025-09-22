package net.xzh.swagger.modules.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.xzh.swagger.modules.model.UmsMenu;

/**
 * 后台菜单节点封装
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class UmsMenuNode extends UmsMenu {
    @ApiModelProperty(value = "子级菜单")
    private List<UmsMenuNode> children;
}
