package bin.g11n.gt.util;


import java.math.*;

/**
 * BigDecimalUtil.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class BigDecimalUtil extends BigDecimal {
	private static BigDecimalUtil zero=new BigDecimalUtil(0);
	
	/**
	 * create a BigDecimalUtil object taking a param with double type
	 *  Constructor 
	 *
	 * @param val  return_type
	 */
	public BigDecimalUtil(double val){
		super(val);
	}
	/**
	 * create a BigDecimalUtil object taking a param with BigDecimal object
	 *  Constructor 
	 *
	 * @param val  return_type
	 */
	public BigDecimalUtil(BigDecimal val){
		this(val.doubleValue());
	}
	/**
	 * create a BigDecimalUtil object taking a param with BigDecimal object and MathContext object
	 *  Constructor 
	 *
	 * @param val  return_type
	 */
	public BigDecimalUtil(double val, MathContext mx){
		super(val,mx);
	}
	/**
	 * 
	 * add method ,the method is useful if it's precision and roundingMode are required.
	 *
	 * @param augend
	 * @param mx
	 * @return  BigDecimalUtil
	 */
	public BigDecimalUtil add(BigDecimal augend,MathContext mx){
		if(augend==null)return this;
		return new BigDecimalUtil(super.add(augend).doubleValue(),mx) ;
	}
	/**
	 * 
	 * add method ,the method is useful if it's scale is nessary to be set.
	 *
	 * @param augend
	 * @param mx
	 * @return  BigDecimalUtil
	 */
	public BigDecimalUtil add(BigDecimal augend,MathContext mx,int scale){
		if(augend==null)return this;
		return new BigDecimalUtil(super.add(augend).doubleValue(),mx) ;
	}
	/**
	 * 
	 * add method ,the method is useful when null object could ignore. 
	 *
	 * @param augend
	 * @param mx
	 * @return  BigDecimalUtil
	 */
	public BigDecimalUtil add(BigDecimal augend){
		if(augend==null)return this;
		return new BigDecimalUtil(super.add(augend).doubleValue()) ;
	}
	/**
	 * 
	 * subtract useful when ignore null pointer
	 *
	 * @param subtrahend
	 * @return  BigDecimalUtil
	 */
	public BigDecimalUtil subtract(BigDecimal subtrahend){
		if(subtrahend==null)return this;
		return new BigDecimalUtil(super.subtract(subtrahend).doubleValue());
		
	}
	/**
	 * 
	 * subtract useful when ignore null pointer and require it's precision and roundingMode.
	 *
	 * @param subtrahend
	 * @param mx
	 * @return  BigDecimalUtil
	 */
	public BigDecimalUtil subtract(BigDecimal subtrahend,MathContext mx){
		if(subtrahend==null)return this;
		return new BigDecimalUtil(super.subtract(subtrahend).doubleValue(),mx);
		
	}
	/**
	 * when divisor is null or zero,continue to run and return null. at the same time ,specifying it's roundingMode and it's scale. 
	 * divide 
	 *
	 * @param divisor
	 * @param scale
	 * @param roundingMode
	 * @return  BigDecimalUtil
	 */
	public BigDecimalUtil divide(BigDecimal divisor,int scale, int roundingMode){
		if(divisor==null||divisor.compareTo(zero)==0)return null;
		return new BigDecimalUtil(super.divide(divisor,scale,roundingMode).doubleValue());
	}
	/**
	 * when divisor is null or zero,continue to run and return null. at the smae time ,specifying it's roundingMode . 
	 * divide 
	 *
	 * @param divisor
	 * @param scale
	 * @param roundingMode
	 * @return  BigDecimalUtil
	 */
	public BigDecimalUtil divide(BigDecimal divisor,MathContext mx){
		if(divisor==null||divisor.compareTo(zero)==0)return null;
		return new BigDecimalUtil(super.divide(divisor,mx).doubleValue(),mx);
	}
	/**
	 * when divisor is null or zero,continue to run and return null. 
	 * divide 
	 *
	 * @param divisor
	 * @param scale
	 * @param roundingMode
	 * @return  BigDecimalUtil
	 */
	public BigDecimalUtil divide(BigDecimal divisor){
		if(divisor==null||divisor.compareTo(zero)==0)return null;
		return new BigDecimalUtil(super.divide(divisor).doubleValue());
	}
	/**
	 * convert from BigDecimal to BigDecimalUtil
	 * valueOf 
	 *
	 * @param val
	 * @return  BigDecimalUtil
	 */
	public static BigDecimalUtil valueOf(BigDecimal val){
		if(val==null)return null;
		return new BigDecimalUtil(val.doubleValue());
	}
	/**
	 * convert from BigDecimalUtil to BigDecimal
	 * valueOf 
	 *
	 * @param val
	 * @param scale
	 * @param mx
	 * @return  BigDecimal
	 */
	public static BigDecimal valueOf(BigDecimalUtil val,int scale,MathContext mx){
		if(val==null) return null;
		return new BigDecimal(val.doubleValue(),mx).setScale(scale,1);
	}
	/**
	 * useful when multiplicand is null.
	 * multiply 
	 *
	 * @param multiplicand
	 * @return  BigDecimalUtil
	 */
	public BigDecimalUtil multiply(BigDecimal multiplicand){
		if(multiplicand==null)return zero;
		return new BigDecimalUtil(super.multiply(multiplicand));
	}

}
