package com.example.tecpie.ZhiheSocket.utils;

import java.util.Date;



public class AlgorithmUtil {

	/**
	 * 
	 * @param e
	 * 待校验数据，格式：double
	 * @param flag
	 * dataWholeFlag，格式：String，288个1
	 * @param num
	 * 判断第num位是否为1
	 * @return
	 * 1则返回e，否则返回0
	 */
	public static double getValidData(double e,String flag, int num) {
		if (Integer.parseInt(String.valueOf(flag.charAt(num - 1))) == 1) {
			return e;
		} else
			return 0;
	}
	
	public enum convert {  
        eToP, pToP
  
    }  

	/**
	 * 
	 * @return 返回00:00到当前时间5分钟间隔个数+1
	 */
	public static int timeToNum() {
		int a = 0;
		double b = 0;
		try {
			b = DateUtil.getTwoHour(DateUtil.getTimeShort(),"00:00");
			a = (int) (b / 5 + 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (a > 0) {
			return a;
		} else
			return 0;
	}

	/**
	 * 
	 * @param time
	 *            时间参数，格式"yyyy-MM-dd HH:mm:ss"
	 * @return 返回00:00到时间time 5分钟间隔个数+1
	 */
	public static int timeToNum(Date time) {
		int a = 0;
		double b = 0;
		try {
			b = DateUtil.getMinute(DateUtil.getZeroTime().getTime(), time);
			a = (int) (b / 5 + 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (a > 0) {
			return a;
		} else
			return 0;
	}

	/**
	 * 
	 * @param flag
	 *            一天电量数据点个数
	 * @param e
	 *            电量
	 * @return 返回功率P
	 */
	public static double eToP(short flag, double e) {
		return 60 *flag* e / 1440;
	}

	/**
	 * 
	 * @param flag
	 * 数据完整度校验字符串DataWholeFlag，一共288位，每位为1或0
	 * @param num
	 * 判断第num-1位是否为1
	 * @return
	 * 数据有效返回1，否则返回0
	 */
	public static boolean flagIsValid(String flag, int num) {
		if (Integer.parseInt(String.valueOf(flag.charAt(num - 1))) == 1) {
			return true;
		} else
			return false;
	}
	public void setValueScope(Object obj1,Object obj2,convert c){
		switch(c){
		case eToP:{
			int num = timeToNum();
			
		}
		case pToP:{
			
		}
		}
	}
}