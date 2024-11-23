package org.hc.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.hc.mybatisplus.domain.dto.PageDTO;
import org.hc.mybatisplus.domain.po.User;
import org.hc.mybatisplus.domain.query.UserQuery;
import org.hc.mybatisplus.domain.vo.UserVO;

import java.util.List;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/23
 */
public interface IUserService extends IService<User> {
    void deductBalance(Long id, Integer amount);

    PageDTO<UserVO> queryUsersByCondition(UserQuery userQuery);

    UserVO queryUserAndAddressById(Long id);

    List<UserVO> queryUsersAndAddressById(List<Long> ids);
}
