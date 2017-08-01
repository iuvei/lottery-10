
package com.wintv.framework.common.table.cell;

import org.ecside.core.ECSideConstants;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.Cell;
import org.ecside.util.HtmlBuilder;
import org.ecside.view.html.ColumnBuilder;




public class DownloadCell implements Cell  {
	

    public String getHtmlDisplay(TableModel model, Column column) {
    	column.setEditable(Boolean.FALSE);
    	column.setSortable(Boolean.FALSE);
    	ColumnBuilder columnBuilder = new ColumnBuilder(column);
        columnBuilder.tdStart();
        columnBuilder.tdBody(getCellValue(model, column));
        columnBuilder.tdEnd();
        return columnBuilder.toString();
    }
    
    public String getExportDisplay(TableModel model, Column column) {
        return column.getPropertyValueAsString();
    }

    protected String getCellValue(TableModel model, Column column) {
    	HtmlBuilder html=new HtmlBuilder();
		html.span().styleClass("downloadButton");
		html.onclick(ECSideConstants.UTIL_FUNCTION_NAME + ".edownloadFromGird(this.parentNode.parentNode,'"+ model.getTable().getTableId() + "');");
		html.close();
		html.append("&#160;");

		html.spanEnd();
		
		return html.toString();

    }
}
