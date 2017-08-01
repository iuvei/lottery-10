package com.wintv.framework.common.table.tool;

import org.ecside.core.ECSideConstants;
import org.ecside.core.TableModel;
import org.ecside.preferences.PreferencesConstants;
import org.ecside.table.tool.BaseTool;
import org.ecside.util.HtmlBuilder;

import com.wintv.framework.common.table.resource.MessageConstants;


public class EdelTool extends BaseTool {
	public EdelTool() {
		super();
	}

	public EdelTool(HtmlBuilder html, TableModel model) {
		super(html, model);
	}

	public void buildTool() {

		boolean showTooltips = getTableModel().getTable().isShowTooltips();

		String action = getToolAction();

		getHtmlBuilder().td(1).nowrap().styleClass("edelTool").close();
		getHtmlBuilder().append("<nobr>");

		getHtmlBuilder().input().type("button");
		getHtmlBuilder().styleClass("toolButton girdEdel");
		getHtmlBuilder().onclick(action);
		if (showTooltips) {
			getHtmlBuilder().title(
					getTableModel().getMessages().getMessage(
							PreferencesConstants.TOOLBAR_DEL_TOOLTIP));
		}
		getHtmlBuilder().xclose();

		getHtmlBuilder().append("</nobr>");
		getHtmlBuilder().tdEnd();
	}

	public String getToolAction() {

		String formId = getTableModel().getTable().getTableId();
		StringBuffer action = new StringBuffer(
				ECSideConstants.UTIL_FUNCTION_NAME + ".edelFromGird(");
		action.append("this, '" + formId + "','"+ "| 确认删除吗? " + "'");
		action.append(");");

		String onInvokeAction = getTableModel().getTable().getOnInvokeAction();
		if (onInvokeAction != null && onInvokeAction.length() > 0) {
			action.append(onInvokeAction);
		}

		return action.toString();
	}

}
