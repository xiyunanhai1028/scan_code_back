package org.cowain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayMerchantOrderSyncRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.cowain.constant.MessageConstant;
import org.cowain.context.BaseContext;
import org.cowain.dto.UserLoginDTO;
import org.cowain.entity.User;
import org.cowain.exception.LoginFailedException;
import org.cowain.mapper.UserMapper;
import org.cowain.properties.AlipayProperties;
import org.cowain.properties.WeChatProperties;
import org.cowain.service.UserService;
import org.cowain.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String ALI_LOGIN = "https://openapi.alipay.com/gateway.do";

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private AlipayProperties alipayProperties;

    //微信登录
    public User wxLogin(UserLoginDTO userLoginDTO) {
        //获取Openid
        String openid = getOpenid(userLoginDTO.getCode());

        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.getByOpenid(openid);
        if (user == null) {
            //此用户是新用户
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            //插入新用户
            userMapper.insert(user);
        }
        return user;
    }

    //调用微信接口，获取openid
    private String getOpenid(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtils.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }

    @Override
    public User alipayLogin(UserLoginDTO userLoginDTO) {
        // 初始化SDK
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(getAlipayConfig());
            // 构造请求参数以调用接口
            AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();

            // 设置刷新令牌
            request.setRefreshToken("");

            // 设置授权码
            request.setCode(userLoginDTO.getCode());

            // 设置授权方式
            request.setGrantType("authorization_code");

            AlipaySystemOauthTokenResponse response = alipayClient.certificateExecute(request);

            System.out.println(response.getBody());

            if (response.isSuccess()) {
                String openId = response.getUserId();
                System.out.println("调用成功");
                User user = userMapper.getByOpenid(openId);
                if (user == null) {
                    //此用户是新用户
                    user = User.builder()
                            .openid(openId)
                            .createTime(LocalDateTime.now())
                            .build();
                    //插入新用户
                    userMapper.insert(user);
                }
                return user;
            } else {
                System.out.println("调用失败");
                return null;
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    public AlipayConfig getAlipayConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        //设置网关地址
        alipayConfig.setServerUrl(ALI_LOGIN);
        //设置应用APPID
        alipayConfig.setAppId(alipayProperties.getAppid());

        //设置应用私钥
        alipayConfig.setPrivateKey(alipayProperties.getPrivateKey());


        //设置应用公钥证书路径 appPublicCertPath
        alipayConfig.setAppCertPath(alipayProperties.getAppPublicCertPath());
        //设置支付宝公钥证书路径 aliPublicCertPath
        alipayConfig.setAlipayPublicCertPath(alipayProperties.getAliPublicCertPath());
        //设置支付宝根证书路径 aliRootCertPath
        alipayConfig.setRootCertPath(alipayProperties.getAliRootCertPath());
        //设置请求格式，固定值json
        alipayConfig.setFormat("json");
        //设置字符集
        alipayConfig.setCharset("UTF8");
        //设置签名类型
        alipayConfig.setSignType("RSA2");
//        String privateKey = alipayProperties.getPrivateKey();
//        String alipayPublicKey = alipayProperties.getPublicKey();
//        AlipayConfig alipayConfig = new AlipayConfig();
//        alipayConfig.setServerUrl(ALI_LOGIN);
//        alipayConfig.setAppId(alipayProperties.getAppid());
//        alipayConfig.setPrivateKey(privateKey);
//        alipayConfig.setFormat("json");
//        alipayConfig.setAlipayPublicKey(alipayPublicKey);
//        alipayConfig.setCharset("UTF-8");
//        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }


    public void updateUserMobile(String mobile) {
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);
        user.setPhone(mobile);
        userMapper.update(user.getId(),mobile);
    }
}
