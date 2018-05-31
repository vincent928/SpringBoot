package org.springboot.build.demo.springbootbuild.utils;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @DESCRIPTION ：获取文件资源工具类
 * @AUTHOR ：sky
 * @CREATETIME ：2018-05-31 14:16
 **/
public class PropertiesUtil {

    private final ResourceBundle resource;
    private final String fileName;

    /**
     * 构造实例化部分对象，获取文件资源对象
     * @param fileName
     */
    public PropertiesUtil(String fileName) {
        this.fileName = fileName;
        Locale locale = new Locale("zh","CN");
        this.resource = ResourceBundle.getBundle(this.fileName,locale);
    }

    /**
     *  根据传入的值，获取value
     * @param key   资源文件对应的key
     * @return
     */
    public String getValue(String key){
        String msg = this.resource.getString(key);
        return msg;
    }

    /**
     * 获取资源文件中的所有key值
     * @return
     */
    public Enumeration<String> getKeys(){
        return resource.getKeys();
    }


}
