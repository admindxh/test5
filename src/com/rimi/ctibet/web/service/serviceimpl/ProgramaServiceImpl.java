package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.dao.IProgramaDao;
import com.rimi.ctibet.web.service.IProgramaService;

@Service("programaService")
@Transactional
public class ProgramaServiceImpl implements IProgramaService{
    
    Logger logger = Logger.getLogger(ProgramaServiceImpl.class);
    
	@Resource
	private IProgramaDao programaDao;
	@Deprecated
	@Override
	public void deletePrograma(Programa programa) {
		programaDao.deletePrograma(programa);		
	}

	@Override
	public Programa getProgramaByCode(String code) {
		return programaDao.findByCode(code);
	}

	@Override
	public void savePrograma(Programa programa) {
		programaDao.save(programa);
	}

	@Override
	public void updatePrograma(Programa programa) {
		

		programaDao.updateLogical(programa);
	}

	@Override
	public Pager getProgramaOrderByRank(Pager pager) {
		return programaDao.getProgramaOrderByRank(pager);
	}

	@Override
	public List<Programa> getTopPrograma() {
		return programaDao.getTopPrograma();
	}

	@Override
	public List<Programa> getSendPrograma(String code) {
		return programaDao.getSendPrograma(code);
	}

	@Override
	public List<Map<String,Object>> getContentByProgramaCode(String code) {
		return programaDao.getContentByProgramaCode(code);
	}

	@Override
	public void delete(Programa programa) {
		programaDao.deleteByCode(programa.getCode());
	}

	@Override
	public List<Programa> findProByMemberCode(String sql,List param) {
		// TODO Auto-generated method stub
		return programaDao.findListBySql(Programa.class, sql, param);
	}

    @Override
    public List<Programa> getReplyManagePrograma() {
        // TODO Auto-generated method stub
        return programaDao.getReplyManagePrograma();
    }
    
    @Override
    public void updateReplyManageSetting(List<Programa> listPrograma){
        if(ListUtil.isNotNull(listPrograma)){
            for (Programa programa : listPrograma) {
                Programa newPro = programaDao.findByCode(programa.getCode());
                newPro.setRemark(programa.getRemark());
                programaDao.update(newPro);
            }
        }else{
            logger.debug("木有数据，检查页面参数和programa表中的数据");
        }
    }

    @Override
    public int isOpenReply(String type) {
        // TODO Auto-generated method stub
        int i = 0;
        Programa programa = programaDao.getReplyManageProgramaByType(type);
        if(programa!=null){
            if (programa.getRemark().equals("yes")) {
                i = 1;
            } else if (programa.getRemark().equals("no")) {
                i = 0;
            }
        }
        return i;
    }

	@Override
	public void delete(String code) {
		programaDao.deleteLogicalByCode(code);
	}

	@Override
	public Pager findByParent(Pager pager, Programa parentPrograma,
			String keyword) {
		
		return programaDao.findByParent(pager, parentPrograma, keyword);
	}
	
	@Override
	public int validateNameIsRepeat (Programa programa) {
		List<Programa> programas =  programaDao.findByParent(programa);
		return programas == null || programas.isEmpty() ? 0 : programas.size();
	}

	@Override
	public Pager findByOrder(Pager pager, Programa programa) {
		return programaDao.findByOrder(pager, programa);
	}
    
}
