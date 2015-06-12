package com.rimi.ctibet.web.service.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.web.dao.IPraiseDao;
import com.rimi.ctibet.web.service.IPraiseService;


/**
 * 赞serviceImpl
 * @author dengxh
 *
 */
@Transactional
@Service(value="praiseService")
public class PraiseServiceImpl  extends BaseServiceImpl<Praise> implements IPraiseService {
	
	@Resource
	private  IPraiseDao praiseDao ;
	
	@Override
	public void cancelPraiseCount(String contentCode) {
		// TODO Auto-generated method stub
		praiseDao.cancelPraiseCount(contentCode);
	}

	@Override
	public Pager findContentByProCode(Pager pager, String proCode) {
		// TODO Auto-generated method stub
		return praiseDao.findContentByProCode(pager, proCode);
	}

	@Override
	public Praise findPraise(Praise praise) {
		// TODO Auto-generated method stub
		return  praiseDao.findPraise(praise);
	}

	@Override
	public void saveContentAndProgramaContent(Content content,
			String programaCode) {
		// TODO Auto-generated method stub
		praiseDao.saveContentAndProgramaContent(content, programaCode);
	}

	@Override
	public void savePraise(Praise praise) {
		
		// TODO Auto-generated method stub
		praise.setAvaliable(1);
		praise.setCreateTime(new Date());
		praise.setCode(Uuid.uuid());
		praiseDao.savePraise(praise);
	}

	@Override
	public void updatePraise(Praise praise) {
		// TODO Auto-generated method stub
		/*praise  = praiseDao.findPraise(praise);
		if (praise!=null) {
			praise.setFalsePraise(praise.getFalsePraise());
			praise.setTruePraise(praise.getTruePraise());
			
		}*/
		
		praiseDao.updatePraise(praise);
	}

	@Override
	public void updatePraiseCount(String contentCode) {
		// TODO Auto-generated method stub
		praiseDao.updatePraiseCount(contentCode);
	}

	@Override
	public void updateViewCount(String contentCode) {
		// TODO Auto-generated method stub
		praiseDao.updateViewCount(contentCode);
	}

	@Override
	public void deleteContentAndProgramaContent(String[] contentCodes) {
		// TODO Auto-generated method stub
		praiseDao.deleteContentAndProgramaContent(contentCodes);
	}
	
	public IPraiseDao getPraiseDao() {
		return praiseDao;
	}

	public void setPraiseDao(IPraiseDao praiseDao) {
		this.praiseDao = praiseDao;
	}

	@Override
	public Pager findPraiseList( List<Object> params, Pager pager) {
		// TODO Auto-generated method stub
		return  praiseDao.findPraiseList(params, pager);
	}

	@Override
	public List<Map<String, Object>> getPraiseList(int row, List<Object> params) {
		// TODO Auto-generated method stub
		return this.praiseDao.getPraiseList(row, params);
	}

	@Override
	public List<Map<String, Object>> getPraiseListReply(int row,
			List<Object> params) {
		// TODO Auto-generated method stub
		return this.getPraiseListReply(row, params);
	}

	@Override
	public boolean isValidExitPraise(String coentcode) {
		// TODO Auto-generated method stub
		return this.isValidExitPraise(coentcode);
	}

	/**
	 * 通过contentCode获取Praise
	 * @param contentCode
	 * @return
	 */
	public Praise getPraiseContentCode(String contentCode){
		return praiseDao.getPraiseContentCode(contentCode);
	}

	public Object[] searchPVCount(String postCode){
		
		
		
		return this.praiseDao.searchPVCount(postCode);
		
	}
	
	/**
     * 更新查看数量 +1
     */
    public void updateViewCountByContentCode(String contentCode) {
        Praise praise = praiseDao.getPraiseContentCode(contentCode);
        if(praise!=null){
            Integer viewCount = praise.getViewCount()==null?0:praise.getViewCount();
            Integer falseViewcount = praise.getFalseViewcount()==null?0:praise.getFalseViewcount();
            praise.setViewCount(viewCount + 1);
            praise.setFalseViewcount(falseViewcount + 1);
            praiseDao.update(praise);
        }else{
            praise = new Praise();
            praise.setAvaliable(1);
            praise.setCode(Uuid.uuid());
            praise.setViewCount(1);
            praise.setFalseViewcount(1);
            praise.setContentCode(contentCode);
            praise.setCreateTime(new Date(System.currentTimeMillis()));
            praiseDao.save(praise);
        }
    }
    
    
    public void updateFavateCount(final String contentCode){
    	
    	 praiseDao.updateFavateCount(contentCode);
    }
    /**
     * 按照查看数Desc排序
     */
    @Override
    public List<Praise> orderByViewCount(List<Praise> plist) {
    	int n = plist.size();
    	//System.out.println("order list size============"+n);
        for(int i =0 ; i< n; ++i) {  
            for(int j = 0; j < n-i-1; ++j) {  
                if( plist.get(j).getViewCount() < plist.get(j+1).getViewCount())  
                {  
                    Praise tmp = plist.get(j); 
                    plist.set(j, plist.get(j+1));
                    plist.set(j+1, tmp); 
                }  
            }  
        }  
        return plist;
    }
    
	
}
