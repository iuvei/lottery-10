package com.wintv.framework.common.table.cell;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;


public class XmlCell extends AbstractCell {
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
        	String cellValue=column.getValueAsString();
        	if(StringUtils.isNotBlank(cellValue))
        	{
        		cellValue=cellValue.replace("<?xml version=\"1.0\" encoding=\"GB2312\"?><string>", "").replace("</string>", "");
        	}
        	if(cellValue.length()>20){
        		return (cellValue.substring(0,20)).replace("&", "&amp;").replace("<","&lt;").replace(">","&gt;")+"...";
        	}
        	else
        		return cellValue.replace("&", "&amp;").replace("<","&lt;").replace(">","&gt;");
        }
	}
}
