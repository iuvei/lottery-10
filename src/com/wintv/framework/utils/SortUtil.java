package com.wintv.framework.utils;


import org.apache.commons.beanutils.WrapDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


/**
 * <p>This utility class will sort a list of objects.
 * There are several ways of using this sorter.
 * <pre>
 * ...
 * SortUtil sorter = new SortUtil();
 * sorter.add("field1");
 * sorter.add("field2");
 * sorter.sort(theListToBeSorted);
 * ...
 * </pre>
 * <p>Alternatively you can define a <code>Collection</code>
 * of attributes and invoke the respective static methods
 * <pre>
 * ...
 * ArrayList orderBy = new ArrayList();
 * orderBy.add("field1");
 * orderBy.add("field2");
 * orderBy.add("field3");
 * SortUtil.sort(theList, orderBy); // static invocation
 * ...
 * </pre>
 *
 * @author Steady Wang
 * @version 2007-4-3 22:06:11
 */
public final class SortUtil implements Comparator {

    private static final Log log = LogFactory.getLog(SortUtil.class);

    /**
     * The sorting field
     */
    private ArrayList sortByList = new ArrayList();

    /**
     * Adds a new cnparing attribute.
     */
    @SuppressWarnings("unchecked")
    public boolean add(Object o) {
        if (o != null && o instanceof String && ((String) o).length() > 0) {
            return this.sortByList.add(o);
        }
        return false;
    }

    /**
     * Adds a new collection of sorting attributes
     */
    @SuppressWarnings("unchecked")
    public boolean addAll(Collection c) {
        if (c != null && c.size() > 0) {
            return this.sortByList.addAll(c);
        }
        return false;
    }

    /**
     * Clears the sorting attribute
     */
    public void clear() {
        this.sortByList.clear();
    }

    /**
     * Returns the size of the attributes
     */
    public int size() {
        return this.sortByList.size();
    }

    /**
     * Returns the attribute at an index
     */
    public Object get(int index) {
        return this.sortByList.get(index);
    }

    /**
     * Returns the attribute (in String) at an index
     */
    public String getAttribute(int index) {
        return (String) this.sortByList.get(index);
    }

    /**
     * cnpares 2 java beans
     *
     * @param o1 1st java bean
     * @param o2 2nd java bean
     * @return the cnparision results
     */
    public int compare(Object o1, Object o2) {
        if (o1 == null || o2 == null) {
            return 0;
        }
        if (this.sortByList.size() < 1) {
            return 0;
        }

        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();

        if (o1 instanceof Map && o2 instanceof Map) {
            Map m1 = (Map) o1;
            Map m2 = (Map) o2;
            for (int i = 0; i < this.sortByList.size(); i++) {
                String key = (String) this.sortByList.get(i);
                Object value1 = m1.get(key);
                Object value2 = m2.get(key);
                if (value1 != null && value2 != null) {
                    sb1.append(value1);
                    sb2.append(value2);
                }
            }
        } else {
            WrapDynaBean bean1 = new WrapDynaBean(o1);
            WrapDynaBean bean2 = new WrapDynaBean(o2);
            for (int i = 0; i < this.sortByList.size(); i++) {
                String key = (String) this.sortByList.get(i);
                Object value1 = bean1.get(key);
                Object value2 = bean2.get(key);
                if (value1 != null && value2 != null) {
                    sb1.append(value1);
                    sb2.append(value2);
                }
            }
        }

        return sb1.toString().toUpperCase().compareTo(sb2.toString().toUpperCase());
    }

    /**
     * Sorts the list
     *
     * @param list the target list to be sorted
     */
    @SuppressWarnings("unchecked")
    public void sort(List list) {
        Collections.sort(list, this);
    }

    /**
     * Sort the list
     *
     * @param listToSort the target list to be sorted
     * @param orderBy    the list of order by strings attribute name
     */
    public static void sort(List listToSort, Collection orderBy) {
        if (orderBy == null || listToSort == null) {
            return;
        }
        if (orderBy.size() < 1 || listToSort.size() < 1) {
            return;
        }

        SortUtil sorter = new SortUtil();
        sorter.addAll(orderBy);
        sorter.sort(listToSort);
    }

    /**
     * Sort the list with one attribute
     *
     * @param listToSort the target list to be sorted
     * @param orderBy    the list of order by strings attribute name
     */
    public static void sort(List listToSort, String orderBy) {
        if (orderBy == null || listToSort == null) {
            return;
        }
        if (orderBy.length() < 1 || listToSort.size() < 1) {
            return;
        }
        SortUtil sorter = new SortUtil();
        sorter.add(orderBy);
        sorter.sort(listToSort);
	}

}