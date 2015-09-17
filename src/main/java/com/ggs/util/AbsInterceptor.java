package com.ggs.util;

import com.ggs.bean.User;
import com.ggs.util.DBUtil;
import com.wabacus.config.component.application.report.ReportBean;
import com.wabacus.system.ReportRequest;
import com.wabacus.system.component.application.report.abstractreport.AbsReportType;
import com.wabacus.system.component.application.report.configbean.editablereport.AbsEditableReportEditDataBean;
import com.wabacus.system.intercept.AbsInterceptorDefaultAdapter;
import com.wabacus.system.intercept.RowDataBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: GGS
 * Date: 13-7-9
 * Time: 下午10:27
 * To change this template use File | Settings | File Templates.
 */
public class AbsInterceptor extends AbsInterceptorDefaultAdapter {
    private DBUtil dbUtil=new DBUtil();
    @Override
    public void beforeDisplayReportDataPerRow(ReportRequest rrequest, ReportBean rbean, RowDataBean rowDataBean) {
        if(rowDataBean.getRowindex()<0)
        {//当前是在显示标题行
            return;
        }
        String styleproperty=rowDataBean.getRowstyleproperty();
        if(rowDataBean.getRowindex() % 2 ==0){
            if(styleproperty==null) styleproperty="";
        } else{
           styleproperty+=" style='background:#AEC7ED'";
        }

        AbsReportType reportTypeObj=rrequest.getDisplayReportTypeObj(rbean.getId());
        if(reportTypeObj.getLstReportData()!=null&&rowDataBean.getRowindex()<reportTypeObj.getLstReportData().size())
        {
            String id =(String) rowDataBean.getColData("id");
            String pageid = rrequest.getPagebean().getId();
            //list类型的报表支持双击事件
            if(rbean.getType().toLowerCase().endsWith("list")){
                String url="ShowReport.wx?PAGEID="+pageid+"_detail&id="+id;
                styleproperty+="ondblclick=wx_winpage('"+url+"',{initsize:'max'})";
            }


        }
        rowDataBean.setRowstyleproperty(styleproperty);
    }


    @Override
    public int doSave(ReportRequest rrequest, ReportBean rbean, AbsEditableReportEditDataBean editbean) {
        /*
        String sql = "insert into t_log (id,userid,name,createtime,optype,opobj,content) values(seq_t_log.nextval,?,?,?,?,?,?)";

        User user = (User)rrequest.getRequest().getSession().getAttribute("admin");
        String user_id=user.getId();
        String realname=user.getRealname();
        String createtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String report  = rbean.getTitle(rrequest);

        List<Map<String,String>> lstInsertData=rrequest.getLstInsertedData(rbean);//添加的记录集合
        List<Map<String,String>> lstUpdateData=rrequest.getLstUpdatedData(rbean);//修改的记录集合
        List<Map<String,String>> lstDeleteData=rrequest.getLstDeletedData(rbean);//删除的记录集合

        StringBuilder insertLog=new StringBuilder();
        if(lstInsertData!=null&&lstInsertData.size()>0)
        {//当前在做添加数据的保存操作
            for(Map<String,String> mDataTmp:lstInsertData)
            {//循环添加的每条记录
                for(Map.Entry<String,String> entry:mDataTmp.entrySet())
                {
                    insertLog.append(entry.getKey()+"："+entry.getValue()+",");
                }
                dbUtil.update(sql,user_id,realname,createtime,"添加",report,insertLog.toString());
            }
        }

        StringBuilder updateLog=new StringBuilder();
        if(lstUpdateData!=null&&lstUpdateData.size()>0)
        {//当前在做修改数据的保存操作
            for(Map<String,String> mDataTmp:lstUpdateData)
            {//循环修改的每条记录
                for(Map.Entry<String,String> entry:mDataTmp.entrySet())
                {
                    updateLog.append(entry.getKey()+"："+entry.getValue()+",");
                }
                dbUtil.update(sql,user_id,realname,createtime,"修改",report,updateLog.toString());
            }
        }
        StringBuilder delLog=new StringBuilder();
        if((lstUpdateData==null || lstUpdateData.size()==0) &&( lstDeleteData!=null&&lstDeleteData.size()>0))
        {//当前在做删除数据的保存操作
            for(Map<String,String> mDataTmp:lstDeleteData)
            {//循环删除的每条记录
                for(Map.Entry<String,String> entry:mDataTmp.entrySet())
                {
                    delLog.append(entry.getKey()+"："+entry.getValue()+",");
                }
                dbUtil.update(sql,user_id,realname,createtime,"删除",report,delLog.toString());
            }
        }
                */

        return super.doSave(rrequest,rbean,editbean);

    }
}
