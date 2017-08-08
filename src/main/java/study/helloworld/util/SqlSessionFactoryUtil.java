package study.helloworld.util;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
/**
 * 用于创建及初始化sqlsessionfactory，
 * 使用单例模式实现，保证一个系统只有一个sqlsessionfactory
 * @author anla7856
 *
 */
public class SqlSessionFactoryUtil {
	private static SqlSessionFactory sqlSessionFactory = null;
	
	//类线程锁
	private static final Class CLASS_LOCK = SqlSessionFactoryUtil.class;
	
	private SqlSessionFactoryUtil() {}
	
	public static SqlSessionFactory initSqlSessionFactory() {
		String resource = "mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (Exception e) {
			Logger.getLogger(SqlSessionFactoryUtil.class.getName()).log(Level.ERROR, null,e);
		}
		
		synchronized(CLASS_LOCK) {
			if(sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			}
		}
		return sqlSessionFactory;
	}
	
	public static SqlSession openSqlSession() {
		if(sqlSessionFactory == null) {
			initSqlSessionFactory();
		}
		return sqlSessionFactory.openSession();
	}
}








