package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.util.HTMLTagUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.GroupTravel;
import com.rimi.ctibet.domain.GroupTravelDestination;
import com.rimi.ctibet.domain.GroupTravelView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.controller.vo.GroupTravelVO;
import com.rimi.ctibet.web.dao.IGroupTravelDao;
import com.rimi.ctibet.web.dao.IGroupTravelDeatinationDao;
import com.rimi.ctibet.web.dao.IGroupTravelViewDao;
import com.rimi.ctibet.web.service.IGroupTravelService;
import com.rimi.ctibet.web.service.IUserFavoriteService;

@Transactional
@Service("groupTravelService")
public class GroupTravelServiceImpl implements IGroupTravelService {

	@Override
	public Pager findListByPager(String sql, List param, Pager pager) {
		// TODO Auto-generated method stub
		return groupTravelDao.findListPagerBySql(GroupTravel.class, pager, sql, param);
	}

	@Resource
	private IGroupTravelDao groupTravelDao;
	@Resource
	private IGroupTravelDeatinationDao groupTravelDestinationDao;
	@Resource
	private IGroupTravelViewDao groupTravelViewDao;
    @Resource
    private IUserFavoriteService userFavoriteService;
	@Override
	public void deleteGroupTravelByCode(String code) {
		if (StringUtils.isNotBlank(code)){
			groupTravelDao.deleteLogicalByCode(code);
			//删除关联的收藏表
		    userFavoriteService.deleteByCode(code);
		}
	}

	@Override
	public List<Destination> getDestinations(String groupTravelCode) {
        if(StringUtils.isNotBlank(groupTravelCode))
		   return groupTravelDao.getDestinations(groupTravelCode);
        return null;
	}

	@Override
	public GroupTravel getGroupTravelByCode(String code) {
		if(StringUtils.isNotBlank(code))
			return groupTravelDao.findByCode(code);
		return null;
	}

	@Override
	public List<View> getViews(String groupTravelCode) {
        if(StringUtils.isNotBlank(groupTravelCode))
        	return groupTravelDao.getViews(groupTravelCode);
		return null;
	}

	@Override
	public Pager orderByGroupTravel(Pager pager, String rule) {
		return groupTravelDao.orderByGroupTravel(pager, rule);
	}

	@Override
	public void saveGroupTravel(GroupTravel gt) {
        groupTravelDao.save(gt);
	}

	@Override
	public Pager searchGroupTravel(Pager pager, String keyWord,
			String destinationCode, String viewCode,String isRecommend) {
		return groupTravelDao.searchGroupTravel(pager, keyWord, destinationCode, viewCode,isRecommend);
	}

	@Override
	public void updateGroupTravel(GroupTravel gt) {
		groupTravelDestinationDao.deleteByGroupTravelCode(gt.getCode());
		groupTravelViewDao.deleteByGroupTravelCode(gt.getCode());
		groupTravelDao.updateAsHibernate(gt);
	}
	@Override
	public void saveGroupTravelDestination(GroupTravelDestination gtd){
	    groupTravelDestinationDao.save(gtd);	
	}

	@Override
	public void saveGroupTravelView(GroupTravelView gtv) {
		groupTravelViewDao.save(gtv);
	}

	@Override
	public List<GroupTravel> getGtForPortal(int num) {
		List<GroupTravel> gts = groupTravelDao.findAllAvaliable();
		return gts.subList(0, num);
	}

	@Override
	public List<Map<String, Object>> getGtForMIndex() {
		List<Map<String, Object>> result = groupTravelDao.getGtForMIndex();
		for (Map<String, Object> map : result) {
			map.put("detail", HTMLTagUtil.delHTMLTag(map.get("detail").toString()));
		}
		return result;
	}

    @Override
    public List<GroupTravelVO> getGroupTravelRecommendByViewCode(int row, String viewCode) {
        return groupTravelDao.getGroupTravelRecommendByViewCode(row, viewCode);
    }

	@Override
	public Pager getGroupTravelList(Pager pager,String destCode, String viewCode,
			String keyWord,String rule) {
		return groupTravelDao.getGroupTravelList(pager,destCode,viewCode,keyWord,rule) ;
	}

    @Override
    public List<GroupTravelVO> getGroupTravelRecommendBydestinationCode(int row, String destinationCode) {
        return groupTravelDao.getGroupTravelRecommendBydestinationCode(row, destinationCode);
    }

	@Override
	public void updateNormalPro(GroupTravel gt) {
	groupTravelDao.update(gt);
		
	}

	@Override
	public Pager gtReplyInfo(Pager pager, String gtCode) {
		if(gtCode==null)
		   return null;
	    return  groupTravelDao.gtReplyInfo(pager,gtCode);
	}

    @Override
    public String getScore(String code) {
        return StringUtil.computScore(groupTravelDao.getScore(code));
    }
}
