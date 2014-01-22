/**
 * 
 */
package com.forusoft.framework.bll.service;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.forusoft.framework.dal.po.Po;

/**
 * @author gudong
 *
 * @param <P>
 * @param <PK>
 */
public interface BaseService<P extends Po, PK extends java.io.Serializable> extends Service {
  /**
   * 
   * @param po
   * @return
   */
  public PK save(P po);

  /**
   * 
   * @param poCollection
   */
  public void saveAll(Collection<P> poCollection);

  /**
   * 
   * @param po
   */
  public void update(P po);

  /**
   * Update/delete all objects according to the given query, binding a number of values to "?" parameters in the query string.
   * 
   * @param hql
   *          queryString an update/delete query expressed in Hibernate's query language
   * @param values
   *          binding a number of values to "?" parameters in the query string.
   * @return the number of instances updated/deleted
   */
  public int update(String hql, Object... values);

  /**
   * Update/delete all objects according to the given query, binding a number of values to "?" parameters in the query string.
   * 
   * @param sql
   *          queryString an update/delete query expressed in SQL
   * @param values
   *          binding a number of values to "?" parameters in the query string.
   * @return the number of instances updated/deleted
   */
  public int updateBySQL(String sql, Object... values);

  /**
   * Execute an HQL query, binding a number of values to "?" parameters in the query string.
   * 
   * @param hql
   *          queryString a query expressed in Hibernate's query language
   * @param firstResult
   * @param maxResults
   * @param values
   *          values the values of the parameters
   * @return
   */
  public List<P> query(String hql, int firstResult, int maxResults, Object... values);

  /**
   * Execute an HQL query, binding a number of values to "?" parameters in the query string.
   * 
   * @param hql
   * @param resultClass
   * @param firstResult
   * @param maxResults
   * @param values
   * @return
   */
  public List query(String hql, Class resultClass, int firstResult, int maxResults, Object... values);

  /**
   * Execute an SQL query, binding a number of values to "?" parameters in the query string.
   * 
   * @param sql
   * @param firstResult
   * @param maxResults
   * @param values
   * @return
   */
  public List<P> queryBySQL(String sql, int firstResult, int maxResults, Object... values);

  /**
   * Execute an SQL query, binding a number of values to "?" parameters in the query string.
   * 
   * @param sql
   *          queryString a query expressed in SQL
   * @param firstResult
   * @param maxResults
   * @param values
   *          values the values of the parameters
   * @return
   */
  public List queryBySQL(String sql, Class resultClass, int firstResult, int maxResults, Object... values);

  /**
   * 
   * Execute a named query binding a number of values to "?" parameters in the query string.
   * 
   * @param queryName
   *          A named query is defined in a Hibernate mapping file.
   * @param firstResult
   * @param maxResults
   * @param values
   * @return a List containing the results of the query execution
   */
  public List<P> queryByName(String queryName, int firstResult, int maxResults, Object... values);

  /**
   * 
   * @param poCollection
   */
  public void updateAll(Collection<P> poCollection);

  /**
   * 
   * @param poClass
   * @param id
   * @return
   */
  public P load(Class<P> poClass, PK id);

  /**
   * 
   * @param poClass
   * @param id
   * @param initProperties
   * @return
   */
  public P get(Class<P> poClass, PK id, String... initProperties);

  /**
   * 
   * @param o
   */
  public int delete(Class<P> poClass, PK... ids);

  /**
   * 
   * @param po
   */
  public void delete(P po);

  /**
   * 
   * @param poCollection
   */
  public void deleteAll(Collection<P> poCollection);

  /**
   * 
   * @param criteria
   * @return
   */
  public int getCount(DetachedCriteria criteria);

  /**
   * 
   * @param criteria
   * @param firstResult
   * @param maxResults
   * @return
   */
  public List<P> queryByQBC(DetachedCriteria criteria, int firstResult, int maxResults);

  /**
   * 
   * @param criteria
   * @return
   */
  public List<P> queryByQBC(DetachedCriteria criteria);
}
