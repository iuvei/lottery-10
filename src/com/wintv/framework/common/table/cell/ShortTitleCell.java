package com.wintv.framework.common.table.cell;

import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;



public class ShortTitleCell extends AbstractCell{

	public String getExportDisplay(TableModel model, Column column) {
		return getValueString(model, column);
	}

	protected String getCellValue(TableModel model, Column column) {
		return getValueString(model, column);
	}
	
	private String getValueString(TableModel model, Column column){
		if(column.getPropertyValue() == null)
            return "";
        else{
        	if(column.getValueAsString().length()>10){
        		return column.getValueAsString().substring(0,10).replace("<","&lt;").replace(">","&gt;")+"...";
        	}
        	else
        		return column.getValueAsString().replace("<","&lt;").replace(">","&gt;");
        }
	}
}
