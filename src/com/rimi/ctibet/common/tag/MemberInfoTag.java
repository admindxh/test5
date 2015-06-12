package com.rimi.ctibet.common.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.rimi.ctibet.common.util.SpringUtil;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.web.service.IMemberService;

/**
 * 会员信息展示 标签
 * @author dengxh
 *
 */
public class MemberInfoTag  extends  TagSupport{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String memberCode;

		@Override
		public int doStartTag() throws JspException {
			
			IMemberService memberService = (IMemberService) SpringUtil.getAppCtx().getBean("memberService");
			List<LogUser> loguser = memberService.findLogUserByCode(memberCode);
			
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			JspWriter out  =  pageContext.getOut(); 
			if (loguser!=null&&loguser.size()>=1) {
				  String  pic  = loguser.get(0).getPic()==""||loguser.get(0).getPic()==null?"/static/images/tx_1.png":loguser.get(0).getPic();
				  try {
					out.print(pic);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return   EVAL_PAGE;
		}
		
		@Override
		public int doEndTag() throws JspException {
		
			
			return   EVAL_PAGE;
		}

		public String getMemberCode() {
			return memberCode;
		}

		public void setMemberCode(String memberCode) {
			this.memberCode = memberCode;
		}
		
}
