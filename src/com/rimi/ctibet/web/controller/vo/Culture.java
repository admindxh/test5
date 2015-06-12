package com.rimi.ctibet.web.controller.vo;

import com.rimi.ctibet.domain.Content;

public class Culture extends Content {
	/**
	 * 排序规则编号规则:<br>
	 * 字段为content 属性 以1为前缀 如：createtime 为100; <br>
	 * 字段为 other 属性 以2为前缀 如： view 为201; <br>
	 * 后两位是表示 ORDER_TYPES数组中的行数数; <br>
	 * 字段字段名 和排序规则 如 view desc 按view字段 降序拍列
	 */
	public static String[][] ORDER_TYPES = {
			{ "100", "时间", "createTime desc" }, { "201", "阅读读量", "view desc" },
			{ "202", "评论数", "comment desc" }, { "203", "点赞数", "praise desc" },
			{ "204", "收藏数", "favorite desc" }, { "205", "评分", "score desc" } 
	};
	public static final String FEILD_VIEW = "view";// 阅读
	public static final String FEILD_FAVORITE = "favorite"; // 收藏
	public static final String FEILD_PRAISE = "praise"; // 点赞

	public static final String FEILD_RL_VIEW = "rlview";
	public static final String FEILD_RL_FAVORITE = "rlfavorite";
	public static final String FEILD_RL_PRAISE = "rlpraise";

	public static final String FEILD_RL_SCORE = "rlscore";
	public static final String FEILD_SCORE = "score";

	public static final String FEILD_COMMENT = "comment";
	public static final String FEILD_RL_COMMENT = "rlcomment";

	/**
	 * 文化类型编号： <br>
	 * 类似于子网划分方法设计 <br>
	 * 千位表示一种类型 <br>
	 * <br>
	 * 百位 和十位， 合起来表示一种类型 <br>
	 * 各位 表示一种类型 <br>
	 * 如：2021 ，2022 将分别表示 “文化传播”中的“小说”中的“玄幻小说” 和 “武侠小说”
	 */
	public static String[][] CULTURE_TYPES = { { "1010", "节日" },
			{ "1020", "历史" }, { "1030", "风俗" }, { "1040", "宗教" },
			{ "1050", "美食" }, { "1060", "动植物" }, { "1070", "名人" },
			{ "1080", "服饰" }, { "1090", "艺术" }, { "1100", "特产" },
			{ "1110", "地理" }, { "1120", "西藏动态" } };
	public static String[][] CULTURE_M_TYPES = { { "2010", "音乐" },
			{ "2020", "小说" }, { "2030", "游戏" }, { "2040", "其他" } };
	/** 文化-音乐 */
	public static int TYPE_M_MUSIC = 0;
	/** 文化-小说 */
	public static int TYPE_M_STORY = 1;
	/** 文化-游戏 */
	public static int TYPE_M_GAME = 2;
	/** 文化-节日 */
	public static int TYPE_HOLIDAY = 0;
	/** 文化-历史 */
	public static int TYPE_HISTORY = 1;
	/** 文化-风俗 */
	public static int TYPE_CUSTOM = 2;
	/** 文化-宗教 */
	public static int TYPE_RELIGION = 3;
	/** 文化-美食 */
	public static int TYPE_FOODS = 4;
	/** 文化-动植物 */
	public static int TYPE_BIONT = 5;
	/** 文化-名人 */
	public static int TYPE_CELEBRITY = 6;
	/** 文化-服饰 */
	public static int TYPE_COSTUME = 7;
	/** 文化-艺术 */
	public static int TYPE_ART = 8;
	/** 文化-特产 */
	public static int TYPE_SPECIALTY = 9;
	/** 文化-地理 */
	public static int TYPE_GEOGRAHY = 10;
	/** 文化-动态 */
	public static int TYPE_DYNAMIC = 11;
	private String dates;

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getTypeName() {
		String typName = null;
		String type = getContentType();
		int post = Integer.parseInt(type);
		int index = (post / 10) % 100 - 1;
		switch (post / 1000) {
		case 1:
			typName = CULTURE_TYPES[index][1];
			break;
		case 2:
			typName = CULTURE_M_TYPES[index][1];
			break;
		default:
			typName = "其他";
			break;
		}
		return typName;
	}

	private String typeName;

	@Override
	public void setContentType(String contentType) {
		super.setContentType(contentType);
		typeName = getTypeName();
	}

	public static String getTypeId(int TypeName) {
		return CULTURE_TYPES[TypeName][0];
	}

	public static String getMTypeId(int typeName) {
		return CULTURE_M_TYPES[typeName][0];
	}

}