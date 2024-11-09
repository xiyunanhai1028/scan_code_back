package org.cowain.exception;

/**
 * 账号不存在异常
 */
public class AccountNotFundException extends BaseException{
    public AccountNotFundException() {
    }

    public AccountNotFundException(String message) {
        super(message);
    }
}
