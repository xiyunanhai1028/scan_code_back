package org.cowain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "scan.jwt")
@Data
public class JwtProperties {
    /**
     * 管理端员工生产JWT令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * C端用户生产JWT令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
}
