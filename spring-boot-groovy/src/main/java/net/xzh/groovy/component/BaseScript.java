package net.xzh.groovy.component;

import groovy.lang.Script;
import net.xzh.groovy.util.SpringContextUtil;

public class BaseScript extends Script {
	
    @Override
    public Object run() {
        return null;
    }

    public Object getBean(Class<?> clazz) {
        return SpringContextUtil.getBean(clazz);
    }
}
