package com.goj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.goj.reggie.common.R;
import com.goj.reggie.entity.User;
import com.goj.reggie.service.UserService;
import com.goj.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    private R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code:{}", code);
            session.setAttribute(phone, code);
            return R.success("短信发送成功");
        }
        return R.error("请输入正确的手机号");
    }

    @PostMapping("login")
    private R<String> login(@RequestBody Map map, HttpSession session){
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");
        String codeSession = (String) session.getAttribute(phone);
        if (codeSession !=null&&codeSession.equals(code)){
            LambdaQueryWrapper<User> lqw=new LambdaQueryWrapper<>();
            lqw.eq(User::getPhone,phone);
            User user = userService.getOne(lqw);
            if (user==null){
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success("登录成功");
        }
        return R.error("验证码输入有误");
    }

    @PostMapping("/loginout")
    private R<String> loginOut(HttpSession session){
        session.removeAttribute("user");
        return R.success("退出成功");
    }
}
