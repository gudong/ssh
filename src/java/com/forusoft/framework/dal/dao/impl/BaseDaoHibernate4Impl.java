/**
 * 
 */
package com.forusoft.framework.dal.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;

import com.forusoft.framework.common.util.ClassUtil;
import com.forusoft.framework.common.util.ObjectUtil;
import com.forusoft.framework.dal.dao.BaseDao;
import com.forusoft.framework.dal.po.Po;

/**
 * @author gudong
 * @param <P>
 * @param <PK>
 */
public class BaseDaoHibernate4Impl<P extends Po, PK extends java.io.Serializable> implements BaseDao<P, PK> {

  private SessionFactory sessionFactory;
  private boolean cacheQueries = false;
  private String queryCacheRegion;
  private int fetchSize = 0;

  public int getFetchSize() {
    return fetchSize;
  }

  public void setFetchSize(int fetchSize) {
    this.fetchSize = fetchSize;
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public boolean isCacheQueries() {
    return cacheQueries;
  }

  public void setCacheQueries(boolean cacheQueries) {
    this.cacheQueries = cacheQueries;
  }

  public String getQueryCacheRegion() {
    return queryCacheRegion;
  }

  public void setQueryCacheRegion(String queryCacheRegion) {
    this.queryCacheRegion = queryCacheRegion;
  }

  public Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Override
  public PK save(P po) {
    return (PK) getSession().save(po);
  }

  @Override
  public void saveAll(Collection<P> poCollection) {
    Session session = getSession();
    for (P e : poCollection) {
      session.saveOrUpdate(e);
    }
  }

  @Override
  public void update(P po) {
    getSession().update(po);
  }

  @Override
  public int update(String hql, Object... values) {
    Query queryObject = getSession().createQuery(hql);
    prepareQuery(queryObject, 0, 0, values);
    return queryObject.executeUpdate();
  }

  @Override
  public int updateBySQL(String sql, Object... values) {
    SQLQuery queryObject = getSession().createSQLQuery(sql);
    prepareQuery(queryObject, 0, 0, values);
    return queryObject.executeUpdate();
  }

  @Override
  public List<P> query(String hql, int firstResult, int maxResults, Object... values) {
    return query(hql, null, firstResult, maxResults, values);
  }

  @Override
  public List query(String hql, Class resultClass, int firstResult, int maxResults, Object... values) {
    Query queryObject = getSession().createQuery(hql);
    prepareQuery(queryObject, resultClass, firstResult, maxResults, values);
    return queryObject.list();
  }

  @Override
  public List<P> queryBySQL(String sql, int firstResult, int maxResults, Object... values) {
    return queryBySQL(sql, null, firstResult, maxResults, values);
  }

  @Override
  public List queryBySQL(String sql, Class resultClass, int firstResult, int maxResults, Object... values) {
    SQLQuery queryObject = getSession().createSQLQuery(sql);
    prepareQuery(queryObject, resultClass, firstResult, maxResults, values);
    return queryObject.list();
  }

  @Override
  public List<P> queryByName(String queryName, int firstResult, int maxResults, Object... values) {
    Query queryObject = getSession().getNamedQuery(queryName);
    prepareQuery(queryObject, firstResult, maxResults, values);
    return queryObject.list();
  }

  @Override
  public void updateAll(Collection<P> poCollection) {
    Session session = getSession();
    for (P e : poCollection) {
      session.saveOrUpdate(e);
    }
  }

  @Override
  public P load(Class<P> poClass, PK id) {
    return (P) getSession().load(poClass, id);
  }

  @Override
  public P get(Class<P> poClass, PK id, String... initProperties) {
    P o = (P) getSession().get(poClass, id);
    if (initProperties != null) {
      for (String property : initProperties)
        Hibernate.initialize(ObjectUtil.getProperty(o, property));
    }
    return o;
  }

  @Override
  public int delete(Class<P> poClass, PK... ids) {
    ClassMetadata classMetadata = sessionFactory.getClassMetadata(poClass);
    String idPropertyName = classMetadata.getIdentifierPropertyName();
    String hql;
    if (ids != null) {
      hql = "DELETE FROM " + poClass.getName();
      if (ids.length == 1)
        hql += " WHERE " + idPropertyName + "=?";
      else
        hql += " WHERE " + idPropertyName + " in (" + StringUtils.repeat("?", ",", ids.length) + ")";
    } else {
      hql = "TRUNCATE TABLE " + poClass.getName();
    }
    Query queryObject = getSession().createQuery(hql);
    prepareQuery(queryObject, 0, 0, ids);
    return queryObject.executeUpdate();
  }

  @Override
  public void delete(P po) {
    getSession().delete(po);
  }

  @Override
  public void deleteAll(Collection<P> poCollection) {
    Session session = getSession();
    for (P entity : poCollection) {
      session.delete(entity);
    }
  }

  @Override
  public int getCount(DetachedCriteria criteria) {
    Criteria executableCriteria = ((DetachedCriteria) ObjectUtil.copy(criteria)).getExecutableCriteria(getSession());
    executableCriteria.setProjection(Projections.rowCount());
    return ((Long) executableCriteria.uniqueResult()).intValue();
  }

  @Override
  public List<P> queryByQBC(DetachedCriteria criteria, int firstResult, int maxResults) {
    Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
    prepareCriteria(executableCriteria, firstResult, maxResults);
    return executableCriteria.list();
  }

  @Override
  public List<P> queryByQBC(DetachedCriteria criteria) {
    return queryByQBC(criteria, 0, 0);
  }

  /**
   * 
   * @param queryObject
   */
  protected void prepareQuery(Query queryObject, int firstResult, int maxResults, Object... values) {
    if (isCacheQueries()) {
      queryObject.setCacheable(true);
      if (getQueryCacheRegion() != null) {
        queryObject.setCacheRegion(getQueryCacheRegion());
      }
    }
    if (getFetchSize() > 0) {
      queryObject.setFetchSize(getFetchSize());
    }
    if (maxResults > 0) {
      queryObject.setMaxResults(maxResults);
    }
    if (firstResult > 0) {
      queryObject.setFirstResult(firstResult);
    }
    if (values != null) {
      for (int i = 0; i < values.length; i++) {
        queryObject.setParameter(i, values[i]);
      }
    }
  }

  /**
   * 
   * @param queryObject
   * @param resultClass
   * @param firstResult
   * @param maxResults
   * @param values
   */
  protected void prepareQuery(Query queryObject, Class resultClass, int firstResult, int maxResults, Object... values) {
    prepareQuery(queryObject, firstResult, maxResults, values);
    if (resultClass != null) {
      if (ClassUtil.isMap(resultClass)) {
        queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
      } else if (ClassUtil.isList(resultClass)) {
        queryObject.setResultTransformer(Transformers.TO_LIST);
      } else {
        queryObject.setResultTransformer(Transformers.aliasToBean(resultClass));
      }
    }
  }

  /**
   * Prepare the given Criteria object, applying cache settings and/or a transaction timeout.
   * 
   * @param criteria
   *          the Criteria object to prepare
   * @see #setCacheQueries
   * @see #setQueryCacheRegion
   * @see SessionFactoryUtils#applyTransactionTimeout
   */
  protected void prepareCriteria(Criteria criteria, int firstResult, int maxResults) {
    if (isCacheQueries()) {
      criteria.setCacheable(true);
      if (getQueryCacheRegion() != null) {
        criteria.setCacheRegion(getQueryCacheRegion());
      }
    }
    if (getFetchSize() > 0) {
      criteria.setFetchSize(getFetchSize());
    }

    if (firstResult >= 0) {
      criteria.setFirstResult(firstResult);
    }
    if (maxResults > 0) {
      criteria.setMaxResults(maxResults);
    }
  }
}
