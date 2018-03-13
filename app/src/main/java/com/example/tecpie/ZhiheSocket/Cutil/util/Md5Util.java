package com.example.tecpie.ZhiheSocket.Cutil.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class Md5Util {
	public final static String MD5(String oldStr) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		// System.out.println("原始字符串为：" + oldStr);
		try {
			// 参数oldStr表示要加密的字符串
			// 转换成字节流
			byte[] oldBytes = oldStr.getBytes();
			/*
			 * for (byte b : oldBytes) { System.out.print(b + " "); }
			 */
			// System.out.println();
			// 得到对象
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 初始化
			md.update(oldBytes);
			// 运行加密算法
			byte[] newBytes = md.digest();
			/*
			 * for (byte b : newBytes) { System.out.print(b + " "); }
			 */
			// System.out.println();
			// 构造长度为2倍的字符串
			char newStr[] = new char[32];
			// 循环进行处理
			for (int i = 0; i < 16; i++) {
				byte tmp = newBytes[i];
				newStr[2 * i] = hexDigits[tmp >>> 4 & 0xf];
				newStr[2 * i + 1] = hexDigits[tmp & 0xf];
			}
			// System.out.println(newStr);
			return new String(newStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public final static String HexStrMD5(String oldStr) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		// System.out.println("原始字符串为：" + oldStr);
		try {
			String md5Hex_str[] = new String[oldStr.length() / 2];
			byte[] md5_byte = new byte[md5Hex_str.length];
			for (int i = 0; i <= oldStr.length() - 2; i += 2) {
				String a = oldStr.substring(i, i + 2);
				md5_byte[i / 2] = (byte) Integer.parseInt(a, 16);
			}
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 初始化
			md.update(md5_byte);
			// 运行加密算法
			byte[] newBytes = md.digest();
			// 构造长度为2倍的字符串
			char newStr[] = new char[32];
			// 循环进行处理
			for (int i = 0; i < 16; i++) {
				byte tmp = newBytes[i];
				newStr[2 * i] = hexDigits[tmp >>> 4 & 0xf];
				newStr[2 * i + 1] = hexDigits[tmp & 0xf];
			}
			return new String(newStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public final static String byteMD5(byte[] str) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		// System.out.println("原始字符串为：" + oldStr);
		try {
			// String md5Hex_str[] = new String[oldStr.length() / 2];
			// byte[] md5_byte = new byte[md5Hex_str.length];
			// for (int i = 0; i <= oldStr.length() - 2; i += 2) {
			// String a = oldStr.substring(i, i + 2);
			// md5_byte[i / 2] = (byte) Integer.parseInt(a, 16);
			// }
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 初始化
			md.update(str);
			// 运行加密算法
			byte[] newBytes = md.digest();
			// 构造长度为2倍的字符串
			char newStr[] = new char[32];
			// 循环进行处理
			for (int i = 0; i < 16; i++) {
				byte tmp = newBytes[i];
				newStr[2 * i] = hexDigits[tmp >>> 4 & 0xf];
				newStr[2 * i + 1] = hexDigits[tmp & 0xf];
			}
			return new String(newStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getMd5ByFile(File file) {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static void main(String[] args) {
		// 5a5a1c000200680443000000ebf788691f1075c7ba122c3dbc49b875
		System.out.println(HexTools.Str2hexStr("hello, world"));
		System.out.println(
				Md5Util.HexStrMD5("68102200020009010300010002400040BB00" + HexTools.Str2hexStr("hello, world")));// 05a671c66aefea124cc08b76ea6d30bb
	}
}
