package net.xzh.gen.dto;

import net.xzh.gen.model.UmsResource;
import net.xzh.gen.model.UmsResourceCategory;

/**
 * Created 2020/12/9.
 */
public class ResourceWithCateDto extends UmsResource {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6999483352587260033L;
	private UmsResourceCategory resourceCategory;

    public UmsResourceCategory getResourceCategory() {
        return resourceCategory;
    }

    public void setResourceCategory(UmsResourceCategory resourceCategory) {
        this.resourceCategory = resourceCategory;
    }
}
