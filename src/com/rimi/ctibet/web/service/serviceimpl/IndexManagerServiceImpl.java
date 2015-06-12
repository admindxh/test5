package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.IndexManager;
import com.rimi.ctibet.portal.controller.vo.BbsIndexManagerP;
import com.rimi.ctibet.web.dao.IndexManagerDao;
import com.rimi.ctibet.web.service.IndexManagerService;


@Transactional
@Service("indexManagerService")
public class IndexManagerServiceImpl  extends BaseServiceImpl<IndexManager> implements  IndexManagerService{
	
	@Resource
	private IndexManagerDao indexManagerDao;
	
	@Override
	public void initIndexManager(String proramCode, String contentType,Integer row) {
		// TODO Auto-generated method stub
		this.indexManagerDao.initIndexManager(proramCode, contentType,row);
	}
	
	@Override
	public void initIndexManagerbyScore(String proramCode, String contentType,Integer row) {
		// TODO Auto-generated method stub
		this.indexManagerDao.initIndexManagerbyScore(proramCode, contentType,row);
	}
	
	@Override
	public void updateIndexManager(IndexManager indexManager) {
		// TODO Auto-generated method stub
		this.indexManagerDao.updateIndexManager(indexManager);
	}
	
	public IndexManagerDao getIndexManagerDao() {
		return indexManagerDao;
	}

	public void setIndexManagerDao(IndexManagerDao indexManagerDao) {
		this.indexManagerDao = indexManagerDao;
	}

	@Override
	public List<IndexManager> getListIndexManager(String proramCode,String contentType) {
		// TODO Auto-generated method stub
		return this.indexManagerDao.getListIndexManager(proramCode,contentType);
	}

	@Override
	public List<Content> getListContent(String programcode,String contentType) {
		// TODO Auto-generated method stub
		return this.indexManagerDao.getListContent(programcode,contentType);
	}
	@Override
	public void deleteProgram(String pro,Integer avlable) {
		this.indexManagerDao.deleteProgram(pro,avlable);
	}

	@Override
	public void initIndexManagerbyPraise(String proramCode, String contentType,
			Integer row) {

				this.indexManagerDao.initIndexManagerbyPraise(proramCode, contentType, row);
	}

	@Override
	public List<BbsIndexManagerP> getListPraiseContent(String programcode,
			String contentType) {
		// TODO Auto-generated method stub
		return this.indexManagerDao.getListPraiseContent(programcode, contentType);
	}
	
	public  List<Object[]> getOrderManager(String proramCode, final String contentType,
			final Integer row){
		
		return this.indexManagerDao.getOrderManager(proramCode, contentType, row);
		
	}
	public List<Content> getContentListByNumber(final int row,final String order, final String contentType,String desCode) {
		
		return this.indexManagerDao.getContentListByNumber(row, order, contentType,desCode);
	}

    @Override
    public List<IndexManager> getMostPraise(int row) {
        return indexManagerDao.getMostPraise(row);
    }
}
