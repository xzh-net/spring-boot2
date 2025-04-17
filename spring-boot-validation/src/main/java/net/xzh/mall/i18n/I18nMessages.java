package net.xzh.mall.i18n;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author xzh
 * @date 2020-12-28 17:01:38
 */
@PropertySource(value = {"classpath:i18n/messages*.properties"})
public class I18nMessages {


    /**
     * 国际化信息map
     */
    private static final Map<String, ResourceBundle> MESSAGES = new HashMap<>();

    /**
     * 获取国际化信息
     * 只配置了 zh en 语言
     */
    public static String getMessage(String key) {
        //获取当前语言环境
        Locale locale = LocaleContextHolder.getLocale();
        if (!Locale.CHINA.getLanguage().equals(locale.getLanguage())
                && !Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
            //其他语言一律为中文
            locale = Locale.CHINA;
        }
        ResourceBundle message = MESSAGES.get(locale.getLanguage());
        if (message == null) {
            //根据语言读取配置
            message = MESSAGES.get(locale.getLanguage());
            if (message == null) {
                message = ResourceBundle.getBundle("i18n/messages", locale);
                MESSAGES.put(locale.getLanguage(), message);
            }
        }
        return message.getString(key);
    }

    /**
     * 清除国际化信息
     */
    public static void flushMessage() {
        MESSAGES.clear();
    }


}
