package com.rimi.ctibet.domain;

import com.rimi.ctibet.common.domain.BaseDomain;

/**
 * 内容赞，和浏览表
 * @author dengxh
 *
 */
public class Praise extends BaseDomain {
		
		private String  contentCode;//内容code
		private Integer truePraise=0;//真赞
		private Integer falsePraise=0;//假赞
		private Integer viewCount=0;//浏览次数
		
		private Integer falseViewcount=0;//假查看数
		private Integer favoriteNum=0;//收藏数
		private Integer falseFavoriteNum=0;//假收藏数
		private Integer replyNum=0;//回复数
		private Integer falseReplyNum=0;//假回复数
		
		public Praise() {
			// TODO Auto-generated constructor stub
		}
		
		
		public Praise(String contentCode) {
			super();
			this.contentCode = contentCode;
			this.truePraise = 0;
			this.falsePraise = 0;
			this.viewCount = 0;
			this.falseViewcount = 0;
			this.favoriteNum = 0;
			this.falseFavoriteNum = 0;
			this.replyNum = 0;
			this.falseReplyNum = 0;
		}


		/********************************************
		 * Setter Getter
		 ********************************************/
		public String getContentCode() {
			return contentCode;
		}
		public void setContentCode(String contentCode) {
			this.contentCode = contentCode;
		}
		public Integer getTruePraise() {
			if(truePraise==null)
			{
				truePraise= Integer.valueOf(0);
			}
			return truePraise;
		}
		public void setTruePraise(Integer truePraise) {
			this.truePraise = truePraise;
		}
		public Integer getFalsePraise() {
			if(falsePraise==null)
			{
				falsePraise= Integer.valueOf(0);
			}
			return falsePraise;
		}
		public void setFalsePraise(Integer falsePraise) {
			this.falsePraise = falsePraise;
		}
		public Integer getViewCount() {
			if(viewCount==null)
			{
				viewCount=Integer.valueOf(0);
			}
			return viewCount;
		}
		public void setViewCount(Integer viewCount) {
			this.viewCount = viewCount;
		}
		public Integer getFalseViewcount() {
			if(falseViewcount==null)
			{
				falseViewcount = Integer.valueOf(0);
			}
			return falseViewcount;
		}
		public void setFalseViewcount(Integer falseViewcount) {
			this.falseViewcount = falseViewcount;
		}
		public Integer getFavoriteNum() {
			if(favoriteNum==null)
			{
				favoriteNum=new Integer(0);
			}
			return favoriteNum;
		}
		public void setFavoriteNum(Integer favoriteNum) {
			this.favoriteNum = favoriteNum;
		}
		public Integer getFalseFavoriteNum() {
			if(falseFavoriteNum==null)
			{
				falseFavoriteNum = new Integer(0);
			}
			
			return falseFavoriteNum;
		}
		public void setFalseFavoriteNum(Integer falseFavoriteNum) {
			this.falseFavoriteNum = falseFavoriteNum;
		}
		public Integer getReplyNum() {
			if(replyNum==null)
			{
				replyNum = new Integer(0);
			}
			return replyNum;
		}
		public void setReplyNum(Integer replyNum) {
			this.replyNum = replyNum;
		}
		public Integer getFalseReplyNum() {
			if(falseReplyNum==null)
			{
				falseReplyNum =new Integer(0);
			}
			return falseReplyNum;
		}
		public void setFalseReplyNum(Integer falseReplyNum) {
			this.falseReplyNum = falseReplyNum;
		}
		
}
