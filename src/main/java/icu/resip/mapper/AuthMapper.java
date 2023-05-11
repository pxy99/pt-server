package icu.resip.mapper;

import icu.resip.domain.uaa.AuthFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author Peng
 * @Date 2022/4/7
 */
@Repository
public interface AuthMapper {

    /**
     * 在auth_file表中插入一条数据
     * @param authFile
     */
    int insert(AuthFile authFile);

    /**
     * 更改user表auth值
     * @param auth
     */
    void updateAuth(@Param("uid") Long uid, @Param("auth") int auth);

    /**
     * 根据uid查询auth_file一条数据
     * @param uid
     * @return
     */
    AuthFile selectOne(Long uid);
}
