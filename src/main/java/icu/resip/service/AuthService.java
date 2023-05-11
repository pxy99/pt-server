package icu.resip.service;

import icu.resip.domain.uaa.AuthFile;

/**
 * @Author Peng
 * @Date 2022/4/7
 */
public interface AuthService {

    /**
     * 发起跑腿认证
     * @param authFile
     * @param token
     */
    void submit(AuthFile authFile, String token);

}
