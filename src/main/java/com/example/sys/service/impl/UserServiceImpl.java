package com.example.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.utils.JwtUtil;
import com.example.sys.entity.User;
import com.example.sys.mapper.UserMapper;
import com.example.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kai
 * @since 2023-08-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Map<String, Object> login(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);
        if(loginUser != null && passwordEncoder.matches(user.getPassword(),loginUser.getPassword())){
//            //PlanA:UUID
//            String key = "user:" + UUID.randomUUID();
//
//            //Redis
            loginUser.setPassword(null);
//            redisTemplate.opsForValue().set(key,loginUser, 30, TimeUnit.MINUTES);

            //PlanB:Jwt
            String token = jwtUtil.createToken(loginUser);

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            return data;
        }

        return null;
    }

//    @Override
//    public Map<String, Object> login(User user) {
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername,user.getUsername());
//        wrapper.eq(User::getPassword,user.getPassword());
//        User loginUser = this.baseMapper.selectOne(wrapper);
//        if(loginUser != null){
//            String key = "user:" + UUID.randomUUID();
//
//            loginUser.setPassword(null);
//            redisTemplate.opsForValue().set(key,loginUser, 30, TimeUnit.MINUTES);
//
//
//
//            Map<String, Object> data = new HashMap<>();
//            data.put("token", key);
//            return data;
//        }
//
//        return null;
//    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
//        Object obj = redisTemplate.opsForValue().get(token);
        User loginUser = null;
        try {
            loginUser = jwtUtil.parseToken(token, User.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (loginUser != null){
//            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name", loginUser.getUsername());
            data.put("avatar", loginUser.getAvatar());

            List<String> roleNameByUserId = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles", roleNameByUserId);
            return data;

        }

        return null;
    }

    @Override
    public void logout(String token) {

//        redisTemplate.delete(token);
    }
}
