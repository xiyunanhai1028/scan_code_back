package org.cowain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "scan.alipay")
@Data
public class AlipayProperties {
    private String appid;
    private String publicKey;
    private String privateKey;
    private String appPublicCertPath;
    private String aliPublicCertPath;
    private String aliRootCertPath;
}
