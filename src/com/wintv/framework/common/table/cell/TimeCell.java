package com.wintv.framework.common.table.cell;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.Cell;
import org.ecside.view.html.ColumnBuilder;

public class TimeCell implements Cell{

	public String getExportDisplay(TableModel arg0, Column arg1) {
		return null;
	}

	public String getHtmlDisplay(TableModel model, Column column) {
		Date date = (Date)column.getPropertyValue();
		SimpleDateFormat df = null;
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = "";
        if(date!=null) dateString = df.format(date);
		column.setEditable(Boolean.FALSE);
        column.setSortable(Boolean.FALSE);
        ColumnBuilder columnBuilder = new ColumnBuilder(column);
        columnBuilder.tdStart();
        columnBuilder.tdBody(dateString);
        columnBuilder.tdEnd();
        return columnBuilder.toString();
	}

}
