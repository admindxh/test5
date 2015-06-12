package com.rimi.ctibet.domain;

import java.util.Arrays;

import javax.persistence.Transient;

import com.rimi.ctibet.common.domain.BaseDomain;
import com.rimi.ctibet.common.util.StringUtil;

public class View extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	
	public static final int ORDER_WANTCOUNT=1;	//想去数排序
	public static final int ORDER_GONECOUNT=2;	//去过数排序
	public static final int ORDER_CHECKNUM=3;	//查看数排序
	public static final int ORDER_REPLYNUM=4;	//评论数排序
	public static final int ORDER_REAL_WANTCOUNT=5;	//真想去数排序
	public static final int ORDER_REAL_GONECOUNT=6;	//真去过数排序
	public static final int ORDER_REAL_CHECKNUM=7;	//真查看数排序
	public static final int ORDER_CREATETIME=-1;	//创建时间
	public static final int ORDER_EDITTIME=8;	//修改时间
	
	//public static final String LINK_URL_PORTAL_VIEW_DETAIL = "portal/view/forViewDetail?code=";	//前台景点详情页地址
	public static final String LINK_URL_PORTAL_VIEW_DETAIL = "tourism/view/detail?code=";	//前台景点详情页地址
	
	//景点名称
	private String viewName;
	//所属目的地id
	private String destinationCode;
	//去过数
	private int goneCount;
	//假去过数
	private int fakeGoneCount;
	//想去数
	private int wantCount;
	//假想去数
	private int fakeWantCount;
	//景点缩略图
	private String img;
	//景点主图（逗号分隔）
	private String viewImage;
	//景点360全景
	private String view_360full;
	//景点介绍
	private String viewIntroduce;
	//门票最低预定价格
	private String ticketPrice;
	//门票预订网址
	private String ticketUrl;
	//是否有票
	private int  isHaveGateTicket;
	
	//查看数
	private int checkNum;
	//假查看数
	private int fakeCheckNum;
	//评论数
	private int replyNum;
	//赞
	private int likeNum;
	//假赞
	private int fakeLikeNum;
	
	//导览
	private String shortGuide;
	private String guide;
	//交通
	private String shortTraffic;
	private String traffic;
	//注意事项
	private String shortNotice;
	private String notice;
	
	
	//景点所在地址
	private String address;
	//高清全景链接
	private String hdFullUrl;
	//实景视频链接
	private String realSceneVideoUrl;
	
	//景点链接
	private String linkUrl;
	
	
	private String keyword;

	//百度地图经纬度
	private String xy;
	
	
	//辅助属性
	private String[] viewImageArray;
	private String destinationName;
	private String viewIntroduceCleanHtml;
    @Transient
    private String wanaOrGne;
	
	public View() {}

	public String getWanaOrGne() {
		return wanaOrGne;
	}

	public void setWanaOrGne(String wanaOrGne) {
		this.wanaOrGne = wanaOrGne;
	}
    public String getViewIntroduceCleanHtml() {
        return StringUtil.cleanHTML(viewIntroduce);
    }
    public void setViewIntroduceCleanHtml(String viewIntroduceCleanHtml) {
        this.viewIntroduceCleanHtml = viewIntroduceCleanHtml;
    }
	@Override
    public String toString() {
        return "View [viewName=" + viewName + ", destinationCode="
                + destinationCode + ", goneCount=" + goneCount
                + ", fakeGoneCount=" + fakeGoneCount + ", wantCount="
                + wantCount + ", fakeWantCount=" + fakeWantCount + ", img="
                + img + ", viewImage=" + viewImage + ", view_360full="
                + view_360full + ", viewIntroduce=" + viewIntroduce
                + ", ticketPrice=" + ticketPrice + ", ticketUrl=" + ticketUrl
                + ", isHaveGateTicket=" + isHaveGateTicket + ", checkNum="
                + checkNum + ", fakeCheckNum=" + fakeCheckNum + ", replyNum="
                + replyNum + ", likeNum=" + likeNum + ", fakeLikeNum="
                + fakeLikeNum + ", shortGuide=" + shortGuide + ", guide="
                + guide + ", shortTraffic=" + shortTraffic + ", traffic="
                + traffic + ", shortNotice=" + shortNotice + ", notice="
                + notice + ", address=" + address + ", hdFullUrl=" + hdFullUrl
                + ", realSceneVideoUrl=" + realSceneVideoUrl + ", linkUrl="
                + linkUrl + ", keyword=" + keyword + ", xy=" + xy
                + ", viewImageArray=" + Arrays.toString(viewImageArray)
                + ", destinationName=" + destinationName
                + ", viewIntroduceCleanHtml=" + viewIntroduceCleanHtml
                + ", wanaOrGne=" + wanaOrGne + ", toString()="
                + super.toString() + "]";
    }

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public int getGoneCount() {
		return goneCount;
	}

	public void setGoneCount(int goneCount) {
		this.goneCount = goneCount;
	}

	public int getWantCount() {
		return wantCount;
	}

	public void setWantCount(int wantCount) {
		this.wantCount = wantCount;
	}

	public String getViewImage() {
		return viewImage;
	}

	public void setViewImage(String viewImage) {
		this.viewImage = viewImage;
	}

	public String getView_360full() {
		return view_360full;
	}

	public void setView_360full(String view_360full) {
		this.view_360full = view_360full;
	}

	public String getViewIntroduce() {
		return viewIntroduce;
	}

	public void setViewIntroduce(String viewIntroduce) {
		this.viewIntroduce = viewIntroduce;
	}

	public String getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getTicketUrl() {
		return ticketUrl;
	}

	public void setTicketUrl(String ticketUrl) {
		this.ticketUrl = ticketUrl;
	}

	public int getIsHaveGateTicket() {
		return isHaveGateTicket;
	}

	public void setIsHaveGateTicket(int isHaveGateTicket) {
		this.isHaveGateTicket = isHaveGateTicket;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getFakeGoneCount() {
		return fakeGoneCount;
	}

	public void setFakeGoneCount(int fakeGoneCount) {
		this.fakeGoneCount = fakeGoneCount;
	}

	public int getFakeWantCount() {
		return fakeWantCount;
	}

	public void setFakeWantCount(int fakeWantCount) {
		this.fakeWantCount = fakeWantCount;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	public int getFakeCheckNum() {
		return fakeCheckNum;
	}

	public void setFakeCheckNum(int fakeCheckNum) {
		this.fakeCheckNum = fakeCheckNum;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	public int getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}

	public int getFakeLikeNum() {
		return fakeLikeNum;
	}

	public void setFakeLikeNum(int fakeLikeNum) {
		this.fakeLikeNum = fakeLikeNum;
	}

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHdFullUrl() {
        return hdFullUrl;
    }

    public void setHdFullUrl(String hdFullUrl) {
        this.hdFullUrl = hdFullUrl;
    }

    public String getRealSceneVideoUrl() {
        return realSceneVideoUrl;
    }

    public void setRealSceneVideoUrl(String realSceneVideoUrl) {
        this.realSceneVideoUrl = realSceneVideoUrl;
    }

    public String[] getViewImageArray() {
        if(StringUtil.isNotNull(getViewImage()))viewImageArray = getViewImage().split(",");
        return viewImageArray;
    }

    public void setViewImageArray(String[] viewImageArray) {
        this.viewImageArray = viewImageArray;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getXy() {
        return xy;
    }

    public void setXy(String xy) {
        this.xy = xy;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShortGuide() {
        return shortGuide;
    }

    public void setShortGuide(String shortGuide) {
        this.shortGuide = shortGuide;
    }

    public String getShortTraffic() {
        return shortTraffic;
    }

    public void setShortTraffic(String shortTraffic) {
        this.shortTraffic = shortTraffic;
    }

    public String getShortNotice() {
        return shortNotice;
    }

    public void setShortNotice(String shortNotice) {
        this.shortNotice = shortNotice;
    }

}
