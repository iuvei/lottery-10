package com.wintv.framework.common;
/**
 * 
 * @author Hikin Yao
 *
 * @version 1.0.0
 */
public class OracleSqlUtil {
	/**
	 * 给查询语句添加Oracle分页条件
	 * 
	 * @param oldSql
	 *            原始sql
	 * @param startRow
	 *            分页查询开始行规
	 * @param pageSize
	 *            分页大小
	 * @return 返回添加过分页条件的语句
	 */
	public static StringBuilder addQueryPageSizeCondition(StringBuilder oldSql,
			Integer startRow, Integer pageSize) {
		
		Integer endRow = startRow + pageSize - 1;
		StringBuilder newSql = new StringBuilder();
		newSql.append(" select * from (select b.*,rownum rn from ( ");
		newSql.append(oldSql);
		newSql.append(" ) b where rownum<="+endRow+") a where a.rn>="+startRow);
		return newSql;
	}
	/**
	 * 将字符串 name1,name2,name3 格式化成 'name1','name2','name3','name4' 格式
	 * @param sqlStr 字符串"name1,name2,name3" 格式
	 * @return 'name1','name2','name3','name4' 格式
	 */
	public static String formatSqlAddCommas(String sqlStr){
		String[] elements=sqlStr.split(",");
		StringBuilder result= new StringBuilder();
		for(String oneElement:elements){
			if(null!=oneElement&&!"".equals(oneElement)){
				result.append(",'"+oneElement+"'");
			}
		}
		return result.toString().replaceFirst(",", "");
	}
}
