package bin.g11n.gt.dao.impl.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.dao.common.GenericDao;
import bin.g11n.gt.model.jdbc.Mapping;
import bin.g11n.gt.model.jdbc.QueryResult;


/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 *
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class GenericDaoHibernate<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {
	protected static transient final ILogger logger = G11nLogFactory.getLog(GenericDaoHibernate.class);
    private Class<T> persistentClass;
	/* FETCH SIZE */
	private static final int FETCH_SIZE = 1000;
	
    public GenericDaoHibernate(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return super.getHibernateTemplate().loadAll(this.persistentClass);
    }

    @SuppressWarnings("unchecked")
    public T get(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.persistentClass, id);

        if (entity == null) {
            logger.log(ILogger.ELevel.WARNING, "Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
	public boolean exists(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.persistentClass, id);
        if (entity == null) {
            return false;
        } else {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
	public T save(T object) {
        return (T) super.getHibernateTemplate().merge(object);
    }

    /**
     * remove 
     *
     * @param id  void
     */
    public void remove(PK id) {
        super.getHibernateTemplate().delete(this.get(id));
    }
    
    /** 
     * delete all records physically from the specified table.
     * removeAllOfTable 
     *
     * @return int deleted row count
     */
    public int removeAllOfTable() {
    	return getSession().createQuery("DELETE " + persistentClass.getName())
        .executeUpdate();

    }
    
    /**
     * setParameter for hibernate query object 
     *
     * @param query
     * @param array of parameters
     * @return  Query
     */
    public Query setParameter(Query q,Object[] arr) {
    	for (int i=0;i<arr.length;i++) {
    		q.setParameter(i, arr[i]);
    	}
    	return q;
    }
    
	/**
	 * query the database by jdbc connection. 
	 * Note: to use this method, the sql must use the physical table and column name.
	 * 
	 * queryByJdbc
	 *  
	 *	@author bguo
	 * @param sql sql string with physical table and column names.
	 * @param mapping parameters for the perpared statement.
	 * @return
	 * @throws SQLException  QueryResult
	 */
	protected final QueryResult queryByJdbc(String sql, Mapping mapping){
		//start
		QueryResult result = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = createPreparedStatement(sql);
			//stm.setFetchSize(FETCH_SIZE);
			mapping.setPreparedStatementParameters(stm);
			rs = stm.executeQuery();
			result = convertResult(rs);
		} catch (SQLException e) {
			result = new QueryResult(0);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stm != null) {
					stm.close();
				}
			} catch (SQLException e) {
				result = new QueryResult(0);
				e.printStackTrace();
			}
		}
		//end
		return result;
		}

	/**
	 * convert result from general ResultSet to QueryResult.
	 * convertResult 
	 *
	 * @param rs
	 * @return
	 * @throws SQLException  QueryResult
	 */
	private QueryResult convertResult(ResultSet rs) throws SQLException {
		QueryResult results = null;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			results = new QueryResult(columnCount);
			// add field values to ResultSet object
			while (rs.next()) {
				for (int i = 0; i < columnCount; i++){
					results.add(rs.getObject(i + 1));
				}
			}
			if (results.getRowCount() > 0){
				// add column names to ResultSet object
				for (int i = 0; i < columnCount; i++) {
					results.addColumnName(rsmd.getColumnName(i + 1));
				}
			}
		} catch (SQLException e) {
			throw new SQLException("SQL Result error!");
		}
		return results;
	}

	/** create prepared statement with sql.
	 * 
	 * createPreparedStatement 
	 *
	 * @author bguo
	 * @param sql
	 * @return
	 * @throws SQLException  PreparedStatement
	 */
	private PreparedStatement createPreparedStatement(String sql)
			throws SQLException {
		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			Session session = this.getSession();
			con = session.connection();
			pStmt = con.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("SQL syntax error!");
		}
		return pStmt;
	}
	
}
