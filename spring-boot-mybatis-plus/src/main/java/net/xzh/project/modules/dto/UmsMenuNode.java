package net.xzh.project.modules.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import net.xzh.project.modules.model.UmsMenu;

/**
 * 后台菜单节点封装
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Schema(description = "菜单节点")
public class UmsMenuNode extends UmsMenu {
    @Schema(description = "子级菜单")
    private List<UmsMenuNode> children;
}
