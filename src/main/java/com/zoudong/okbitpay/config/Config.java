package com.zoudong.okbitpay.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource({"classpath:config.properties"})
public class Config {
    @Value("${rpcaddress}")
    String rpcaddress;
    @Value("${rpcuser}")
    String rpcuser;
    @Value("${rpcpassword}")
    String rpcpassword;
    @Value("${rpcport}")
    String rpcport;
    @Value("${validation_level}")
    int validationLevel;
    @Value("${maxretry_count}")
    int maxretrycount;
}
