package com.fast_build_auth.controller;

import com.fast_build_auth.domain.UserInfo;
import com.fast_build_auth.dto.RetDTO;
import com.fast_build_auth.service.UserInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/User")
public class UserInfoController {

    @Resource
    UserInfoService userInfoService;

    @RequestMapping(value = "getById",method = RequestMethod.GET)
    public RetDTO<UserInfo> getById(@RequestParam("id") Integer id) {
        try {
            return RetDTO.success(userInfoService.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return RetDTO.error("Query Error!");
        }
    }
}
