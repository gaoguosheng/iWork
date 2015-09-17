package com.ggs.web;

import com.ggs.bean.User;
import com.ggs.dao.ComDAO;
import com.ggs.util.DateUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.wabacus.util.DesEncryptTools;


public class LoginAction extends  BaseAction implements ModelDriven<User> {

    private  User model = new User();

    public User getModel() {
        return model;
    }


    /***
     * 登录
     * */
    public void checkLogin(){
        boolean isExist=false;
        String password= DesEncryptTools.encrypt(model.getPassword());
        isExist =ComDAO.checkLogin(model.getUsername(),password);
        if(isExist){
            this.outSuccess(true,1);
            User user = ComDAO.getUser(model.getUsername());
            session.setAttribute("admin",user);
            return;
        }
        this.outSuccess(false,0);
    }
}
