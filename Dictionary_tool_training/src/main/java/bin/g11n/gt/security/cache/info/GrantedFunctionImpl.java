package bin.g11n.gt.security.cache.info;

import java.io.Serializable;

/**
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class GrantedFunctionImpl implements GrantedFunction , Serializable{


    private String function;

    //~ Constructors ===========================================================

    public GrantedFunctionImpl(String function) {
        super();
        this.function = function;
    }

    protected GrantedFunctionImpl() {
        throw new IllegalArgumentException("Cannot use default constructor");
    }

    //~ Methods ================================================================

    public String getFunction() {
        return this.function;
    }

    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return obj.equals(this.function);
        }

        if (obj instanceof GrantedFunction) {
            GrantedFunction attr = (GrantedFunction) obj;

            return this.function.equals(attr.getFunction());
        }

        return false;
    }

    public int hashCode() {
        return this.function.hashCode();
    }

    public String toString() {
        return this.function;
    }
	
	


}
