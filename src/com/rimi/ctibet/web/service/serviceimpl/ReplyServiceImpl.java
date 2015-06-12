package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.Reply;
import com.rimi.ctibet.web.dao.IReplyDao;
import com.rimi.ctibet.web.service.IReplyService;

@Service("replyService")
@Transactional
public class ReplyServiceImpl extends BaseServiceImpl<Reply> implements IReplyService {
	@Resource IReplyDao replyDao;

	/**
	 * 通过指定字段物理删除数据
	 * @param properrt
	 * @param code
	 * @return
	 */
	public int deleteByProperty(String properrt, String code){
		return replyDao.deleteByProperty(properrt, code);
	}
	
    @Override
    public void deleteReplyByPostCode(String postCode) {
        replyDao.deleteReplyByPostCode(postCode);
    }
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	public IReplyDao getReplyDao() {
		return replyDao;
	}

	public void setReplyDao(IReplyDao replyDao) {
		this.replyDao = replyDao;
	}

	@Override
	public List<Reply> findByProperty(String propertyName,Object value) {
		// TODO Auto-generated method stub
		return replyDao.findByProWithoutAvali(propertyName, value);
	}
	
}
