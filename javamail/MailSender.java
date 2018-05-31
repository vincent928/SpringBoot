package org.springboot.build.demo.springbootbuild.utils;
import org.springboot.build.demo.springbootbuild.entity.MailEntity;
import org.springboot.build.demo.springbootbuild.enums.MailContentTypeEnum;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.List;
import java.util.Properties;

/**
 * @DESCRIPTION ：用于发送邮件
 * @AUTHOR ：sky
 * @CREATETIME ：2018-05-31 13:55
 **/
public class MailSender {
    //邮件实体
    private static MailEntity mail = new MailEntity();

    /**
     * 设置邮件的标题
     * @param title 标题
     * @return
     */
    public MailSender title(String title){
        mail.setTitle(title);
        return this;
    }

    /**
     * 设置邮件的内容
     * @param content 内容
     * @return
     */
    public MailSender content(String content){
        mail.setContent(content);
        return this;
    }

    /**
     * 设置邮件格式 默认使用html
     * @param contentTypeEnum
     * @return
     */
    public MailSender contentType(MailContentTypeEnum contentTypeEnum){
        mail.setContentType(contentTypeEnum.getValue());
        return this;
    }

    /**
     * 设置邮件目标地址
     * @param target    地址集合
     * @return
     */
    public MailSender targets(List<String> target){
        mail.setList(target);
        return this;
    }

    /**
     * 执行发送邮件
     * @throws Exception 发送失败抛出异常信息
     */
    public void send() throws Exception{
        //默认使用html发送邮件
        if (mail.getContentType() == null){
            mail.setContentType(MailContentTypeEnum.HTML.getValue());
        }
        if (mail.getTitle() == null || mail.getTitle().trim().length() == 0){
            throw new Exception("尚未设置邮件标题,请调用title方法进行设置");
        }
        if (mail.getContent() == null || mail.getContent().trim().length() == 0){
            throw new Exception("尚未设置邮件内容,请调用content方法进行设置");
        }
        if (mail.getList().size() == 0){
            throw new Exception("尚未设置接收者邮箱地址,请调用targets方法进行设置");
        }
        //读取/resource/mail_zh_CN.properties文件内容
        final PropertiesUtil properties = new PropertiesUtil("mail");
        //创建Properties 类用于记录邮箱的一些属性
        final Properties props = new Properties();
        //表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth","true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host",properties.getValue("mail.smtp.service"));
        //设置端口号，QQ邮箱给出了两个端口号465/587
        props.put("mail.smtp.port",properties.getValue("mail.smtp.port"));
        //设置发送邮箱
        props.put("mail.user",properties.getValue("mail.from.address"));
        //设置发送邮箱的16位STMP口令
        props.put("mail.password",properties.getValue("mail.from.smtp.pws"));

        //构建授权信息,用于进行SMTP身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName,password);
            }
        };
        //使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props,authenticator);
        //创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        //设置发件人
        String nickName = MimeUtility.encodeText(properties.getValue("mail.from.nickname"));
        InternetAddress form = new InternetAddress(nickName + "<" + props.getProperty("mail.user") + ">");
        message.setFrom(form);

        //设置邮件标题
        message.setSubject(mail.getTitle());
        //html发送邮件
        if (mail.getContentType().equals(MailContentTypeEnum.HTML.getValue())){
            //设置邮件内容体
            message.setContent(mail.getContent(),mail.getContentType());
        }
        //文本发送邮件
        else if (mail.getContentType().equals(MailContentTypeEnum.TEXT.getValue())){
            message.setText(mail.getContent());
        }
        //发送邮箱地址
        List<String> targets = mail.getList();
        for (int i =0 ; i < targets.size();i++){
            try {
                //设置收件人邮箱
                InternetAddress to = new InternetAddress(targets.get(i));
                message.setRecipient(Message.RecipientType.TO, to);
                //发送邮件
                Transport.send(message);
            }catch (Exception e){
                continue;
            }
        }

    }



}