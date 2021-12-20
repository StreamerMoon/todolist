package com.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.dto.LoginDto;
import com.common.lang.Result;
import com.entity.SysUser;
import com.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@RestController

public class loginController {


    @Autowired
    private  SysUserService  sysUserService;


    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {


        String result = (String) request.getAttribute("verification result");
        if("logged in".equals(result)) {//校验用户是否已经登录
            System.out.println("用户已登录");

            return  Result.succ("用户已登录，请勿重复登录");
        }

        //方法调用有问题
        SysUser user =sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", loginDto.getUserName()));
        Assert.notNull(user, "用户不存在");
        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getUserPwd()))){
            return Result.fail("密码不正确");
        }
        String Token = UUID.randomUUID().toString();
        session.setAttribute("Token",Token);
        user.setToken(Token);

        //System.out.println(res);//输出UUID
//        response.setHeader("Authorization", jwt);
//        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.succ(MapUtil.builder()
                .put("id", user.getUuid())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("token",user.getToken())
                .map()
        );
    }
    @PostMapping("/register")
    public Result register(@Validated @RequestBody SysUser sysUser, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        sysUser.setPassword(SecureUtil.md5(sysUser.getPassword()));
        sysUserService.save(sysUser);
        return Result.succ(MapUtil.builder()
        );
    }

    @GetMapping("/logout")
    public Result logout() {
        return Result.succ(null);
    }
}
