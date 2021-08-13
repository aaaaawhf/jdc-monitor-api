package com.g1335333249.jdc.monitor.api;

import com.baomidou.mybatisplus.core.toolkit.AES;

/**
 * @Author: guanpeng
 * @Date: Create at 2021/8/13 16:25
 * @Description:
 * @Modified By:
 */
public class MybatisAes {
    public static void main(String[] args) {
        // 生成 16 位随机 AES 密钥
        String randomKey = AES.generateRandomKey();
        String url = "jdbc:mysql://www.jsj1304.com:3399/jdc_monitor?useUnicode=true&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT%2b8&useSSL=false";
        String username = "root";
        String password = "QQ251014qq";

        // 随机密钥加密
        String urlAes = AES.encrypt(url, randomKey);
        String usernameAes = AES.encrypt(username, randomKey);
        String passwordAes = AES.encrypt(password, randomKey);
        System.out.println(randomKey);
        System.out.println(urlAes);
        System.out.println(usernameAes);
        System.out.println(passwordAes);


    }
}
