package com.wintv.framework.utils;


import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author agilejava
 * @version 1.0 2007-1-29
 */
public class PropertyCopyUtil {
    public static Log log = LogFactory.getLog(PropertyCopyUtil.class);

    /**
     * 我们在从页面取到数据并对数据库进行更新操作的时候，在一些情况下，我们只需要更新一部分的值，
     * 但是我们如果一个个的调用 get 方法从 VO 取值，再用 set 方法给 PO 赋值显得比较烦琐，于是加入了这个 Util，
     * 我们可以直接将需要的属性从 VO copy 到 PO
     *
     * @param origin        被拷贝的对象，通常是一个 VO
     * @param target        需要拷贝到的对象，通常是一个 PO
     * @param propertyNames 含有需要拷贝的 property 名称的数组
     */
    public static void copyProperties(Object origin, Object target, String[] propertyNames) {
    	Object object = null;
    	try {
            for (String propertyName : propertyNames) {
                object = BeanUtils.getProperty(origin, propertyName);
                if (object != null) {
                    BeanUtils.copyProperty(target, propertyName, PropertyUtils.getProperty(origin, propertyName));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 我们在从页面取到数据并对数据库进行更新操作的时候，在一些情况下，我们只需要更新一部分的值，
     * 但是我们如果一个个的调用 get 方法从 VO 取值，再用 set 方法给 PO 赋值显得比较烦琐，于是加入了这个 Util，
     * 我们可以直接将需要的属性从 VO copy 到 PO
     *
     * @param origin        被拷贝的对象，通常是一个 VO
     * @param target        需要拷贝到的对象，通常是一个 PO
     * @param propertyNames 含有需要拷贝的 property 
     */
    @SuppressWarnings("unchecked")
	public static void copyProperties(Object origin, Object target, Map propertyNames) {
    	Set pSet = propertyNames.entrySet();
    	String pName = null;
    	try {
            for (Object propertyName : pSet) {
            	pName = (String)propertyName;
                if (null != BeanUtils.getProperty(origin, pName)) {
                    BeanUtils.copyProperty(target, pName, PropertyUtils.getProperty(origin, pName));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
