package icu.resip.service.impl;

import icu.resip.annotation.RequiredPermission;
import icu.resip.domain.uaa.Permission;
import icu.resip.exception.LogicException;
import icu.resip.mapper.PermissionMapper;
import icu.resip.result.CommonCodeMsg;
import icu.resip.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @Author: Peng
 * @Date: 2022/3/30
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RequestMappingHandlerMapping rmhm;

    private PermissionMapper permissionMapper;

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public void reload() {
        try {
            // 查询数据库中权限表的所有权限信息
            List<Permission> permissions = permissionMapper.selectAll();
            // 将所有权限表达式存入Set集合中，Set集合存储元素的唯一性
            Set<String> set = new HashSet<>();
            for (Permission permission : permissions) {
                set.add(permission.getExpression());
            }

            // 获取所有Controller中的方法
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = rmhm.getHandlerMethods();
            Collection<HandlerMethod> methods = handlerMethods.values();

            // 获取方法上的注解信息
            for (HandlerMethod method : methods) {
                RequiredPermission permission = method.getMethodAnnotation(RequiredPermission.class);
                //如果方法上贴了注解
                if (permission != null) {
                    String name = permission.name();
                    String expression = permission.expression();

                    // Set集合中删除要添加的权限表达式，如果为true，表示Set集合中有该权限表达式，无需添加
                    // 如果为false，则表示Set集合中没有，数据库中也没有，需要添加该权限表达式所在的权限信息
                    boolean isRemove = set.remove(expression);
                    if (!isRemove) {
                        Permission per = new Permission();
                        per.setName(name);
                        per.setExpression(expression);
                        int insert = permissionMapper.insert(per);
                        // 如果存入权限信息失败，则加载失败
                        if (insert == 0) {
                            throw new LogicException(CommonCodeMsg.PERMISSION_RELOAD_ERROR);
                        }
                    }
                }

            }

            // 如果Set集合中还有剩余权限表达式，说明这些权限表达式是用户不需要的，需要删除数据库中的这些权限信息
            if (!set.isEmpty()) {
                for (String s : set) {
                    // 同时删除权限与角色之间关系表的脏数据
                    permissionMapper.deleteRolePermission(s);
                    permissionMapper.deleteByExpression(s);
                }
            }
        } catch (Exception e) {
            throw new LogicException(CommonCodeMsg.PERMISSION_RELOAD_ERROR);
        }
    }

}
