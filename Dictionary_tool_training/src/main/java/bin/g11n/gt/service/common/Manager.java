package bin.g11n.gt.service.common;

import java.io.Serializable;
import java.util.List;

/**
 * The interface for all of the services
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface Manager {
    /**
     * Generic method used to get a all objects of a particular type. 
     * @param clazz the type of objects 
     * @return List of populated objects
     */
    public List getAll(Class clazz);
    
    /**
     * Generic method to get an object based on class and identifier. 
     * 
     * @param clazz model class to lookup
     * @param id the identifier (primary key) of the class
     * @return a populated object 
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    public Object get(Class clazz, Serializable id);

    /**
     * Generic method to save an object.
     * @param o the object to save
     */
    public Object save(Object o);

    /**
     * Generic method to delete an object based on class and id
     * @param clazz model class to lookup
     * @param id the identifier of the class
     */
    public void remove(Class clazz, Serializable id);
}
