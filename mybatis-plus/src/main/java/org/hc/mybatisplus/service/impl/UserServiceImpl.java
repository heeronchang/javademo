package org.hc.mybatisplus.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.hc.mybatisplus.domain.dto.PageDTO;
import org.hc.mybatisplus.domain.po.Address;
import org.hc.mybatisplus.domain.po.User;
import org.hc.mybatisplus.domain.query.UserQuery;
import org.hc.mybatisplus.domain.vo.AddressVO;
import org.hc.mybatisplus.domain.vo.UserVO;
import org.hc.mybatisplus.enums.UserStatusEnum;
import org.hc.mybatisplus.mapper.UserMapper;
import org.hc.mybatisplus.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
//    @Transactional // 事务
    public void deductBalance(Long id, Integer amount) {
        User user = getById(id);
        if (Objects.isNull(user) || user.getStatus() == UserStatusEnum.FREEZE) {
            throw new RuntimeException("用户不存在或已被冻结");
        }
        if (user.getBalance() < amount) {
            throw new RuntimeException("余额不足");
        }
//        baseMapper.deductBalance(id, amount);

        int remainBalance = user.getBalance() - amount;
        lambdaUpdate()
                .set(User::getBalance, remainBalance)
                .set(remainBalance == 0, User::getStatus, 2)
                .eq(User::getId, id)
                .eq(User::getBalance, user.getBalance()) // 乐观锁
                .update();
    }

    @Override
    public PageDTO<UserVO> queryUsersByCondition(UserQuery userQuery) {
        Page<User> page = userQuery.toPage();
        Page<User> users = lambdaQuery()
                .like(null != userQuery.getName(), User::getUsername, userQuery.getName())
                .eq(null != userQuery.getStatus(), User::getStatus, userQuery.getStatus())
                .ge(null != userQuery.getMinBalance(), User::getBalance, userQuery.getMinBalance())
                .le(null != userQuery.getMaxBalance(), User::getBalance, userQuery.getMaxBalance())
                .page(page);

//        PageDTO<UserVO> dto = PageDTO.of(users, UserVO.class);
        PageDTO<UserVO> dto = PageDTO.of(users, user -> {
            // 1. to do sth
            // 2. return
            return BeanUtil.copyProperties(user, UserVO.class);
        });

        return dto;
    }

    @Override
    public UserVO queryUserAndAddressById(Long id) {
        User user = getById(id);
        if (Objects.isNull(user) || user.getStatus() == UserStatusEnum.FREEZE) {
            throw new RuntimeException("用户不存在或已被冻结");
        }

        List<Address> addresses = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, id)
                .list();
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        if (CollUtil.isNotEmpty(addresses)) {
            userVO.setAddresses(BeanUtil.copyToList(addresses, AddressVO.class));
        }
        return userVO;
    }

    @Override
    public List<UserVO> queryUsersAndAddressById(List<Long> ids) {
        List<User> users = listByIds(ids);
        if (CollUtil.isEmpty(users)) {
            return Collections.emptyList();
        }
        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        List<Address> addresses = Db.lambdaQuery(Address.class)
                .in(Address::getUserId, userIds)
                .list();
        List<AddressVO> addressVOS = BeanUtil.copyToList(addresses, AddressVO.class);
        Map<Long, List<AddressVO>> addressMap = new HashMap<>(0);
        if (CollUtil.isNotEmpty(addressVOS)) {
            addressMap = addressVOS.stream().collect(Collectors.groupingBy(AddressVO::getUserId));
        }

        List<UserVO> userVOS = BeanUtil.copyToList(users, UserVO.class);
        Map<Long, List<AddressVO>> finalAddressMap = addressMap;
        userVOS.forEach(userVO -> userVO.setAddresses(finalAddressMap.get(userVO.getId())));
        return userVOS;
    }
}
