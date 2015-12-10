package com.xinnet.xa.collector.general.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("commonDAO")
public class CommonDAO implements ICommonDAO{
	
	private Logger logger = Logger.getLogger(CommonDAO.class);

	@PersistenceUnit
    private EntityManagerFactory emf;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByNativeSQL(String sql, Object[] params) {
		EntityManager em = this.emf.createEntityManager();
		try{
			Query q = em.createNativeQuery(sql);		
			int index = 1;
			for (Object obj : params) {
				q.setParameter(index, obj);
				index++;
			}
			return q.getResultList();
		}
		catch (PersistenceException e) {
			e.printStackTrace();
			logger.error("Class CommonDAO is method findByNativeSQL(String sql, Object[] params) error!",e);
			return null;
		}
		finally{
			em.close();
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByNativeSQL(String sql, Map<String, Object> params) {
		EntityManager em = this.emf.createEntityManager();
		try{
			Query q = em.createNativeQuery(sql);		
			for (Entry<String,Object> entry : params.entrySet()) {
				q.setParameter(entry.getKey(), entry.getValue());
			}
			return q.getResultList();
		}
		catch (PersistenceException e) {
			e.printStackTrace();
			logger.error("Class CommonDAO is method findByNativeSQL(String sql, Object[] params) error!",e);
			return null;
		}
		finally{
			em.close();
		}
	}


	@Override
	public void execSQLScript(String sql) {
		EntityManager em = emf.createEntityManager();	
		try{
			em.createNativeQuery(sql);
		}
		catch (PersistenceException e) {
			logger.error("Class CommonDAO is method findByNativeSQL(String sql) error!",e);
		}
		finally{
			em.close();
		}
	}

	
	/**
	 * 
	 	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByNativeSQL(String sql) {
		EntityManager em = this.emf.createEntityManager();
		try{
			Query q = em.createNativeQuery(sql);
			return q.getResultList();
		}
		catch (PersistenceException e) {
			e.printStackTrace();
			logger.error("Class CommonDAO is method findByNativeSQL(String sql) error!",e);
			return null;
		}
		finally{
			em.close();
		}
	}
	 */
	
}
