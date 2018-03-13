package com.example.tecpie.ZhiheSocket.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 公共计算方法处理类，目前只处理Double类型
 * @author 姜炜超,chenming
 */
public class ArithmeticUtil {
	/**
	 * 两个Double数相减
     * @param v1
     * @param v2
     * @return Double
    */
    public static Double sub(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
	}
    /**
     * 两个Double数相加
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double add(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }

    
    /**
     * 两个Double数相除，并保留scale位小数
     * @param v1
     * @param v2
     * @param scale
     * @return Double
     */
    public static Double div(Double v1,Double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
            "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 两个Double数相乘
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double multi(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }
    
    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
   }
    /**
     * 根据提供的时间精度数据，调整起始下标，结束下标到精确位置
     * @param dataPointFlag
     * @param startIndex
     * @param endIndex
     * @return map
     */
    public static Map<String,Integer> adjustIndex(String dataPointFlag,int startIndex,int endIndex){
    	Map<String,Integer> index = new HashMap<String,Integer>();
    	// 根据数据点标志间隔将开始和结束时间的序列进位，调整
		if ("1".equals(dataPointFlag)) {
			int i = 0;
			while ((3 * i + 1) < startIndex) {
				i++;
				if((3 * i + 1) >= startIndex){
					startIndex = 3 * i + 1;
				}
			}
			i = 0;
			while ((3 * i + 1) <= endIndex) {
				int count = i + 1;
				if ((count * 3 + 1) > endIndex) {
					endIndex = 3 * i + 1;
					break;
				}
				i++;
			}
		} else if ("2".equals(dataPointFlag)) {
			int i = 0;
			while ((6 * i + 1) < startIndex) {
				i++;
				if((6 * i + 1) >= startIndex){
					startIndex = 6 * i + 1;
					break;
				}
			}
			i = 0;
			while ((6 * i + 1) <= endIndex) {
				int count = i + 1;
				if ((count * 6 + 1) > endIndex) {
					endIndex = 6 * i + 1;
					break;
				}
				i++;
			}
		} else if ("3".equals(dataPointFlag)) {
			int i = 0;
			while ((12 * i + 1) < startIndex) {
				i++;
				if((12 * i + 1) >= startIndex){
					startIndex = 12 * i + 1;
					break;
				}
			}
			i = 0;
			while ((12 * i + 1) <= endIndex) {
				int count = i + 1;
				if ((count * 12 + 1) > endIndex) {
					endIndex = 12 * i + 1;
					break;
				}
				i++;
			}
		}
		index.put("endIndex",endIndex);
		index.put("startIndex",startIndex);
    	return index;
    }
    
    /**
     * 找出集合中的最大值，工具方法
     * 
     * @param list
     *            输入集合
     * @return 最大值
     */
    public static double findMaxData(List<Object> list) {
	double data = 0;
	for (int i = 0; i < list.size(); i++) {
	    if (list.get(i) != null) {
		double tempData = Double.parseDouble(list.get(i).toString());
		if ( tempData >= data) {
		    data = tempData;
		}
	    }
	}
	return data;
    }
    
    /**
     * 将传入的集合中的数据除以1000
     * 
     * @param list
     *            传入的集合
     * @return 除以1000之后的集合
     */
    public static List<Object> getLowerDataList(List<Object> list) {
	List<Object> tempList = new ArrayList<Object>();
	for (int i = 0; i < list.size(); i++) {
	    if (list.get(i) == null) {
		tempList.add(null);
	    } else {
		tempList.add(Double.parseDouble(list.get(i).toString()) /1000);
	    }
	}
	return tempList;
    }
    
    /**
     * 将传入的集合中的数据除以1000
     * 
     * @param list
     *            传入的集合
     * @return 除以1000之后的集合
     */
    public static List<Object> getLowerDataList2(List<Object> list) {
	List<Object> tempList = new ArrayList<Object>();
	for (int i = 0; i < list.size(); i++) {
	    if ("-".equals(list.get(i))) {
		tempList.add("-");
	    } else {
		tempList.add(Double.parseDouble(list.get(i).toString()) / 1000);
	    }
	}
	return tempList;
    }
}
