package com.ggs.dao;

import java.util.Map;

import com.ggs.bean.User;
import com.ggs.util.BeanUtil;
import com.ggs.util.DBUtil;


public class ComDAO {

	private static DBUtil dbUtil = new DBUtil();

    /**
     *      获取数据字典
     * * */
	public static String getDDName(String ddtype,String code){
		
		Map<String,String> item =dbUtil.queryForMap("select * from t_data where ddtype =? and code=?",ddtype,code);
		if(item!=null){
			return item.get("name");
		}
		return "";
	}

    /**
     *       检查是否登录* */
    public static  boolean   checkLogin(String username,String password){
        int result  = dbUtil.queryForInt("select count(*) from t_user where username =? and password=?",username,password);
        return  result>0?true:false;
    }

    /**
     *    获取用户信息   * */

    public static User getUser(String username){
        User user  =null;
        Map result = dbUtil.queryForMap("select * from t_user where username=?",username);
        if(result!=null){
            user=new User();
            BeanUtil.mapCopyObject(result,user);
        }
        return user;
    }
    /**
     * 修改密码
     * */
    public static void updatePwd(String username,String password){
    	dbUtil.update("update t_user set password=? where username=?",password,username);
    }

}
