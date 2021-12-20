package com;

import cn.hutool.core.lang.UUID;

public class study {

    public static void main(String[] args) {
        String res = UUID.randomUUID().toString();
        System.out.println(res);//输出UUID
    }
}
