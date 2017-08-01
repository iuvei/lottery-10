package com.wintv.framework.common.table.cell;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.Cell;
import org.ecside.view.html.ColumnBuilder;

public class AbbrCell implements Cell{

	public String getExportDisplay(TableModel model, Column column) {
		return column.getPropertyValueAsString();
	}

	public String getHtmlDisplay(TableModel model, Column column) {
		column.setEditable(Boolean.FALSE);
        column.setSortable(Boolean.FALSE);
        ColumnBuilder columnBuilder = new ColumnBuilder(column);
        columnBuilder.tdStart();
        columnBuilder.tdBody(StringUtils.isBlank(column.getPropertyValueAsString())?"-":"Y");
        columnBuilder.tdEnd();
        return columnBuilder.toString();
	}

}
