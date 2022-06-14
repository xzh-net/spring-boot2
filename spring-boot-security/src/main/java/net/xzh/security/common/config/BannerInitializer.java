package net.xzh.security.common.config;


import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.taobao.text.Color;

import net.xzh.security.common.util.CustomBanner;

/**
 * Banner初始化
 *
 * @author zlt
 * @date 2019/8/28
 */
public class BannerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!(applicationContext instanceof AnnotationConfigApplicationContext)) {
            LogoBanner logoBanner = new LogoBanner(BannerInitializer.class, "/zltmp/logo.txt", "Welcome to zlt", 5, 6, new Color[5], true);
            CustomBanner.show(logoBanner, new Description(BannerConstant.VERSION + ":","992224", 0, 1)
                    , new Description("Github:", "https://github.com/xzh-net", 0, 1)
                    , new Description("Blog:", "https://www.xuzhihao.net", 0, 1)
            );
        }
    }
}
