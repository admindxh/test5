package com.rimi.ctibet.web.service.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.LoginMD5;
import com.rimi.ctibet.common.util.MatchHolder;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Security;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.common.util.sendemail.SendEmail;
import com.rimi.ctibet.common.util.sendemail.ServiceException;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberEmail;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.MemberMobile;
import com.rimi.ctibet.domain.MobileCode;
import com.rimi.ctibet.web.controller.vo.MemberVO;
import com.rimi.ctibet.web.dao.IMemberDao;
import com.rimi.ctibet.web.dao.IMemberEmailDao;
import com.rimi.ctibet.web.dao.IMemberInfoDao;
import com.rimi.ctibet.web.dao.IMemberMobileDao;
import com.rimi.ctibet.web.dao.IMobileCodeDao;
import com.rimi.ctibet.web.service.IMemberService;
/**
 * 
 * @author xiangwq
 *
 */
@Service("memberService")
@Transactional
public class MemberServiceImpl extends BaseServiceImpl<Member> implements IMemberService {

	@Resource
	private IMemberDao memberDao;
	@Resource
	private IMemberInfoDao memberInfoDao;
	@Resource
	private IMemberMobileDao memberMobileDao;
	@Resource
	private IMemberEmailDao memberEmailDao;
	@Resource
	private IMobileCodeDao mobileCodeDao;
	private Logger log = Logger.getLogger(getClass());
	
	
	@Override
	public void saveMemAvatars(Member member) {
		// TODO Auto-generated method stub
		
	}
     /**
      * 新增一个用户并保存
      */
	@Override
	public Member saveMember(String password,String createIp) {
		Member member = new Member();
		member.setAvaliable(1);
		member.setCode(Uuid.uuid());
		member.setPassword(LoginMD5.compile(password));
		member.setCreateTime(new Timestamp(new Date().getTime()));
		member.setCreateIp(createIp);
		//还需要有个member.setStatus();
		member.setMemberType(1);
		member.setStatus(1);
		memberDao.save(member);
		return member;
	}
    /**
     * 保存一个EMAIL信息
     */
	@Override
	public void saveMemEmail(Member member,String email,String validateCode) {
		MemberEmail memberEmail = new MemberEmail();
		memberEmail.setEmail(email);
		memberEmail.setAvaliable(1);
		memberEmail.setCode(Uuid.uuid());
		memberEmail.setMemberCode(member.getCode());
	    memberEmail.setIsBind(0);
	    memberEmail.setValidateCode(validateCode);
		
		memberEmailDao.save(memberEmail);
	}
    /**
     * 新增一个会员的信息对象
     */
	@Override
	public void saveMemInfo(Member member,String name) {
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setAvaliable(1);
		memberInfo.setCode(Uuid.uuid());
		memberInfo.setMemberCode(member.getCode());
		memberInfo.setName(name);
		memberInfo.setSex(1);
		memberInfo.setPic("/portal/static/default/male.jpg");
		memberInfoDao.save(memberInfo);
	}
	
	@Override
	public void saveMemMobile(Member member,String mobile,int isByMobile) {
		MemberMobile memberMobile = new MemberMobile();
		memberMobile.setAvaliable(1);
		memberMobile.setCode(Uuid.uuid());
		memberMobile.setMobile(mobile);
		memberMobile.setMemberCode(member.getCode());
		//isbymobile=1 说明是通过手机注册进来的，肯定验证过的
		if(isByMobile==1){  
			memberMobile.setIsVerified(1);
			memberMobile.setVerifyTime(new Timestamp(new Date().getTime()));
		}
		memberMobileDao.save(memberMobile);	
	}

	@Override
	public void saveMemSession(Member member) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 检查手机是否重复
	 * @return ture为重复 false为不重复
	 */
	@Override
	public boolean checkMobileIsRepeat(String mobile) {
		//如果返回结果>0 说明查到了，就重复了
		if(memberMobileDao.findByProperty("mobile", mobile).size()>0){
			return true;
		}
		else return false;		
	}
	/**
	 * 通过手机号和验证码查询
	 */
	
	public List<MobileCode> validateCode(String mobile, String validate) {
	     return mobileCodeDao.findByMobileAndValidate(mobile, validate);
	}
	/**
	 * 通过验证码查询手机号
	 */
	
	public List<MobileCode> validateCode(String mobile) {
		return mobileCodeDao.findByMobile(mobile);
	}
	/**
	 * 更新验证码
	 */
	@Override
	public void updateMobileCode(MobileCode mobileCode) {
		mobileCodeDao.update(mobileCode);
	}
    /**
     * 保存验证码
     */
	@Override
	public void saveMobileCode(MobileCode mobileCode) {
		mobileCodeDao.save(mobileCode);
	}
	
	/**
	 * 发送邮件到用户邮箱
	 */
	@Override
	public boolean processregister(String email, String name, String password,
			String createIp, String basePath,String resend) {
		
        Member member = null;
		String validateCode = LoginMD5.compile(String.valueOf(Math.round(Math.random() * 899999 + 100000)));// 随机生成6位随机数
		if("1".equals(resend)){
			//***************************邮件内容****************************
			String code=Security.getInstance().encode((System.currentTimeMillis()+1200000)+","+email+","+validateCode+","+LoginMD5.compile(password));
			StringBuffer content = new StringBuffer("");
			content.append("您好，尊敬的用户。 你的账号<b><font color=\"#ff0000\">")
			.append(email)
			.append("</font></b>，注册成功。请在20分钟内点击验证，否则账户将无法登录，<a href=\"")
			.append(basePath).append("portal/registerController/emailValidate.html?code=")
			.append(code)
			.append("\">点击验证</a><div>&nbsp; &nbsp;请注意保管！</div>");	
			//发送邮件
			SendEmail.send(email, content.toString());
			List<MemberEmail> list=memberEmailDao.findByProperty("email", email);
			if(list.size()>0){
			   MemberEmail me=list.get(0);
			   me.setValidateCode(validateCode);
			   memberEmailDao.update(me);
			}
			return true;   
		}else{
			if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(name)
					&& StringUtils.isNotBlank(password)) {
				if(!MatchHolder.isEmail(email)){
					//System.out.println("邮箱不符合规范");
					return false; //邮箱不符合规范
				}
				
				member = saveMember(password, createIp);
				saveMemInfo(member, name);
				saveMemEmail(member, email, validateCode);
				//***************************邮件内容****************************
				String code=Security.getInstance().encode((System.currentTimeMillis()+1200000)+","+email+","+validateCode+","+LoginMD5.compile(password));
				StringBuffer content = new StringBuffer("");
				content.append("您好，尊敬的用户。 你的账号<b><font color=\"#ff0000\">")
				.append(email)
				.append("</font></b>，注册成功。请在20分钟内点击验证，否则账户将无法登录，<a href=\"")
				.append(basePath).append("portal/registerController/emailValidate.html?code=")
				.append(code)
				.append("\">点击验证</a><div>&nbsp; &nbsp;请注意保管！</div>");	
				//发送邮件
				SendEmail.send(email, content.toString());
				//System.out.println("发送邮件");
				return true;   
			}
		}
		return false;
		
	}

	@Override
	public  List<LogUser> login(String username, String password) {
		// TODO Auto-generated method stub
		return memberDao.login(username,password);
	}
    
	
	/**
	 * 用户点击邮件验证
	 */
	@Override
	public boolean processActivate(String email, String validateCode) throws ServiceException {
	    //数据访问层，通过email获取用户信息   
		List<MemberEmail> emailList = new ArrayList<MemberEmail>();
		emailList=memberEmailDao.findByProperty("email", email);
        if(emailList.size()<=0){
        	throw new ServiceException("该邮箱未注册（邮箱地址不存在）！");
        }
        MemberEmail memberEmail =emailList.get(0);
        Member member = memberDao.findByCode(memberEmail.getMemberCode());
        //验证用户是否存在
        if(memberEmail!=null) { 
            //验证用户激活状态 
            if(memberEmail.getIsBind()==0) {
                ///没激活
                Date currentTime = new Date();//获取当前时间 
                //验证链接是否过期
                currentTime.before(member.getCreateTime());
                if(currentTime.before(member.getLastActivateTime())) { 
                    //验证激活码是否正确 
                    if(validateCode.equals(memberEmail.getValidateCode())) { 
                        //激活成功， //并更新用户的激活状态，为已激活
                        //System.out.println("==sq==="+memberEmail.getIsBind());
                        memberEmail.setIsBind(1);//邮箱状态改为激活
                        memberEmail.setBindTime(new Timestamp(new Date().getTime()));
                        member.setStatus(1);  //把会员状态改为已激活
                        //System.out.println("==sh==="+memberEmail.getIsBind());
                        memberEmailDao.update(memberEmail);
                        memberDao.update(member);
                        return true;
                    } else { 
                       throw new ServiceException("激活码不正确"); 
                    } 
                } else { 
                	throw new ServiceException("激活码已过期！"); 
                } 
            } else {
              return true; 
            } 
        } else {
            throw new ServiceException("该邮箱未注册（邮箱地址不存在）！"); 
        } 
 
	}
	@Override
	public Member findByCode(String code) {
		// TODO Auto-generated method stub
		return memberDao.findByCode(code);
	}
	/**
	 * 更新member对象
	 */
	@Override
	public void updateMember(Member member) {
	    memberDao.update(member);	
	}
	@Override
	public List<MemberInfo> findbymecode(String mecode) {
		// TODO Auto-generated method stub
		return memberInfoDao.findByProperty("memberCode", mecode);
	}
	@Override
	public void updateMemberInfo(MemberInfo m) {
		// TODO Auto-generated method stub
		memberInfoDao.update(m);
	}
	@Override
	public List<LogUser> findLogUserByCode(String code) {
		// TODO Auto-generated method stub
		return memberDao.findLogUserByCode(code);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Member saveByName(MemberInfo mi) {
	 List<MemberMobile> ml= memberMobileDao.findByProperty("mobile", "10010086001");
	 Member m=new Member();
	 if(ml.size()==0){
		//用户表
		   m.setAvaliable(1);
		   m.setCode(Uuid.uuid());
		   m.setStatus(1);
		   m.setCreateTime(new Timestamp(new Date().getTime()));
		   m.setMemberType(0);
		   m.setPassword(LoginMD5.compile("888888"));
		memberDao.save(m);
	    String code="";
	    if(m.getCode()!=null){
	    	code=m.getCode();
	    }
	    
	    MemberMobile mm=new MemberMobile();
	       mm.setCode(Uuid.uuid());
	       mm.setIsVerified(1);
	       mm.setMobile("10010086001");
	       mm.setVerifyTime(new Timestamp(new Date().getTime()));
	       mm.setAvaliable(1);
	       mm.setMemberCode(code);
	    //用户信息表
	    mi.setCode(Uuid.uuid());
	    mi.setAvaliable(1);
	    mi.setName("天上西藏官方");
	    mi.setMemberCode(code);
	    mi.setPic("/portal/static/default/male.jpg");
	    memberInfoDao.save(mi);
	    memberMobileDao.save(mm);
	  }else{
		  MemberMobile mm=ml.get(0);
		  if(mm!=null)
		   m=memberDao.findByCode(mm.getMemberCode());
		  
	  }
		return m;
	}
	@SuppressWarnings("unchecked")
	@Override
	public MemberInfo findMemberInfo(String memberCode) {
		// TODO Auto-generated method stub
	   List<MemberInfo> list=memberInfoDao.findByProperty("memberCode", memberCode);
		
		return   list!=null&&list.size()>=1?list.get(0):null;
	}
	@Override
	public List<LogUser> getTopFive() {
		// TODO Auto-generated method stub
		return memberDao.getTopFive();
	}
	@Override
	public List<LogUser> getTopFivByPcount() {
		// TODO Auto-generated method stub
		return memberDao.getTopFivByPcount();
	}
	@Override
	public Integer getMemCountByTime(final Date start,final Date end)  {
		// TODO Auto-generated method stub
		return memberDao.getMemCountByTime(start,end);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Pager getMemberList(Map map,Pager pager) {
		// TODO Auto-generated method stub
	   String sql="SELECT m.id AS id,m.createTime As createTime,m.status AS status,m.memberType AS memberType, m.`code` AS CODE,m.`password` AS PASSWORD ,me.`email` AS email,me.isBind AS isBind"
			+",mi.`name` AS name,mi.`memberCode` AS memberCode,mi.`phone` AS phone ,mi.`pic` AS pic,mi.`sex` AS sex,mi.`score` AS score,mi.`birthday` AS birthday"
			+",mi.`province` AS province,mi.`city` AS city,mm.`mobile` AS mobile,mm.`isVerified` AS isVerified FROM member m "
					      +"LEFT JOIN member_email me ON m.`code`=me.`memberCode` "
					      +"LEFT JOIN member_mobile mm ON m.`code`=mm.`memberCode` "
					      +"LEFT JOIN member_info mi ON m.`code`=mi.`memberCode` WHERE m.`avaliable`='1' ";
	   List param=new ArrayList();
	   if(map.size()>0){
		   if(map.containsKey("keywords")){
			   String para="%"+map.get("keywords")+"%";
			   param.add(para);
			   param.add(para);
			   param.add(para);
			   sql+=" AND (me.email LIKE ? OR mm.mobile LIKE ? OR mi.name LIKE ?) ";
		   }
		   if(map.containsKey("status")){
			   param.add(map.get("status"));
			   sql+=" AND m.status=? ";
		   }
		   if(map.containsKey("memberType")){
			   param.add(map.get("memberType"));
			   sql+=" AND m.memberType=? ";
		   }
	   }
		  sql+="order by m.createTime desc";
	   
		return memberDao.findByJDBCSql(sql, param, pager);
	}
	@Override
	public void saveByModel(Member mem, MemberInfo mi, MemberEmail me,MemberMobile mm) {
		// TODO Auto-generated method stub
		if(mem!=null){
		 try {
			 memberDao.save(mem);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
		}
		if(mi!=null){
			try {
				memberInfoDao.save(mi);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
		}
		if(me!=null){
			try {
				memberEmailDao.save(me);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
		}
		if(mm!=null){
		   try {
			   memberMobileDao.save(mm);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
		}
	}
	@Override
	public void updateByModel(Member mem, MemberInfo mi, MemberEmail me,MemberMobile mm) {
		// TODO Auto-generated method stub
		log.error(mem);
		log.error(mi);
		log.error(me);
		log.error(mm);
		if(mem!=null){
		   try {
			   memberDao.update(mem);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
		}
		if(mi!=null){
			try {
				memberInfoDao.update(mi);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
		}
		if(me!=null){
		   try {
			   memberEmailDao.update(me);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
		}
		//有bug，把服务器搞了哟。。。。。
		if(mm!=null){
		   try {
			   memberMobileDao.update(mm);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void deleteByMemCode(String code) {
		if(StringUtils.isBlank(code)){
			return;
		}
		
		memberDao.deleteLogicalByCode(Member.class, code);
	   
	    memberEmailDao.deleteLogicalByMemberCode(MemberEmail.class, code);
	   
	    memberInfoDao.deleteLogicalByMemberCode(MemberInfo.class, code);
	   
	    memberMobileDao.deleteLogicalByMemberCode(MemberMobile.class, code);
	  
	}
	@Override
	public void delPhyByMemCode(String code) {
		if(StringUtils.isBlank(code)){
			return;
		}
		
		memberDao.deleteByCode(code);
	   List<MemberEmail> list= memberEmailDao.findByProperty("memberCode", code);
	   for(int i=0;i<list.size();i++){
		   memberDao.deleteByCode(MemberEmail.class, list.get(i).getCode());
	   }
	   List< MemberInfo> list1=  memberInfoDao.findByProperty("memberCode", code);
	   for(int i=0;i<list1.size();i++){
		   memberDao.deleteByCode(MemberInfo.class, list1.get(i).getCode());
	   }
	   List< MemberMobile> list2=  memberMobileDao.findByProperty("memberCode", code);
	   for(int i=0;i<list2.size();i++){
		   memberDao.deleteByCode(MemberMobile.class, list2.get(i).getCode());
	   }
	}
	@Override
	public List<MemberEmail> findEmailByPro(String pro, Object value) {
		// TODO Auto-generated method stub
		return memberEmailDao.findByProperty(pro, value);
	}
	@Override
	public List<MemberMobile> findMobileByPro(String pro, Object value) {
		// TODO Auto-generated method stub
		return memberMobileDao.findByProperty(pro, value);
	}
	@Override
	public List<LogUser> getAllMemberList(Map map) {
		// TODO Auto-generated method stub
		String sql="SELECT m.id AS id,m.createTime As createTime,m.status AS status,m.memberType AS memberType, m.`code` AS CODE,m.`password` AS PASSWORD ,me.`email` AS email,me.isBind AS isBind"
			+",mi.`name` AS username,mi.`memberCode` AS memberCode,mi.`phone` AS phone ,mi.`pic` AS pic,mi.`sex` AS sex,mi.`score` AS score,mi.`birthday` AS birthday"
			+",mi.`province` AS province,mi.`city` AS city,mm.`mobile` AS mobile,mm.`isVerified` AS isVerified FROM member m "
					      +"LEFT JOIN member_email me ON m.`code`=me.`memberCode` "
					      +"LEFT JOIN member_mobile mm ON m.`code`=mm.`memberCode` "
					      +"LEFT JOIN member_info mi ON m.`code`=mi.`memberCode` WHERE m.`avaliable`='1' ";
	   List param=new ArrayList();
	   if(map.size()>0){
		   if(map.containsKey("keywords")){
			   String para="%"+map.get("keywords")+"%";
			   param.add(para);
			   param.add(para);
			   param.add(para);
			   sql+=" AND (me.email LIKE ? OR mm.mobile LIKE ? OR mi.name LIKE ?) ";
		   }
		   if(map.containsKey("status")){
			   param.add(map.get("status"));
			   sql+=" AND m.status=? ";
		   }
		   if(map.containsKey("memberType")){
			   param.add(map.get("memberType"));
			   sql+=" AND m.memberType=? ";
		   }
	   }
	   sql+= " order by m.createTime desc";
	   List<LogUser> list=memberDao.findListBysql2(sql, param);
	   return list;
	}
    @Override
    public MemberVO getMemberByMemberCode(String code) {
        return memberDao.getMemberByMemberCode(code);
    }
	@SuppressWarnings("unchecked")
	@Override
	public List<LogUser> getMemberListByCodes(String[] codes) {
		String sql="SELECT m.id AS id,m.createTime As createTime,m.status AS status,m.memberType AS memberType, m.`code` AS CODE,m.`password` AS PASSWORD ,me.`email` AS email,me.isBind AS isBind"
			+",mi.`name` AS username,mi.`memberCode` AS memberCode,mi.`phone` AS phone ,mi.`pic` AS pic,mi.`sex` AS sex,mi.`score` AS score,mi.`birthday` AS birthday"
			+",mi.`province` AS province,mi.`city` AS city,mm.`mobile` AS mobile,mm.`isVerified` AS isVerified FROM member m "
					      +"LEFT JOIN member_email me ON m.`code`=me.`memberCode` "
					      +"LEFT JOIN member_mobile mm ON m.`code`=mm.`memberCode` "
					      +"LEFT JOIN member_info mi ON m.`code`=mi.`memberCode` WHERE m.`avaliable`='1' AND m.code=?";
	  List<LogUser> logList=new ArrayList<LogUser>();
	  List param=new ArrayList();
	  String temp=sql;
	  if(codes!=null){
		  for(int i=0;i<codes.length;i++){
			sql=temp;
		    param.add(codes[i]);
		    sql+= " order by m.createTime desc";
		    List<LogUser> list=memberDao.findListBysql2(sql, param);
		    for(LogUser l:list){
			   logList.add(l);
		    }
		    param.clear();
		  }
	  }
	   return logList;
	}
	@Override
	public boolean SycMemberEmail(String email, String name, String password,
			String createIp) {
		 Member member = null;
			String validateCode = LoginMD5.compile(email);
			if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(name)
					&& StringUtils.isNotBlank(password)) {
				if(!MatchHolder.isEmail(email)){
					//System.out.println("邮箱不符合规范");
					return false; //邮箱不符合规范
				}		
				member = saveMember(password, createIp);
				saveMemInfo(member, name);
				saveMemEmail(member, email, validateCode);
				return true;   
			}
		return false;
	}
	@Override
	public List<MemberInfo> findInfoByPro(String pro, Object value) {
		// TODO Auto-generated method stub
		return memberInfoDao.findByProperty(pro, value);
	}
	@Override
	public int memberNum() {
		// TODO Auto-generated method stub
		String sql="SELECT * FROM member WHERE avaliable='1' AND memberType='1'";
		List param=new ArrayList();
		
		return memberDao.findListBySql(Member.class,sql, param).size();
	}
	@Override
	public void saveMemEmail(MemberEmail me) {
		// TODO Auto-generated method stub
		memberEmailDao.save(me);
	}
	@Override
	public void saveMemMobile(MemberMobile mm) {
		// TODO Auto-generated method stub
		memberMobileDao.save(mm);
	}
	@Override
	public void saveMem(Member m) {
		// TODO Auto-generated method stub
		memberDao.save(m);
	}
	
	
	
}
