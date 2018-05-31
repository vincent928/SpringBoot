package org.springboot.build.demo.springbootbuild.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @DESCRIPTION ：对javamail的封装
 * @AUTHOR ：sky
 * @CREATETIME ：2018-05-31 13:49
 **/
@Data
public class MailEntity implements Serializable{
    //SMTP服务器
    private String smtpService;
    //设置端口号
    private String smtpPort;
    //设置发送邮箱
    private String fromMailAddress;
    //设置发送邮箱的STMP口令
    private String fromMailStmpPwd;
    //设置邮件标题
    private String title;
    //设置邮件内容
    private String content;
    //内容格式(默认采用html)
    private String contentType;
    //接收邮件地址集合
    private List<String> list= new ArrayList<>();
}
