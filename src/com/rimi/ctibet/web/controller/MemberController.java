package com.rimi.ctibet.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.LoginMD5;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberEmail;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.MemberMobile;
import com.rimi.ctibet.web.service.IMemberService;

@Controller
@RequestMapping("/web/member/")
public class MemberController extends BaseController {
   @Resource
   private IMemberService memberService;
   
   
   /**
    * 用户列表
    * @param req
    * @param pageNum
    * @param keywords
    * @param status
    * @param memberType
    * @return
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value="list",produces="text/html;charset=utf-8")
   public String list(HttpServletRequest req,@RequestParam(defaultValue="1",required=false)int currentPage,String keywords,String status,String memberType){
	   Pager pager=new Pager();
	    pager.setCurrentPage(currentPage);
	    pager.setPageSize(20);
	   Map map=new HashMap();
	    if(StringUtils.isNotBlank(keywords)){
	    	map.put("keywords",keywords);
	    }
	    if(StringUtils.isNotBlank(status)){
	    	map.put("status",status);
	    }
	    if(StringUtils.isNotBlank(memberType)){
	    	map.put("memberType",memberType);
	    }
	    memberService.getMemberList(map, pager);
	    req.setAttribute("pager", pager);
	    req.setAttribute("keywords", keywords);
	    req.setAttribute("status", status);
	    req.setAttribute("memberType", memberType);
	   return "manage/html/settings/member_list";
   }
   
   /**
    * 跳转到新增页面
    * @param req
    * @return
    */
   @RequestMapping("add")
   public String add(HttpServletRequest req){
	   req.setAttribute("flag", "save");
	   return "manage/html/settings/member_add";
   }
   
   /**
    * 验证邮箱或电话是否被使用
    * @param phone
    * @param email
    * @return
    * @throws JSONException
    */
   @RequestMapping(value="checkUnique",produces="text/html;charset=utf-8")
   @ResponseBody
   public String checkUnique(String phone,String email,String name) throws JSONException{
	    JSONObject obj=new JSONObject();
	   //验证电话号码是否重复
	    if(StringUtils.isNotBlank(phone)){
	     List<MemberMobile> mlist=memberService.findMobileByPro("mobile", phone);
		 if(mlist.size()>0){
			 obj.put("data", 1);
			 return obj.toString();
		 }
	    }
	    if(StringUtils.isNotBlank(email)){
		     //验证邮箱是否重复
			 List<MemberEmail> elist=memberService.findEmailByPro("email", email);
			 if(elist.size()>0){
				 obj.put("data", 1);
				return obj.toString(); 
			 }
	    }
	    if(StringUtils.isNotBlank(name)){
		     //验证昵称是否重复
	    	List<MemberInfo> elist1=memberService.findInfoByPro("name", name);
			 if(elist1.size()>0){
				 obj.put("data", 1);
				return obj.toString(); 
			 }
	    }
		 obj.put("data", 0);
		
		return obj.toString();
   }
   /**
    * 验证邮箱或电话是否被使用（修改）
    * @param phone
    * @param email
    * @return
    * @throws JSONException
    */
   @RequestMapping(value="checkUniqueM",produces="text/html;charset=utf-8")
   @ResponseBody
   public String checkUniqueM(String phone,String email,String code,String name) throws JSONException{
	    JSONObject obj=new JSONObject();
	   //验证电话号码是否重复
	    if(StringUtils.isNotBlank(phone)){
	     List<MemberMobile> mlist=memberService.findMobileByPro("mobile", phone);
		 if(mlist.size()>0){
			   MemberMobile mm=mlist.get(0);
			   if(mm!=null){
				  if(mm.getMemberCode()!=null){
				   if(!code.equals(mm.getMemberCode())){
					   obj.put("data", 1);
					   return obj.toString();
					}
				  }
			   }
			}
	    }
	    if(StringUtils.isNotBlank(email)){
		     //验证邮箱是否重复
			 List<MemberEmail> elist=memberService.findEmailByPro("email", email);
			 if(elist.size()>0){
				 MemberEmail me=elist.get(0);
				 if(me!=null){
					 if(!code.equals(me.getMemberCode())){
						 obj.put("data", 1);
					   return obj.toString(); 
					 }
				 }
			}
	    }
	    if(StringUtils.isNotBlank(name)){
		     //验证昵称是否重复
	    	List<MemberInfo> elist1=memberService.findInfoByPro("name", name);
			 if(elist1.size()>0){
				 MemberInfo me=elist1.get(0);
				 if(me!=null){
					 if(!code.equals(me.getMemberCode())){
						 obj.put("data", 1);
					   return obj.toString(); 
					 }
				 }
			}
	    }
		 obj.put("data", 0);
		
		return obj.toString();
   }
   
   /**
    * 新建用户
    * @param req
    * @param sex
    * @param file
    * @param email
    * @param phone
    * @param username
    * @param password
    * @param birthday
    * @param province
    * @param city
    * @param score
    * @return
    * @throws ParseException
    */
   @RequestMapping(value="save")
   public String save(HttpServletRequest req,String sex,String pic, String email,String phone,String username,String password,String birthday,String province,String city,String score) throws ParseException{
	   String chk=check(req, "save", sex, email, phone, username, password, birthday, province, city, score);
	   SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
	    if(StringUtils.isNotBlank(chk)){
	    	 req.setAttribute("error", chk);
	    	 if(StringUtils.isNotBlank(birthday))
	    	 req.setAttribute("birthday", fmt.parse(birthday));
	    	 req.setAttribute("province", province);
	    	 req.setAttribute("pic", pic);
	    	 req.setAttribute("city", city);
	    	 req.setAttribute("flag", "save");
	    	 return "manage/html/settings/member_add";
	    }
	   //验证电话号码是否重复
	    if(StringUtils.isNotBlank(phone)){
	     List<MemberMobile> mlist=memberService.findMobileByPro("mobile", phone);
		 if(mlist.size()>0){
			 if(StringUtils.isNotBlank(birthday))
			  req.setAttribute("birthday", fmt.parse(birthday));
			  req.setAttribute("phoneExsist", 1);
			  req.setAttribute("province", province);
			  req.setAttribute("pic", pic);
		      req.setAttribute("city", city);
		      req.setAttribute("flag", "save");
			  return "manage/html/settings/member_add";
		 }
	    }
	     //验证邮箱是否重复
	    if(StringUtils.isNotBlank(email)){
		 List<MemberEmail> elist=memberService.findEmailByPro("email", email);
		 if(elist.size()>0){
			 if(StringUtils.isNotBlank(birthday))
			 req.setAttribute("birthday", fmt.parse(birthday));
			 req.setAttribute("emailExsist", 1);
			 req.setAttribute("province", province);
			 req.setAttribute("pic", pic);
	    	 req.setAttribute("city", city);
	    	 req.setAttribute("flag", "save");
			 return "manage/html/settings/member_add";
		}
       }
	  //验证昵称是否重复
//	  if(StringUtils.isNotBlank(username)){
//	    List<MemberInfo> elist1=memberService.findInfoByPro("name", username);
//		if(elist1.size()>0){
//			 if(StringUtils.isNotBlank(birthday))
//			 req.setAttribute("birthday", fmt.parse(birthday));
//			 req.setAttribute("nameExsist", 1);
//			 req.setAttribute("province", province);
//			 req.setAttribute("pic", pic);
//	    	 req.setAttribute("city", city);
//	    	 req.setAttribute("flag", "save");
//			 return "manage/html/settings/member_add";
//		}
//      }
	  //用户表
	   Member m=new Member();
	    m.setCode(Uuid.uuid());
	    m.setAvaliable(1);
	    m.setCreateTime(new Timestamp(new Date().getTime()));
	    m.setMemberType(0);
	    m.setPassword(LoginMD5.compile(password));
	    m.setStatus(1);
	  //用户信息表
	   MemberInfo mi=new MemberInfo();
	    mi.setAvaliable(1);
	   if(StringUtils.isNotBlank(birthday))
	    mi.setBirthday(fmt.parse(birthday));
	    mi.setCity(city);
	    mi.setCode(Uuid.uuid());
	    mi.setEmail(email);
	    mi.setMemberCode(m.getCode());
	    mi.setName(username);
	    mi.setPhone(phone);
//	    //上传头像
//	    if(!file.isEmpty()){
//	    	if(!storeFile(req,file)){
//	    		req.setAttribute("fileError", 1);
//	    		return "manage/html/settings/member_add";
//	    	}
//	    	String pic=(String)req.getSession().getAttribute("userPic");
//	    	if(pic!=null)
//	    	mi.setPic(pic);
//	    }
	    if(StringUtils.isNotBlank(pic))
	    	mi.setPic(pic);
	    mi.setProvince(province);
	   if(StringUtils.isNotBlank(score))
	    mi.setScore(Integer.parseInt(score));
	   if(StringUtils.isNotBlank(sex))
	    mi.setSex(Integer.parseInt(sex));
	   //用户邮箱
	   MemberEmail me=new MemberEmail();
	    me.setCode(Uuid.uuid());
	    me.setAvaliable(1);
	    me.setEmail(email);
	    me.setMemberCode(m.getCode());
	    me.setIsBind(1);
	   //用户手机
	    MemberMobile mm=new MemberMobile();
	    mm.setAvaliable(1);
	    mm.setCode(Uuid.uuid());
	    mm.setIsVerified(1);
	    mm.setMemberCode(m.getCode());
	    mm.setMobile(phone);
	    mm.setVerifyTime(new Timestamp(new Date().getTime()));
	   try {
		   memberService.saveByModel(m, mi, me,mm);
		   req.setAttribute("saveflag", 1);
		} catch (Exception e) {
			req.setAttribute("saveflag", 0);
			e.printStackTrace();// TODO: handle exception
		}
		req.getSession().removeAttribute("userPic");
	   return list(req,1,"","","");
   }
   /**
    * 跳转到修改页
    * @param req
    * @param code
    * @return
    */
   @RequestMapping("detail")
   public String detail(HttpServletRequest req,String code){
	 if(StringUtils.isNotBlank(code)){
		   List<MemberInfo> list=memberService.findbymecode(code);
		   if(list.size()>0){
			   MemberInfo mi=list.get(0);
			   if(mi!=null){
				   if(mi.getBirthday()!=null)
				      req.setAttribute("birthday", mi.getBirthday());
				   if(mi.getCity()!=null)
					   req.setAttribute("city", mi.getCity());
				   if(mi.getName()!=null)
					   req.setAttribute("username", mi.getName());
				   if(mi.getPic()!=null)
					   req.setAttribute("pic", mi.getPic());
				   if(mi.getProvince()!=null)
					   req.setAttribute("province", mi.getProvince());
				   if(mi.getScore()!=null)
					   req.setAttribute("score", mi.getScore());
				   if(mi.getSex()!=null)
					   req.setAttribute("sex", mi.getSex());
				   
			  }
		   }
		  
	      List<MemberEmail> me= memberService.findEmailByPro("memberCode", code);
	      if(me.size()>0){
	    	  MemberEmail m=me.get(0);
		      if(m!=null){
		    	  if(m.getEmail()!=null)
				      req.setAttribute("email",m.getEmail());
		      }
	      }
	     List<MemberMobile> mml=memberService.findMobileByPro("memberCode", code);
	     if(mml.size()>0){
	    	 MemberMobile m=mml.get(0);
	    	 if(m!=null){
	    		 if(m.getMobile()!=null){
	    			 req.setAttribute("phone", m.getMobile());
	    		 }
	    	 }
	     }
	  }
	   
	   req.setAttribute("code", code);
	   return  "manage/html/settings/member_detail";
   }
   /**
    * 跳转到修改页
    * @param req
    * @param code
    * @return
    */
   @RequestMapping("edit")
   public String edit(HttpServletRequest req,String code){
	 if(StringUtils.isNotBlank(code)){
		   List<MemberInfo> list=memberService.findbymecode(code);
		   if(list.size()>0){
			   MemberInfo mi=list.get(0);
			   if(mi!=null){
				   if(mi.getBirthday()!=null)
				      req.setAttribute("birthday", mi.getBirthday());
				   if(mi.getCity()!=null)
					   req.setAttribute("city", mi.getCity());
				   if(mi.getName()!=null)
					   req.setAttribute("username", mi.getName());
				   if(mi.getPic()!=null)
					   req.setAttribute("pic", mi.getPic());
				   if(mi.getProvince()!=null)
					   req.setAttribute("province", mi.getProvince());
				   if(mi.getScore()!=null)
					   req.setAttribute("score", mi.getScore());
				   if(mi.getSex()!=null)
					   req.setAttribute("sex", mi.getSex());
				   
			  }
		   }
		  
	      List<MemberEmail> me= memberService.findEmailByPro("memberCode", code);
	      if(me.size()>0){
	    	  MemberEmail m=me.get(0);
		      if(m!=null){
		    	  if(m.getEmail()!=null)
				      req.setAttribute("email",m.getEmail());
		      }
	      }
	     List<MemberMobile> mml=memberService.findMobileByPro("memberCode", code);
	     if(mml.size()>0){
	    	 MemberMobile m=mml.get(0);
	    	 if(m!=null){
	    		 if(m.getMobile()!=null){
	    			 req.setAttribute("phone", m.getMobile());
	    		 }
	    	 }
	     }
	  }
	   req.setAttribute("flag", "update");
	   req.setAttribute("code", code);
	   return  "manage/html/settings/member_add";
   }
   
   /**
    * 修改
    * @param req
    * @param sex
    * @param file
    * @param email
    * @param phone
    * @param username
    * @param password
    * @param birthday
    * @param province
    * @param city
    * @param score
    * @return
    * @throws JSONException
 * @throws ParseException 
    */
   @RequestMapping("update")
   public String update(HttpServletRequest req,String sex,String pic, String email,String phone,String username,String password,String birthday,String province,String city,String score) throws JSONException, ParseException{
	     String chk=check(req, "update", sex, email, phone, username, password, birthday, province, city, score);
	     SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
	     if(StringUtils.isNotBlank(chk)){
	    	 if(StringUtils.isNotBlank(birthday))
	    	 req.setAttribute("birthday", fmt.parse(birthday));
	    	 req.setAttribute("province", province);
	    	 req.setAttribute("city", city);
	    	 req.setAttribute("error", chk);
	    	 req.setAttribute("flag", "update");
	    	 req.setAttribute("code", req.getParameter("code"));
	    	 return "manage/html/settings/member_add";
	     }
	     String code=req.getParameter("code");
	     //验证电话号码是否重复
	    if(StringUtils.isNotBlank(phone)){
		     List<MemberMobile> mlist=memberService.findMobileByPro("mobile", phone);
			 if(mlist.size()>0){
				   MemberMobile mm=mlist.get(0);
				   if(mm!=null){
					  if(mm.getMemberCode()!=null){
					   if(!code.equals(mm.getMemberCode())){
						  if(StringUtils.isNotBlank(birthday))
						  req.setAttribute("birthday", fmt.parse(birthday));
						  req.setAttribute("province", province);
					      req.setAttribute("city", city);
					      req.setAttribute("phoneExsist", 1);
					      req.setAttribute("flag", "update");
					      req.setAttribute("code", code);
					      return "manage/html/settings/member_add";
					   }
					  }
				   }
			 }
		 }
	     //验证邮箱是否重复
	    if(StringUtils.isNotBlank(email)){
			 List<MemberEmail> elist=memberService.findEmailByPro("email", email);
			 if(elist.size()>0){
				 MemberEmail me=elist.get(0);
				 if(me!=null){
					 if(!code.equals(me.getMemberCode())){
						 if(StringUtils.isNotBlank(birthday))
						 req.setAttribute("birthday", fmt.parse(birthday));
						 req.setAttribute("emailExsist", 1);
						 req.setAttribute("province", province);
				    	 req.setAttribute("city", city);
				    	 req.setAttribute("flag", "update");
				    	 req.setAttribute("code", code);
						 return "manage/html/settings/member_add";
					 }
				 }
			 }
	    }  
	    //验证昵称是否重复
//	    if(StringUtils.isNotBlank(email)){
//	    	List<MemberInfo> elist1=memberService.findInfoByPro("name", username);
//			 if(elist1.size()>0){
//				 MemberInfo me=elist1.get(0);
//				 if(me!=null){
//					 if(!code.equals(me.getMemberCode())){
//						 if(StringUtils.isNotBlank(birthday))
//						 req.setAttribute("birthday", fmt.parse(birthday));
//						 req.setAttribute("nameExsist", 1);
//						 req.setAttribute("province", province);
//				    	 req.setAttribute("city", city);
//				    	 req.setAttribute("flag", "update");
//				    	 req.setAttribute("code", code);
//						 return "manage/html/settings/member_add";
//					 }
//				 }
//			 }
//	    } 
	     Member m= memberService.findByCode(code);
	     if(StringUtils.isNotBlank(password)){
	    	 m.setPassword(LoginMD5.compile(password));
	     }
	     //实例化对象
	     MemberInfo mi=new MemberInfo();
	     MemberMobile mm= new MemberMobile();
	     MemberEmail me=new MemberEmail();
	     
	     List<MemberInfo> milist= memberService.findbymecode(code);
	     if(milist.size()>0){
		      mi=milist.get(0);
//		     //上传头像
//			  if(!file.isEmpty()){
//			    	if(!storeFile(req,file)){
//			    		req.setAttribute("fileError", 1);
//			    		return "manage/html/settings/member_add";
//			    	}
//			    	String pic=(String)req.getSession().getAttribute("userPic");
//			    	if(pic!=null)
//			    	mi.setPic(pic);
//			   }
		      if(StringUtils.isNotBlank(pic))
			    	mi.setPic(pic);
			   mi.setSex(Integer.parseInt(sex));
			   mi.setEmail(email);
			   mi.setPhone(phone);
			   mi.setName(username);
			  if(StringUtils.isNotBlank(birthday)){
		       mi.setBirthday(fmt.parse(birthday));
			  }else{
				  mi.setBirthday(null);
			  }
		       mi.setProvince(province);
			   mi.setCity(city);
			   if(StringUtils.isNotBlank(score)){
			      mi.setScore(Integer.parseInt(score));
			   }else{
				   mi.setScore(null);
			   }
	     }
	     
	     List<MemberEmail> melist= memberService.findEmailByPro("memberCode", code);
	     if(melist.size()>0){
	          me=melist.get(0);
	         me.setEmail(email);
	     }else{
	    	 if(StringUtils.isNotBlank(email)){
		    	 MemberEmail mel=new MemberEmail();
		    	 mel.setAvaliable(1);
		    	 mel.setCode(Uuid.uuid());
		    	 mel.setEmail(email);
		    	 mel.setIsBind(1);
		    	 mel.setMemberCode(code);
		    	 mel.setBindTime(new Timestamp(new Date().getTime()));
		    	 memberService.saveMemEmail(mel);
		    	 List<MemberEmail> melis= memberService.findEmailByPro("memberCode", code);
			     if(melis.size()>0){
			          me=melis.get(0);
			        if(StringUtils.isNotBlank(email))
			    	 me.setEmail(email);
			     }
	    	 }
	     }
	     List<MemberMobile> mmlist=memberService.findMobileByPro("memberCode", code);
	     if(mmlist.size()>0){
	    	 mm=mmlist.get(0);
	    	 mm.setMobile(phone);
	     }else{
	    	 if(StringUtils.isNotBlank(phone)){
	    		 MemberMobile mmb=new MemberMobile();
	    		 mmb.setAvaliable(1);
	    		 mmb.setCode(Uuid.uuid());
	    		 mmb.setIsVerified(1);
	    		 mmb.setMemberCode(code);
	    		 mmb.setMobile(phone);
	    		 mmb.setVerifyTime(new Timestamp(new Date().getTime()));
	    		 memberService.saveMemMobile(mmb);
	    		 List<MemberMobile> mmlis=memberService.findMobileByPro("memberCode", code);
	    	     if(mmlis.size()>0){
	    	    	 mm=mmlis.get(0);
	    	    	 if(StringUtils.isNotBlank(phone))
	    	    	 mm.setMobile(phone);
	    	     }
	    	 }
	     }
	      
	   try {
		   memberService.updateByModel(m, mi, me,mm);
		   req.setAttribute("updateflag", 1);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}    
       req.getSession().removeAttribute("userPic");
	   return list(req,1,"","","");
   }
   
   public String check(HttpServletRequest req,String optType,String sex, String email,String phone,String username,String password,String birthday,String province,String city,String score){
	   req.setAttribute("sex", sex); 
	   req.setAttribute("email", email);
	   req.setAttribute("phone", phone);
	   req.setAttribute("username", username);
	   req.setAttribute("birthday", birthday);
	   req.setAttribute("province", province);
	   req.setAttribute("city", city);
	   req.setAttribute("score", score);
	   if("save".equals(optType)){ 
		   if(StringUtils.isBlank(sex)){
			   //req.setAttribute("error", -1);
			   return "-1";
		   }
		   if(StringUtils.isBlank(email)&&StringUtils.isBlank(phone)){
			   //req.setAttribute("error", -2);
			   return "-2";
		   }
//		   if(StringUtils.isBlank(phone)){
//			   //req.setAttribute("error", -3);
//			   return "-3";
//		   }
		   if(StringUtils.isBlank(username)){
			   //req.setAttribute("error", -4);
			   return "-4";
		   }
		   if(StringUtils.isBlank(password)){
			  // req.setAttribute("error", -5);
			   return "-5";
		   }
//		   if(StringUtils.isBlank(birthday)){
//			  // req.setAttribute("error", -6);
//			   return "-6";
//		   }
//		   if(StringUtils.isBlank(province)){
//			  // req.setAttribute("error", -7);
//			   return "-7";
//		   }
//		   if(StringUtils.isBlank(city)){
//			  // req.setAttribute("error", -8);
//			   return "-8";
//		   }
//		   if(StringUtils.isBlank(score)){
//			  // req.setAttribute("error", -9);
//			   return "-9";
//		   }
	   }
	  if("update".equals(optType)){ 
		   if(StringUtils.isBlank(sex)){
			  // req.setAttribute("error", -1);
			   return "-1";
		   }
		   if(StringUtils.isBlank(email)&&StringUtils.isBlank(phone)){
			   //req.setAttribute("error", -2);
			   return "-2";
		   }
//		   if(StringUtils.isBlank(phone)){
//			  // req.setAttribute("error", -3);
//			   return "-3";
//		   }
		   if(StringUtils.isBlank(username)){
			  // req.setAttribute("error", -4);
			   return "-4";
		   }
//		   if(StringUtils.isBlank(password)){
//			   req.setAttribute("error", -5);
//			   return "manage/html/settings/member_add";
//		   }
//		   if(StringUtils.isBlank(birthday)){
//			  // req.setAttribute("error", -6);
//			   return "-6";
//		   }
//		   if(StringUtils.isBlank(province)){
//			  // req.setAttribute("error", -7);
//			   return "-7";
//		   }
//		   if(StringUtils.isBlank(city)){
//			  // req.setAttribute("error", -8);
//			   return "-8";
//		   }
//		   if(StringUtils.isBlank(score)){
//			  // req.setAttribute("error", -9);
//			   return "-9";
//		   }
	   }
	   return null;
   }
   
   /**
    * 删除
    * @param req
    * @param codes
    * @return
    * @throws JSONException
    */
   @RequestMapping("delete")
   public String delete(HttpServletRequest req,String[] codes) throws JSONException{
	   if(codes.length==0){
		   req.setAttribute("codeEmpty", 1);
		   return list(req,1,"","","");
	   }
	   for(int i=0;i<codes.length;i++){
		   try {
			   memberService.deleteByMemCode(codes[i]);
			   req.setAttribute("delflag", 1);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
				 req.setAttribute("delflag", 0);
			}
	  }
	  
	   return list(req,1,"","","");
   }
   
   /**
    * 删除
    * @param req
    * @param codes
    * @return
    * @throws JSONException
    */
   @RequestMapping("deleteSingle")
   public String deleteSingle(HttpServletRequest req,String code) throws JSONException{
	   if(StringUtils.isBlank(code)){
		   req.setAttribute("codeEmpty", 1);
		   return list(req,1,"","","");
	   }
	   try {
		   memberService.deleteByMemCode(code);
		   req.setAttribute("delflag", 1);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			req.setAttribute("delflag", 0);
		}
	   
	   
		return list(req,1,"","","");
   }
   
   /**
    * 停封/开启
    * @param req
    * @param code
    * @param type
    * @return
    */
   @RequestMapping("changeState")
   public String changeState(HttpServletRequest req,String code,String type,@RequestParam(defaultValue="1",required=false)int currentPage,String keywords,String status,String memberType){
	   if(StringUtils.isBlank(code)){
		   req.setAttribute("codeEmpty", 1);
		   list(req,1,"","","");
	   }
	   if("wakeup".equals(type)){
		  Member m= memberService.findByCode(code);
		   m.setStatus(1);
		   try {
			   memberService.updateMember(m);
			   req.setAttribute("wakeup", 1);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
				req.setAttribute("wakeup", 0);
			}
		   
	   }
	   if("limit".equals(type)){
		   Member m= memberService.findByCode(code);
		    m.setStatus(0);
		   try {
			   memberService.updateMember(m);
			   req.setAttribute("limit", 1);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
				req.setAttribute("limit", 0);
			} 
	   }
	   return list(req,currentPage,keywords,status,memberType);
   }
   
   /**
	 * 导出
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("export")
	@SuppressWarnings("deprecation")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String iden = session.getAttribute("_Exlsiden").toString();
		String excelName = session.getServletContext().getRealPath("")
				+ "/upload/export/" + iden + "_UserList.xls";
		excelName = excelName.replace("//", "/");
		String realname = excelName.substring(excelName.lastIndexOf("_") + 1);
		
		try {
			realname = URLDecoder.decode(realname, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		File file = new File(excelName);
		
		if (!file.exists() || file.isDirectory()) {
			return;
		}
		
		InputStream input = null;
		OutputStream output = null;
		
		try {
			input = new FileInputStream(excelName);
			output = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment;filename="
							+ new String(realname.getBytes("utf-8"), "iso-8859-1"));
			byte[] buffer = new byte[input.available()];
			int i = 0;
			
			while ((i = input.read(buffer)) != -1) {
				output.write(buffer, 0, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != output) {
					output.flush();
					output.close();
				}
				
				if (null != input) {

					input.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 如果文件存在就删除它
		File fileex = new File(excelName);
		
		if (fileex.exists()) {
			fileex.delete();
		}
	}

	/**
	 * WriteExcel
	 * 
	 * @return
	 */
	@RequestMapping("writeexcel")
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void writeexcel(HttpServletRequest request, HttpServletResponse response, 
			String keywords, String status,String memberType,String codes[]) {
		String iden = Uuid.uuid().toString();
		HttpSession session = request.getSession();
		session.setAttribute("_Exlsiden", iden);
		String excelName = session.getServletContext().getRealPath("")
				+ "/upload/export/" + iden + "_UserList.xls";
		excelName = excelName.replace("//", "/");
		String Path= request.getSession().getServletContext().getRealPath("")
	       + "/upload/export/";
		try {
			File chk=new File(Path);
			if(!chk.exists()){
				chk.mkdirs();
			}
			File excelFile = new File(excelName);
			
			// 如果文件存在就删除它
			if (excelFile.exists()) {
				excelFile.delete();
			}
			
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(excelFile);
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet(" 会员清单 ", 0);
			// 合并单元格
			// sheet.mergeCells(5, 5, 6, 6);
			// 文字样式
			jxl.write.WritableFont wfc = new jxl.write.WritableFont(
					WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);

			jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc);
			WritableFont font1 = new WritableFont(WritableFont.TIMES, 16, 
					WritableFont.BOLD); // 设置字体格式为excel支持的格式
			WritableFont font3 = new WritableFont(WritableFont
					.createFont("楷体_GB2312"), 15, WritableFont.BOLD);
			WritableCellFormat format1 = new WritableCellFormat(font3);
			format1.setAlignment(jxl.format.Alignment.CENTRE);
			format1.setBackground(jxl.format.Colour.GRAY_25);
			// 设置单元格样式
			// wcfFC.setBackground(jxl.format.Colour.WHITE);// 单元格颜色
			wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
			// wcfFC.setWrap(true);设置自动换行
			// 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
			// 以及单元格内容为
			Label label_1 = new Label(0, 0, "注册时间", format1);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label_1);
			// sheet.setRowView(0,200);
			sheet.setColumnView(0, 24);
			Label label_2 = new Label(1, 0, "昵   称", format1);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label_2);
			sheet.setColumnView(1, 24);
			
			Label label_3 = new Label(2, 0, "账号类型", format1);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label_3);
			sheet.setColumnView(2, 21);
			Label label_4 = new Label(3, 0, "邮    箱", format1);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label_4);
			sheet.setColumnView(3, 21);
			Label label_5 = new Label(4, 0, "手 机 号", format1);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label_5);
			sheet.setColumnView(4, 21);
			Label label_6 = new Label(5, 0, "状    态", format1);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label_6);
			sheet.setColumnView(5, 22);
			// 遍历所有记录
			int n = 1;
			String type=request.getParameter("type");
			//String[] codes=request.getParameterValues("codes");
			List<LogUser> list=null;
			if("bylis".equals(type)){
			   Map map=new HashMap();
			    if(StringUtils.isNotBlank(keywords)){
			    	map.put("keywords",keywords);
			    }
			    if(StringUtils.isNotBlank(status)){
			    	map.put("status",status);
			    }
			    if(StringUtils.isNotBlank(memberType)){
			    	map.put("memberType",memberType);
			    }
			     list= memberService.getAllMemberList(map);
			}
			if("bychk".equals(type)){
				list=memberService.getMemberListByCodes(codes);
			}
			
			SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (LogUser lu : list) {
			   if(lu.getCreateTime()!=null){
					Label label = new Label(0, n, fmt.format(lu.getCreateTime()), wcfFC);
					// 将定义好的单元格添加到工作表中
					sheet.addCell(label);
			   }
			   if(lu.getUsername()!=null){
	                Label label2 = new Label(1, n, lu.getUsername(), wcfFC);
					// 将定义好的单元格添加到工作表中
					sheet.addCell(label2);
			   }
			   if(lu.getMemberType()!=null){
					Label label3 = new Label(2, n, lu.getMemberType()==0?"管理员添加":"用户注册", wcfFC);
					// 将定义好的单元格添加到工作表中
					sheet.addCell(label3);
			   }
			   if(lu.getEmail()!=null){
					Label label4 = new Label(3, n, lu.getIsBind()==1?lu.getEmail():"", wcfFC);
					// 将定义好的单元格添加到工作表中
					sheet.addCell(label4);
			   }
			   if(lu.getMobile()!=null){
					Label label5 = new Label(4, n,lu.getMobile(), wcfFC);
					// 将定义好的单元格添加到工作表中
					sheet.addCell(label5);
			   }
			   if(lu.getStatus()!=null){
					Label label6 = new Label(5, n,lu.getStatus()==0?"已停用":"已启用", wcfFC);
					// 将定义好的单元格添加到工作表中
					sheet.addCell(label6);
			   }
				// 写入数据并关闭文件
				n = n + 1;
			}
			book.write();
			book.close();
			//System.out.println("Excel创建成功");
			response.getWriter().write("1");
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();

		}
		// return ajaxJson("succ");
	}
   
   /**
    * 根据日期归类图像存放路径
    * @return
    */
   @SuppressWarnings("unused")
   private String getStorePath() {
	    SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
	    String date=fmt.format(new Date());
		return "/upload/userpic/" + date + "/";
   }
   
   /**
    * 上传文件
    * @param imgFile
    * @param req
    * @return
    */
	private static final String ACCESSFILETYPE = ".*(jpeg|jpg|png|gif|bmp)$";
	private boolean storeFile(HttpServletRequest req,MultipartFile imgFile){
		try{
			if(!imgFile.isEmpty()){
				if(!checkFileType(imgFile, ACCESSFILETYPE)){
					//System.out.println("File Type Not Allow!");
					return false;
				}
				
				String fileName = System.currentTimeMillis()+ "."+getFileExt(imgFile.getOriginalFilename());
				String path = "/var/ctibet" + getStorePath()+ fileName;
				checkFolder("/var/ctibet/" + getStorePath());
				writeByte(path, imgFile.getBytes());
				req.getSession().setAttribute("userPic", path);
				
			}
		}catch (Exception e) {
			//System.out.println("Student Pic or GradutePic Upload Faild!");
			return false;
		}
		
		return true;
	}
	/**
	 * 上传
	 * @param fileName
	 * @param b
	 * @return
	 */
	public static boolean writeByte(String fileName, byte[] b) {
		try {
			BufferedOutputStream fos = new BufferedOutputStream(
					new FileOutputStream(fileName));
			fos.write(b);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 获取文件后缀
	 * @param fileName
	 * @return
	 */
	public String getFileExt(String fileName) {
		if (fileName != null) {
			String fileExt = "";
			fileName = fileName.toLowerCase();
			int index = fileName.lastIndexOf(".");
			fileExt = fileName.substring(index + 1, fileName.length());
			return fileExt;
		} else {
			return "";
		}
	}
	/**
	 * 判断目录是否存在，如果不存在则建立
	 * 
	 * @param fileRealPath
	 * @return
	 */
	public boolean checkFolder(String fileRealPath) {
		boolean tt = false;
		try {

			File f = new File(fileRealPath);
			if (f.exists()) {
				tt = true;
			} else {
				tt = f.mkdirs();
			}
		} catch (Exception ex) {
			//System.out.println("[checkFolder] error:" + ex.getMessage());
		}
		return tt;
	}
	/**
	 * 检测文件类型
	 * @param fileResource
	 * @param ACCESS_FILE_TYPE
	 * @return
	 */
	public boolean checkFileType(MultipartFile fileResource,String ACCESS_FILE_TYPE) {
		if (fileResource == null||fileResource.isEmpty()){
			return true;
		}
		
		if (getFileExt(fileResource.getOriginalFilename()).toLowerCase().matches(ACCESS_FILE_TYPE)){
			return true;
		}
		
		return false;
	}
   
   /**
    * 返回错误信息
    * @param msg
    * @return
    * @throws JSONException
    */
   private static String error(String msg) throws JSONException{
	   JSONObject obj=new JSONObject();
	   obj.put("status", 101);
	   obj.put("msg", msg);
	   return obj.toString();
   }
   
   /**
    * 返回成功信息
    * @param msg
    * @return
    * @throws JSONException
    */
   private static String success(String msg) throws JSONException{
	   JSONObject obj=new JSONObject();
	   obj.put("status", 1);
	   obj.put("msg", msg);
	   return obj.toString();
   }
   
   /**
    * 检查用户是否登录
    * web/member/checkIslogin
    */
   @RequestMapping("checkIslogin")
   @ResponseBody
   public String checkIslogin(){
       LogUser user = getFrontUser();
       if(user!=null){
           return "success";
       }
       return "error";
   }
   
}
