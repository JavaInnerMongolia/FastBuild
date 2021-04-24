package com.fast_build_auth.serviceimpl;

import com.fast_build_auth.dao.UserInfoDao;
import com.fast_build_auth.domain.UserInfo;
import com.fast_build_auth.service.UserInfoService;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger LOG = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private static final String prefixKey = "User_";

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    RedissonClient redissonClient;

    @Resource
    UserInfoDao userInfoDao;

    @Override
    public UserInfo getById(Integer id) {
        String key = prefixKey + id;
        UserInfo value = (UserInfo) redisTemplate.opsForValue().get(key);
        if (value == null) {
            synchronized (this) {
                value = (UserInfo) redisTemplate.opsForValue().get(key);
                if (value == null) {
                    value = userInfoDao.getById(id);
                    redisTemplate.opsForValue().set(key, value, 5, TimeUnit.SECONDS);
                    LOG.info("Query Key: " + key + " In MySQL");
                } else {
                    LOG.info("Query Key: " + key + " In Cache");
                }
            }
        } else {
            LOG.info("Query Key: " + key + " In Cache");
        }
        return value;
    }
}
