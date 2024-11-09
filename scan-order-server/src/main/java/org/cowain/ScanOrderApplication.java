package org.cowain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@EnableTransactionManagement //开启事物
@EnableCaching //开启spring cache
@EnableScheduling //开启任务调度
public class ScanOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScanOrderApplication.class, args);
        log.info("server started...");
    }
}
