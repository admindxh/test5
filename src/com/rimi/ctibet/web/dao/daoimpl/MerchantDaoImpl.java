package com.rimi.ctibet.web.dao.daoimpl;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.HTMLTagUtil;
import com.rimi.ctibet.common.util.HotelRatePlanHandler;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.ctripapi.HttpAccessAdapter;
import com.rimi.ctibet.domain.GroupTravel;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MerchantManage;
import com.rimi.ctibet.domain.MerchantType;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.portal.controller.vo.HotMerchantVo;
import com.rimi.ctibet.portal.controller.vo.MerchantVo;
import com.rimi.ctibet.web.dao.IMerchantDao;
import com.rimi.ctibet.web.dao.IMerchantTypeDao;
import com.rimi.ctibet.web.dao.IPraiseDao;
import com.rimi.ctibet.web.dao.IUserFavoriteDao;

@Repository("merchantDao")
public class MerchantDaoImpl extends BaseDaoImpl<Merchant> implements
		IMerchantDao {

	@Resource
	private IMerchantTypeDao merchantTypeDao;

	@Resource
	private IUserFavoriteDao userFavoriteDao;

	@Resource
	private IPraiseDao praiseDao;

	public void deleteMerchant(Merchant merchant) {
		merchant.setAvaliable(0);
		this.updateAsHibernate(merchant);
	}

	@Override
	public Pager getMerchantByMerchantType(Pager pager, String merchantType) {
		Map<String, Object> byMap = new HashMap<String, Object>();
		List<Merchant> merchants = new ArrayList<Merchant>();
		String hql = "select m,v.viewName from Merchant m , View v where m.viewCode = v.code";
		if (StringUtils.isNotBlank(merchantType)) {
			byMap.put("merchantType", merchantType);
			hql += " and m.merchantType = :merchantType ";
		}
		return findWithPagerByMap(hql, byMap, pager);
	}

	@Override
	public Pager getMerchantByViewId(Pager pager, String viewCode) {
		Map<String, Object> byMap = new HashMap<String, Object>();
		List<Merchant> merchants = new ArrayList<Merchant>();
		String hql = "select m,v.viewName from Merchant m , View v where m.viewCode = v.code";
		if (StringUtils.isNotBlank(viewCode)) {
			byMap.put("viewCode", viewCode);
			hql += " and m.viewCode = :viewCode ";
		}
		return findWithPagerByMap(hql, byMap, pager);
	}

	@Override
	public List<String> getMerchantImgListByCode(String merchantCode) {
		Merchant merchant = this.findByCode(merchantCode);
		String[] imgs = merchant.getMerchantImage().split(",");
		List<String> imgPaths = new ArrayList<String>();
		for (String imgPath : imgs) {
			imgPaths.add(imgPath);
		}

		return imgPaths;
	}

	@Override
	public Pager getMerchantByAvaliable(Pager pager, String isAvailable) {
		Map<String, Object> byMap = new HashMap<String, Object>();
		List<Merchant> merchants = new ArrayList<Merchant>();
		String hql = "select m,v.viewName from Merchant m , View v where m.viewCode = v.code";
		if (StringUtils.isNotBlank(isAvailable)) {
			byMap.put("available", Integer.valueOf(isAvailable));
			hql += " and m.available = :available ";
		}
		return findWithPagerByMap(hql, byMap, pager);
	}

	@Override
	public Merchant findByCode(String code) {
		final String mcode = code;
		return getHibernateTemplate().execute(
				new HibernateCallback<Merchant>() {
					@Override
					public Merchant doInHibernate(org.hibernate.Session arg0)
							throws HibernateException, SQLException {
						Query query1 = arg0
								.createQuery(
										"select m from Merchant m where m.avaliable <> '0' and  m.code= ?  ")
								.setString(0, mcode);
						Query query2 = arg0
								.createQuery(
										"select gt from GroupTravel gt where  gt.avaliable <> '0' and gt.code= ?  ")
								.setString(0, mcode);
						if (query1.list().size() >= 1)
							return (Merchant) query1.list().get(0);
						else if (query2.list().size() >= 1) {
							GroupTravel gt = (GroupTravel) query2.list().get(0);
							Merchant m = new Merchant();
							m.setIsRecommend(gt.getIsRecommend());
							m.setMerchantName(gt.getName());
							m.setMerchantImage(gt.getImg());
							m.setPrice(gt.getPrice());
							m.setUrl(gt.getUrl());
							m.setCode(gt.getCode());
							m.setMerchantDetail(gt.getDetail());
							return m;
						} else
							return null;
					}
				});
	}

	/** 通过商户类型获取商户 有效信息 */
	public Pager getMerchantByMerchantTypeSql(Pager pager, String type) {
		StringBuffer sql = new StringBuffer();
		List<Object> param = new ArrayList<Object>();
		sql
				.append(" SELECT *,avaliable as available FROM merchant WHERE avaliable=1 ");
		if (type != null && !type.equals("")) {
			sql.append(" AND merchantType=? ");
			param.add(type);
		}
		return findListPagerBySql(Merchant.class, pager, sql.toString(), param);
	}

	/** 通过栏目内容中间表栏目code和商户类型获取有效商户 */
	public Pager getMerchantByProCodeMerchantTypeSql(Pager pager,
			String proCode, String type) {
		StringBuffer sql = new StringBuffer();
		List<Object> param = new ArrayList<Object>();
		sql
				.append(" SELECT m.*,m.avaliable as available FROM programa_content pc LEFT JOIN merchant m ON pc.conCode = m.code WHERE m.avaliable = 1 ");
		if (proCode != null && !proCode.equals("")) {
			sql.append(" AND pc.proCode=? ");
			param.add(proCode);
		}
		if (type != null && !type.equals("")) {
			sql.append(" AND m.merchantType=? ");
			param.add(type);
		}
		return findListPagerBySql(Merchant.class, pager, sql.toString(), param);
	}

	/** 通过栏目内容中间表栏目code和商户类型获取指定行数的有效商户 */
	public List<Merchant> getMerchantByProCodeMerchantTypeSql(String proCode,
			String type, int row) {
		StringBuffer sql = new StringBuffer();
		List<Object> param = new ArrayList<Object>();
		sql
				.append(" SELECT m.*,m.avaliable as available FROM programa_content pc LEFT JOIN merchant m ON pc.conCode = m.code WHERE m.avaliable = 1 ");
		if (proCode != null && !proCode.equals("")) {
			sql.append(" AND pc.proCode=? ");
			param.add(proCode);
		}
		if (type != null && !type.equals("")) {
			sql.append(" AND m.merchantType=? ");
			param.add(type);
		}
		return findListBySqlRow(Merchant.class, row, sql.toString(), param);
	}

	/** 通过商户code 查询有效商户 */
	public Merchant getAvailableMerchantByCode(String code) {
		StringBuffer sql = new StringBuffer(
				" SELECT *,avaliable as available FROM merchant WHERE avaliable=1 ");
		sql.append(" AND code = " + code);
		List<Merchant> list = findListBySql(Merchant.class, sql.toString());
		return (list != null && list.size() > 0) ? (list.get(0)) : (null);
	}

	@Override
	public Map<String, List<Merchant>> getProtalMerchant(int num) {
		Map<String, List<Merchant>> merchantMap = new HashMap<String, List<Merchant>>();
		final String hql = "from MerchantType";
		List<MerchantType> typeList = getHibernateTemplate().execute(
				new HibernateCallback<List<MerchantType>>() {
					@Override
					public List<MerchantType> doInHibernate(
							org.hibernate.Session arg0)
							throws HibernateException, SQLException {
						Query query = arg0.createQuery("from MerchantType");
						if (query.list().size() >= 1)
							return (List<MerchantType>) query.list();
						else
							return null;
					}
				});

		for (final MerchantType merchantType : typeList) {
			final String hql2 = "from Merchant m where m.merchantType = ? ";
			List<Merchant> merchantList = getHibernateTemplate().execute(
					new HibernateCallback<List<Merchant>>() {
						@Override
						public List<Merchant> doInHibernate(
								org.hibernate.Session arg0)
								throws HibernateException, SQLException {
							Query query = arg0.createQuery(
									"from Merchant m where m.merchantType = ?")
									.setString(0, merchantType.getCode());
							if (query.list().size() >= 1)
								return (List<Merchant>) query.list();
							else
								return null;
						}
					});
			merchantMap.put(merchantType.getName(), merchantList);
		}
		return merchantMap;
	}

	@Override
	public Pager searchMerchant(Pager pager, String areaCode,
			String distintionCode, String type, String isOffice,
			String keyWord, String rule) {
		//如需恢复来删除到下面代码即可-------------
		if(true){
			return search2(pager, areaCode, distintionCode, type, isOffice, keyWord, rule);
		}
		//-----------
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT d.destinationName,mt.`name`,m.merchantName,m.isRecommend ,m.code,m.url  " +
				"FROM merchant m LEFT JOIN praise ON praise.contentcode = m.`code` LEFT JOIN destination d ON  m.distination = d.`code`  " +
				"LEFT JOIN merchant_view mv ON m.`code` = mv.merchantCode " +
				"LEFT JOIN merchant_type mt ON mt.`code` = m.merchantType  ,tview v  " +
				"WHERE m.avaliable = 1 AND  mv.viewCode = v.`code`  ");
		// if (StringUtils.isNotBlank(areaCode)) {
		// sql.append("AND m.viewCode = ? ");
		// params.add(areaCode);
		// }
		if (StringUtils.isNotBlank(distintionCode)) {
			sql.append("AND m.distination = ? ");
			params.add(distintionCode);
			sql.append("AND d.`code` = ? ");
			params.add(distintionCode);
		} else {
			sql.append("AND m.distination = d.`code` ");
		}
		if (StringUtils.isNotBlank(type)) {
			sql.append("AND m.merchantType = ? ");
			params.add(type);
			sql.append("AND mt.`code` = ? ");
			params.add(type);
		} else {
			sql.append("AND mt.`code` = m.merchantType ");
		}
		if (StringUtils.isNotBlank(isOffice) && !isOffice.equals("2")) {// 2表示默认前台传的是查询所有类型
			sql.append("AND m.isRecommend = ? ");
			params.add(isOffice);
		}
		if (StringUtils.isNotBlank(keyWord)) {
			sql.append("AND m.merchantName LIKE ? ");
			params.add("%" + keyWord + "%");
		}

		sql.append("  AND praise.contentcode = m.`code` GROUP BY m.`code`");
		if ("view".equals(rule))
			sql.append("  ORDER BY  p.viewcount DESC");
		if ("reply".equals(rule))
			sql.append(" ORDER BY  p.replyNum DESC");
		if ("collect".equals(rule))
			sql.append(" ORDER BY  p.favoriteNum DESC");
		return findByJDBCSql(sql.toString(), params, pager);
	}
	private Pager search2(Pager pager, String areaCode,
			String distintionCode, String type, String isOffice,
			String keyWord, String rule){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT d.destinationName,mt.`name`,m.merchantName,m.isRecommend ,m.code,m.url  " +
				"FROM merchant m LEFT JOIN praise ON praise.contentcode = m.`code`  LEFT JOIN destination d ON  m.distination = d.`code`  " +
				"LEFT JOIN merchant_view mv ON m.`code` = mv.merchantCode " +
				"LEFT JOIN merchant_type mt ON mt.`code` = m.merchantType  " +
				"WHERE m.avaliable = 1  ");
		if (StringUtils.isNotBlank(distintionCode)) {
			sql.append("AND m.distination = ? ");
			params.add(distintionCode);
			sql.append("AND d.`code` = ? ");
			params.add(distintionCode);
		} 
		if (StringUtils.isNotBlank(type)) {
			sql.append("AND m.merchantType = ? ");
			params.add(type);
			sql.append("AND mt.`code` = ? ");
			params.add(type);
		}
		if (StringUtils.isNotBlank(isOffice) && !isOffice.equals("2")) {// 2表示默认前台传的是查询所有类型
			sql.append("AND m.isRecommend = ? ");
			params.add(isOffice);
		}
		if (StringUtils.isNotBlank(keyWord)) {
			sql.append("AND m.merchantName LIKE ? ");
			params.add("%" + keyWord + "%");
		}

		sql.append("   GROUP BY m.`code`");
		if ("view".equals(rule))
			sql.append("  ORDER BY  p.viewcount DESC");
		if ("reply".equals(rule))
			sql.append(" ORDER BY  p.replyNum DESC");
		if ("collect".equals(rule))
			sql.append(" ORDER BY  p.favoriteNum DESC");
		return findByJDBCSql(sql.toString(), params, pager);
	}

	// 游西藏首页-推荐商户显示数据封装
	@Override
	public List<MerchantVo> getPortalMerchant(int num) {
		List<MerchantVo> merchantVos = new ArrayList<MerchantVo>();
		List<MerchantManage> merchantManages = new ArrayList<MerchantManage>();
		// 获取到商户管理的对象集合
		merchantManages = getHibernateTemplate().execute(
				new HibernateCallback<List<MerchantManage>>() {
					@Override
					public List<MerchantManage> doInHibernate(
							org.hibernate.Session arg0)
							throws HibernateException, SQLException {
						Query query = arg0
								.createQuery("from MerchantManage mm where mm.available = 1 ");
						if (query.list().size() >= 1)
							return (List<MerchantManage>) query.list();
						else
							return null;
					}
				});
		if (merchantManages != null && merchantManages.size() > 0) {
			for (MerchantManage merchantManage : merchantManages) {
				// 封转数据对象
				MerchantVo mvo = new MerchantVo();
				MerchantType merchantType;
				if (merchantManage.getMerchantTypeCode().equals("gt")) {
					merchantType = new MerchantType();
					merchantType.setName("团游出行");
					merchantType.setUrl("");
				} else {
					merchantType = merchantTypeDao.findByCode(merchantManage
							.getMerchantTypeCode());
				}
				if (merchantType != null) {
					mvo.setmType(merchantType.getName());
					mvo.setmUrl(merchantType.getUrl());
					String[] merchantCodes = {
							merchantManage.getMerchantCode1(),
							merchantManage.getMerchantCode2(),
							merchantManage.getMerchantCode3(),
							merchantManage.getMerchantCode4() };
					if (merchantCodes.length != 0) {
						List<Merchant> merchants = new ArrayList<Merchant>();
						for (int i = 0; i < merchantCodes.length; i++) {
							Merchant merchant = this
									.findByCode(merchantCodes[i]);

							if (merchant == null) {
								continue;
							}
							// Praise praise = (Praise)
							// getMySession().createQuery("from Praise p where p.contentCode = ? ").setString(0,
							// merchant.getCode()).uniqueResult();
							Praise praise = null;
							if (merchant != null) {
								praise = praiseDao
										.getPraiseContentCode(merchant
												.getCode());
							}
							if (praise != null) {
								Integer praiseNum = praise
										.getFalseFavoriteNum();
								// 将赞数和保存到help属性中去
								merchant.setHelp(praiseNum.toString());
							} else
								merchant.setHelp("0");
							if (merchant != null)
								merchants.add(merchant);
						}
						mvo.setMerchantList(merchants);
						merchantVos.add(mvo);
					}
				} else {
					// 刘洪兵新增 当本栏不存在数据时用null代替
					merchantVos.add(null);
				}
			}
			return merchantVos;
		}
		return null;
	}

	// 根据目的地code得到商户(辅助热门商户)
	public List<Merchant> getMerchantsByDestination(String destinationCode) {
		final String fdestinationCode = destinationCode;
		List<Merchant> merchantList = new ArrayList<Merchant>();
		merchantList = getHibernateTemplate().execute(
				new HibernateCallback<List<Merchant>>() {
					@Override
					public List<Merchant> doInHibernate(
							org.hibernate.Session arg0)
							throws HibernateException, SQLException {
						Query query = arg0
								.createQuery(
										"from Merchant m where m.avaliable = '1' and  m.distination = ? ")
								.setString(0, fdestinationCode);
						if (query.list().size() >= 1)
							return (List<Merchant>) query.list();
						else
							return null;
					}
				});
		return merchantList;
	}

	// 根据目的地code与显示条数，做出热门的商户推举
	@Override
	public List<HotMerchantVo> getHotMerchant(int num, String destinationCode) {

		List<HotMerchantVo> mvos = new ArrayList<HotMerchantVo>();
		// 进入首页没有目的地code
		if (StringUtils.isBlank(destinationCode)) {
			List<Merchant> merchants = this.findAllAvaliable();
			for (Merchant merchant : merchants) {
				// 获取商户的回复数
				List<Object> params1 = new ArrayList<Object>();
				params1.add(merchant.getCode());
				params1.add(merchant.getCode());
				String replyCountSql = "SELECT r.code FROM merchant m ,reply r "
						+ "WHERE r.postCode = ? AND m.`code` = ?";
				Integer replyCount = findCountBySql(replyCountSql, params1);
				// Integer replyCount =
				// (Integer)findListBysql(replyCountSql,params1).get(0);
				// 获取收藏数
				String favoriteCountSql = "SELECT uf.id FROM merchant m, user_favorite uf "
						+ "WHERE uf.state = '1' AND m.avaliable = '1' AND m.`code` = ? AND uf.`code` = ?";
				Integer favoriteCount = findCountBySql(favoriteCountSql,
						params1);
				HotMerchantVo mvo = new HotMerchantVo();
				mvo.setMerchant(merchant);
				mvo.setMerchantCollectCount(favoriteCount);
				mvo.setMerchantCommetnCount(replyCount);
				mvos.add(mvo);
			}
			if (mvos.size() <= num) {
				num = mvos.size();
			}
			Comparator comp = new Mycomparator();
			Collections.sort(mvos, comp);
			return mvos.subList(0, num);
		}
		// 获取指定目的地的商户集合
		List<Merchant> merchants = this
				.getMerchantsByDestination(destinationCode);
		if (ListUtil.isNotNull(merchants)) {
			for (Merchant merchant : merchants) {
				// 获取商户的回复数
				List<Object> params1 = new ArrayList<Object>();
				params1.add(merchant.getCode());
				params1.add(destinationCode);
				params1.add(merchant.getCode());
				String replyCountSql = "SELECT r.code FROM merchant m ,reply r "
						+ "WHERE r.postCode = ? AND m.distination = ?  AND m.`code` = ?";
				Integer replyCount = findCountBySql(replyCountSql, params1);
				// Integer replyCount =
				// (Integer)findListBysql(replyCountSql,params1).get(0);
				// 获取收藏数
				String favoriteCountSql = "SELECT uf.id FROM merchant m, user_favorite uf "
						+ "WHERE uf.state = '1' AND m.avaliable = '1' AND uf.`code` = ? AND m.distination = ? AND m.`code` = ?";
				Integer favoriteCount = findCountBySql(favoriteCountSql,
						params1);
				// Integer favoriteCount =
				// (Integer)findListBysql(replyCountSql,params1).get(0);

				HotMerchantVo mvo = new HotMerchantVo();
				mvo.setMerchant(merchant);
				mvo.setMerchantCollectCount(favoriteCount);
				mvo.setMerchantCommetnCount(replyCount);
				mvos.add(mvo);
			}
		} else {
			// System.out.println("木有数据啊空指针了啊");
		}
		// 排序，取到前num个
		Comparator comp = new Mycomparator();
		Collections.sort(mvos, comp);
		//
		if (mvos != null && mvos.size() < num) {
			return mvos;
		}
		return mvos.subList(0, num);
	}

	// 根据目的地code与显示条数，做出热门的商户推举
	@Override
	public List<HotMerchantVo> getAllHotMerchant(int num) {

		List<HotMerchantVo> mvos = new ArrayList<HotMerchantVo>();
		// 获取指定目的地的商户集合
		List<Merchant> merchants = this.findAllAvaliable();
		for (Merchant merchant : merchants) {
			// 获取商户的回复数
			List<Object> params1 = new ArrayList<Object>();
			params1.add(merchant.getCode());
			String replyCountSql = "SELECT r.code FROM merchant m ,reply r "
					+ "WHERE m.`code` = r.postCode   AND m.`code` = ?";
			Integer replyCount = findCountBySql(replyCountSql, params1);
			// Integer replyCount =
			// (Integer)findListBysql(replyCountSql,params1).get(0);
			// 获取收藏数
			String favoriteCountSql = "SELECT uf.id FROM merchant m, user_favorite uf "
					+ "WHERE m.`code` = uf.`code`  AND uf.state = '1' AND m.avaliable = '1' AND m.`code` = ?";
			Integer favoriteCount = findCountBySql(favoriteCountSql, params1);
			// Integer favoriteCount =
			// (Integer)findListBysql(replyCountSql,params1).get(0);

			HotMerchantVo mvo = new HotMerchantVo();
			mvo.setMerchant(merchant);
			mvo.setMerchantCollectCount(favoriteCount);
			mvo.setMerchantCommetnCount(replyCount);
			mvos.add(mvo);
		}
		// 排序，取到前num个
		Comparator comp = new Mycomparator();
		Collections.sort(mvos, comp);
		return mvos.subList(0, num);
	}

	// 自定义排序规则
	class Mycomparator implements Comparator {

		public int compare(Object o1, Object o2) {
			HotMerchantVo p1 = (HotMerchantVo) o1;
			HotMerchantVo p2 = (HotMerchantVo) o2;
			if (p1.getMerchantCommetnCount() < p2.getMerchantCommetnCount())
				return 1;
			else
				return 0;
		}
	}

	@Override
	public List<MerchantManage> getMerchantManage() {
		return null;
	}

	@Override
	public Pager getFrontMerchantList(Pager pager, String viewCode,
			String destinationCode, String keyWord, String type, String rule) {
		String sql = "SELECT m.isRecommend,m.code, m.merchantName,m.price,m.url,m.merchantDetail,m.ctripUrl,m.merchantSummary,m.merchantImage,mt.`name`,d.destinationName,v.viewName, "
				+ "praise.falsepraise  AS falsepraise ,"
				+ "praise.falseFavoriteNum AS falsefavoritenum ,mt.`code` AS type "
				+ "FROM merchant m "
				+ "LEFT JOIN praise ON praise.contentcode = m.`code` "
				+ "LEFT JOIN destination d ON  m.distination = d.`code` "
				+ "LEFT JOIN merchant_view mv ON m.`code` = mv.merchantCode "
				+ "LEFT JOIN merchant_type mt ON mt.`code` = m.merchantType  ,tview v WHERE m.avaliable = 1 AND  mv.viewCode = v.`code` ";
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(viewCode)) {
			sql += " AND v.`code` = ?  ";
			params.add(viewCode);
		}
		if (StringUtils.isNotBlank(destinationCode)) {
			sql += " AND d.`code` = ?  ";
			params.add(destinationCode);
		}
		if (StringUtils.isNotBlank(keyWord)) {
			sql += " AND (m.merchantName LIKE ? OR m.merchantDetail LIKE ? ) ";
			params.add("%" + keyWord + "%");
			params.add("%" + keyWord + "%");
		}
		if (StringUtils.isNotBlank(type) && !"0".equals(type)) {
			sql += " AND mt.`code` = ? ";
			params.add(type);
		}
		sql += " GROUP BY m.`code` ";
		if (StringUtils.isNotBlank(rule)) {
			if ("favorite".equals(rule)) {
				sql += " ORDER BY falsepraise desc  ";
			} else if ("collect".equals(rule))
				sql += " ORDER BY falsefavoritenum desc ";
		}
		List<Map<String, Object>> ms = new ArrayList<Map<String, Object>>();
		ms = findByJDBCSql(sql, params, pager).getDataList();
		if (ms != null && ms.size() > 0) {
			for (Map<String, Object> map : ms) {
				String priceRef = map.get("price").toString();
				if(StringUtils.isNotBlank(priceRef)&&priceRef.indexOf("暂无")>-1){
					map.put("price", 0);
				}
				map.put("merchantName", HTMLTagUtil.delHTMLTag(map.get(
						"merchantName").toString()));
				map.put("merchantDetail", HTMLTagUtil.delHTMLTag(map.get(
						"merchantDetail").toString()));
				map.put("merchantDetail", map.get("merchantDetail").toString().replace("&nbsp;", ""));
			}
		}
		Pager resultPpager = findByJDBCSql(sql, params, pager);
		resultPpager.setDataList(ms);
		return resultPpager;
	}

	@Override
	public List<Map<String, Object>> getFrontMerchantByType(String typeCode,
			int num) {
		List<Object> params = new ArrayList<Object>();
		params.add(typeCode);
		params.add(num);
		String sql = "SELECT m.*,p.falseFavoriteNum as falsepraise,d.destinationName,ifnull(p.viewcount,0) as viewcount " +
				"FROM merchant m " +
				"LEFT JOIN destination d ON m.distination = d.`code` " +
				"LEFT JOIN praise p ON p.contentcode = m.`code` " +
				"WHERE m.avaliable = 1  " +
				"AND m.merchantType = ? " +
				"GROUP BY m.`code`"+
        		"LIMIT 0,? ";
		
		List<Map<String, Object>> merchants = findByJDBCSql(sql, params);
		for (Map<String, Object> map : merchants) {
			map.put("merchantname", HTMLTagUtil.delHTMLTag(map.get(
					"merchantname").toString()));
			map.put("merchantdetail", HTMLTagUtil.delHTMLTag(map.get(
					"merchantdetail").toString()));
		}
		return merchants;
	}

	@Override
	public List<Map<String, Object>> getFrontTravel(int num) {
		return null;
	}

	@Override
	public List<Map<String, Object>> getOffice(int num) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT m.* ,p.falsepraise,d.destinationName,ifnull(p.viewcount,0) as viewcount FROM merchant m ,destination d,praise p  "
				+ "WHERE p.contentcode = m.`code` AND d.`code` = m.distination  AND m.avaliable = 1 AND m.`code`  "
				+ "IN (SELECT c.content "
				+ "FROM content c WHERE c.contentType = '商户首页推荐') GROUP BY m.`code` limit 0,?";
		params.add(num);
		return findByJDBCSql(sql, params);

	}

	@Override
	public List<Map<String, Object>> getViewsByMerchantCode(String merchantCode) {
		List<Object> params = new ArrayList<Object>();
		params.add(merchantCode);
		String sql = "SELECT v.* FROM merchant m,tview v ,merchant_view mv WHERE m.`code` = mv.merchantCode AND v.avaliable=1 and v.`code` = mv.viewCode AND m.`code` = ? GROUP BY v.`code`";
		return findByJDBCSql(sql, params);
	}

	@Override
	public Pager getReplyInfoByMerchantCode(Pager pager, String merchantCode) {
		List<Object> params = new ArrayList<Object>();
		params.add(merchantCode);
		String sql = "SELECT c.createuserCode,c.createTime,c.content,mi.`name`,mi.pic,mi.sex, c.title "
				+ "FROM merchant m, reply r,content c,member me,member_info mi "
				+ "WHERE  c.avaliable = 1 and c.state=1 and m.`code` = r.postCode AND c.`code` = r.contentCode AND c.createuserCode = mi.memberCode AND me.`code` = mi.memberCode AND m.`code` = ? "
				+ " GROUP BY  c.createTime ORDER BY  c.createTime DESC";

		return findByJDBCSql(sql, params, pager);
	}

	@Override
	public List<Map<String, Object>> getPraiseAndView(String merchantCode) {
		List<Object> params = new ArrayList<Object>();
		params.add(merchantCode);
		String sql = "SELECT p.falseFavoriteNum,p.falseViewcount FROM merchant m,praise p WHERE p.contentcode = m.`code` AND m.`code` = ? ";
		return findByJDBCSql(sql, params);
	}

	@Override
	public List<HotMerchantVo> getHotMerchantByView(String viewCode, int row) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("  ");
		sql.append(" SELECT  ");
		sql.append("     m.*, ");
		sql.append("     p.falseFavoriteNum as falseFavoriteNum, ");
		sql.append("     IFNULL(f.favNum, 0) AS favoriteNum ");
		sql.append(" FROM merchant m  ");
		sql.append(" INNER JOIN ( ");
		sql
				.append("     SELECT merchantCode, viewCode FROM merchant_view GROUP BY merchantCode, viewCode ");
		sql.append(" ) mv ON m.code=mv.merchantCode ");
		sql.append(" LEFT JOIN( ");
		sql
				.append("     SELECT  code, type, COUNT(code) AS favNum FROM user_favorite uf ");
		sql.append("     WHERE type = 'merchant' ");
		sql.append("     GROUP BY code , type ");
		sql.append(" ) f ON f.code = m.code ");
		sql
				.append(" LEFT JOIN praise p ON p.contentcode=m.code AND p.available=1 ");
		sql.append(" WHERE m.avaliable = 1  ");
		sql.append("     AND m.isRecommend = 1 ");

		if (StringUtil.isNotNull(viewCode)) {
			sql.append(" AND mv.viewCode = ? ");
			params.add(viewCode);
		}

		sql.append(" ORDER BY m.createTime DESC ");
		return findListBySqlRow(HotMerchantVo.class, row, sql.toString(),
				params);
	}

	@Override
	public Pager orderByMerchat(Pager pager, String rule) {
		String sql = "SELECT v.viewName,d.destinationName,mt.`name`,m.merchantName,m.isRecommend ,m.code  "
				+ "FROM merchant m ,destination d ,merchant_type mt ,merchant_view mv,tview v ,praise p "
				+ " WHERE m.`code` = mv.merchantCode AND mv.viewCode = v.`code`  "
				+ "AND m.distination = d.`code` AND mt.`code` = m.merchantType AND m.avaliable = 1 "
				+ "AND p.contentcode = m.`code` " + "GROUP BY m.`code` ";
		if ("view".equals(rule))
			sql += " ORDER BY  p.viewcount DESC";
		if ("reply".equals(rule))
			sql += " ORDER BY  p.replyNum DESC";
		if ("collect".equals(rule))
			sql += " ORDER BY  p.favoriteNum DESC";
		return findByJDBCSql(sql, null, pager);
	}

	@Override
	public List<Map<String, Object>> getMUrlAndCodeByMType(String mTypeCode,
			int num, String desCode) {
		String sql = "SELECT m.`code`,m.url FROM merchant m WHERE  m.avaliable=1 and m.merchantType = ?  ";
		if (StringUtils.isNotBlank(desCode)) {
			sql += " HAVING      (select  COUNT(mv.id)   from merchant_view  mv LEFT  JOIN  tview  tv on  tv.`code` = mv.viewCode  where     mv.merchantcode=m.code  and tv.destinationCode = '"
					+ desCode + "' ) >=1  ";
		}
		sql += "   LIMIT 0,?  ";
		List<Object> params = new ArrayList<Object>();
		params.add(mTypeCode);
		params.add(num);
		return findByJDBCSql(sql, params);
	}

	@Override
	public Pager getOfficeList(Pager pager) {
		String sql = "SELECT m.merchantName,m.url,m.merchantDetail,m.ctripUrl,m.merchantSummary,m.merchantImage,mt.`name`,d.destinationName,v.viewName, "
				+ "(SELECT praise.falsepraise FROM praise , merchant WHERE praise.contentcode = merchant.`code`AND m.`code` = merchant.`code`) AS falsepraise ,"
				+ "(SELECT praise.falseFavoriteNum FROM praise , merchant WHERE praise.contentcode = merchant.`code` AND m.`code` = merchant.`code`) AS falsefavoritenum "
				+ "FROM merchant m,destination d,tview v,merchant_view mv,merchant_type mt "
				+ "WHERE m.distination = d.`code` AND m.`code` = mv.merchantCode AND mv.viewCode = v.`code` AND mt.`code` = m.merchantType AND m.isRecommend = 1 ";
		sql += " GROUP BY m.`code` ";
		return findByJDBCSql(sql, null, pager);
	}

	@Override
	public String getCtripPrice(String mName) {
		String sql = "SELECT m.price FROM merchant m WHERE m.merchantName = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(mName);
		List<Map<String, Object>> result = findByJDBCSql(sql, params);
		if (result != null && result.size() > 0) {
			return (String) result.get(0).get("price");
		}
		return "0";
	}

	// 根据时间排序商户分类
	public List<Map<String, Object>> getTop3Mt() {
		String sql = "SELECT mt.* FROM merchant_type mt ORDER BY mt.createTime DESC limit 0,3";
		return findByJDBCSql(sql, null);
	}

	@Override
	public List<HotMerchantVo> getHotMerchantBydestinationCode(String destinationCode, int row) {
	    List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  ");
		sql.append("     m.* , ");
		sql.append("     p.falseFavoriteNum as falseFavoriteNum ");
		sql.append(" FROM merchant m  ");
		sql.append(" LEFT JOIN praise p ON p.contentcode = m.code AND p.available = 1 ");
		sql.append(" WHERE m.avaliable=1 AND m.isRecommend=1 ");
		sql.append(" AND m.distination=? ");
		params.add(destinationCode);
		sql.append(" ORDER BY m.createTime DESC ");
		return findListBySqlRow(HotMerchantVo.class, row, sql.toString(), params);
	}

	@Override
	public String getCtripNow(String mName) {
		String sql = "SELECT m.`code` FROM merchant m WHERE m.avaliable = 2 AND  m.merchantName = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(mName);
		String ctripCode = "";
		List<Map<String, Object>> res = findByJDBCSql(sql, params);
		if (res != null && res.size() > 0) {
			ctripCode = res.get(0).get("code").toString();
			String hotelRatePlan = HttpAccessAdapter
					.respForHotelRatePlan(ctripCode);
			ByteArrayInputStream inputStream;
			try {
				inputStream = new ByteArrayInputStream(hotelRatePlan
						.getBytes("UTF-8"));
				HotelRatePlanHandler ratePlanHandler = new HotelRatePlanHandler();
				SAXParserFactory factory1 = SAXParserFactory.newInstance();
				SAXParser saxParser1 = factory1.newSAXParser();
				saxParser1.parse(inputStream, ratePlanHandler);
				List<String> ratePlan = ratePlanHandler.getList();
				if (ratePlan.size() > 0) {
					Collections.sort(ratePlan, new Comparator<String>() {
						public int compare(String arg0, String arg1) {
							Double aDouble = Double.parseDouble(arg0);
							Double bDouble = Double.parseDouble(arg1);
							return aDouble.compareTo(bDouble);
						}
					});
				    String s =  ratePlan.get(0);
				    int i = s.indexOf(".");
				    String ss = s.substring(0, i);
					return ss;
				} else {
					return "0";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "0";
	}

    @Override
    public float getScore(String code) {
        StringBuffer sql = new StringBuffer();
        //sql.append("  ");
        sql.append(" SELECT  ");
        //sql.append("     sum(c.title),count(c.title), ");
        sql.append("     ifnull(sum(c.title)/count(c.title), 0) as score ");
        sql.append(" FROM merchant m ");
        sql.append("     LEFT JOIN reply r ON r.postCode=m.code ");
        sql.append("     LEFT JOIN content c ON r.contentCode=c.code and c.state=1 and c.avaliable=1 ");
        sql.append(" WHERE m.avaliable=1 ");
        sql.append("     AND m.code=? ");
        sql.append(" GROUP BY m.code ");
        return getJdbcTemplate().queryForObject(sql.toString(), new Object[]{code}, Float.class);
    }
}
