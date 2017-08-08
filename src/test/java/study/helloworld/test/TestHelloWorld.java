package study.helloworld.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import study.helloworld.mapper.RoleMapper;
import study.helloworld.po.Role;
import study.helloworld.util.SqlSessionFactoryUtil;

public class TestHelloWorld {
	
	@Test
	public void testHelloWorld() {
		SqlSession sqlSession = null;
		
		try {
			sqlSession = SqlSessionFactoryUtil.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = new Role();
			role.setRoleName("testName");
			role.setNote("testNote");
			roleMapper.insertRole(role);
			roleMapper.deleteRole(1L);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
			
		}
	}
}
