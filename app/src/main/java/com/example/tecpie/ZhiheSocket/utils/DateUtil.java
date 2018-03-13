package com.example.tecpie.ZhiheSocket.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 公共日期方法处理类
 * 
 * @author HuaJian
 */
public class DateUtil {

    /**
     * @description 将日期从String格式转换成Date格式
     * @return Date
     * @date 20140411
     * @author wxz
     */
    public static SimpleDateFormat getFormater(String part) {
	if (null == part) {
	    part = "yyyy-MM-dd";
	}
	SimpleDateFormat format = new SimpleDateFormat(part);
	return format;
    }

    /**
     * @description 将日期从String格式转换成Date格式
     * @param dateStr
     * @return Date
     * @date 20140411
     * @author wxz
     */
    public static Date formatDate(String dateStr) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Date date = null;
	if (dateStr != null && !"".equals(dateStr)) {
	    try {
		date = format.parse(dateStr);
	    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return date;
    }

    /**
     * date转化为String（格式yyyy-MM-dd），如果有错返回当日信息
     * 
     * @param date
     * @return String
     */
    public static String dateToString(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	try {
	    return format.format(date);
	} catch (Exception ex) {
	    return format.format(new Date());
	}
    }

    public static String dateToTimeShortNoSecString(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	try {
	    return format.format(date);
	} catch (Exception ex) {
	    return format.format(new Date());
	}
    }

    /**
     * date转化为String（格式yyyy-MM-dd HH:mm:ss），如果有错返回当日信息
     * 
     * @param date
     * @return String
     */
    public static String dateToTSString(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	try {
	    return format.format(date);
	} catch (Exception ex) {
	    return format.format(new Date());
	}
    }

    /**
     * date转化为String（格式yyyy.MM.dd），如果有错返回当日信息
     * 
     * @param date
     * @return String
     */
    public static String dateToString4Dot(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
	try {
	    return format.format(date);
	} catch (Exception ex) {
	    return format.format(new Date());
	}
    }

    public static String dateToTimeShortString(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	try {
	    return format.format(date);
	} catch (Exception ex) {
	    return format.format(new Date());
	}
    }

    /**
     * 格式化日期（格式yyyyMMdd），如果有错返回当日信息
     * 
     * @param date
     * @return String
     */
    public static String dateToStringNoV(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	try {
	    return format.format(date);
	} catch (Exception ex) {
	    return format.format(new Date());
	}
    }

    /**
     * 获取两个日期的相差天数
     * 
     * @param date1
     * @param date2
     * @return int
     * @throws Exception
     */
    public static int getInterval(Date date1, Date date2) {
	if (date2.after(date1)) {
	    Date cal = date1;
	    date1 = date2;
	    date2 = cal;
	}
	long a1 = date1.getTime();
	long a2 = date2.getTime();
	long a3 = a1 - a2;
	return (int) (a3 / (1000 * 60 * 60 * 24));
    }

    /**
     * 计算2个日期之间的月差
     */
    public static int getInterValMonth(Date date1, Date date2) {
	long a1 = date1.getTime();
	long a2 = date2.getTime();
	long a3 = a1 - a2;
	return (int) (a3 / (1000 * 60 * 24 * 60) / 30);
    }

    /**
     * 返回下个月（格式yyyy-MM）
     * 
     * @param date
     * @return String
     */
    public static String afterMonth(String date) {
	String str[];
	String chgDate = "";
	if (null != date) {
	    str = date.split("-");
	    if (null != str && 2 <= str.length) {
		if ("12".equals(str[1])) {
		    chgDate = (Integer.valueOf(str[0]).intValue() + 1) + "-01";
		} else {
		    chgDate = str[0] + "-"
			    + (Integer.valueOf(str[1]).intValue() + 1);
		}
	    }
	}
	return chgDate;
    }

    /**
     * 返回下i个月（格式yyyy-MM）
     * 
     * @param date
     * @return String
     */
    public static String afterMonth(int year, int month, int i) {
	String chgDate = "";
	if (i <= 0) {
	    chgDate = year + "-" + month;
	} else {
	    int yearInte = i / 12;
	    int monthInte = i % 12;
	    if ((month + monthInte) > 12) {
		chgDate = (year + yearInte + 1) + "-"
			+ (month + monthInte - 12);
	    } else {
		chgDate = (year + yearInte) + "-" + (month + monthInte);
	    }
	}
	return chgDate;
    }

    /**
     * 返回下i个月（格式yyyy-MM）
     * 
     * @param date
     * @return String
     */
    public static String afterMonth(Date date, int i) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	String chgDate = "";
	if (i <= 0) {
	    chgDate = year + "-" + month;
	} else {
	    int yearInte = i / 12;
	    int monthInte = i % 12;
	    if ((month + monthInte) > 12) {
		chgDate = (year + yearInte + 1) + "-"
			+ (month + monthInte - 12);
	    } else {
		chgDate = (year + yearInte) + "-" + (month + monthInte);
	    }
	}
	return chgDate;
    }

    /**
     * 返回下i个月（格式yyyy-MM）
     * 
     * @param date
     * @return String
     */
    public static String afterMonth2(Date date, int i) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	String chgDate = "";
	if (i <= 0) {
	    chgDate = year + "-" + month;
	} else {
	    int yearInte = i / 12;
	    int monthInte = i % 12;
	    if ((month + monthInte) > 12) {
		if ((month + monthInte - 12) < 10) {
		    chgDate = (year + yearInte + 1) + "-0"
			    + (month + monthInte - 12);
		} else {
		    chgDate = (year + yearInte + 1) + "-"
			    + (month + monthInte - 12);
		}
	    } else {
		if (month + monthInte < 10) {
		    chgDate = (year + yearInte) + "-0" + (month + monthInte);
		} else {
		    chgDate = (year + yearInte) + "-" + (month + monthInte);
		}

	    }
	}
	return chgDate;
    }

    /**
     * 返回date时间加i个月（格式yyyy-MM-DD）
     * 
     * @param date
     *            时间
     * @param i
     *            月份间隔
     * @return String
     */
    public static String afterDateMonth(Date date, int i) {
	// 判断间隔时间不可超过12 超过返回date
	if (Math.abs(i) > 12) {
	    return dateToString(date);
	}
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.MONTH, i);
	/*
	 * int year = cal.get(Calendar.YEAR); int month =
	 * cal.get(Calendar.MONTH)+1; int day = cal.get(Calendar.DAY_OF_MONTH);
	 * String chgDate = ""; //判断月份有没有超限，超限 int yearInte = i/12; int
	 * monthInte = i%12; if((month+monthInte) > 12){ //添加月份跨度超过12各月超出本年
	 * cal.set(Calendar.YEAR,year+2);
	 * cal.set(Calendar.MONTH,month+monthInte-12);
	 * cal.set(Calendar.DAY_OF_MONTH,day); chgDate =
	 * dateToString(cal.getTime()); }else if((month+monthInte) < 0){
	 * //添加月份正常在本年内 cal.set(Calendar.YEAR,year-1);
	 * cal.set(Calendar.MONTH,month+monthInte-12);
	 * cal.set(Calendar.DAY_OF_MONTH,day); chgDate =
	 * dateToString(cal.getTime()); }else { //添加月份为负值返回去年
	 * cal.set(Calendar.YEAR,year+1);
	 * cal.set(Calendar.MONTH,month+monthInte-12);
	 * cal.set(Calendar.DAY_OF_MONTH,day); chgDate =
	 * dateToString(cal.getTime()); }
	 */
	return dateToString(cal.getTime());
    }

    /**
     * 返回同期（即去年同日期）
     * 
     * @param date
     * @return String
     */
    public static String beforeYear(String date) {
	String str[];
	String chgDate = "";
	if (null != date) {
	    str = date.split("-");
	    if (null != str && 3 <= str.length) {
		chgDate = (Integer.valueOf(str[0]).intValue() - 1) + "-"
			+ str[1] + "-" + str[2];
	    }
	}
	return chgDate;
    }

    /**
     * date往前推一定天数，然后转化为String
     * 
     * @param date
     * @return String
     */
    public static String beforeDate(Date date, int i) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.DATE, -i);
	return dateToString(cal.getTime());
    }

    /**
     * date往后推一定天数，然后转化为String（格式yyyy-MM-dd）
     * 
     * @param date
     * @return String
     */
    public static String afterDate(Date date, int i) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.DATE, i);
	return dateToString(cal.getTime());
    }

    /**
     * date往后推一定天数
     * 
     * @param date
     * @return date
     */
    public static Date afterDateNC(Date date, int i) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.DATE, i);
	return cal.getTime();
    }

    /**
     * date往后推一定天数，然后转化为String（格式yyyy.MM.dd）
     * 
     * @param date
     * @return String
     */
    public static String afterDate4Dot(Date date, int i) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.DATE, i);
	return dateToString4Dot(cal.getTime());
    }

    /**
     * date往后推一定天数，然后转化为String（格式yyyyMMdd）
     * 
     * @param date
     * @return String
     */
    public static String afterDateNoV(Date date, int i) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.DATE, i);
	return dateToStringNoV(cal.getTime());
    }

    /**
     * 格式化日期（年月），如果有错返回当月信息
     * 
     * @param date
     * @return String
     */
    public static String dateToStringByMonth(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
	try {
	    return format.format(date);
	} catch (Exception ex) {
	    return format.format(new Date());
	}
    }

    /**
     * timeStamp转化为String
     * 
     * @return String
     */
    public static String timeStampToStr(Timestamp time) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	try {
	    return format.format(time);
	} catch (Exception ex) {
	    return "";
	}
    }

    /**
     * timeStamp转化为String
     * 
     * @return String
     */
    public static String timeStampToShortStr(Timestamp time) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	try {
	    return format.format(time);
	} catch (Exception ex) {
	    return "";
	}
    }

    /**
     * date转化为String，主要是返回查询日的所在月份的第一天，如果有错返回当月首日
     * 
     * @param date
     * @return String
     */
    public static String fristDayByDate(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
	try {
	    return format.format(date) + "-01";
	} catch (Exception ex) {
	    return format.format(new Date()) + "-01";
	}
    }

    /**
     * 得到输入月份的下个月第一天
     * 
     * @param date
     *            格式yyyy-MM或yyyy-MM-dd
     * @return String
     */
    public static String getNextMonthFirstDay(String date) {
	String[] str = date.split("-");
	if (null != str && str.length >= 2) {
	    int year = Integer.valueOf(str[0]).intValue();
	    int month = Integer.valueOf(str[1]).intValue();
	    if (month < 12) {
		if (month < 9) {
		    return year + "-0" + (month + 1) + "-01";
		} else {
		    return year + "-" + (month + 1) + "-01";
		}
	    } else {
		return (year + 1) + "-" + "01-01";
	    }
	} else {
	    return "";
	}
    }

    /**
     * date转化为数据库保存格式的date，如果有错返回当日信息
     * 
     * @param date
     * @return java.sql.Date
     */
    public static java.sql.Date dateToSqlDate(Date date) {
	if (null == date) {
	    return new java.sql.Date(new Date().getTime());
	}
	try {
	    return (new java.sql.Date(date.getTime()));
	} catch (Exception ex) {
	    return new java.sql.Date(new Date().getTime());
	}
    }

    /**
     * 获取一个月多少天
     * 
     * @param date
     * @return java.sql.Date
     */
    @SuppressWarnings("static-access")
    public static int getDaysForMonth(Date date) {
	if (null == date) {
	    return 31;
	}
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	return cal.getActualMaximum(cal.DAY_OF_MONTH);
    }

    /**
     * 返回两个日期相差的天数(含结束日期)
     * 
     * @param beginDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return 返回天数(含结束日期)
     * @throws Exception
     */
    public static int getDay(String beginDate, String endDate) throws Exception {
	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
	Date d1 = sim.parse(beginDate);
	Date d2 = sim.parse(endDate);
	return (int) ((d2.getTime() - d1.getTime()) / (3600L * 1000 * 24)) + 1;
    }

    /**
     * 返回两个日期相差的天数
     * 
     * @param beginDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return 返回天数
     * @throws Exception
     */
    public static int getDay(Date beginDate, Date endDate) throws Exception {
	return (int) ((endDate.getTime() - beginDate.getTime()) / (3600L * 1000 * 24)) + 1;
    }

    /**
     * string转化为date（格式yyyy-MM-dd），如果有错返回当日信息
     * 
     * @return date
     */
    public static Date stringToDate(String date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	try {
	    return format.parse(date);
	} catch (Exception ex) {
	    return new Date();
	}
    }

    /**
     * 获取当天零点时间
     * 
     * @return 当天零点时间
     */
    public static Calendar getZeroTime() {
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND, 0);
	return cal;
    }

    /**
     * 切割时间段字符串
     * 
     * @return
     * @author chen ming
     * @throws ParseException
     */
    public static List<String> splitPeriod(String drPeriod) {
	List<String> dateList = new ArrayList<String>();
	String[] period = drPeriod.split("--");
	dateList.add(period[0]);
	dateList.add(period[1]);
	return dateList;
    }

    /**
     * 获取当前时间在对应flag的位置
     * 
     * @param dataPointFlag
     *            时间间隔
     * @return
     * @author chen ming
     */
    public static int getTimeIndex(int dataPointFlag) {
	String nowTime = DateUtil.dateToTimeShortString(new Date());
	String[] timeArray = nowTime.split(":");
	int index = (int) ((Integer.parseInt(timeArray[0]) * 60 + Integer
		.parseInt(timeArray[1])) / 5) + 1;

	if (!"4".equals(dataPointFlag + "")) {
	    Map<String, Integer> indexMap = ArithmeticUtil.adjustIndex(
		    dataPointFlag + "", 1, index);
	    index = indexMap.get("endIndex");
	}

	return index;
    }

    /**
     * 根据传入的时间判定时间下标
     * <p>请记述函数的功能概要。</p>
     *
     * @param time
     * @return
     */
    public static int getIndexbyTime(String time) {

	String[] timeArray = time.split(":");
	int index = (int) ((Integer.parseInt(timeArray[0]) * 60 + Integer
		.parseInt(timeArray[1])) / 5) + 1;

	return index;
    }

    /**
     * 获取对应的时间
     * 
     * @param timeIndex
     *            时间点
     * @author chen ming
     * @return
     */
    public static String getTimeByIndex(int timeIndex) {
	String timeM = ((timeIndex - 1) * 5) % 60 + "";
	if (Integer.parseInt(timeM) < 10) {
	    timeM = "0" + ((timeIndex - 1) * 5) % 60;
	}
	String timeH = ((timeIndex - 1) * 5) / 60 + "";
	if (((timeIndex - 1) * 5) / 60 < 10) {
	    timeH = "0" + ((timeIndex - 1) * 5) / 60;
	}
	String timeData = timeH + ":" + timeM;

	return timeData;
    }
    
    public static String getTimeByIndex1(int timeIndex) {
    	String timeM = ((timeIndex - 1) * 5) % 60 + "";
    	/*if (Integer.parseInt(timeM) < 10) {
    	    timeM = "0" + ((timeIndex - 1) * 5) % 60;
    	}*/
    	String timeH = ((timeIndex - 1) * 5) / 60 +1+ "";
    	/*if (((timeIndex - 1) * 5) / 60 < 10) {
    	    timeH = "0" + ((timeIndex - 1) * 5) / 60;
    	}*/
    	String timeData = timeH;

    	return timeData;
        }

    /**
     * @return 返回当前时间的HH:mm
     */
    public static String getTimeShort() {
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
	Date currentTime = new Date();
	String dateString = formatter.format(currentTime);
	return dateString;
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，st1-st2返回整型的分钟
     */
    public static int getTwoHour(String st1, String st2) {
	String[] kk = null;
	String[] jj = null;
	kk = st1.split(":");
	jj = st2.split(":");
	if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
	    return 0;
	else {
	    int y = Integer.parseInt(kk[0]) * 60 + Integer.parseInt(kk[1]);
	    int u = Integer.parseInt(jj[0]) * 60 + Integer.parseInt(jj[1]);
	    if ((y - u) > 0)
		return y - u;
	    else
		return 0;
	}
    }

    /**
     * 返回两个日期相差的分钟数
     * 
     * @param beginDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @throws Exception
     */
    public static Double getMinute(Date beginDate, Date endDate)
	    throws Exception {
	if ((endDate.getTime() - beginDate.getTime()) % (1000 * 60) == 0) {
	    return (double) ((endDate.getTime() - beginDate.getTime()) / (1000 * 60));
	} else
	    return (double) ((endDate.getTime() - beginDate.getTime()) / (1000 * 60)) + 1;
    }

}
