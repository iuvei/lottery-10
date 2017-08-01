package com.wintv.framework.utils;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * 跟 Hibernate 相关的查询工具类
 *
 * @author agilejava
 */
public class QueryUtil {
	/**
	 * 用于指定查询类型的枚举类型
	 */
	public enum CriteriaType {
		LIKE, EQUAL
	}

	/**
	 * 从 Entity 中取出指定的属性，为 DetachedCriteria 添加等值查询的条件
	 *
	 * @param criteria	  DetachedCriteria
	 * @param entity		查询对应的 Entity 类，其中包括有需要的查询条件
	 * @param propertyNames Entity类的属性名数组
	 */
	public static void addEqCriteria(DetachedCriteria criteria, Object entity, String[] propertyNames) {
		addCriteria(criteria, entity, propertyNames, CriteriaType.EQUAL);
	}

	/**
	 * 从 Entity 中取出指定的属性，为 DetachedCriteria 添加一个like查询的条件
	 *
	 * @param criteria	  DetachedCriteria
	 * @param entity		查询对应的 Entity 类，其中包括有需要的查询条件
	 * @param propertyNames Entity类的属性名数组
	 */
	public static void addLikeCriteria(DetachedCriteria criteria, Object entity, String[] propertyNames) {
		addCriteria(criteria, entity, propertyNames, CriteriaType.LIKE);
	}

	/**
	 * 从 Entity 中取出指定的属性，为 DetachedCriteria 添加等值查询的条件
	 *
	 * @param criteria	  DetachedCriteria
	 * @param entity		查询对应的 Entity 类，其中包括有需要的查询条件
	 * @param propertyNames Entity类的属性名数组
	 * @param criteriaType  查询类型
	 */
	private static void addCriteria(DetachedCriteria criteria, Object entity, String[] propertyNames,
									CriteriaType criteriaType) {
		Object propertyValue;
		String stringValue;
		for (String propertyName : propertyNames) {
			try {
				// 如果为 String 类型，判断其是否为 null 或空字符串,并根据 criteriaType 来相应的使用 eq 或 like 查询
				propertyValue = PropertyUtils.getProperty(entity, propertyName);
				if (propertyValue instanceof String) {
					stringValue = (String) propertyValue;
					if (StringUtils.isNotEmpty(stringValue)) {
						if (CriteriaType.EQUAL == criteriaType) {
							criteria.add(Restrictions.eq(propertyName, stringValue));
						} else if (CriteriaType.LIKE == criteriaType) {
							criteria.add(Restrictions.like(propertyName, "%" + stringValue + "%"));
						}
					}
				} else {
					if (null != propertyValue) {
						criteria.add(Restrictions.eq(propertyName, propertyValue));
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}

		}
	}
}