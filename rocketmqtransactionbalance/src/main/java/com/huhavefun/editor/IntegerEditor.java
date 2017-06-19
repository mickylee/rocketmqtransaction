package com.huhavefun.editor;

import org.apache.commons.lang3.math.NumberUtils;

import java.beans.PropertyEditorSupport;

/**
 * Created by HuHaifan on 2017/6/15.
 */
public class IntegerEditor extends PropertyEditorSupport {

    /**
     * <B>方法名称：</B>获取属性值文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @return String 文本
     * @see PropertyEditorSupport#setAsText(String)
     */
    @Override
    public String getAsText() {
        Integer value = (Integer) getValue();
        if (value == null) {
            return null;
        }
        return "" + value;
    }

    /**
     * <B>方法名称：</B>按照文本设定属性值<BR>
     * <B>概要说明：</B><BR>
     *
     * @param text 文本
     * @see PropertyEditorSupport#setAsText(String)
     */
    @Override
    public void setAsText(String text) {
        Integer value = null;
        String temp = text;
        if (text.startsWith("-")) {
            temp = text.substring(1);
        }
        if (NumberUtils.isDigits(temp)) {
            value = Integer.parseInt(text);
        }
        setValue(value);
    }
}