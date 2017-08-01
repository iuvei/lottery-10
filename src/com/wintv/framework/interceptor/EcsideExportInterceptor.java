package com.wintv.framework.interceptor;

import org.ecside.core.TableModel;
import org.ecside.core.bean.Export;
import org.ecside.table.interceptor.ExportInterceptor;

public class EcsideExportInterceptor implements ExportInterceptor{

	@Override
	public void addExportAttributes(TableModel arg0, Export arg1) {
		arg1.setEncoding("UTF-8");
		String aa = arg0.getColumnInstance().getValueAsString();
	}

}
