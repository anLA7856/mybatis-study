package study.helloworld.mapper;

import org.apache.ibatis.annotations.Select;

import study.helloworld.po.Role;

public interface RoleMapper {
	@Select(value="select id, role_name as roleName, note from t_role where id = #{id}")
	public Role getRole(Long id);
	public int insertRole(Role role);
	public int deleteRole(Long id);
	
}
