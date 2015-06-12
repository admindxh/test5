package com.rimi.ctibet.portal.controller.vo;

import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Merchant;

/**
 * 热门商户展示，先按照地区匹配，如果地区没有则按照景点匹配
 * @author xiaozhen
 *
 */
public class HotMerchantVo extends Merchant {

    private Merchant merchant;
    private String  code;
    private Integer merchantCommetnCount;
    private Integer merchantCollectCount;
    int favoriteNum;
    int falseFavoriteNum;
    String merchantDetailCleanHtml;

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Integer getMerchantCommetnCount() {
        return merchantCommetnCount;
    }

    public void setMerchantCommetnCount(Integer merchantCommetnCount) {
        this.merchantCommetnCount = merchantCommetnCount;
    }

    public Integer getMerchantCollectCount() {
        return merchantCollectCount;
    }

    public void setMerchantCollectCount(Integer merchantCollectCount) {
        this.merchantCollectCount = merchantCollectCount;
    }

    public int getFavoriteNum() {
        return favoriteNum;
    }

    public void setFavoriteNum(int favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public int getFalseFavoriteNum() {
        return falseFavoriteNum;
    }

    public void setFalseFavoriteNum(int falseFavoriteNum) {
        this.falseFavoriteNum = falseFavoriteNum;
    }

    public String getMerchantDetailCleanHtml() {
        this.merchantDetailCleanHtml = StringUtil.cleanHTML(super.getMerchantDetail());
        return merchantDetailCleanHtml;
    }

    public void setMerchantDetailCleanHtml(String merchantDetailCleanHtml) {
        this.merchantDetailCleanHtml = merchantDetailCleanHtml;
    }

	@Override
	public String toString() {
		return "HotMerchantVo [code=" + code + ", falseFavoriteNum="
				+ falseFavoriteNum + ", favoriteNum=" + favoriteNum
				+ ", merchant=" + merchant + ", merchantCollectCount="
				+ merchantCollectCount + ", merchantCommetnCount="
				+ merchantCommetnCount + ", merchantDetailCleanHtml="
				+ merchantDetailCleanHtml + "]";
	}
    
}
