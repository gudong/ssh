/**
 * 
 */
package com.forusoft.framework.bll.service.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.forusoft.framework.bll.service.BaseService;
import com.forusoft.framework.dal.dao.BaseDao;
import com.forusoft.framework.dal.po.Po;

/**
 * @author gudong
 * 
 */
public class BaseServiceImpl<P extends Po, PK extends java.io.Serializable> implements BaseService<P, PK> {

  private BaseDao<P, PK> baseDao;

  public BaseDao<P, PK> getBaseDao() {
    return baseDao;
  }

  public void setBaseDao(BaseDao<P, PK> baseDao) {
    this.baseDao = baseDao;
  }

  @Override
  public PK save(P po) {
    return baseDao.save(po);
  }

  @Override
  public void saveAll(Collection<P> poCollection) {
    baseDao.saveAll(poCollection);
  }

  @Override
  public void update(P po) {
    baseDao.update(po);
  }

  @Override
  public int update(String hql, Object... values) {
    return baseDao.update(hql, values);
  }

  @Override
  public int updateBySQL(String sql, Object... values) {
    return baseDao.updateBySQL(sql, values);
  }

  @Override
  public List<P> query(String hql, int firstResult, int maxResults, Object... values) {
    return baseDao.query(hql, firstResult, maxResults, values);
  }

  @Override
  public List query(String hql, Class resultClass, int firstResult, int maxResults, Object... values) {
    return baseDao.query(hql, resultClass, firstResult, maxResults, values);
  }

  @Override
  public List<P> queryBySQL(String sql, int firstResult, int maxResults, Object... values) {
    return baseDao.queryBySQL(sql, firstResult, maxResults, values);
  }

  @Override
  public List queryBySQL(String sql, Class resultClass, int firstResult, int maxResults, Object... values) {
    return baseDao.queryBySQL(sql, resultClass, firstResult, maxResults, values);
  }

  @Override
  public List<P> queryByName(String queryName, int firstResult, int maxResults, Object... values) {
    return baseDao.queryByName(queryName, firstResult, maxResults, values);
  }

  @Override
  public void updateAll(Collection<P> poCollection) {
    baseDao.updateAll(poCollection);
  }

  @Override
  public P load(Class<P> poClass, PK id) {
    return baseDao.load(poClass, id);
  }

  @Override
  public P get(Class<P> poClass, PK id, String... initProperties) {
    return baseDao.get(poClass, id, initProperties);
  }

  @Override
  public int delete(Class<P> poClass, PK... ids) {
    return baseDao.delete(poClass, ids);
  }

  @Override
  public void delete(P po) {
    baseDao.delete(po);
  }

  @Override
  public void deleteAll(Collection<P> poCollection) {
    baseDao.deleteAll(poCollection);
  }

  @Override
  public int getCount(DetachedCriteria criteria) {
    return baseDao.getCount(criteria);
  }

  @Override
  public List<P> queryByQBC(DetachedCriteria criteria, int firstResult, int maxResults) {
    return baseDao.queryByQBC(criteria, firstResult, maxResults);
  }

  @Override
  public List<P> queryByQBC(DetachedCriteria criteria) {
    return baseDao.queryByQBC(criteria);
  }

}
