package icu.resip.service.impl;

import icu.resip.constants.CommonConstants;
import icu.resip.domain.uaa.AuthFile;
import icu.resip.domain.uaa.Permission;
import icu.resip.domain.uaa.User;
import icu.resip.entity.UserInfo;
import icu.resip.exception.LogicException;
import icu.resip.mapper.AuthMapper;
import icu.resip.mapper.UserMapper;
import icu.resip.redis.service.UserRedisService;
import icu.resip.result.CommonCodeMsg;
import icu.resip.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author Peng
 * @Date 2022/4/7
 */
@Service
public class AuthServiceImpl implements AuthService {

    private UserRedisService userRedisService;

    @Autowired
    public void setUserRedisService(UserRedisService userRedisService) {
        this.userRedisService = userRedisService;
    }

    private AuthMapper authMapper;

    @Autowired
    public void setAuthMapper(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public void submit(AuthFile authFile, String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 判断是否已提交认证审核材料，如果已提交，不再重复提交
        AuthFile authFile1 = authMapper.selectOne(uid);
        if (authFile1 != null) {
            throw new LogicException(CommonCodeMsg.AUTH_RESUBMIT_ERROR);
        }

        // 封装uid和提交审核时间
        authFile.setUid(uid);
        authFile.setCreateTime(new Date());

        // 在mysql中存储审核数据
        int row = authMapper.insert(authFile);
        if (row == 0) {
            throw new LogicException(CommonCodeMsg.AUTH_SUBMIT_ERROR);
        }

        // 更改用户认证状态为待认证
        authMapper.updateAuth(uid, CommonConstants.TO_BE_AUTH);

        // 更新用户信息缓存里的权限信息及认证信息
        User user = userMapper.selectUser(uid);
        List<Permission> permissions = userMapper.listPermission(uid);
        user.setPermissions(permissions);
        userRedisService.saveUser(token, user);

        UserInfo userInfo = userMapper.selectUserInfo(uid);
        userRedisService.saveUserInfo(token, userInfo);
    }

}
