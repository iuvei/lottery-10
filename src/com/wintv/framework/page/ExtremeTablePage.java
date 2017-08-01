package com.wintv.framework.page;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.context.HttpServletRequestContext;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.LimitFactory;
import org.ecside.table.limit.Sort;
import org.ecside.table.limit.TableLimit;
import org.ecside.table.limit.TableLimitFactory;

public class ExtremeTablePage {
	public static Limit getLimit(HttpServletRequest request) {
		if(StringUtils.isBlank(request.getParameter("tableId")))
			return getLimit(request, "ec",10);
		else
			return getLimit(request,request.getParameter("tableId"),10);
    }

	public static Limit getLimit(HttpServletRequest request, String tableId, int defautPageSize) {
        LimitFactory limitFactory = new TableLimitFactory(new HttpServletRequestContext(request), tableId);
        TableLimit limit = new TableLimit(limitFactory);
        limit.setRowAttributes(1000000000, defautPageSize);
        return limit;
    }

	public static Map getSort(Limit limit) {
        Map<String, String> sortMap = new HashMap<String, String>();
        if (limit != null) {
            Sort sort = limit.getSort();
            if (sort != null && sort.isSorted()) {
                sortMap.put(sort.getProperty(), sort.getSortOrder());
            }
        }  
        return sortMap;
    }
}
