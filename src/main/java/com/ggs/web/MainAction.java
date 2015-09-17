package com.ggs.web;

import com.ggs.bean.User;
import com.ggs.dao.ComDAO;
import com.opensymphony.xwork2.ModelDriven;
import com.wabacus.util.DesEncryptTools;


public class MainAction extends  BaseAction implements ModelDriven<User> {

    private  User model = new User();

    public User getModel() {
        return model;
    }


    /***
     * 修改密码
     * */
    public void updatePwd(){
        boolean isExist=false;
        String password= DesEncryptTools.encrypt(model.getPassword());
        String username = ((User)session.getAttribute("admin")).getUsername();
        ComDAO.updatePwd(username,password);
        User user = ComDAO.getUser(username);
        session.setAttribute("admin",user);
        this.outSuccess(true,1);
    }

    /***
     *   旧密码校验
     * */
    public void checkOldPwd(){
        String password= DesEncryptTools.encrypt(model.getPassword());
        String username = ((User)session.getAttribute("admin")).getUsername();
        boolean t = ComDAO.checkLogin(username,password);
        this.outSuccess(t,1);
    }


}
