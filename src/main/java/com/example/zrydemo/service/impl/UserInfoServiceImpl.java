package com.example.zrydemo.service.impl;

import com.example.zrydemo.entity.UserInfo;
import com.example.zrydemo.mapper.UserInfoMapper;
import com.example.zrydemo.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dyb
 * @since 2022-05-11
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
