package bin.g11n.gt.model.jdbc;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Wraps query result.
 * 
 * SQL result set
 * 
 * QueryResult.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class QueryResult {

	private List result = null;

	private List colName = null;

	private int colCount = 0;

	/**
	 * constructor
	 * 
	 * @param columnCount
	 */
	public QueryResult(int columnCount) {
		result = new ArrayList();
		colName = new ArrayList();
		colCount = columnCount;
	}

	/**
	 * get column count.
	 * getColumnCount 
	 *
	 * @return  int
	 */
	public int getColumnCount() {
		return colName.size();
	}

	/**
	 * get column name list
	 * getColumnNames 
	 *
	 * @return  List
	 */
	public List getColumnNames() {
		return colName;
	}

	/** add one object into the result.
	 * add 
	 *
	 * @param o
	 * @return  boolean
	 */
	public boolean add(Object o) {
		return result.add(o);
	}

	/**
	 * add one column into the result.
	 * 
	 * @param columnName
	 */
	public void addColumnName(String columnName) {
		colName.add(columnName);
	}
	
	/**
	 * get row count.
	 * getRowCount 
	 *
	 * @return  int
	 */
	public int getRowCount() {
		if(colCount == 0)
			return (0);
		else 
			return result.size() / colCount;
	}

	/**
	 * get object with typ of 'Object'.
	 * getObject 
	 *
	 * @param row
	 * @param col
	 * @return  Object
	 */
	public Object getObject(int row, int col) {
		return result.get((row * colCount) + col);
	}

	/**
	 * get object with typ of 'BigDecimal'.
	 * getBigDecimal 
	 *
	 * @param row
	 * @param col
	 * @return  BigDecimal
	 */
	public BigDecimal getBigDecimal(int row, int col) {
		return (BigDecimal)getObject(row, col);
	}
	
	/**
	 * get object with typ of 'BigDecimal'.
	 * getBigDecimal 
	 *
	 * @param row
	 * @param column
	 * @return  BigDecimal
	 */
	public BigDecimal getBigDecimal(int row, String column) {
		return (BigDecimal)getObject(row, getColumnIndex(column));
	}
	
	/**
	 * getInt 
	 *
	 * @param row
	 * @param columnName
	 * @return  int
	 */
	public int getInt(int row, String columnName) {
		return getInt(row, getColumnIndex(columnName));
	}

	/**
	 * getInt 
	 *
	 * @param row
	 * @param col
	 * @return  int
	 */
	public int getInt(int row, int col) {
		if (isNull(row, col)) {
			return (0);
		}

		Object o = getObject(row, col);
		if (o instanceof Integer) {
			return (((Integer) o).intValue());
		} else {
			return (((BigDecimal) o).intValue());
		}
	}

	/**
	 * getIntegerObject 
	 *
	 * @param row
	 * @param columnName
	 * @return  Integer
	 */
	public Integer getIntegerObject(int row, String columnName) {
		return getIntegerObject(row, getColumnIndex(columnName));
	}

	/**
	 * getIntegerObject 
	 *
	 * @param row
	 * @param col
	 * @return  Integer
	 */
	public Integer getIntegerObject(int row, int col) {
		if (isNull(row, col)) {
			return null;
		}

		Object o = getObject(row, col);
		if (o instanceof Integer) {
			return ((Integer) o);
		} else {
			return new Integer(((BigDecimal) o).intValue());
		}
	}
	
	/**
	 * getLong 
	 *
	 * @param row
	 * @param columnName
	 * @return  long
	 */
	public long getLong(int row, String columnName) {
		return (getLong(row, getColumnIndex(columnName)));
	}

	/**
	 * getLong 
	 *
	 * @param row
	 * @param col
	 * @return  long
	 */
	public long getLong(int row, int col) {
		if (isNull(row, col))
			return (0);

		Object o = getObject(row, col);
		if (o instanceof Long)
			return (((Long) o).longValue());
		else if (o instanceof Integer)
			return (((Integer) o).longValue());
		else
			return (((BigDecimal) o).longValue());
	}

	/**
	 * getLongObject 
	 *
	 * @param row
	 * @param columnName
	 * @return  Long
	 */
	public Long getLongObject(int row, String columnName) {
		return (getLongObject(row, getColumnIndex(columnName)));
	}

	/**
	 * getLongObject 
	 *
	 * @param row
	 * @param col
	 * @return  Long
	 */
	public Long getLongObject(int row, int col) {
		if (isNull(row, col))
			return null;

		Object o = getObject(row, col);
		if (o instanceof Long)
			return ((Long) o);
		else if (o instanceof Integer)
			return new Long(((Integer) o).longValue());
		else
			return new Long(((BigDecimal) o).longValue());
	}
	
	/**
	 * getString 
	 *
	 * @param row
	 * @param col
	 * @return  String
	 */
	public String getString(int row, int col) {
		Object o = getObject(row, col);
		if (o instanceof BigDecimal) {
			return ("" + getBigDecimal(row, col));
		}else if ((o instanceof Date) || (o instanceof Timestamp)){
			return ("" + getDate(row, col));
		}else {
			String s = (String) o;
			return (s);
		}
	}
	
	/**
	 * getString 
	 *
	 * @param row
	 * @param columnName
	 * @return  String
	 */
	public String getString(int row, String columnName) {
		return (getString(row, getColumnIndex(columnName)));
	}

	/**
	 * getBoolean 
	 *
	 * @param row
	 * @param col
	 * @return  boolean
	 */
	public boolean getBoolean(int row, int col) {
		if (isNull(row, col))
			return (false);

		Object o = getObject(row, col);
		Boolean b = (Boolean) o;
		return (b.booleanValue());
	}
	
	/**
	 * getBoolean 
	 *
	 * @param row
	 * @param columnName
	 * @return  boolean
	 */
	public boolean getBoolean(int row, String columnName) {
		return (getBoolean(row, getColumnIndex(columnName)));
	}

	/**
	 * getDate 
	 *
	 * @param row
	 * @param col
	 * @return  Date
	 */
	public Date getDate(int row, int col) {
		if (isNull(row, col))
			return (null);

		Object o = getObject(row, col);
		if (o instanceof Timestamp) {
			Timestamp t = (Timestamp) o;
			Date d = new Date(t.getTime());
			return (d);
		} else {
			Date d = (Date) o;
			return (d);
		}
	}

	/**
	 * getDate 
	 *
	 * @param row
	 * @param columnName
	 * @return  Date
	 */
	public Date getDate(int row, String columnName) {
		return (getDate(row, getColumnIndex(columnName)));
	}

	/**
	 * getTime 
	 *
	 * @param row
	 * @param col
	 * @return  Time
	 */
	public Time getTime(int row, int col) {
		if (isNull(row, col))
			return (null);

		Object o = getObject(row, col);
		Time t = (Time) o;
		return (t);
	}

	/**
	 * getTime 
	 *
	 * @param row
	 * @param columnName
	 * @return  Time
	 */
	public Time getTime(int row, String columnName) {
		return (getTime(row, getColumnIndex(columnName)));
	}

	/**
	 * getTimestamp 
	 *
	 * @param row
	 * @param col
	 * @return  Timestamp
	 */
	public Timestamp getTimestamp(int row, int col) {
		if (isNull(row, col))
			return (null);

		Object o = getObject(row, col);
		if (o instanceof Date) {
			Date d = (Date) o;
			Timestamp t = new Timestamp(d.getTime());
			return (t);
		} else {
			Timestamp t = (Timestamp) o;
			return (t);
		}
	}

	/**
	 * getTimestamp 
	 *
	 * @param row
	 * @param columnName
	 * @return  Timestamp
	 */
	public Timestamp getTimestamp(int row, String columnName) {
		return (getTimestamp(row, getColumnIndex(columnName)));
	}

	/**
	 * getDouble 
	 *
	 * @param row
	 * @param columnName
	 * @return  double
	 */
	public double getDouble(int row, String columnName) {
		return (getDouble(row, getColumnIndex(columnName)));
	}
	
	/**
	 * getDouble 
	 *
	 * @param row
	 * @param col
	 * @return  double
	 */
	public double getDouble(int row, int col) {
		if (isNull(row, col))
			return (0);

		Object o = getObject(row, col);
		if (o instanceof BigDecimal) {
			BigDecimal b = (BigDecimal) o;
			return (b.doubleValue());
		} else {
			Double d = (Double) o;
			return (d.doubleValue());
		}
	}
	
	/**
	 * getDoubleObject 
	 *
	 * @param row
	 * @param columnName
	 * @return  Double
	 */
	public Double getDoubleObject(int row, String columnName) {
		return (getDoubleObject(row, getColumnIndex(columnName)));
	}
	
	/**
	 * getDoubleObject 
	 *
	 * @param row
	 * @param col
	 * @return  Double
	 */
	public Double getDoubleObject(int row, int col) {
		if (isNull(row, col))
			return null;

		Object o = getObject(row, col);
		if (o instanceof BigDecimal) {
			return new Double(((BigDecimal) o).doubleValue());
		} else {
			return ((Double) o);
		}
	}

	/**
	 * getFloat 
	 *
	 * @param row
	 * @param columnName
	 * @return  float
	 */
	public float getFloat(int row, String columnName) {
		return (getFloat(row, getColumnIndex(columnName)));
	}
	
	/**
	 * getFloat 
	 *
	 * @param row
	 * @param col
	 * @return  float
	 */
	public float getFloat(int row, int col) {
		if (isNull(row, col))
			return (0);

		Object o = getObject(row, col);
		if (o instanceof BigDecimal) {
			BigDecimal b = (BigDecimal) o;
			return (b.floatValue());
		} else {
			Float f = (Float) o;
			return (f.floatValue());
		}
	}

	/**
	 * getFloatObject 
	 *
	 * @param row
	 * @param columnName
	 * @return  Float
	 */
	public Float getFloatObject(int row, String columnName) {
		return (getFloatObject(row, getColumnIndex(columnName)));
	}
	
	/**
	 * getFloatObject 
	 *
	 * @param row
	 * @param col
	 * @return  Float
	 */
	public Float getFloatObject(int row, int col) {
		if (isNull(row, col))
			return null;

		Object o = getObject(row, col);
		if (o instanceof BigDecimal) {
			return new Float(((BigDecimal) o).floatValue());
		} else {
			return ((Float) o);
		}
	}

	/**
	 * getIntegerString 
	 *
	 * @param row
	 * @param columnName
	 * @return  String
	 */
	public String getIntegerString(int row, String columnName) {
		return (getIntegerString(row, getColumnIndex(columnName)));
	}

	/**
	 * getIntegerString 
	 *
	 * @param row
	 * @param col
	 * @return  String
	 */
	public String getIntegerString(int row, int col) {
		if (isNull(row, col)) {
			return null;
		}
		
		Object o = getObject(row, col);
		if (o instanceof BigDecimal) {
			return String.valueOf(new Long(((BigDecimal) o).longValue()));
		} else {
			return (String.valueOf(o));
		}
	}
	
	/**
	 * isNull 
	 *
	 * @param row
	 * @param col
	 * @return  boolean
	 */
	public boolean isNull(int row, int col) {
		return (getObject(row, col) == null);
	}

	/**
	 * isNull 
	 *
	 * @param row
	 * @param columnName
	 * @return  boolean
	 */
	public boolean isNull(int row, String columnName) {
		return (isNull(row, getColumnIndex(columnName)));
	}

	/**
	 * getRow 
	 *
	 * @param rowcount
	 * @return  String[]
	 */
	public String[] getRow(int rowcount){
		String[] tmp = new String[colCount];
		int rownum = rowcount * colCount;
		
		for(int i = 0 ; i < colCount ; i++){
			if(result.get(i + rownum) == null) tmp[i] = null; 			
			else tmp[i] = "" + result.get(i + rownum);
		}
		
		return tmp;
	}
	
	/**
	 * getRowLine 
	 *
	 * @param rowcount
	 * @return  String
	 */
	public String getRowLine(int rowcount){
		String tmp = "";
		int rownum = rowcount * colCount;
		
		for(int i = 0 ; i < colCount ; i++){
			tmp = tmp +result.get(i + rownum); 
		}		
		return tmp;
	}
	
	/**
	 * getColumnIndex 
	 *
	 * @param columnName
	 * @return  int
	 */
	private int getColumnIndex(String columnName) {
		int colIndex = -1;

		for (int i = 0; i < colName.size(); i++) {
			String name = (String) colName.get(i);
			if (name.equals(columnName)) {
				colIndex = i;
				break;
			}
		}
		return colIndex;
	}

}