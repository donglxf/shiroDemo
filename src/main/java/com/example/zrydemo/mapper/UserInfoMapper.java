package com.example.zrydemo.mapper;

import com.example.zrydemo.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dyb
 * @since 2022-05-11
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    public List<UserInfo> getPremissByUserName(String userName);

}
