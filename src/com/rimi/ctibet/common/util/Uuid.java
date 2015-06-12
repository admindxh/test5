package com.rimi.ctibet.common.util;

import java.util.UUID;

public class Uuid {
	public static String uuid(){
		return UUID.randomUUID().toString();
	}
	public static long longUuid(){
		return UUID.randomUUID().getMostSignificantBits();
	}
	public static String replaceLine(String str){
		return str.replaceAll("-", "");
	}
	public static void main(String[] args) {
		////System.out.println(replaceLine("ti-huan-diao-heng-xian-jiu-neng-yi-qi-le"));
        for (int i = 0; i < 10; i++) {
            //System.out.println(uuid());
        }
	}
}
