package com.rimi.ctibet.web.pay.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.DesUtil;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.CommonOrder;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Order;
import com.rimi.ctibet.web.service.IActivityService;
import com.rimi.ctibet.web.service.ICommonOrderService;
import com.rimi.ctibet.web.service.IOrderService;
/**
 * 
 * @ClassName: AlipayAction
 * @Description:支付宝支付接口
 * @author 
 * @date 2013-11-21 下午03:15:19
 * 
 */
@Controller
@RequestMapping("alipay")
public class AlipayController extends BaseController implements ServletContextAware {
    
    ServletContext application;
    
    /**
     * 是否对 同步通知接收到的支付宝参数进行转码（乱码时才开启）rimi_alipay.properties中配置
     */
    @Value("${code_return_url}")
    private boolean code_return_url;
    /**
     * 是否对 异步通知(post请求一般不会乱码)接收到的支付宝参数进行转码（乱码时才开启）rimi_alipay.properties中配置
     */
    @Value("${code_notify_url}")
    private boolean code_notify_url;
    
    private Logger log = Logger.getLogger(AlipayController.class);
    
    @Resource IActivityService activityService;
    @Resource IOrderService orderService;
    @Resource ICommonOrderService commonOrderService;
    
    
    // 支付类型
    private static final String payment_type = "1";
    // 卖家支付宝帐户 // 必填
    private static final String seller_email = "info@ctibet.cn";
    
    @RequestMapping("paySuccess")
    public String paySuccess(Model model, String activityCode){
        model.addAttribute("type", "success");
        model.addAttribute("activityCode", activityCode);
        model.addAttribute("programNam","5");
        return "portal/app/activity/pay_for_success";
    }
    @RequestMapping("payFailure")
    public String payFailure(Model model, String activityCode){
        model.addAttribute("type", "failure");
        model.addAttribute("activityCode", activityCode);
        model.addAttribute("programNam","5");
        return "portal/app/activity/pay_for_success";
    }
    
    /**
     * 会员支付
     */
    @RequestMapping("pay")
    public String pay(HttpServletRequest request, HttpServletResponse response, String activityCode){
        log.debug("进入支付");
        LogUser user = getFrontUser();
        if(user==null){
            log.debug("需要登录");
            return render("请登录", "text/html;charset=utf-8", response);
        }
        Activity activity = activityService.findByCode(activityCode);
        Order order = orderService.getOrderByActCodeMemberCode(activityCode, user.getCode());
        if(order==null){
            log.debug("没有订单信息");
            return render("没有订单信息", "text/html;charset=utf-8", response);
        }
        
        // 卖家支付宝帐户
        // 必填
        //String seller_email = "e-learning@rimionline.com";

        // 商户订单号
        // 商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getCode();
        
        // 订单名称
        // 必填
        String subject = activity.getName().trim();

        // 付款金额
        // 必填
        String total_fee = new DecimalFormat("0.00").format(order.getMoney());

        // 订单描述
        String body = activity.getName().trim() + "_报名费";

        // 防钓鱼时间戳
        // 若要使用请调用类文件submit中的query_timestamp函数
        String anti_phishing_key = "";

        // 客户端的IP地址
        // 非局域网的外网IP地址，如：221.0.0.1
        String exter_invoke_ip = "";
        
        //商品展示地址
        String show_url = "";
        
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        
        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.INPUT_CHARSET);
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", basePath + "alipay/notify_url");
        sParaTemp.put("return_url", basePath + "alipay/return_url");
        sParaTemp.put("seller_email", seller_email);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
        sParaTemp.put("extra_common_param", activityCode.trim());
        
        
        // 建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
        if (sHtmlText != null && !"".equals(sHtmlText.trim())) {
            log.debug("跳转到支付宝页面:"+sHtmlText);
            return render(sHtmlText, "text/html;charset=utf-8", response);
        }
        return null;
    }

    /**
     * 渠道支付
     */
    @RequestMapping("channelPay")
    public  String channelPay(HttpServletRequest request, HttpServletResponse response, String chcd){
        log.debug("进入渠道支付");
        //活动code
        String activityCode = "";
        //渠道code
        String orderChannelCode = "";
        
        
        try {
            // 解密并解析字符串
            String decrypt = new DesUtil().decrypt(chcd);
            String[] strings = decrypt.split(",");
            activityCode = strings[0];
            orderChannelCode = strings[1];
        } catch (Exception e) {
            e.printStackTrace();
            return render("解析地址发生错误，请检查链接是否完整。", "text/html", response);
        }
        //保存订单
        Order order = orderService.saveOrderByPayLink(activityCode, orderChannelCode);
        if(order==null){
            return render("订单生成失败。", "text/html", response);
        }
        
        Activity activity = activityService.findByCode(activityCode);
        // 商户订单号
        // 商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getCode();
        
        // 订单名称
        // 必填
        String subject = activity.getName().trim();

        // 付款金额
        // 必填
        String total_fee = new DecimalFormat("0.00").format(order.getMoney());

        // 订单描述
        String body = activity.getName().trim() + "_报名费";

        // 防钓鱼时间戳
        // 若要使用请调用类文件submit中的query_timestamp函数
        String anti_phishing_key = "";

        // 客户端的IP地址
        // 非局域网的外网IP地址，如：221.0.0.1
        String exter_invoke_ip = "";
        
        //商品展示地址
        String show_url = "";
        
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        
        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.INPUT_CHARSET);
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", basePath + "alipay/notify_url");
        sParaTemp.put("return_url", basePath + "alipay/return_url");
        sParaTemp.put("seller_email", seller_email);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
        sParaTemp.put("extra_common_param", activityCode.trim());
        
        // 建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
        if (sHtmlText != null && !"".equals(sHtmlText.trim())) {
            log.debug("跳转到支付宝页面:"+sHtmlText);
            return render(sHtmlText, "text/html;charset=utf-8", response);
        }
        
        return null;
    }
    
    /**
     * 同步通知
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("return_url")
    public String return_url(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
        //if(1==1)return redirect("paySuccess?activityCode=" + "front");
        log.debug("进入同步通知");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            if(this.code_return_url){
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            }
            //log.debug(name + "："+valueStr);
            //System.out.println("t:"+name + "："+valueStr);
            params.put(name, valueStr);
        }
        
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        
        //付款人邮箱
        String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"),"UTF-8");

        //传回的参数
        String extra_common_param = new String(request.getParameter("extra_common_param").getBytes("ISO-8859-1"),"UTF-8");
        
        String activityCode = (StringUtil.isNotNull(extra_common_param))?extra_common_param.trim():extra_common_param;
        log.debug("activityCode");
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        
        //计算得出通知验证结果
        boolean verify_result = AlipayNotify.verify(params);
        
        if(verify_result){//验证成功

            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                try {
                    log.debug("查询订单：" + out_trade_no);
                    Order order = orderService.findByCode(out_trade_no);
                    if(order!=null){
                        if(order.getPayState()==1){
                            log.debug("已付款");
                            return redirect("paySuccess?activityCode=" + activityCode);
                        }else if(order.getPayState()==0){
                            log.debug("开始更新订单：" + out_trade_no);
                            order.setPayState(1);
                            order.setDealTime(DateUtil.getCurrentDate());
                            order.setAlipayOrderCode(trade_no);
                            order.setAlipayEmail(buyer_email);
                            orderService.update(order);
                            log.debug("更新完成：" + out_trade_no);
                            //return render("支付成功", "text/html", response);
                            return redirect("paySuccess?activityCode=" + activityCode);
                        }
                    }else{
                        log.error("支付成功没有订单信息:" + out_trade_no + ",支付宝交易号:" + trade_no);
                        return render("支付成功没有订单信息:" + out_trade_no + ",支付宝交易号:" + trade_no, "text/html",response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("支付成功订单更新出错:" + out_trade_no + ",支付宝交易号:" + trade_no);
                    return render("支付成功订单更新出错:" + out_trade_no + ",支付宝交易号:" + trade_no, "text/html",response);
                }
            }
            
        }else{
            //return render("支付失败(验证失败)", "text/html",response);
            //错误代码
            String parameter = request.getParameter("error_code");
            String error_code = (parameter!=null)?new String(parameter.getBytes("ISO-8859-1"),"UTF-8"):parameter;
            log.error("验证失败(有可能接收参数乱码导致rimi_alipay.properties中配置),订单:" + out_trade_no + ",支付宝交易号:" + trade_no + ",error_code:" + error_code);
            return redirect("payFailure?activityCode=" + activityCode);
        }
        return null;
    }
    
    /**
     * 异步通知
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("notify_url")
    public String notify_url(HttpServletRequest request, PrintWriter out) throws UnsupportedEncodingException{
        log.debug("进入异步通知");
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            if(this.code_notify_url){
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            }
            //log.debug(name + "："+valueStr);
            //System.out.println("y:"+name + "："+valueStr);
            params.put(name, valueStr);
        }
        
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        
        //付款人邮箱
        String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"),"UTF-8");
        
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if(AlipayNotify.verify(params)){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            
            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                try {
                    log.debug("查询订单：" + out_trade_no + ",支付宝交易号:" + trade_no);
                    Order order = orderService.findByCode(out_trade_no);
                    if(order!=null){
                        if(order.getPayState()==1){
                            log.debug("已付款");
                            out.println("success");
                            return null;
                        }else if(order.getPayState()==0){
                            log.debug("更新完成：" + out_trade_no + ",支付宝交易号:" + trade_no);
                            order.setPayState(1);
                            order.setDealTime(DateUtil.getCurrentDate());
                            order.setAlipayOrderCode(trade_no);
                            order.setAlipayEmail(buyer_email);
                            orderService.update(order);
                            log.debug("更新完成：" + out_trade_no + ",支付宝交易号:" + trade_no);
                            out.println("success");
                            return null;
                        }
                    }else{
                        log.error("支付成功没有订单信息:" + out_trade_no + ",支付宝交易号:" + trade_no);
                        out.println("success");
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("支付成功订单更新出错:" + out_trade_no + ",支付宝交易号:" + trade_no);
                    out.println("success");
                    return null;
                }
            }

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                
            out.println("success"); //请不要修改或删除

            //////////////////////////////////////////////////////////////////////////////////////////
        }else{//验证失败
            //错误代码
            String parameter = request.getParameter("error_code");
            String error_code = (parameter!=null)?new String(parameter.getBytes("ISO-8859-1"),"UTF-8"):parameter;
            log.error("验证失败(有可能接收参数乱码导致rimi_alipay.properties中配置),订单:" + out_trade_no + ",支付宝交易号:" + trade_no + ",error_code:" + error_code);
            out.println("fail");
        }
        return null;
    }
    
    
    protected String render(String text, String contentType, HttpServletResponse response) {
        try {
            response.setContentType(contentType);
            response.getWriter().write(text);
        } catch (IOException e) {
        }
        return null;
    }
    
    /********************************************
     * common pay
     ********************************************/
    /**
     * 通用支付
     */
    @RequestMapping(value = "commonPay", method=RequestMethod.POST)
    public String commonPay(HttpServletRequest request, HttpServletResponse response){
        log.debug("进入支付");
        
        boolean isSave = false;
        String commonOrderCode = request.getParameter("commonOrderCode");
        CommonOrder commonOrder = null;
        if(StringUtil.isNotNull(commonOrderCode)){
            commonOrder = commonOrderService.findByCode(commonOrderCode);
        }
        if(commonOrder==null){
            isSave = true;
            //需要接受的全部参数
            String orderCode = request.getParameter("code");
            String name = request.getParameter("name");
            float money = 0;
            try {
                money = new Float(request.getParameter("money"));
            } catch (Exception e) {
                e.printStackTrace();
                money=0;
            }
            String note = request.getParameter("note");
            String successUrl = request.getParameter("successUrl");
            String failureUrl = request.getParameter("failureUrl");
            
            //商品编号
            String goodsCode = request.getParameter("goodsCode");
            //数量
            int num = 0;
            try {
                num = new Integer(request.getParameter("num"));
            } catch (Exception e) {
                e.printStackTrace();
                num = 0;
            }
            //收货人
            String receiver = request.getParameter("receiver");
            //联系电话
            String mobile = request.getParameter("mobile");
            //收货地址
            String address = request.getParameter("address");
            //邮编
            String zipcode = request.getParameter("zipcode");
            //会员code
            String memberCode = request.getParameter("memberCode");
            //类型
            String type = request.getParameter("type");
            //来源
            String source = request.getParameter("source");
            
    
            commonOrder = new CommonOrder();
            commonOrder.setCode(CommonOrder.createCode());
            commonOrder.setOrderCode(orderCode);
            commonOrder.setOrderName(name);
            commonOrder.setMoney(money);
            commonOrder.setNote(note);
            commonOrder.setSuccessUrl(successUrl);
            commonOrder.setFailureUrl(failureUrl);
            commonOrder.setNum(num);
            commonOrder.setReceiver(receiver);
            commonOrder.setMobile(mobile);
            commonOrder.setAddress(address);
            commonOrder.setZipcode(zipcode);
            commonOrder.setMemberCode(memberCode);
            commonOrder.setGoodsCode(goodsCode);
            commonOrder.setType(type);
            commonOrder.setSource(source);
        }
        
        // 卖家支付宝帐户
        // 必填
        //String seller_email = "e-learning@rimionline.com";

        // 商户订单号
        // 商户网站订单系统中唯一订单号，必填
        String out_trade_no = commonOrder.getOrderCode().trim();
        // 订单名称 // 必填
        String subject = commonOrder.getOrderName().trim();
        // 付款金额 // 必填
        String total_fee = new DecimalFormat("0.00").format(commonOrder.getMoney());
        // 订单描述
        String body = commonOrder.getNote().trim();
        // 防钓鱼时间戳
        // 若要使用请调用类文件submit中的query_timestamp函数
        String anti_phishing_key = "";
        // 客户端的IP地址
        // 非局域网的外网IP地址，如：221.0.0.1
        String exter_invoke_ip = "";
        //商品展示地址
        String show_url = "";
        
        
        try {
            if(isSave){
                commonOrderService.save(commonOrder);
            }
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            // 把请求参数打包成数组
            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("notify_url", basePath + "alipay/common_notify_url");
            sParaTemp.put("return_url", basePath + "alipay/common_return_url");
            sParaTemp.put("service", "create_direct_pay_by_user");
            sParaTemp.put("partner", AlipayConfig.partner);
            sParaTemp.put("_input_charset", AlipayConfig.INPUT_CHARSET);
            sParaTemp.put("payment_type", payment_type);
            sParaTemp.put("seller_email", seller_email);
            sParaTemp.put("out_trade_no", out_trade_no);
            sParaTemp.put("subject", subject);
            sParaTemp.put("total_fee", total_fee);
            sParaTemp.put("body", body);
            sParaTemp.put("show_url", show_url);
            sParaTemp.put("anti_phishing_key", anti_phishing_key);
            sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
            sParaTemp.put("extra_common_param", commonOrder.getCode().trim());
            
            // 建立请求
            String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
            if (sHtmlText != null && !"".equals(sHtmlText.trim())) {
                log.debug("跳转到支付宝页面:"+sHtmlText);
                return render(sHtmlText, "text/html;charset=utf-8", response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建定单发生错误");
            render("创建定单发生错误", "text/html", response);
        }
        
        return null;
    }
    
    /**
     * 同步通知
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("common_return_url")
    public String common_return_url(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
        log.debug("进入同步通知");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            if(this.code_return_url){
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            }
            params.put(name, valueStr);
        }
        
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //商品名称
        String subject = new String(request.getParameter("subject").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        //付款人邮箱
        String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"),"UTF-8");
        //传回的参数
        String extra_common_param = new String(request.getParameter("extra_common_param").getBytes("ISO-8859-1"),"UTF-8");
        String orderCode = (StringUtil.isNotNull(extra_common_param))?extra_common_param.trim():extra_common_param;

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        
        //计算得出通知验证结果
        boolean verify_result = AlipayNotify.verify(params);
        
        if(verify_result){//验证成功

            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                
                CommonOrder commonOrder = commonOrderService.findByCode(orderCode);
                try {
                    if(commonOrder!=null){
                        if(commonOrder.getPayState()==0){
                            commonOrder.setPayState(1);
                            commonOrder.setAlipayEmail(buyer_email);
                            commonOrder.setAlipayOrderCode(trade_no);
                            commonOrder.setDealTime(DateUtil.getCurrentDate());
                            commonOrderService.update(commonOrder);
                        }
                        //System.out.println(commonOrder.getSuccessUrl());
                        
                        if(commonOrder.getType()!=null && commonOrder.getType().equals(CommonOrder.TYPE_EQUIPMENT)){
                            return redirect("/common-pay/success");
                        }else{
                            return redirect(commonOrder.getSuccessUrl());
                        }
                    }else{
                        log.error("支付成功没有订单信息，订单号：" + out_trade_no + "，支付宝交易号：" + trade_no + "，商品名称：" + subject);
                        return render(
                            "支付成功没有订单信息，订单号：" + out_trade_no + "，支付宝交易号：" + trade_no + "，商品名称：" + subject
                            , "text/html",response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("支付成功更新订单信息出错，订单号：" + out_trade_no + "，支付宝交易号：" + trade_no + "，商品名称：" + subject);
                    if(commonOrder!=null && StringUtil.isNotNull(commonOrder.getFailureUrl())){
                        if(commonOrder.getType()!=null && commonOrder.getType().equals(CommonOrder.TYPE_EQUIPMENT)){
                            return redirect("/common-pay/failure");
                        }else{
                            return redirect(commonOrder.getFailureUrl());
                        }
                    }else{
                        return render("支付成功更新订单信息出错，订单号：" + out_trade_no + "，支付宝交易号：" + trade_no + "，商品名称：" + subject, "text/html",response);
                    }
                }
                
            }
            
        }else{
            //错误代码
            String parameter = request.getParameter("error_code");
            String error_code = (parameter!=null)?new String(parameter.getBytes("ISO-8859-1"),"UTF-8"):parameter;
            log.error("验证失败(有可能接收参数乱码导致rimi_alipay.properties中配置),订单:" + out_trade_no + ",支付宝交易号:" + trade_no + ",error_code:" + error_code);
           
        }
        return null;
    }
    /**
     * 异步通知
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("common_notify_url")
    public String common_notify_url(HttpServletRequest request, PrintWriter out) throws UnsupportedEncodingException{
        log.debug("进入异步通知");
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            if(this.code_notify_url){
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            }
            params.put(name, valueStr);
        }
        
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //商品名称
        String subject = new String(request.getParameter("subject").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        //付款人邮箱
        String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"),"UTF-8");
        //传回的参数
        String extra_common_param = new String(request.getParameter("extra_common_param").getBytes("ISO-8859-1"),"UTF-8");
        String orderCode = (StringUtil.isNotNull(extra_common_param))?extra_common_param.trim():extra_common_param;
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if(AlipayNotify.verify(params)){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            
            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                
                try {
                    CommonOrder commonOrder = commonOrderService.findByCode(orderCode);
                    if(commonOrder!=null){
                        if(commonOrder.getPayState()==0){
                            commonOrder.setPayState(1);
                            commonOrder.setAlipayEmail(buyer_email);
                            commonOrder.setAlipayOrderCode(trade_no);
                            commonOrder.setDealTime(DateUtil.getCurrentDate());
                            commonOrderService.update(commonOrder);
                        }
                        out.println("success");
                        return null;
                    }else{
                        log.error("支付成功没有订单信息，订单号：" + out_trade_no + "，支付宝交易号：" + trade_no + "，商品名称：" + subject);
                        out.println("success");
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("支付成功更新订单信息出错，订单号：" + out_trade_no + "，支付宝交易号：" + trade_no + "，商品名称：" + subject);
                    out.println("success");
                    return null;
                }
                
            }

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                
            out.println("success"); //请不要修改或删除

            //////////////////////////////////////////////////////////////////////////////////////////
        }else{//验证失败
            //错误代码
            String parameter = request.getParameter("error_code");
            String error_code = (parameter!=null)?new String(parameter.getBytes("ISO-8859-1"),"UTF-8"):parameter;
            log.error("验证失败(有可能接收参数乱码导致rimi_alipay.properties中配置),订单:" + out_trade_no + ",支付宝交易号:" + trade_no + ",error_code:" + error_code);
            out.println("fail");
        }
        return null;
    }
    
    /********************************************
     * Setter Getter
     ********************************************/
    @Override
    public void setServletContext(ServletContext arg0) {
        this.application = arg0;
    }
    
    public static void main(String[] args) {
        float d = 123f;
        //System.out.println(d);
        //System.out.println(new DecimalFormat("0.00").format(d));
        System.out.println(new Boolean("asdafdsaf"));
    }
    
}
