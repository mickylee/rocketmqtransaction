package com.huhavefun.editor;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Created by HuHaifan on 2017/6/15.
 */
public class StringArrayEditor extends PropertyEditorSupport {

    /** 切分标记 */
    public static final String SPLIT_FLAG = ",";

    /**
     * <B>方法名称：</B>获取属性值文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @return String 文本
     * @see PropertyEditorSupport#setAsText(String)
     */
    @Override
    public String getAsText() {
        String[] value = (String[]) getValue();
        if (value == null || value.length < 1) {
            return null;
        }
        StringBuffer text = new StringBuffer();
        for (int i = 0; i < value.length; i++) {
            if (i > 0) {
                text.append(SPLIT_FLAG);
            }
            text.append(value[i]);
        }
        return text.toString();
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
        if (StringUtils.isBlank(text)) {
            setValue(null);
            return;
        }
        setValue(text.split(SPLIT_FLAG));
    }
}