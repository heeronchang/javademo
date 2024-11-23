package org.hc.mybatisplus.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.hc.mybatisplus.domain.po.User;
import org.hc.mybatisplus.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/23
 */
@SpringBootTest
class IUserServiceTest {
    @Autowired
    private IUserService userSerice;
    @Test
    void testInsertUser() {
        User user = new User();
//        user.setId(10L);
        user.setUsername("D");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
//        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setInfo(UserInfo.of(24, "英文老师", "female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userSerice.save(user);
    }
    @Test
    void testPageQuery() {
        int pageNo = 1, pageSize = 5;
        Page<User> page = Page.of(pageNo, pageSize);
        page.addOrder(new OrderItem("balance", true));
        Page<User> p = userSerice.page(page);
        System.out.println(p.getTotal());
        System.out.println(p.getCurrent());
        System.out.println(p.getPages());
        System.out.println(p.getRecords());
    }
}