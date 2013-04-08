package bin.g11n.gt.dao.common.extension;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;

/** solve the char type result trim problem
 * 
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class GtMysqlDialect extends MySQLDialect {

	public GtMysqlDialect() {
		super();
		registerColumnType( Types.CHAR, "char($l)" );
		registerHibernateType( Types.CHAR, Hibernate.STRING.getName() );
		
		registerColumnType(Types.BLOB, "varbinary(max)");
		registerHibernateType(Types.BLOB, Hibernate.BLOB.getName());
	}


}
