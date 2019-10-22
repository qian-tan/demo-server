package com.jojoreading.demo.server.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jojoreading.demo.server.core.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserDao
 *
 * @author huangjing@tinman.cn
 * @date 2019/10/22
 **/
@Mapper
public interface UserDao extends BaseMapper<UserDO> {

}
