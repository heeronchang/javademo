package org.hc.mybatisplus.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hc.mybatisplus.domain.dto.PageDTO;
import org.hc.mybatisplus.domain.dto.UserFormDTO;
import org.hc.mybatisplus.domain.po.User;
import org.hc.mybatisplus.domain.query.UserQuery;
import org.hc.mybatisplus.domain.vo.UserVO;
import org.hc.mybatisplus.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/23
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @ApiOperation("新增用户接口")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userFormDTO) {
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        userService.save(user);
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("/{id}")
    public void deleteUserById(@ApiParam("用户ID") @PathVariable("id") Long id) {
        userService.removeById(id);
    }

    @ApiOperation("查询用户接口")
    @GetMapping("/{id}")
    public UserVO queryUserById(@ApiParam("用户ID") @PathVariable("id") Long id) {
//        User user = userService.getById(id);
//        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
//        return userVO;
        return userService.queryUserAndAddressById(id);
    }

    @ApiOperation("批量查询用户接口")
    @GetMapping
    public List<UserVO> queryUserByIds(@ApiParam("用户ID集合") @RequestParam("ids") List<Long> ids) {
//        List<User> users = userService.listByIds(ids);
//        List<UserVO> userVOs = BeanUtil.copyToList(users, UserVO.class);
//    	return userVOs;
        return userService.queryUsersAndAddressById(ids);
    }

    @ApiOperation("条件查询用户接口")
    @GetMapping("/list")
    public PageDTO<UserVO> queryUsersByCodition(UserQuery userQuery) {
        return userService.queryUsersByCondition(userQuery);
    }

    @ApiOperation("扣减余额接口")
    @PutMapping("/{id}/deduct/{amount}")
    public void deducBalance(@ApiParam("用户ID") @PathVariable Long id, @ApiParam("扣减金额") @PathVariable Integer amount) {
        userService.deductBalance(id, amount);
    }
}
