package com.rimi.ctibet.portal.controller.vo;

import java.util.Date;

public class ReplyVO {
    
    //内容code
    private String contentCode;
    //内容
    private String content;
    //审核状态
    private int state;
    //会员code
    private String memberCode;
    //会员name
    private String memberName;
    //会员sex
    private int memberSex;
    //会员头像
    private String memberPic;
    //发表时间
    private Date createTime;
    
    public ReplyVO(){}
    
    /********************************************
     * Setter Getter
     ********************************************/
    public String getContentCode() {
        return contentCode;
    }
    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getMemberCode() {
        return memberCode;
    }
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public int getMemberSex() {
        return memberSex;
    }
    public void setMemberSex(int memberSex) {
        this.memberSex = memberSex;
    }
    public String getMemberPic() {
        return memberPic;
    }
    public void setMemberPic(String memberPic) {
        this.memberPic = memberPic;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
