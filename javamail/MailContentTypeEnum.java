package org.springboot.build.demo.springbootbuild.enums;

/**
 * @DESCRIPTION ：
 * @AUTHOR ：sky
 * @CREATETIME ：2018-05-31 14:03
 **/
public enum  MailContentTypeEnum {
    HTML("text/html;charset=UTF-8"),    //HTML格式
    TEXT("text")
    ;

    private String value;

    MailContentTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
