package icu.resip.mapper;

import icu.resip.domain.uaa.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Peng
 * @Date: 2022/3/30
 */
@Repository
public interface PermissionMapper {

    /**
     * 查询出Mysql中存储得所有权限信息
     * @return
     */
    List<Permission> selectAll();

    /**
     * 通过权限表达式删除权限数据
     * @param expression
     */
    void deleteByExpression(@Param("expression") String expression);

    /**
     * 插入一条权限数据
     * @param permission
     * @return
     */
    int insert(Permission permission);

    /**
     * 通过权限表达式删除角色权限表关联得数据
     * @param expression
     */
    void deleteRolePermission(@Param("expression") String expression);
}
