package com.rimi.ctibet.web.controller.vo;

import java.util.Date;
import java.util.List;

import com.rimi.ctibet.domain.Programa;

public class ReplyManageVO {
    
    // 标题
    private String title;
    // 链接
    private String linkUrl;
    // 相关code
    private String postCode;
    // 栏目code
    private String programaCode;
    // 栏目名字
    private String programaName;
    // 栏目对应的回复类型
    private String programaEnName;
    // 内容code（回复code）
    private String contentCode;
    // 回复内容
    private String content;
    // 回复类型
    private String contentType;
    // 会员code
    private String memberCode;
    // 会员名
    private String memberName;
    // 会员头像
    private String memberPic;
    // 会员性别 0女 1男
    private int memberSex;
    // 审核状态
    private int state;
    // 创建时间
    private Date createTime;
    
    
    // 接收参数
    private List<Programa> listPrograma;
    
    public ReplyManageVO() {}
    
    @Override
    public String toString() {
        return "ReplyManageVO [title=" + title + ", linkUrl=" + linkUrl
                + ", postCode=" + postCode + ", programaCode=" + programaCode
                + ", programaName=" + programaName + ", programaEnName="
                + programaEnName + ", contentCode=" + contentCode
                + ", content=" + content + ", contentType=" + contentType
                + ", memberCode=" + memberCode + ", memberName=" + memberName
                + ", memberPic=" + memberPic + ", memberSex=" + memberSex
                + ", state=" + state + ", createTime=" + createTime
                + ", listPrograma=" + listPrograma + ", toString()="
                + super.toString() + "]";
    }

    /********************************************
     * Setter Getter
     ********************************************/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getProgramaCode() {
        return programaCode;
    }

    public void setProgramaCode(String programaCode) {
        this.programaCode = programaCode;
    }

    public String getProgramaName() {
        return programaName;
    }

    public void setProgramaName(String programaName) {
        this.programaName = programaName;
    }

    public String getProgramaEnName() {
        return programaEnName;
    }

    public void setProgramaEnName(String programaEnName) {
        this.programaEnName = programaEnName;
    }

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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

    public String getMemberPic() {
        return memberPic;
    }

    public void setMemberPic(String memberPic) {
        this.memberPic = memberPic;
    }

    public int getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(int memberSex) {
        this.memberSex = memberSex;
    }

    public List<Programa> getListPrograma() {
        return listPrograma;
    }

    public void setListPrograma(List<Programa> listPrograma) {
        this.listPrograma = listPrograma;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
}
