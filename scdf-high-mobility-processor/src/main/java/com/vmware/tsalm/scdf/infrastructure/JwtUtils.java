package com.vmware.tsalm.scdf.infrastructure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class JwtUtils {

    public static String createJwt(JwtConfigurationProperties configurationProperties) {
        final Algorithm algorithm = Algorithm.ECDSA256(null, getPrivateKeyFromConfiguration(configurationProperties));
        return JWT.create()
                .withClaim("api_version", configurationProperties.getApiVersion())
                .withClaim("app_id", configurationProperties.getAppId())
                .withClaim("access_token", configurationProperties.getVehicleAccessToken())
                .withAudience(configurationProperties.getAud())
                .withIssuer(configurationProperties.getIss())
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    private static ECPrivateKey getPrivateKeyFromConfiguration(JwtConfigurationProperties configurationProperties) {
        final byte[] keyBytes = Base64.getMimeDecoder().decode(
                configurationProperties.getPrivateKeyString()
                        .replace("-----BEGIN PRIVATE KEY-----\n", "")
                        .replace("\n-----END PRIVATE KEY-----", "")
        );
        return (ECPrivateKey) JwtUtils.getPrivateKey(keyBytes);
    }

    private static PrivateKey getPrivateKey(byte[] keyBytes) {
        try {
            KeyFactory kf = KeyFactory.getInstance("EC");
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            return kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}