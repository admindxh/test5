package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.SysMessage;
import com.rimi.ctibet.web.dao.ISysMessageDao;
@Repository("sysMessageDao")
public class SysMessageDaoImpl extends BaseDaoImpl<SysMessage> implements ISysMessageDao{

}
