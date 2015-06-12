package com.rimi.ctibet.common.controller;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.FileHelper;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.web.controller.UserInfoController;
import com.rimi.ctibet.web.controller.vo.InfoLinkManage;
import com.rimi.ctibet.web.controller.vo.UserVO;


public class BaseController {
    
    public static final    Logger log = Logger.getLogger(BaseController.class);
    
	//@Autowired
	protected HttpSession session; 
	
	protected HttpServletRequest request;  
	
	protected HttpServletResponse response;
	@SuppressWarnings("unused")
	@ModelAttribute
	private void handlerRequestAndResponse(HttpServletRequest request,HttpServletResponse response){
		this.request=request;		
		InfoLinkManage.path = request.getSession().getServletContext().getRealPath("/");//绝对路径
		InfoLinkManage.appliction=request.getSession().getServletContext();
		LuceneUtil2.path=request.getSession().getServletContext().getRealPath("/")+"lucene";//绝对路径
		this.response=response;
		this.session = request.getSession();
	}
	protected ModelAndView jsonView(Object data){
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson json=new Gson();
		return new ModelAndView("json").addObject("data",json.toJson(data));
	}
	/**
	 * 输出json
	 * @param json
	 */
	protected void writerJson(String json) {
		try {
			response.getWriter().write(json);
			response.getWriter().flush();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 输出json
	 * @param json
	 */
	protected void writerJson(Object obj) {
		try {
	        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();  
			response.getWriter().write(gson.toJson(obj));
			response.getWriter().flush();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 输出JSON成功消息，返回null
	protected String ajaxJsonSuccessMessage(String message) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("message", message);
		return ajax(jsonObject.toString(), "text/html");
	}
	// AJAX输出，返回null
	public String ajax(String content, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// forward
	public String forward(String path){
		return "forward:" + path;
	}
	// redirect
	public String redirect(String path){
		return "redirect:" + path;
	}
	
	/**
	 * 通过指定name检查用户是否进行了某操作(有值说明已进行过操作返回true)
	 * @param name
	 * @return
	 */
	public boolean checkSessionOperate(HttpSession session, String name){
		String attribute = (String) session.getAttribute(name);
		if(StringUtil.isNotNull(attribute)){
			return true;
		}else{
			session.setAttribute(name, "value");
			return false;
		}
	}
	/**
	 * 通过指定name检查用户是否进行了某操作(有值说明已进行过操作返回true)
	 * @param name
	 * @return
	 */
	public boolean checkSessionOperate2(HttpSession session, String name){
		String attribute = (String) session.getAttribute(name);
		if(StringUtil.isNotNull(attribute)){
			return true;
		}else{
			//session.setAttribute(name, "value");
			return false;
		}
	}
	
	
	/**
	 * 获取前台用户会员
	 * @return
	 */
	public LogUser  getFrontUser(){
		return 	this.session.getAttribute("logUser")==null?null:(LogUser)this.session.getAttribute("logUser");
	}
	
	/**
	 * 获取后台用户
	 * @return
	 */
	public UserVO  getManagerUser(){
		UserVO  userVO =  this.session.getAttribute(UserInfoController.SESSION_USER)==null?null:(UserVO)this.session.getAttribute(UserInfoController.SESSION_USER);
		 return userVO;
	}
	/**
	 *获得url
	 *@param code 是查看详细需要的参数
	*/
	public String getUrl(String mvcUrl,String code){
		InetAddress myIPaddress = null;
		try {
			myIPaddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        String mi = mvcUrl;
        if(StringUtils.isNotBlank(code))
        	mi +="?code="+code;
        //System.out.println(mi);
        return mi;
	}
	/**
	 *获得url
	 *@param code 是查看详细需要的参数
	*/
	public String getUrlHtml(String mvcUrl,String code){
		InetAddress myIPaddress = null;
		try {
			myIPaddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        String mi = mvcUrl;
        if(StringUtils.isNotBlank(code))
        	mi +="/"+code+".html";
        //System.out.println(mi);
        return mi;
	}
	
	/**
	 * 对象转json字符串
	 */
	public String obj2json(Object obj){
		return new Gson().toJson(obj);
	}
	/**
	 * 获取项目请求路径 root
	 * @param request
	 * @return
	 */
	public  String getURI(HttpServletRequest request) {
		UrlPathHelper helper = new UrlPathHelper();
		String ctx = helper.getOriginatingContextPath(request);
	    String	url  =  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+ctx;
		return url;
	}
	
	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public  String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
	
	public static void main(String[] args) {
		 String  a  =  "<p style=\"text-align: center;\"> <br/><span style=\"font-size: 14px;\"><span>是打发士大夫速度范围是打发士大夫是</span></p><p><img src=\"/ctibet/upload/img/post/20141218/1418877026956073282.jpg\" title=\"1418877026956073282.jpg\" alt=\"Tulips.jpg\"/><span>是打发士大夫</span><img src=\"/ctibet/upload/img/post/20141218/1418877043633067558.jpg\" title=\"1418877043633067558.jpg\" alt=\"Lighthouse.jpg\"/></span>";
		 Pattern  pattern   = Pattern.compile("<img.*?>");
		 Matcher  m = pattern.matcher(a);
		 Pattern pSrc  = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)");
		// Matcher mSrc  = pSrc.matcher(input)
		 while(m.find()){
			 //System.out.println(m.group());
			 String img  = m.group();
				Matcher mScr  = pSrc.matcher(img);
				if (mScr.find()) {
					 //System.out.println(mScr.group().replaceAll("src=\"", "").replaceAll("\"", ""));
					
					 //获取地址
				}
		 }
	}
	
	/**
	 * 获取内容中的第一个图片
	 * @param reg
	 * @param content
	 * @return
	 */
	public String  getContentImgSrc(String content){
		String  a  =  content;
		 Pattern  pattern   = Pattern.compile("<img.*?>");
		 Matcher  m = pattern.matcher(a);
		 Pattern pSrc  = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)");
		// Matcher mSrc  = pSrc.matcher(input)
		 String  src  = "";
		 while(m.find()){
			 //System.out.println(m.group());
			    String img  = m.group();
				Matcher mScr  = pSrc.matcher(img);
				if (mScr.find()) {
					src  =  mScr.group().replaceAll("src=\"", "").replaceAll("\"", "");
					 //获取地址
				}
				break;
		 }
		 return   src;
	}
	public String getContentImgSrc(){
		return  "";
	}
	
	/**
	 * form表单接收上传的个文件,文件存储你指定目录下的当前年月的目录中，文件名加了前缀
	 * <br>在指定文件保存的基础目录（dir）建立当前年月的目录，目录中保存文文件 
	 * <br>文件会被重命名,重名名规则在原文件名上加前缀前缀为7位数字
	 * <br>如:image.jpg会重命名为^\d{7}image.jpg
	 * @param request
	 * @param response
	 * @param dir 文件保存的基础路径
	 * @return 所有文件保存路径的
	 */
	public List<String> uploadFile(HttpServletRequest request,
			HttpServletResponse response,String dir){
		List<MultipartFile> files = FileHelper.getFileSet(request,
				200 * 1024 * 1024, null, "file");
		String path = request.getSession().getServletContext().getRealPath("/");//绝对路径
		return  saveFile(files,path, dir);
	}
	
	/**
	 * <br>多个文件保存到指定目录中
	 * <br>在指定目录dir建立当 
	 * <br>文件会被重命名,重名名规则在原文件名上加前缀前缀为7位数字
	 * <br>如:image.jpg会重命名为^\d{7}image.jpg
	 * @param files MultipartFile 组
	 *@param path 文件保存根路径
	 * @param dir 指定的目录
	 */
	private List<String> saveFile(List<MultipartFile> files,String path,String dir){
		if(!StringUtil.isNotNull(dir)){dir="";}
		if(!StringUtil.isNotNull(path)){path="";}
		List<String> listPath = new ArrayList<String>();
		Date date = new Date();
		String filePre=(date.getTime()+"").substring(6);//
		String yyyyMM= DateUtil.formatDateTime(date, "yyyyMM");
		String currDir="/"+yyyyMM+"/";
		String ralativePath =  dir+"/"+currDir;
		ralativePath=ralativePath.replaceAll("/+", "/");//替换多余的的"/"
		String absolutePath=(path+"/"+ralativePath).replaceAll("/+", "/");
		File fileDir= new File(path);
		if(!fileDir.exists())
		{
			fileDir.mkdirs();
		}
		for (MultipartFile file : files) {
			try {
				String filename=filePre+file.getOriginalFilename();
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(
						absolutePath, filename));
				listPath.add((ralativePath+"/"+filename).replaceAll("/+", "/"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listPath;
	}
	
    public final String downloadFile(File file, String fileName) {
        try {
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            if(file.exists()){
                byte[] b = new byte[1024];
                int i = 0;
                FileInputStream in = new FileInputStream(file);
                OutputStream out = response.getOutputStream();
                while ((i=in.read(b))>0) {
                    out.write(b, 0, i);
                }
                in.close();
                response.flushBuffer();
                out.close();
            }else{
                response.sendError(HttpServletResponse.SC_NO_CONTENT, "文件不存在");
            }
            return "success";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    /**
     * 导出Excel
     * @param titles    excel标题
     * @param properties    对象属性名
     * @param datalist  数据
     */
    public <X> void exportExcel(HttpServletResponse response, String fileName, String[] titles, String[] properties, List<X> datalist) {
        exportExcel(response, fileName, titles, properties, datalist, null);
    }
    /**
     * 导出Excel
     * @param titles    excel标题
     * @param properties    对象属性名
     * @param datalist  数据
     * @param dateFormatPattern  时间格式（yyyy-MM-dd等）
     */
    public <X> void exportExcel(HttpServletResponse response, String fileName, String[] titles, String[] properties, List<X> datalist, String dateFormatPattern) {
        WritableWorkbook book = null;
        Set<String> set = new HashSet<String>();
        if(titles!=null && titles.length>0 && properties!=null && properties.length>0){
            if(titles.length==properties.length){
                try {
                    response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
                    response.setContentType("application/octet-stream; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    // 打开文件
                    book = Workbook.createWorkbook(response.getOutputStream());
                    // 生成工作表，参数0表示这是第一页
                    WritableSheet sheet = book.createSheet(fileName, 0);
                    WritableFont titleFont = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
                    WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
                    titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
                    titleFormat.setBackground(jxl.format.Colour.GRAY_25);
                    for (int i = 0; i < titles.length; i++) {
                        Label label = new Label(i, 0, titles[i], titleFormat);
                        sheet.addCell(label);
                        sheet.setColumnView(i, 25);
                    }
                    int row = 1;
                    for (X x : datalist) {
                        for (int i = 0; i < properties.length; i++) {
                            String propertyName = properties[i];
                            Object value = null;
                            try {
                                Method readMethod = new PropertyDescriptor(propertyName, x.getClass()).getReadMethod();
                                value = readMethod.invoke(x);
                                if(value!=null && dateFormatPattern!=null && !dateFormatPattern.equals("")){
                                    if(value instanceof Date || value instanceof Timestamp){
                                        value = new SimpleDateFormat(dateFormatPattern).format(value);
                                    }
                                }
                                
                            } catch (IntrospectionException e) {
                                set.add(propertyName);
                            }
                            Label label = new Label(i, row, value==null?null:value.toString());
                            sheet.addCell(label);
                        }
                        row++;
                    }
                    if(set.size()>0){
                        String info = "-> 未发现以下属性：";
                        for (String s : set) {
                            info += s + ",";
                        }
                        log.debug(info);
                    }
                    // 写入数据并关闭文件
                    book.write();
                    book.close();
                } catch (Exception e) {
                    try {
                        response.sendError(HttpServletResponse.SC_NO_CONTENT, "文件不存在");
                        e.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }else{
                log.debug("标题,属性长度不一致.");
            }
        }else{
            log.debug("标题,属性必传.");
        }
    }
    public String getImgDef(){
    	
    	String   webRoot = "";
    	return "portal/static/default/square.png";
    }
	
	
}