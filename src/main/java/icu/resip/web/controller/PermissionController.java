package icu.resip.web.controller;

import icu.resip.result.Result;
import icu.resip.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限资源控制器
 * @Author: Peng
 * @Date: 2022/3/30
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    private PermissionService permissionService;

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    // 加载权限列表
    @PostMapping("/reload")
    public Object reload() {
        permissionService.reload();
        return Result.success(null);
    }

}
