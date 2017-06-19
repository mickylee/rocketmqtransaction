package com.huhavefun.editor;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;

/**
 * Created by HuHaifan on 2017/6/15.
 */
public class TimestampEditor extends PropertyEditorSupport {

    /** 默认格式 */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * <B>方法名称：</B>获取属性值文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @return String 文本
     * @see PropertyEditorSupport#setAsText(String)
     */
    @Override
    public String getAsText() {
        Timestamp value = (Timestamp) getValue();
        return ("" + value);
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
        String temp = null;
        if (!StringUtils.isBlank(text)) {
            if (text.length() == 10) {
                temp = text + " 00:00:00.0";
            }
            else if (text.length() == 16) {
                temp = text + ":00.0";
            }
            else if (text.length() == 19) {
                temp = text + ".0";
            }
            else {
                temp = text;
            }
        }
        Timestamp value = null;
        if (temp != null) {
            value = Timestamp.valueOf(temp);
        }
        setValue(value);
    }
}