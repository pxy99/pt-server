package icu.resip.mapper;

import icu.resip.domain.uaa.Open;
import icu.resip.domain.uaa.Permission;
import icu.resip.domain.uaa.User;
import icu.resip.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author Peng
 * @Date 2022/1/28 16:23
 * @Version 1.0
 */
@Repository
public interface UserMapper {

    /**
     * 根据openid查询open表信息
     * @param openid
     * @return
     */
    Open getOpenByWxOpenId(String openid);

    /**
     * 往user表中插入一条数据
     * @param user
     */
    void insertUser(User user);

    /**
     * 往open表中插入一条数据
     * @param open
     */
    void insertOpen(Open open);

    /**
     * 通过openid查询open表关联的用户id
     * @param openid
     * @return
     */
    Long selectUserIdByOpenid(String openid);

    /**
     * 更改登录时间、登录ip、登录状态
     * @param user
     */
    void updateUserInfo(User user);

    /**
     * 查询user表部分信息封装到UserInfo对象中
     * @return
     */
    UserInfo selectUserInfo(Long id);

    /**
     * 保存从微信获得的用户信息，更改部分数据
     * @param user
     */
    void updateUser(User user);

    /**
     * 通过uid查询openid
     * @param uid
     * @return
     */
    String selectOpenidByUid(Long uid);

    /**
     * 更改user表的nick_name字段
     * @param id 主键id
     * @param newName 新昵称
     */
    void updateNickName(@Param("id") Long id, @Param("newName") String newName);

    /**
     * 查询此用户所拥有的所有权限
     * @param uid 用户id
     * @return
     */
    List<Permission> listPermission(Long uid);

    /**
     * 查询一条user数据
     * @param uid
     * @return
     */
    User selectUser(Long uid);

    /**
     * 更新user表中avatar_url字段
     * @param user 头像url信息
     * @return 更新数据数目
     */
    int updateAvatarUrl(User user);

    /**
     * 在user_role表中插入一条关联数据
     * @param uid
     * @param rid
     */
    void insertUserRole(@Param("uid") Long uid, @Param("rid") Long rid);

    /**
     * 根据角色sn查询角色id
     * @param sn
     * @return
     */
    Long selectRoleId(String sn);

    /**
     * 查询user表create_time字段
     * @param uid
     * @return
     */
    User selectCreateTime(Long uid);

    /**
     * 查询user表balance字段值
     * @param uid
     * @return
     */
    BigDecimal selectBalance(Long uid);

    /**
     * 给user表的balance重新设置值
     * @param uid
     * @param balance
     */
    int updateBalance(@Param("uid") Long uid, @Param("balance") BigDecimal balance);

    /**
     * 修改user表passwd字段
     * @param passwd
     * @param uid
     */
    void updatePasswd(@Param("passwd") String passwd, @Param("uid") Long uid);

    /**
     * 查询user表passwd字段值
     * @param uid
     * @return
     */
    String findPasswd(Long uid);

    /**
     * 更新user表phone字段值
     * @param phone
     * @param uid
     */
    int updatePhone(@Param("phone") String phone, @Param("uid") Long uid);
}
