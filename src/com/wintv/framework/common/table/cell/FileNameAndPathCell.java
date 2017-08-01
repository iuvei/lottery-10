package com.wintv.framework.common.table.cell;

import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;


public class FileNameAndPathCell extends AbstractCell {

	public String getExportDisplay(TableModel model, Column column) {
		return getValueString(model, column);
	}

	protected String getCellValue(TableModel model, Column column) {
		return getValueString(model, column);
	}
	
	private String getValueString(TableModel model, Column column){
		if(column.getPropertyValue() == null)
            return "";
        else
            {String  temp[]=column.getValueAsString().split(";");
		return temp[0];}
        
	}

}
