package com.xiu.todos.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 通用的读取配置文件工具类
 * @author xiu
 * @date 2019/1/8 14:34
 */
public class PropertiesUtil {
    private final static String PROPERTIES_FILE_NAME = "config.properties";
    private static Properties properties;

    private static void getProperties(){
        if(properties == null){
            properties = new Properties();
            InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据key获取配置文件中的值
     * @param key 要获取的key
     * @return 从配置文件中获取到的值
     */
    public static String getPropertiesByKey(String key){
        getProperties();
        return properties.getProperty(key);
    }
}
