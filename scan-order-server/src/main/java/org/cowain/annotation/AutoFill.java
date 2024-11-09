package org.cowain.annotation;

import org.cowain.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解：用于表示某个方法需要进行功能字段自动填充处理
 */
@Target(ElementType.METHOD)//目标是真的方法
@Retention(RetentionPolicy.RUNTIME)//固定写法
public @interface AutoFill {
    //数据库操作类型：UPDATE INSERT
    OperationType value();
}
