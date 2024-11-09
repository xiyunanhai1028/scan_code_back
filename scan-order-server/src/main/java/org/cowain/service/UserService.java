package org.cowain.service;

import com.alipay.api.AlipayApiException;
import org.cowain.dto.UserLoginDTO;
import org.cowain.entity.User;

public interface UserService {
    //微信登录
    User wxLogin(UserLoginDTO userLoginDTO);

    //支付宝等了
    User alipayLogin(UserLoginDTO userLoginDTO);

    //更新用户手机号
    void updateUserMobile(String mobile);
}
