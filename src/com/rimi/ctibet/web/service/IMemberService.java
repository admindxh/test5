package com.rimi.ctibet.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.sendemail.ServiceException;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberEmail;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.MemberMobile;
import com.rimi.ctibet.domain.MobileCode;
import com.rimi.ctibet.web.controller.vo.MemberVO;

/**
 * 
 * @author xiangwq
 *
 */
public interface IMemberService extends BaseService<Member>{
    
    //添加会员并保存
	public Member saveMember(String password, String createIp);
    //保存会员信息
	public void saveMemInfo(Member member, String name);
	//保存会员的手机信心
	public void saveMemMobile(Member member, String mobile, int isByMobile);
	//保存会员头像信息
	public void saveMemAvatars(Member member);
	//保存会员session信息
	public void saveMemSession(Member member);
	//保存会员的email信息
	public void saveMemEmail(Member member,String email,String validateCode);
	//检查手机号是否重复
	public boolean checkMobileIsRepeat(String mobile);

	//用户登录
	public  List<LogUser> login(String username,String password);
	public Member findByCode(String code);
	//判断传入的手机号和验证码是否存在
	public List<MobileCode> validateCode(String mobile,String validate);
	//通过手机号查询
	public List<MobileCode> validateCode(String mobile);
	//更新验证码
	public void updateMobileCode(MobileCode mobileCode);
	//保存验证码
	public void saveMobileCode(MobileCode mobileCode);
	public boolean SycMemberEmail(String email, String name, String password,String createIp);
	//邮箱注册
	public boolean processregister(String email, String name, String password,
			String createIp, String basePath,String resend);
	//邮箱验证
	public boolean processActivate(String email, String validateCode) throws ServiceException;
	//更新member
	public void updateMember(Member member);
	//根据membercode查询
	public List<MemberInfo> findbymecode(String mecode);
	//更新memberInfo
	public void updateMemberInfo(MemberInfo m);
	//根据code查询用户关联信息
	public List<LogUser> findLogUserByCode(String code);
	//通过会员名新增对象资料
	public Member saveByName(MemberInfo mi); 
	//查询memberInfo
	public MemberInfo findMemberInfo(String memberCode);
	//查询积分前五名的用户
	public List<LogUser> getTopFive();
	//根据发帖数量排名（取前5,数量不足返回实际数量）
	public List<LogUser> getTopFivByPcount();
	public int memberNum();
	/**
	 * 通过日期返回 会员数量
	 * @param date
	 * @return
	 */
	public Integer getMemCountByTime(final Date start,final Date end);
	//查询所有用户
	public Pager getMemberList(Map map,Pager pager);
	public void saveByModel(Member mem,MemberInfo mi,MemberEmail me,MemberMobile mm);
	public void updateByModel(Member mem,MemberInfo mi,MemberEmail me,MemberMobile mm);
	public void deleteByMemCode(String code);
	public List<MemberEmail> findEmailByPro(String pro,Object value);
	public List<MemberMobile> findMobileByPro(String pro,Object value);
	public List<MemberInfo> findInfoByPro(String pro,Object value);
	public List<LogUser> getAllMemberList(Map map);
	public List<LogUser> getMemberListByCodes(String[] codes);
    /**
     * 通过会员code获取会员信息
     */
    public MemberVO getMemberByMemberCode(String code);
    
    public void saveMemEmail(MemberEmail me);
    public void saveMemMobile(MemberMobile mm);
    public void saveMem(Member m);
    public void delPhyByMemCode(String memberCode);
    
}
