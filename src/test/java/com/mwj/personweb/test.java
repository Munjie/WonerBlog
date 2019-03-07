package com.mwj.personweb;

import java.util.Random;

/**
 * @Author: 母哥 @Date: 2019-03-07 15:49 @Version 1.0
 */
public class test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        String strAll = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 6; i++) {
            int f = (int) (Math.random() * 62);
            sb.append(strAll.charAt(f));
        }
        System.out.println(sb);
    }
}
