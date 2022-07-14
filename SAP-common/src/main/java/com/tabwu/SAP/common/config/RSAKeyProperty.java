package com.tabwu.SAP.common.config;


import com.tabwu.SAP.common.utils.RsaUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @PROJECT_NAME: springsecurityTest
 * @USER: tabwu
 * @DATE: 2022/3/17 16:43
 * @DESCRIPTION:
 */
@ConfigurationProperties("rsa.key")
public class RSAKeyProperty {
    private String publicKeyPath;
    private String privateKeyPath;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    private void loadKey() throws Exception {
        publicKey = RsaUtil.getPublicKey(publicKeyPath);
        privateKey = RsaUtil.getPrivateKey(privateKeyPath);
    }

    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
