package org.cowain.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.constant.JwtClaimsConstant;
import org.cowain.constant.PlatformConstant;
import org.cowain.dto.UserLoginDTO;
import org.cowain.entity.User;
import org.cowain.properties.JwtProperties;
import org.cowain.properties.WeChatProperties;
import org.cowain.result.Result;
import org.cowain.service.UserService;
import org.cowain.utils.JwtUtils;
import org.cowain.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("userController")
@RequestMapping("/user/user")
@Api(tags = "C端用户相关接口")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @ApiOperation("微信登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信登录:{}", userLoginDTO.getCode());
        String provider = userLoginDTO.getProvider();
        User user = null;
        //登录
        if (PlatformConstant.WEIXIN.equals(provider)) {
            user = userService.wxLogin(userLoginDTO);
        } else {
            user = userService.alipayLogin(userLoginDTO);
        }


        //为微信用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtils.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        //构建返回值VO
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }

    @ApiOperation("更新用户手机号")
    @PutMapping("/{mobile}")
    public Result<String> updateUser(@PathVariable("mobile") String mobile){
        userService.updateUserMobile(mobile);
        return Result.success("更新成功");
    }
}
