package com.forusoft.framework.common.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;

/**
 * 
 * @author gudong
 *
 */
public class StringUtil {
  private static Log log = LogFactory.getLog(StringUtil.class);

  /**
   * encode string to new string using md5 encryption by Base64<br/> return
   * the source data if failed.
   * 
   * @author GuDong
   * @param str
   * @return encode string
   * @createDate 2008-12-11
   */
  public static String encodeMd5ByBase64(String str) {
    try {
      if (StringUtils.isBlank(str))
        return null;
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      byte[] digest = messageDigest.digest(str.getBytes("utf-8"));
      return new String(Base64.encodeBase64(digest));
    } catch (Exception e) {
      log.error(e);
      return str;
    }
  }

  /**
   * encode string to new string using md5 encryption by Hex <br/> return the
   * source data if failed.
   * 
   * @author GuDong
   * @param str
   * @return new string
   * @createDate 2008-12-11
   */
  public static String encodeMd5ByHex(String str) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      byte[] digest = messageDigest.digest(str.getBytes("utf-8"));
      return new String(Hex.encodeHex(digest));
    } catch (Exception e) {
      log.error(e);
      return str;
    }
  }

  /**
   * BASE64 encode
   * 
   * @param s
   * @return
   */
  public static String encodeByBASE64(String s) {
    if (s == null)
      return null;
    return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
  }

  /**
   * BASE64 decode
   * 
   * @param s
   * @return
   */
  public static String decodeByBASE64(String s) {
    if (s == null)
      return null;
    BASE64Decoder decoder = new BASE64Decoder();
    try {
      byte[] b = decoder.decodeBuffer(s);
      return new String(b);
    } catch (Exception e) {
      log.error(e);
      return null;
    }
  }

  /**
   * Upper substring assigned length.
   * 
   * @param s
   * @param start
   * @param end
   * @return
   */
  public static String toUpperCase(String s, int start, int end) {
    int length = s.length();
    if (length >= end) {
      s = s.substring(0, start) + s.substring(start, end).toUpperCase()
          + s.substring(end, length);
    }
    return s;
  }

  /**
   * Lower substring assigned length.
   * 
   * @param s
   * @param start
   * @param end
   * @return
   */
  public static String toLowerCase(String s, int start, int end) {
    int length = s.length();
    if (length >= end) {
      s = s.substring(0, start) + s.substring(start, end).toLowerCase()
          + s.substring(end, length);
    }
    return s;
  }

  /**
   * Lower the first char.
   * 
   * @param s
   * @return
   */
  public static String toLowerCaseFirst(String s) {
    return toLowerCase(StringUtils.defaultString(s), 0, 1);
  }

  /**
   * Upper the first char.
   * 
   * @param s
   * @return
   */
  public static String toUpperCaseFirst(String s) {
    return toUpperCase(StringUtils.defaultString(s), 0, 1);
  }

  /**
   * 转化成数据库表名，如：LotteryOrder转成LOTTERY_ORDER
   * 
   * @param name
   * @return
   */
  public static String toDBTableName(String name) {
    return toDBColumnName(name);
  }

  /**
   * 转化成数据库列表，如：isCurrentPeriod转成IS_CURRENT_PERIOD
   * 
   * @param fieldName
   * @return
   */
  public static String toDBColumnName(String fieldName) {
    if (StringUtils.isBlank(fieldName))
      return "";
    StringBuffer columnNameBuffer = new StringBuffer();
    char[] charArray = StringUtils.defaultString(fieldName).toCharArray();
    columnNameBuffer.append(Character.toUpperCase(charArray[0]));
    for (int i = 1; i < charArray.length; i++) {
      if (Character.isUpperCase(charArray[i])) {
        columnNameBuffer.append("_");
      }
      columnNameBuffer.append(Character.toUpperCase(charArray[i]));
    }
    return columnNameBuffer.toString();
  }
}
