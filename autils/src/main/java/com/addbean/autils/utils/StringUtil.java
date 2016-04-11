package com.addbean.autils.utils;


import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 判断是否是邮邮箱；
	 * 
	 * @param strEmail
	 * @return
	 */
	public static boolean isEmail(String strEmail) {
		String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

	/**
	 * URL检查<br>
	 * <br>
	 * 
	 * @param pInput
	 *            要检查的字符串<br>
	 * @return boolean 返回检查结果<br>
	 */
	public static boolean isUrl(String pInput) {
		if (pInput == null) {
			return false;
		}
		// String regEx = "^(http|https|ftp)//://([a-zA-Z0-9//.//-]+(//:[a-zA-"
		// + "Z0-9//.&%//$//-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
		// + "2}|[1-9]{1}[0-9]{1}|[1-9])//.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
		// + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-4][0-9]|"
		// + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-"
		// + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
		// + "-9//-]+//.)*[a-zA-Z0-9//-]+//.[a-zA-Z]{2,4})(//:[0-9]+)?(/"
		// + "[^/][a-zA-Z0-9//.//,//?//'///////+&%//$//=~_//-@]*)*$";
		// Pattern p = Pattern.compile(regEx);
		// Matcher matcher = p.matcher(pInput);
		// return matcher.matches();
		return pInput.contains("http");
	}

	public static Boolean isNumberStr(String str) {
		if (TextUtils.isEmpty(str))
			return true;
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence) str);
		return matcher.matches();
	}

	/**
	 * 右到左每隔几位位添加一个分隔符 将字符串每隔几个分割， 列如：String str1 = "10000000000000"; 每隔三位以逗号分割
	 * 输出10,000,000,000,000
	 * 
	 * @param res
	 *            字符串分割
	 * @param splitString
	 *            分割字符串
	 * @param splictNumber
	 *            每隔几个字符分割
	 * @return 如果分割成功返回字符串，分割失败返回NULL , 参数错误返回NULL
	 */
	public static String stringSplitConvert(String res, String splitString, int splictNumber) {

		if (!TextUtils.isEmpty(res) && !TextUtils.isEmpty(splitString) && splictNumber > 0) {
			try {

				String temp = new StringBuilder(res).reverse().toString();
				String str2 = "";

				for (int i = 0; i < temp.length(); i++) {
					if (i * splictNumber + splictNumber > temp.length()) {
						str2 += temp.substring(i * splictNumber, temp.length());
						break;
					}
					str2 += temp.substring(i * splictNumber, i * splictNumber + splictNumber) + splitString;
				}
				if (str2.endsWith(splitString)) {
					str2 = str2.substring(0, str2.length() - splitString.length());
				}
				return new StringBuilder(str2).reverse().toString();
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
}
