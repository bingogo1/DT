package bin.g11n.gt.model.jdbc;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
/**
 * Mapping java object with database type
 * 
 * Mapping.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class Mapping {

	private ArrayList params;
	private Object lastUpdate;
	
	public Mapping(){
		params = new ArrayList();
	}

	public void setArgument(Object obj){
		params.add(obj);
	}

	public Object getArgment(int i) {
		return params.get(i);
	}
	
	public Object getlastUpdate() {
		return lastUpdate;
	}
	
	/** get parameter map size
	 * 
	 * size 
	 *
	 * @return  int
	 */
	public int size() {
		return params.size();
	}
	
	/** set objects in the parameter map for prepared statement.
	 * 
	 * PreparedStatement
	 * 
	 * @param statement PreparedStatement Object
	 * @throws SQLException
	 */
	public void setPreparedStatementParameters(PreparedStatement statement) throws SQLException {
		for (int i = 0; i < params.size(); i++) {
			Object param = params.get(i);
			if (param instanceof Integer) {
				int value = ((Integer) param).intValue();
				statement.setInt(i + 1, value);
			} else if (param instanceof Short) {
				short sh = ((Short) param).shortValue();
				statement.setShort(i + 1, sh);
			} else if (param instanceof String) {
				String s = (String) param;
				statement.setString(i + 1, s);
			} else if (param instanceof BigDecimal) {
				BigDecimal d = ((BigDecimal) param);
				statement.setBigDecimal(i + 1, d);
			} else if (param instanceof Double) {
				double d = ((Double) param).doubleValue();
				statement.setDouble(i + 1, d);
			} else if (param instanceof Float) {
				float f = ((Float) param).floatValue();
				statement.setFloat(i + 1, f);
			} else if (param instanceof Long) {
				long l = ((Long) param).longValue();
				statement.setLong(i + 1, l);
			} else if (param instanceof Boolean) {
				boolean b = ((Boolean) param).booleanValue();
				statement.setBoolean(i + 1, b);
			} else if (param instanceof Timestamp) { 
				statement.setTimestamp(i + 1, (Timestamp)param);
			} else if (param instanceof java.sql.Date) {
				java.sql.Date d = (java.sql.Date) param;
				statement.setDate(i + 1, d);
			} else {
				clearParams();
			}
		}
	}
	
	/**
	 * set objects in the parameter map for callable statement.
	 * 
	 * @param statement CallableStatement Object
	 * @param returnIndex 
	 * @throws SQLException
	 */
	protected void setCallableStatementParameters(CallableStatement statement, int returnIndex) throws SQLException {
		for (int i = 0; i < params.size(); i++) {
			Object param = params.get(i);
			if (param instanceof Integer) {
				int value = ((Integer) param).intValue();
				statement.setInt(i + returnIndex, value);
			} else if (param instanceof Short) {
				short sh = ((Short) param).shortValue();
				statement.setShort(i + returnIndex, sh);
			} else if (param instanceof String) {
				String s = (String) param;
				statement.setString(i + returnIndex, s);
			} else if (param instanceof BigDecimal) {
				BigDecimal d = ((BigDecimal) param);
				statement.setBigDecimal(i + 1, d);
			} else if (param instanceof Double) {
				double d = ((Double) param).doubleValue();
				statement.setDouble(i + returnIndex, d);
			} else if (param instanceof Float) {
				float f = ((Float) param).floatValue();
				statement.setFloat(i + returnIndex, f);
			} else if (param instanceof Long) {
				long l = ((Long) param).longValue();
				statement.setLong(i + returnIndex, l);
			} else if (param instanceof Boolean) {
				boolean b = ((Boolean) param).booleanValue();
				statement.setBoolean(i + returnIndex, b);
			} else if (param instanceof Timestamp) { 
				statement.setTimestamp(i + returnIndex , (Timestamp)param);
			} else if (param instanceof java.sql.Date) {
				java.sql.Date d = (java.sql.Date) param;
				statement.setDate(i + returnIndex, d);
			} else {
				clearParams();
			}
		}
	}
	
	private void clearParams() {
		params.clear();
	}
}
