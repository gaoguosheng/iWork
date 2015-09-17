package com.ggs.util;

import java.io.IOException;

import com.ggs.bean.User;
import com.wabacus.system.ReportRequest;
import com.wabacus.system.intercept.AbsPageInterceptor;

public class PermissionInterceptor extends AbsPageInterceptor {
    private DBUtil dbUtil = new DBUtil();
    public void doStart(ReportRequest rrequest) {
        String pageid = rrequest.getPagebean().getId();// pageid
        User user = (User)rrequest.getRequest().getSession().getAttribute("admin");
        if (user == null ) {
            try {
                // 用户没登录则跳转到登录页面
                rrequest.getWResponse().getResponse().getWriter().print("<script>top.location='"+rrequest.getRequest().getContextPath()+"/login.html';</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }        
    }
    public void doEnd(ReportRequest rrequest) {

    }
}

