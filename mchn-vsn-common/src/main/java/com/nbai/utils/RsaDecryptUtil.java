package com.nbai.utils;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * RSA加解密工具
 *
 * @author 周展
 * @date 2021-11-19
 */
public class RsaDecryptUtil {

    private static final String privateKey =
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCJkLFhuPEuRGMpKGHKq4sjoVnJ\n" +
            "PGFHLFj79jbib58qUQ02+vah+GuIPV4Da5LcUhF676u3K11D62H87UjePDXSxza8EYhGLXtublzO\n" +
            "Ukgh3ipHXR+szZy+7o8zY5Co5mcGEu6m2MKqzBEX0hLpi2RWGFCvjRMFhkaOJYdhtod6U3eZawXx\n" +
            "RPYNk1j8PSHl4tablfFYJ+xhsNcgBuWZxMlhrt0inP1yrHCGNB7B4pnOFw0acfDvKfMpoxTudxDx\n" +
            "DK5zp6gyt1GViv0aPfePz6a3q2TaEmL90lU/G/HCaqvCoU3FmFnRf8QBxnoj4sN0QxXJIbHDsHJN\n" +
            "YvRsjrJ832TlAgMBAAECggEASa/g5EitoCvvtd80qtxCtr46GwolzXsa2zRi594EsyUhX/8EV3R7\n" +
            "HUpJ7qkTzMKFLIXvRDmNRtFx9oqgCrLT3ejqD8mQZo78XAXv3nDCxKl9miZuziCBQuEEgfFmTqLE\n" +
            "MsO3ZFYdyGFxEoJrsQACqf9yUObPdpkbeA7wOs4MOeBsn48dp9JodyoyqCOtifIe1/OMVrLQ6Q+j\n" +
            "sra0yhUuhI6n7c9MlVj0HclQrzJY6jEoFgxe/2nLneqQVzKbPoDz7B+hVy0gwfv0rt0MrRM4kJhQ\n" +
            "ITwkumUHLwNRYusHvGHoOA95ltHrVCd2eUHIUEdYIkLgN2RjLPYvgR2T7qFVIQKBgQDEn2tP5UOI\n" +
            "18A6KmffAxCSDnSCve9UDIEQ2DFDTUpCUGlpG4Qhb1hkmDGXXqh8CSd0V33wGfEwFrC5d9Wz0yCF\n" +
            "dRPBr7jDFAnB4YfAu3mEHSZhC56pSjppibl7rTk31pptPR61OhodcKoQgq0KURn7nv9+vWDouPVY\n" +
            "w6XRJ1GObQKBgQCzG6IkAnO5yJ20baIG89RpzOZchdK1V8V7bcJmdXKU8IUTcZRF9a4nz3ztskTq\n" +
            "YDDXAg7tLnIjcf6g50ipty2mcBoXijt4e2N5M3aTobh51jOY3dgmQJ2l+mzsRsOQ6ALn1yBDLz5B\n" +
            "h60W5GDcZUxDYHn/Fg7SCN0IW7Yu27fFWQKBgQCi1/3Cr/ic70NPaSuZ8inZLYuldLUwgSKr1sIG\n" +
            "IY8tGPgUiyuFnXgx6DK6UTVtdQ9uNcAiCPpOf5xiHzOouWUn4u037tcxCfmCz8zk/OAJYTMWEM+Z\n" +
            "GdcC7YPnQHIxItXHP2WdZr+WPmc7QV8bOkwwr2CfkS0f0X+xwmgkr1HGiQKBgCfjy6TBdwy1hYod\n" +
            "sKxjubcmj1ej5l3FApKOy3EOT0qklr7R2PgL05HCQEygG4EnkzkPmEtywLDsPo2PdTt3NI+Q//6m\n" +
            "JsCamnjv4UTYC8H21t8A6Zb7ZSEGOSQWTpADXEJOQz0zVGY71Wz3yCrN1MJcxk7tlrdoifHGmzis\n" +
            "bq1hAoGBALoXeVXNQMBsj9NkVfHFJDf815l1mxVMZZ2PJiBKkIU+DJVQKIYVE6I59K+JG9e+0Qyt\n" +
            "67wdaKPI4/51BV+vX8VjUTMRfl8iiHoqM12YQcNrba0UTZ+zwk4PHDREUN8EpGxbSwK0rVlYjri1\n" +
            "ivxDyDK6LnwBypiX1sWdHsVn/kQy";

    /**
     * 私钥解密
     *
     * @param data       加密后的数据
     * @return 解密得到的数据
     */
    public static final String rsaDecrypt(String data) {
        try {
            // 读回秘钥
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] keyBytes = base64Decoder.decodeBuffer(privateKey);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey key = keyFactory.generatePrivate(privateKeySpec);
            // 私钥解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptResult = cipher.doFinal(base64Decoder.decodeBuffer(data));
            String finalResult = new String(decryptResult, "UTF-8");
            return finalResult;
        } catch (Exception e) {
            return null;
        }
    }

}
