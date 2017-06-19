package com.huhavefun.editor;

import com.sun.beans.editors.IntegerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by HuHaifan on 2017/6/15.
 */
public class CustomWebBindingInitializer implements WebBindingInitializer {

    /**
     * <B>方法名称：</B>初始化绑定内容<BR>
     * <B>概要说明：</B><BR>
     *
     * @param binder 页面数据绑定器
     * @param request 页面请求
     * @see WebBindingInitializer#initBinder(WebDataBinder,
     *      WebRequest)
     */
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Integer.class, new IntegerEditor());
        binder.registerCustomEditor(Date.class, new DateEditor());
        binder.registerCustomEditor(Timestamp.class, new TimestampEditor());
        binder.registerCustomEditor(String[].class, new StringArrayEditor());
    }

}