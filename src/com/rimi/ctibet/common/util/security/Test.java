package com.rimi.ctibet.common.util.security;

import java.util.Map;


public class Test {
	
	private String publicKey;  
    private String privateKey;  
    

    /**
     * 生成公钥和私钥
     * @throws Exception
     */
    public   void setUp() throws Exception {  
        Map<String, Object> keyMap = RSACoder.initKey();  
        publicKey = RSACoder.getPublicKey(keyMap);  //公钥app 骑行保留
        privateKey = RSACoder.getPrivateKey(keyMap);  //私钥 门户网站    自己保留
    }  
    
    public static void main(String[] args) throws Exception {
    	 Test t  = new Test();
    	 t.setUp();
    	 t.test();
    	 t.testSign();
    
    }
  
   // @Test  
    public void test() throws Exception {  
    	
        
        String inputStr = "你好";  
        
        byte[] data = inputStr.getBytes();  
  
        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);  
        
        //--------------------base64
        //BASE64Decoder
        
        //将 字节数组转化成 字符串, 然后 字符串变成 字节数组
        String   requestParam = Coder.encryptBASE64(encodedData); //加密参数
        
        
        //解密参数
        byte[] requestParamDecoder = Coder.decryptBASE64(requestParam) ;
        
        
        
        
        encodedData =  requestParamDecoder;
        
        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,  
                privateKey);  
       // System.out.println("加密：字符串加密--"+new String(encodedData));
  
        String outputStr = new String(decodedData);  
        //assertEquals(inputStr, outputStr);  
  
    }  
  
    public void testSign() throws Exception {  
        
        String inputStr = "sign";  
        
        byte[] data = inputStr.getBytes();  
  
        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);  
  
        byte[] decodedData = RSACoder  
                .decryptByPublicKey(encodedData, publicKey);  
  
        String outputStr = new String(decodedData);  
        
        //assertEquals(inputStr, outputStr);  
  
        //产生签名  
        String sign = RSACoder.sign(encodedData, privateKey);
        
        // 验证签名  
        boolean status = RSACoder.verify(encodedData, publicKey, sign);  
       // assertTrue(status);  
  
    }   
	
	
}
