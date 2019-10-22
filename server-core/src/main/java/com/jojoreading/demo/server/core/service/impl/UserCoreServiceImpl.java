package com.jojoreading.demo.server.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jojoreading.demo.server.core.dao.UserDao;
import com.jojoreading.demo.server.core.domain.UserDO;
import com.jojoreading.demo.server.core.service.IUserCoreService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @author huangjing@tinman.cn
 * @date 2019/10/22
 **/
@Service
public class UserCoreServiceImpl extends ServiceImpl<UserDao, UserDO> implements IUserCoreService {

}
