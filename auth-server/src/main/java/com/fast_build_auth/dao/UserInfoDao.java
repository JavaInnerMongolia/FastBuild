package com.fast_build_auth.dao;

import com.fast_build_auth.domain.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao {
    UserInfo getById(@Param("id") Integer id);
}
