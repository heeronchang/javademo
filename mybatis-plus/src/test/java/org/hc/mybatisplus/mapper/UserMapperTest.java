package org.hc.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.hc.mybatisplus.domain.po.User;
import org.hc.mybatisplus.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/22
 */
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsertUser() {
        User user = new User();
//        user.setId(10L);
        user.setUsername("C");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
//        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setInfo(UserInfo.of(24, "英文老师", "female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userMapper.insert(user);
    }

    @Test
    void testSelectUser() {
        User user = userMapper.selectById(10L);
        System.out.println(user);
    }

    @Test
    void testBathSelectUser() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L));
        users.forEach(System.out::println);
    }

    @Test
    void testQueryWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .select("id", "username", "info", "balance")
                .like("username", "o")
                .ge("balance", 1000);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testLambdaQueryWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(User::getUsername, "o")
                .ge(User::getBalance, 1000);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateWrapper() {
        User user = new User();
        user.setBalance(10000);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user, new QueryWrapper<User>().eq("username", "Heeron"));
    }

    @Test
    void testUpdateWrapper2() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = balance + 100")
                .in("id", 6L, 7L);
        userMapper.update(null, wrapper);
    }

    @Test
    void testCustomSqlUpdate() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(User::getId, List.of(6L, 7L));
        int amount = 200;

        userMapper.updateBalanceByIds(queryWrapper, amount);
    }
}