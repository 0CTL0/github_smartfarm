package com.smartfarm.base.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class AESUtil {


    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";
    /**
     * 生成key
     */
    //微信支付API密钥设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
    private static String paySign = "sgxyct13326529222sgtygg8622220TY";  //西窑彩田
   // private static String paySign = "2IBtBXdrqC3kCBs4gaceL7nl2nnFadQv";  //测试
    //对商户key做md5，得到32位小写key*
    private static SecretKeySpec key = new SecretKeySpec(MD5Util.MD5Encode(paySign, "UTF-8").toLowerCase().getBytes(), ALGORITHM);

    static {

    }

    /**
     * AES加密setSsslcsf
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64Util.encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     * <p>
     * （1）对加密串A做base64解码，得到加密串B
     * （2）用key*对加密串B做AES-256-ECB解密（PKCS7Padding）
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64Util.decode(base64Data)));
    }

    //测试
    public static void main(String[] args) throws Exception {
        //微信返回的加密信息req_info
//        String A = "m4NnwrtY0jhpDgNp65H1V/0OWMtSoTYhhY89MHbflhmnaHq9ZKjx9ABq6Jpg4SccA876HVy7J9P85NpdvCMNGInZ4fANDRE+YfZe4HeF+bbFj6JETcEFPpE9YW+bTbC0D+gl/otScJfvB2QUK7+EeBGPHN1EWX9zbr2Gw6AUaORdFk3mGxV5dtjuwWQrv5juzkXDs33Z2dUMslO+i3j0cTDHqwS4hptx2j6h2HvzgzltFbjo7nysU+4rArqJvrGW/9r18e1St9XgG21NALqixfaSmqetOR4zLVL4/+z3CEz8cg5r+/4GUOTf3XFmLCZ/wEkRQhKRNVibG0NFfiG3KnqArMJ/dheQHCd7qL+XX/ZV6tj8RLMgL7R6hOiR03Ljyikdxq9M3K9CTYgf03pHJd3geXX1LgXrLxc1flL6NW+zD3ZayGYpr1WpLsSMG7z8W5j1pme6cRj3n2+CwSFnOnOkxaFuLKoJAJIqM3gbC0eN++vY73RKphlI4zZqg6o5s3MXI6ju1yoi/ZQ+XbTg2JttsdbU0aySernKwkt0rYMf0j/Mcvo2axgHbI3w/iTm141WxHUjkQ+ga2X1pOWdGifGhSmMP8oGaA/WD5MAsK1qXX0eFvUWS/PTauCSTWq5Cmr8loA/KL3jgvB0nyR4mfccB+tPy4Ny7kzOlr/VNeb0ULf96R0AWFWCtdt8AmujAP0DYiM5FSmYLI0XRhpSDjnEbBM8+isNE1GlAVR3NzzemwQORihScovpAktbRSN/d3N+NgTjSoVDiJvCOxCs3thX9qt9iwYbA+/X/gv8lza2FZyIzwkQxGRcYl8JWKpXzNW8EWUNVnSLdHvQttDeV3CvgP/x579RGd6whyFYS6AaI0qw7oTjCFh2EHS/VzGvFuv166ZlVIJ4MNvg79O9h63ZOSE1LzVqEsVh8fDCfM2GgJ9aUdl95Djgunit4yIZOdoigR3f/BEHKrYCEham11rYohaAXs4XAXWihsV3WD5j4G/P+txvjAwujvf4HDwzHgFsmSml013U2mUiy+v4zw==";
//        System.out.println(AESUtil.decryptData(A));
//        System.out.println("\n");
//        //加密
//        String str = "<root>"+
//                "<out_refund_no><![CDATA[2531340110812300]]></out_refund_no>"+
//                "<out_trade_no><![CDATA[2531340110812100]]></out_trade_no>"+
//                "<refund_account><![CDATA[REFUND_SOURCE_RECHARGE_FUNDS]]></refund_account>"+
//                "<refund_fee><![CDATA[1]]></refund_fee>"+
//                "<refund_id><![CDATA[50000505542018011003064518841]]></refund_id>"+
//                "<refund_recv_accout><![CDATA[支付用户零钱]]></refund_recv_accout>"+
//                "<refund_request_source><![CDATA[API]]></refund_request_source>"+
//                "<refund_status><![CDATA[SUCCESS]]></refund_status>"+
//                "<settlement_refund_fee><![CDATA[1]]></settlement_refund_fee>"+
//                "<settlement_total_fee><![CDATA[1]]></settlement_total_fee>"+
//                "<success_time><![CDATA[2018-01-10 10:31:24]]></success_time>"+
//                "<total_fee><![CDATA[1]]></total_fee>"+
//                "<transaction_id><![CDATA[4200000052201801101409025381]]></transaction_id>"+
//                "</root>";
//        System.out.println(encryptData(str));


    }
}