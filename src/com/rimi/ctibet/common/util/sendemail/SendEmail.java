package com.rimi.ctibet.common.util.sendemail;

import javax.mail.*;

/**
 * 发送Email
 * @author xiangwq
 *
 */
public class SendEmail {
	
//	public static boolean sendCheckEmail(ClientUser user,HttpServletRequest request) {
//		String code=Security.getInstance().encode((System.currentTimeMillis()+3600000000L)+","+user.getId()+","+user.getEmail());//加密
//		
//		StringBuffer send = new StringBuffer("");
//		send.append("<font face=\"Arial Narrow\"><font color=\"#909090\"><span style=\"font-size: 12px; line-height: 18px;\">您好，尊敬的睿峰苹果在线教育客户。 你的账号</span></font><font color=\"#ff0000\" style=\"font-size: 12px; line-height: 18px;\">");
//		send.append(user.getUsername()).append("</font><font color=\"#909090\" style=\"font-size: 12px; line-height: 18px;\">已经注册成功，请</font><a href=\"")
//			.append(getBasePath(request))
//			.append("regist/passEmail.html?code=")
//			.append(code)
//			.append("\" style=\"font-size: 12px; line-height: 18px; color: rgb(144, 144, 144);\">点击验证</a><font color=\"#909090\" style=\"font-size: 12px; line-height: 18px;\">&nbsp;，即可通过！</font></font><div><font color=\"#909090\" face=\"Arial Narrow\"><span style=\"font-size: 12px; line-height: 18px;\">&nbsp; &nbsp; 祝： 学习愉快！</span></font></div>");
//		
//		return AsyncUtils.sendMail(new String[]{user.getEmail()}, "睿峰苹果在线教育邮箱验证", send.toString());
//	}
	
//	public static boolean sendFindEmail(ClientUser user,HttpServletRequest request) {
//		StringBuffer send = new StringBuffer("");
//		String code=Security.getInstance().encode((System.currentTimeMillis()+1200000)+","+LoginMD5.compile(user.getUsername()+user.getId()+user.getEmail())+","+user.getId());
//		send.append("您好，尊敬的睿峰苹果在线教育客户。 你的账号<b><font color=\"#ff0000\">")
//			.append(user.getUsername())
//			.append("</font></b>，申请密码找回。请在20分钟内找回密码，<a href=\"")
//			.append(getBasePath(request)).append("regist/changPassword.html?code=")
//			.append(code)
//			.append("\">点击找回</a><div>&nbsp; &nbsp;请注意保管！</div>");
//			
//		return AsyncUtils.sendMail(new String[]{user.getEmail()}, "睿峰苹果在线教育密码找回", send.toString());
//	}
	
	// 发送邮件的服务器的IP和端口   
    public static final String MAILSERVERHOST = "smtp.163.com";   
    public static final String MAILSERVERPORT = "25";   
    // 邮件发送者的地址   
    public static final String FROMADDRESS = "tianshangxizang123@163.com";   
    // 邮件接收者的地址   
//    public static final String toAddress = "";   
    // 登陆邮件发送服务器的用户名和密码   
    public static final String USERNAME = "tianshangxizang123@163.com";   
    public static final String PASSWORD = "tianshangxizang";   
    // 是否需要身份验证   
    private static boolean validate = true;   
    // 邮件主题   
    public static final String subject = "天上西藏邮箱验证";    
    // 邮件的文本内容   
//    public static final String content = "";   
    // 邮件附件的文件名   
//    public static final String[] attachFileNames = "";     
	
    public static void send(String email,String content){
    	MailSenderInfo mailinfo = new MailSenderInfo();
    	mailinfo.setToAddress(email);
    	mailinfo.setContent(content);
    	mailinfo.setFromAddress(SendEmail.FROMADDRESS);
    	mailinfo.setMailServerHost(SendEmail.MAILSERVERHOST);
    	mailinfo.setMailServerPort(SendEmail.MAILSERVERPORT);
    	mailinfo.setPassword(SendEmail.PASSWORD);
    	mailinfo.setSubject(SendEmail.subject);
    	mailinfo.setUserName(SendEmail.USERNAME);
    	mailinfo.setValidate(SendEmail.validate);
    	
    	//调用工具类
    	SimpleMailSender.sendHtmlMail(mailinfo);
    }
    
    
    public static void main(String[] args) {
		//发送邮件
		
	}
    
    
    
    
    
	
}
