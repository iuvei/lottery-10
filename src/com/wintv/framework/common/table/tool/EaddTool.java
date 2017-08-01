package com.wintv.framework.common.table.tool;

import org.ecside.core.ECSideConstants;
import org.ecside.core.TableModel;
import org.ecside.preferences.PreferencesConstants;
import org.ecside.table.tool.BaseTool;
import org.ecside.util.HtmlBuilder;

public class EaddTool extends BaseTool{
	public EaddTool(){
		super();
	}
	
	public EaddTool(HtmlBuilder html,TableModel model) {
		super(html, model);
	}
	
	public void buildTool() {

		boolean showTooltips = getTableModel().getTable().isShowTooltips();

		String action = getToolAction();

		getHtmlBuilder().td(1).nowrap().styleClass("addTool").close();
		getHtmlBuilder().append("<nobr>");

		getHtmlBuilder().input().type("button");
		getHtmlBuilder().styleClass("toolButton girdEadd");
		getHtmlBuilder().onclick(action);
		if (showTooltips) {
			getHtmlBuilder().title(
					getTableModel().getMessages().getMessage(
							PreferencesConstants.TOOLBAR_ADD_TOOLTIP));
		}
		getHtmlBuilder().xclose();

		getHtmlBuilder().append("</nobr>");
		getHtmlBuilder().tdEnd();
	}

	public String getToolAction() {

		String formId = getTableModel().getTable().getTableId();
		StringBuffer action = new StringBuffer(
				ECSideConstants.UTIL_FUNCTION_NAME + ".eaddToGird(");
		action.append("this,'" + formId + "'");
		action.append(");");

		String onInvokeAction = getTableModel().getTable().getOnInvokeAction();
		if (onInvokeAction != null && onInvokeAction.length() > 0) {
			action.append(onInvokeAction);
		}

		return action.toString();
	}

}
