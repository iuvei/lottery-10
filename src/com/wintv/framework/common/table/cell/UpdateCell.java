package com.wintv.framework.common.table.cell;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.Cell;
import org.ecside.util.HtmlBuilder;
import org.ecside.view.html.ColumnBuilder;

public class UpdateCell implements Cell {
	public UpdateCell() {
	}

	public String getExportDisplay(TableModel model, Column column) {
		return column.getPropertyValueAsString();
	}

	public String getHtmlDisplay(TableModel model, Column column) {
		String body = "";
		if(!(StringUtils.isNotBlank(column.getCellValue()) && column.getCellValue().equals("false"))){
			body = getCellValue(model, column);
		}
		column.setEditable(Boolean.FALSE);
		column.setSortable(Boolean.FALSE);
		ColumnBuilder columnBuilder = new ColumnBuilder(column);
		columnBuilder.tdStart();
		columnBuilder.tdBody(body);
		columnBuilder.tdEnd();
		return columnBuilder.toString();
	}

	protected String getCellValue(TableModel model, Column column) {
		HtmlBuilder html = new HtmlBuilder();
		html.span().styleClass("updateButton");
		html.onclick((new StringBuilder(
				"ECSideUtil.eupdateToGird(this.parentNode.parentNode,'")).append(
				model.getTable().getTableId()).append("');").toString());
		html.close();
		html.append("&#160;");
		html.spanEnd();
		return html.toString();
	}

}
