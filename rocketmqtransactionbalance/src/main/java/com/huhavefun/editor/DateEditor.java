package com.huhavefun.editor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by HuHaifan on 2017/6/15.
 */
public class DateEditor extends PropertyEditorSupport {

    /** 默认格式 */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    /**
     * <B>方法名称：</B>获取属性值文本<BR>
     * <B>概要说明：</B><BR>
     *
     * @return String 文本
     * @see PropertyEditorSupport#setAsText(String)
     */
    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        if (value == null) {
            return null;
        }
        return DateFormatUtils.format(value, DEFAULT_FORMAT);
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
        try {
            setValue(DateUtils.parseDate(text, DEFAULT_FORMAT));
        }
        catch (ParseException e) {
            throw new IllegalArgumentException(text, e);
        }
    }
}