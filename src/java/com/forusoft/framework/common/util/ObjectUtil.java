package com.forusoft.framework.common.util;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;

/**
 * 
 * @author gudong
 * 
 */
public class ObjectUtil {

  private static Log log = LogFactory.getLog(ObjectUtil.class);
  private static final BeanUtilsBean beanUtilsBean = new BeanUtilsBean();

  /**
   * 获取对象的某个属性值，支持对象链 如：propertyName.propertyName.propertyName
   * 
   * @param object
   * @param propertyName
   * @return
   */
  public static Object getProperty(Object object, String propertyName) {
    try {
      String[] propertyNames = propertyName.split("\\.");
      Object propertyObj = object;
      for (int i = 0; i < propertyNames.length; i++) {
        String getMethodName = "get" + StringUtil.toUpperCaseFirst(propertyNames[i]);
        Method method = propertyObj.getClass().getMethod(getMethodName);
        if (method == null)
          return null;
        else
          propertyObj = method.invoke(propertyObj);
      }
      return propertyObj;
    } catch (Exception e) {
      log.error("get ['" + propertyName + "' property of '" + object + "' object] error:" + e);
      return null;
    }
  }

  /**
   * 设置对象的某个属性值，支持对象链 如：propertyName.propertyName.propertyName
   * 
   * @param clazz
   * @param object
   * @param propertyName
   * @param value
   * @return
   */
  public static boolean setProperty(Object object, String propertyName, Object value) {
    String[] propertyNames = propertyName.split("\\.");
    List<Object> propertyObjList = new ArrayList<Object>();
    Object invokeObj = object;
    propertyObjList.add(invokeObj);
    for (int i = 0; i < propertyNames.length - 1; i++) {
      String property = propertyNames[i];
      String methodFlag = StringUtil.toUpperCaseFirst(property);
      try {
        Method getMethod = object.getClass().getMethod("get" + methodFlag);
        if (getMethod == null)
          return false;
        else {
          invokeObj = getMethod.getReturnType().newInstance();
          propertyObjList.add(invokeObj);
        }
      } catch (Exception e) {
        log.error("get [getMethod of '" + property + "' property of '" + invokeObj + "' object] error:" + e);
        return false;
      }
    }
    Object invokeValue = value;
    for (int i = propertyObjList.size() - 1; i >= 0; i--) {
      String property = propertyNames[i];
      String methodFlag = StringUtil.toUpperCaseFirst(property);
      try {
        Method getMethod = propertyObjList.get(i).getClass().getMethod("get" + methodFlag);
        if (getMethod == null)
          return false;
        Method setMethod = propertyObjList.get(i).getClass().getMethod("set" + methodFlag, getMethod.getReturnType());
        if (setMethod == null)
          return false;
        else {
          try {
            setMethod.invoke(propertyObjList.get(i), invokeValue);
          } catch (Exception e) {
            try {
              BeanUtils.setProperty(propertyObjList.get(i), property, invokeValue);
            } catch (Exception e1) {
              log.error("set value of '" + property + "' property of '" + propertyObjList.get(i) + "' object] error:" + e1);
              return false;
            }
          }
          invokeValue = propertyObjList.get(i);
        }
      } catch (Exception e) {
        log.error("set value of '" + property + "' property of '" + propertyObjList.get(i) + "' object] error:" + e);
        return false;
      }
    }
    return true;
  }

  /**
   * 
   * @param obj
   * @return
   */
  public static Object copy(Object obj) {
    try {
      ByteArrayOutputStream baostream = new ByteArrayOutputStream();
      ObjectOutputStream oostream = new ObjectOutputStream(baostream);
      oostream.writeObject(obj);
      oostream.flush();
      oostream.close();
      ByteArrayInputStream baistream = new ByteArrayInputStream(baostream.toByteArray());
      ObjectInputStream oistream = new ObjectInputStream(baistream);
      Object copy = oistream.readObject();
      oistream.close();
      return copy;
    } catch (Throwable t) {
      throw new HibernateException(t);
    }
  }

  /**
   * copy properties whose value isn't null to dest obj
   * 
   * @param dest
   * @param orig
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public void copyNotNullProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
    // Validate existence of the specified beans
    if (dest == null) {
      throw new IllegalArgumentException("No destination bean specified");
    }
    if (orig == null) {
      throw new IllegalArgumentException("No origin bean specified");
    }
    if (log.isDebugEnabled()) {
      log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
    }

    // Copy the properties, converting as necessary
    if (orig instanceof DynaBean) {
      DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
      for (int i = 0; i < origDescriptors.length; i++) {
        String name = origDescriptors[i].getName();
        // Need to check isReadable() for WrapDynaBean
        // (see Jira issue# BEANUTILS-61)
        if (beanUtilsBean.getPropertyUtils().isReadable(orig, name) && beanUtilsBean.getPropertyUtils().isWriteable(dest, name)) {
          Object value = ((DynaBean) orig).get(name);
          if (value != null)
            beanUtilsBean.copyProperty(dest, name, value);
        }
      }
    } else if (orig instanceof Map) {
      @SuppressWarnings("unchecked")
      // Map properties are always of type <String, Object>
      Map<String, Object> propMap = (Map<String, Object>) orig;
      for (Map.Entry<String, Object> entry : propMap.entrySet()) {
        String name = entry.getKey();
        if (beanUtilsBean.getPropertyUtils().isWriteable(dest, name)) {
          if (entry.getValue() != null) {
            beanUtilsBean.copyProperty(dest, name, entry.getValue());
          }
        }
      }
    } else /* if (orig is a standard JavaBean) */{
      PropertyDescriptor[] origDescriptors = beanUtilsBean.getPropertyUtils().getPropertyDescriptors(orig);
      for (int i = 0; i < origDescriptors.length; i++) {
        String name = origDescriptors[i].getName();
        if ("class".equals(name)) {
          continue; // No point in trying to set an object's class
        }
        if (beanUtilsBean.getPropertyUtils().isReadable(orig, name) && beanUtilsBean.getPropertyUtils().isWriteable(dest, name)) {
          try {
            Object value = beanUtilsBean.getPropertyUtils().getSimpleProperty(orig, name);
            if (value != null) {
              beanUtilsBean.copyProperty(dest, name, value);
            }
          } catch (NoSuchMethodException e) {
            // Should not happen
          }
        }
      }
    }
  }
}
