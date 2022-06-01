package net.xzh.elasticsearch.util;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

import cn.hutool.core.util.StrUtil;

/**
 * 基于 Jackson 的 json 工具类
 *
 * @author zlt
 * @date 2020/10/8
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
public class JsonUtil {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 忽略在json字符串中存在，但是在java对象中不存在对应属性的情况
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略空Bean转json的错误
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        // 允许不带引号的字段名称
        MAPPER.configure(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true);
        // 允许单引号
        MAPPER.configure(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature(), true);
        // allow int startWith 0
        MAPPER.configure(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS.mappedFeature(), true);
        // 允许字符串存在转义字符：\r \n \t
        MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        // 排除空值字段
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 使用驼峰式
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        // 使用bean名称
        MAPPER.enable(MapperFeature.USE_STD_BEAN_NAMING);
        // 所有日期格式都统一为固定格式String
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        
        MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    /**
     * 字符串转换为JsonNode对象
     * @param json json字符串
     */
    public static JsonNode parse(String json) {
        if (StrUtil.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
