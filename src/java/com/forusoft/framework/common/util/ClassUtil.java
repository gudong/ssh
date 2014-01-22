package com.forusoft.framework.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author gudong
 *
 */
public class ClassUtil {
  private static Log log = LogFactory.getLog(ClassUtil.class);

  /**
   * 获取 Field，包括父类
   * 
   * @param clazz
   * @param name
   * @return
   */
  public static Field getDeclaredField(Class clazz, String name) {
    try {
      Field field = null;
      while (clazz != null) {
        try {
          field = clazz.getDeclaredField(name);
        } catch (Exception e) {
          field = null;
        }
        if (field == null) {
          clazz = clazz.getSuperclass();
        } else
          break;
      }
      return field;
    } catch (Exception e) {
      log
          .error(" get declared field included superClass of property name ['"
              + name + "'] error:" + e);
      return null;
    }
  }

  /**
   * 获取标准bean的 属性，该属性必须含有set和get方法
   * 
   * @param clazz
   * @return
   */
  public static List<Field> getDeclaredFields4StandardBean(Class clazz) {
    return getDeclaredFields(clazz, false, true);
  }

  /**
   * 获取所有的Field List
   * 
   * @param clazz
   * @param onlyPublicField
   * @return
   */
  public static List<Field> getDeclaredFields(Class clazz,
      boolean onlyPublicField) {
    return getDeclaredFields(clazz, onlyPublicField, false);
  }

  /**
   * 获取所有的Field List
   * 
   * @param clazz
   * @param onlyPublicField
   * @param mustHasSetAndGetMethod
   * @return
   */
  private static List<Field> getDeclaredFields(Class clazz,
      boolean onlyPublicField, boolean mustHasSetAndGetMethod) {
    Map<String, Field> fieldNameMap = new HashMap<String, Field>();
    List<Field> fieldList = new ArrayList<Field>();
    while (clazz != null) {
      fillField(clazz, fieldNameMap, fieldList, onlyPublicField,
          mustHasSetAndGetMethod);
      clazz = clazz.getSuperclass();
    }
    return fieldList;
  }

  /**
   * 填充 Field
   * 
   * @param clazz
   * @param fieldNameMap
   * @param fieldList
   * @param onlyPublicField
   * @param mustHasSetAndGetMethod
   */
  private static void fillField(Class clazz, Map<String, Field> fieldNameMap,
      List<Field> fieldList, boolean onlyPublicField,
      boolean mustHasSetAndGetMethod) {
    if (fieldNameMap == null)
      fieldNameMap = new HashMap<String, Field>();
    if (fieldList == null)
      fieldList = new ArrayList<Field>();
    Field[] fields = null;
    if (onlyPublicField)
      fields = clazz.getFields();
    else
      fields = clazz.getDeclaredFields();
    if (fields != null) {
      for (Field field : fields) {
        if (mustHasSetAndGetMethod) {
          Method setMethod = null;
          Method getMethod = null;
          try {
            String methodFlag = StringUtil.toUpperCaseFirst(field
                .getName());
            setMethod = clazz.getMethod("set" + methodFlag, field
                .getType());
            getMethod = clazz.getMethod("get" + methodFlag);
          } catch (Exception e) {
            setMethod = null;
            getMethod = null;
          }
          if (setMethod == null || getMethod == null)
            continue;
        }
        if (!fieldNameMap.containsKey(field.getName())) {
          fieldNameMap.put(field.getName(), field);
          fieldList.add(field);
        }
      }
    }
  }

  /**
   * 是否是日期类型
   * 
   * @param clazz
   * @return
   */
  public static boolean isDateClass(Class clazz) {
    if (clazz != null && "java.util.Date".equals(clazz.getName()))
      return true;
    return false;
  }

  /**
   * 是否是基本类型
   * 
   * @param clazz
   * @return
   */
  public static boolean isPrimitive(Class clazz) {
    try {
      Field f = clazz.getField("TYPE");
      if (f != null) {
        boolean b = ((Class) f.get(null)).isPrimitive();
        return b;
      }
    } catch (Exception e) {
    }
    if ("java.lang.String".equals(clazz.getName()))
      return true;
    return false;
  }

  /**
   * 是否是map
   * 
   * @param clazz
   * @return
   */
  public static boolean isMap(Class clazz) {
    if ("java.util.Map".equals(clazz.getName()))
      return true;
    else if (StringUtils.join(clazz.getInterfaces(), ",").contains(
        "java.util.Map"))
      return true;
    return false;
  }

  /**
   * 是否是list
   * 
   * @param clazz
   * @return
   */
  public static boolean isList(Class clazz) {
    if ("java.util.List".equals(clazz.getName()))
      return true;
    else if (StringUtils.join(clazz.getInterfaces(), ",").contains(
        "java.util.List"))
      return true;
    return false;
  }


}
