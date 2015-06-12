package com.rimi.ctibet.web.controller.vo;

import java.util.Date;

public class MemberVO {
    // 会员code
    String code;
    // 有效性
    int avaliable;
    // 状态
    int status;
    // 创建时间
    String createTime;
    // 创建ip
    String createIp;
    // 名字
    String name;
    // 头像
    String pic;
    // 性别
    int sex;
    // 描述
    String description;
    // 
    String phone;
    // 手机
    String mobile;
    // 是否验证
    int isVerified;
    // 验证时间
    Date verifyTime;
    // 邮箱
    String email;
    // 是否绑定
    int isBind;
    // 绑定时间
    Date bindTime;
    
    public MemberVO(){}

    @Override
    public String toString() {
        return "MemberVO [code=" + code + ", avaliable=" + avaliable
                + ", status=" + status + ", createTime=" + createTime
                + ", createIp=" + createIp + ", name=" + name + ", pic=" + pic
                + ", sex=" + sex + ", description=" + description + ", phone="
                + phone + ", mobile=" + mobile + ", isVerified=" + isVerified
                + ", verifyTime=" + verifyTime + ", email=" + email
                + ", isBind=" + isBind + ", bindTime=" + bindTime + "]";
    }

    /********************************************
     * Setter Getter
     ********************************************/
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(int avaliable) {
        this.avaliable = avaliable;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsBind() {
        return isBind;
    }

    public void setIsBind(int isBind) {
        this.isBind = isBind;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }
    
    
    
}
