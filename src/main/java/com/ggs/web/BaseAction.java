package com.ggs.web;

import com.ggs.bean.User;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Action 基类
 * 
 * @author GGS
 * @version 20110915
 */

public  class BaseAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware, Serializable {

    
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected ServletContext servletContext;


	/**
	 * 设置request、session、servletcontext
	 */
	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
		this.session = this.request.getSession();
		this.servletContext = this.session.getServletContext();		
		try {
			this.request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置response
	 */
	public void setServletResponse(HttpServletResponse res) {
		this.response = res;		
		this.response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
	}

	/**
	 * 请求转发
	 * 
	 * @param url
	 *            转发地址
	 */
	public void forward(String url) {
		try {
			this.request.getRequestDispatcher(url).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重定向
	 * 
	 * @param url
	 *            重定向地址
	 */
	public void redirect(String url) {
		try {
			response.sendRedirect(request.getContextPath() + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出js脚本
	 *
	 */
	public void out(String html) {
		try {
			response.getWriter().print(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到json
	 * */
	public String getJson(Object o){		
			return new GsonBuilder().create().toJson(o);
	}
	
	
	/**
	 * 输出json
	 * */
	public void outJson(Object o){
		try {
			response.getWriter().print(getJson(o));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 输出是否成功
	 * */
	public void outSuccess(boolean isSuccess){
		Map result =new HashMap();
		result.put("success",isSuccess);
		this.outJson(result);
	}
    /**
     * 输出是否成功
     * */
    public void outSuccess(boolean isSuccess,int status){
        Map result =new HashMap();
        result.put("issuccess",isSuccess);
        result.put("status",status);
        this.outJson(result);
    }
	
	/**
	 * json转Object
	 * */
	public Object jsonToObject(String json,Class c){		
		return  new GsonBuilder().create().fromJson(json, c);
	}
    /**
     * 自动获取json参数转Object
     * */
    public Object getJsonObject(String jsonParam,Class c){
        String json = request.getParameter(jsonParam);
        return  new GsonBuilder().create().fromJson(json, c);
    }
    /**
     * 自动获取json参数转Object  默认json参数为data
     * */
    public Object getJsonObject(Class c){
        String json = request.getParameter("data");
        return  new GsonBuilder().create().fromJson(json, c);
    }
	
	

    public User getAdmin(){
        return (User)session.getAttribute("admin");
    }
    
    public String getParam(String param){
        return request.getParameter(param);
    }
    public Long getLongParam(String param){
    	String p = this.getParam(param);
    	if(p!=null){
    		return Long.parseLong(p);
    	}else{
    		return 0L;
    	}
    }
    public Integer getIntParam(String param){
    	String p = this.getParam(param);
    	if(p!=null){
    		return Integer.parseInt(p);
    	}else{
    		return 0;
    	}
    }


   
}

