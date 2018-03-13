package com.example.tecpie.ZhiheSocket.Cutil.util;

public class HexTools {
	// 字符串左边补0直到长度为i
	public static String obox(String s, int i) {
		String ss = s;
		while (ss.length() < i) {
			ss = "0" + ss;
		}
		return ss;
	}

	// 字符串右边补0直到长度为i
	public static String boxo(String s, int i) {
		String ss = s;
		while (ss.length() < i) {
			ss += "0";
		}
		return ss;
	}

	// 将字符串变成16进制字符串
	public static String Str2hexStr(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			sb.append(obox(Integer.toHexString(s.charAt(i)), 2));
		}
		return sb.toString();
	}

	// 将16进制字符串变成字符串
	public static String hexStr2Str(String s) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		int length = s.length();
		while (index + 4 <= length) {
			String sh = s.substring(index, index + 4);
			sb.append((char) Integer.parseInt(sh, 16));
			index += 4;
		}
		if (sb.charAt(sb.length() - 1) == 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	// String数组转换为char数组
	public static char[] Ss2Cs(String[] s) {
		char[] result = new char[s.length];
		for (int i = 0; i < s.length; i++) {
			result[i] = (char) Integer.parseInt(s[i], 16);
		}
		return result;
	}

	// String转换为char数组
	public static char[] Str2Cs(String s) {
		char[] result = new char[s.length() / 2];
		for (int i = 0; i < s.length() / 2; i++) {
			StringBuffer sb = new StringBuffer();// (char)s.charAt(i)+s.charAt(i+1);
			sb.append(s.charAt(i * 2));
			sb.append(s.charAt(i * 2 + 1));
			result[i] = (char) Integer.parseInt(sb.toString(), 16);
		}
		return result;
	}

	// hexString转换为数组
	public static char[] hexStr2Cs(String s) {
		// System.out.println("hexStr:" + s);
		char[] result = new char[s.length() / 2];
		for (int i = 0; i < s.length() / 2; i++) {
			StringBuffer sb = new StringBuffer();// (char)s.charAt(i)+s.charAt(i+1);
			sb.append(s.charAt(i * 2));
			sb.append(s.charAt(i * 2 + 1));
			result[i] = (char) Integer.parseInt(sb.toString(), 16);
		}
		return result;
	}

	// char数组转换为String数组
	public static String[] Cs2Ss(char[] s) {
		String[] result = new String[s.length];
		for (int i = 0; i < s.length; i++) {
			result[i] = Integer.toHexString(s[i]);
		}
		return result;
	}

	// char数组转换为hexString
	public static String Cs2hexStr(char[] s) {
		StringBuffer sb = new StringBuffer();
		// String[] result = new String[s.length];
		for (int i = 0; i < s.length; i++) {
			sb.append(obox(Integer.toHexString(s[i]), 2));
		}
		return sb.toString();
	}

	// long转换为hexString
	public static String long2hexStr(long lo) {
		// String s = Long.toHexString(lo);
		// s =
		return Long.toHexString(lo);
	}

	// longs数组转换为hexString
	public static String longs2hexStr(long[] lo) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lo.length; i++) {
			sb.append(Long.toHexString(lo[0]));
			sb.append(" ");
		}
		return sb.toString();
	}

	// hexString转换为long
	public static long hexStr2long(String s) {
		return Long.parseLong(s, 16);
	}

	// hexString转换为long数组
	public static long[] hexStr2longs(String s) {
		String[] ss = s.split(" ");
		long[] ls = new long[ss.length];
		for (int i = 0; i < ls.length; i++) {
			ls[i] = Long.parseLong(ss[i], 16);
		}
		return ls;
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static String BytesHexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toLowerCase();
		}
		return ret;
	}

	public static byte[] HexStringtoBytes(String hex) {
		String md5Hex_str[] = new String[hex.length() / 2];
		byte[] md5_byte = new byte[md5Hex_str.length];
		for (int i = 0; i <= hex.length() - 2; i += 2) {
			String a = hex.substring(i, i + 2);
			md5_byte[i / 2] = (byte) Integer.parseInt(a, 16);
		}
		return md5_byte;
	}

	public static String hexToString(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
						i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "ASCII");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}


	public static float bytesToFloat(byte[] data) {// 解析4个字节中的数据，按照IEEE754的标准  
        int s = 0;// 浮点数的符号  
        float f = 0;// 浮点数  
        int e = 0;// 指数  
        if ((data[3] & 0xff) >= 128) {// 求s  
            s = -1;  
        } else {  
            s = 1;  
        }  
        int temp = 0;// 指数位的最后一位  
        if ((data[2] & 0xff) >= 128) {  
            temp = 1;  
        } else  
            temp = 0;  
        e = ((data[3] & 0xff) % 128) * 2 + temp;// 求e  
        // f=((data[2]&0xff)-temp*128+128)/128+(data[1]&0xff)/(128*256)+(data[0]&0xff)/(128*256*256);  
        float[] data2 = new float[3];  
        data2[0] = data[0] & 0xff;  
        data2[1] = data[1] & 0xff;  
        data2[2] = data[2] & 0xff;  
        f = (data2[2] - temp * 128 + 128) / 128 + data2[1] / (128 * 256)  
                + data2[0] / (128 * 256 * 256);  
        float result = 0;  
        if (e == 0 && f != 0) {// 次正规数  
            result = (float) (s * (f - 1) * Math.pow(2, -126));  
            return result;  
        }  
        if (e == 0 && f == 0) {// 有符号的0  
            result = (float) 0.0;  
            return result;  
        }  
        if (s == 0 && e == 255 && f == 0) {// 正无穷大  
            result = (float) 1111.11;  
            return result;  
        }  
        if (s == 1 && e == 255 && f == 0) {// 负无穷大  
            result = (float) -1111.11;  
            return result;  
        } else {  
            result = (float) (s * f * Math.pow(2, e - 127));  
            return result;  
        }  
  
    }  
}