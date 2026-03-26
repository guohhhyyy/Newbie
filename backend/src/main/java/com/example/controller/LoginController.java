package com.example.controller;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Date;  // 需要导入Date类

@RestController
@RequestMapping("/api")  // 可以添加这个注解，统一前缀
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 登录接口
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        Map<String, Object> result = new HashMap<>();

        String phone = loginData.get("phone");
        String password = loginData.get("password");

        if (phone == null || phone.isEmpty() || password == null || password.isEmpty()) {
            result.put("success", false);
            result.put("message", "手机号或密码不能为空");
            return result;
        }

        try {
            Optional<User> userOptional = userRepository.findByPhone(phone);

            if (userOptional.isEmpty()) {
                result.put("success", false);
                result.put("message", "该手机号未注册");
                return result;
            }

            User user = userOptional.get();

            if (!user.getPassword().equals(password)) {
                result.put("success", false);
                result.put("message", "密码错误");
                return result;
            }

            result.put("success", true);
            result.put("message", "登录成功");
            return result;

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "系统错误: " + e.getMessage());
            e.printStackTrace();
            return result;
        }
    }

    // 注册接口 - 直接添加在这里
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> registerData) {
        Map<String, Object> result = new HashMap<>();

        try {
            String phone = registerData.get("phone");
            String password = registerData.get("password");

            // 验证
            if (phone == null || phone.isEmpty() || password == null || password.isEmpty()) {
                result.put("success", false);
                result.put("message", "手机号和密码不能为空");
                return result;
            }

            // 检查是否已存在
            if (userRepository.findByPhone(phone).isPresent()) {
                result.put("success", false);
                result.put("message", "手机号已注册");
                return result;
            }

            // 创建用户
            User user = new User();
            user.setPhone(phone);
            user.setPassword(password);  // 实际应该加密
            user.setCreateTime(new Date());
            userRepository.save(user);

            result.put("success", true);
            result.put("message", "注册成功");
            result.put("userId", user.getId());

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "注册失败：" + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}