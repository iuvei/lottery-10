/*
 * Copyright 2006-2007 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wintv.framework.common.table.cell;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;

import org.ecside.table.cell.CheckBoxCell;
import org.ecside.util.HtmlBuilder;



public class ExtendCheckBoxCell extends CheckBoxCell {

	protected String getCellValue(TableModel model, Column column) {
		String value = column.getValueAsString();
		String checkBoxName = column.getAlias();
		HtmlBuilder build = new HtmlBuilder();
		build.input("checkbox").name(checkBoxName).value(value);
		if (column.getStyleClass() != null) {
			build.styleClass(column.getStyleClass());
		} else {
			build.styleClass("checkbox");
		}
		if (StringUtils.isNotBlank(column.getTagAttributes())) {
			build.append(" ").append(column.getTagAttributes()).append(" ");
		}
        if(StringUtils
				.isNotBlank(column.getCellValue()) && column.getCellValue()
				.equals("false")){
        	build.disabled();
        }
        build.xclose();
		return build.toString();
	}
}
