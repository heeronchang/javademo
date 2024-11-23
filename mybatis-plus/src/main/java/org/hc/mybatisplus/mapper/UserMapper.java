package org.hc.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.hc.mybatisplus.domain.po.User;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/22
 */
public interface UserMapper extends BaseMapper<User> {

    void updateBalanceByIds(@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper, @Param("amount") int amount);

    @Update("update user set balance = balance - #{amount} where id = #{id} and balance > #{amount}")
    void deductBalance(@Param("id") Long id, @Param("amount") Integer amount);
}
