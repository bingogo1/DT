package bin.g11n.gt.dao.common;

import java.io.Serializable;
import java.util.List;


/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) DAO's for your
 * domain objects.
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public interface GenericDao <T, PK extends Serializable> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    public List<T> getAll();

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    public T get(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id
     * @return - true if it exists, false if it doesn't
     */
    public boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     */
    public T save(T object);

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    public void remove(PK id);
    
    /** 
     * delete records physically from the current table object of current manager.
     * Sample: this.dao.removeObjectsOfTable();
     * 
     * removeAllOfTable 
     *
     * @return int deleted row count
     */
    public int removeAllOfTable();  
}
