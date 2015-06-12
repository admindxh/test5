package com.rimi.ctibet.common.sortmsg;

import javax.xml.rpc.ServiceException;

public class SendSMS {
	public static void main(String[] args) throws Exception {
//		SDKServiceBindingStub binding= (SDKServiceBindingStub) new SDKService_ServiceLocator().getSDKService();
//        
//		//new SDKServiceBindingStub();
//		String[] call={"18200158896"};
//		int i=binding.sendSMS("9SDK-EMY-0229-JCXPO", "rimionline123456", "", call, "早上好！今天辛苦了【睿峰科技】", "", "", 5,10690229229991L);
//		//System.out.println("结果:"+i);
		String mess = "你的互动营销代理商账户于【成都睿峰科技】";
        //SDKService.SDKService service = new SDKService.SDKService();
		SDKServiceBindingStub binding= (SDKServiceBindingStub) new SDKService_ServiceLocator().getSDKService();
        //Service.registEx(ManageAppSettings.MessageSequence, ManageAppSettings.MessageKey, ManageAppSettings.MessagePass);
        String[] tel = new String[]{"18249552561"};
        int ss=binding.sendSMS(Parameter.messageSequence, Parameter.messageKey, "", tel, mess, "", "", 5, Parameter.messageAccess);
        //System.out.println(ss);
		
	}
	public static boolean send(String msg,String[] phone) throws Exception{
		//System.out.println("发送短信的手机号码-------->"+phone[0]);
		if(msg==null||"".equals(msg)||phone==null||phone.length==0) return false;
		SDKServiceBindingStub binding= (SDKServiceBindingStub) new SDKService_ServiceLocator().getSDKService();
        int result=binding.sendSMS(Parameter.messageSequence, Parameter.messageKey, "", phone, msg, "", "", 5, Parameter.messageAccess);
        return result==0;
	}
	
	
}
