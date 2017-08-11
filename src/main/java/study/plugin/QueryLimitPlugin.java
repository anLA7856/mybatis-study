package study.plugin;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.javassist.tools.reflect.Metaobject;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

@Intercepts({ @Signature(type = StatementHandler.class, // 确定要拦截的对象
		method = "prepare", // 要拦截的方法
		args = { Connection.class, Integer.class }) }) // 拦截方法的参数
public class QueryLimitPlugin implements Interceptor {

	/**
	 * 默认限制查询返回行数
	 */
	private int limit;

	private String dbType;

	public Object intercept(Invocation invocation) throws Throwable {
		// 取出被拦截的对象
		StatementHandler stmtHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStmtHandler = SystemMetaObject.forObject(stmtHandler);
		// 分离代理对象，从而形成多次代理，通过两次循环最原始的被代理类，Mybatis使用的是JDK代理
		while (metaStmtHandler.hasGetter("h")) {
			Object object = metaStmtHandler.getValue("h");
			metaStmtHandler = SystemMetaObject.forObject(object);
		}

		// 分离最后一个代理目标类
		while (metaStmtHandler.hasGetter("target")) {
			Object object = metaStmtHandler.getValue("target");
			metaStmtHandler = SystemMetaObject.forObject(object);
		}
		// 取出即将执行的sql
		String sql = (String) metaStmtHandler.getValue("delegate.boundSql.sql");
		String limitSql;
		// 判断参数是不是mysql数据库且sql有没有被插件重写过，重写过就不重写了
		if ("mysql".equals(this.dbType)) {
			// 去掉前后空格
			sql = sql.trim();
			// 将参数写入到sql
			// limitSql = "select * from ("+sql+")"+LMT_TABLE_NAME + "limit "+limit;
			limitSql = sql + " limit " + limit;
			// 重写要执行的sql
			metaStmtHandler.setValue("delegate.boundSql.sql", limitSql);
		}
		// 调用原来对象的方法，进入责任链的下一层级
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		// 使用mybatis提供的默认的类生成代理对象
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String strLimit = (String) properties.getProperty("limit", "50");
		this.limit = Integer.parseInt(strLimit);
		// 这里设置要读取的数据库类型
		this.dbType = (String) properties.getProperty("dbtype", "mysql");
	}

}
