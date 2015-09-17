package com.ggs.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: GGS
 * Date: 13-7-10
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
public class CreaXmlUtil {
    private static DBUtil dbUtil = new DBUtil();

    public static String createEditListXml(String dir,String tableName,String key){
        String path =dir+tableName+".xml";
        try{
            File file = new File(dir);
            file.mkdirs();
            FileOutputStream fos =new FileOutputStream(path);
            OutputStreamWriter os = new OutputStreamWriter(fos,"UTF-8");
            String xml = CreaXmlUtil.createTableXml(tableName,key);
            os.write(xml);
            os.flush();
            os.close();
            return tableName+"报表生成成功！路径："+path+"<br/>";
        }catch (Exception e){
            e.printStackTrace();
           return "生成报表异常！";
        }
    }
    public static String createEditList2Xml(String dir,String tableName,String key){
        String path =dir+tableName+"_2.xml";
        try{
            File file = new File(dir);
            file.mkdirs();
            FileOutputStream fos =new FileOutputStream(path);
            OutputStreamWriter os = new OutputStreamWriter(fos,"UTF-8");
            String xml = CreaXmlUtil.createTableXml2(tableName, key);
            os.write(xml);
            os.flush();
            os.close();
            return tableName+"报表生成成功！路径："+path+"<br/>";
        }catch (Exception e){
            e.printStackTrace();
            return "生成报表异常！";
        }
    }

    /**
     * 通过表名生成xml
     * */
    public static String createTableXml(String tableName,String key){

        //pageid
        String listPageid=tableName+"_list";
        String formPageid=tableName+"_form";
        String detailPageid=listPageid+"_detail";
        ;
        //字段列表
        List<String> fieldList =  dbUtil.getFields(tableName);
        fieldList.remove(key);

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("\r\n");
        xml.append("<applications xmlns=\"http://www.wabacus.com\"");
        xml.append("\r\n");
        xml.append("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        xml.append("\r\n");
        xml.append("  xsi:schemaLocation=\"http://www.wabacus.com ../xsd/wabacus.applications.xsd\">");
        xml.append("\r\n");

        //管理列表开始
        xml.append("<page id=\""+listPageid+"\">");
        xml.append("\r\n");
        xml.append("  <report id=\"report1\"  type=\"editablelist\" rowselect=\"checkbox\"  dataexport=\"richexcel\" intercept=\"com.ggs.intercept.AbsInterceptor\">");
        xml.append("\r\n");
        xml.append("    <display>");
        xml.append("\r\n");
        xml.append("      <col column=\"{sequence:1}\" label=\"序号\" />");
        xml.append("\r\n");
        xml.append("      <col column=\""+key+"\" displaytype=\"hidden\"/>");
        xml.append("\r\n");
        for(String field:fieldList){
            xml.append("      <col column=\""+field+"\" label=\""+field+"\"/>");
            xml.append("\r\n");
        }
        xml.append("    </display>");
        xml.append("\r\n");
        xml.append("    <sql>");
        xml.append("\r\n");
        xml.append("      <select>");
        xml.append("\r\n");
        xml.append("      <value><![CDATA[");
        xml.append("      select * from "+tableName);
        xml.append("       ]]></value>");
        xml.append("\r\n");
        xml.append("      </select>");
        xml.append("\r\n");
        xml.append("      <insert pageurl=\"report{"+formPageid+".report1}\" popupparams=\"{initsize:'max'}\" />");
        xml.append("\r\n");
        xml.append("      <update pageurl=\"report{"+formPageid+".report1}\" urlparams=\"id=@{"+key+"}\"  popupparams=\"{initsize:'max'}\" />");
        xml.append("\r\n");
        xml.append("      <delete><![CDATA[delete from "+tableName+" where id=@{"+key+"}]]></delete>");
        xml.append("\r\n");
        xml.append("    </sql>");
        xml.append("\r\n");
        xml.append("  </report>");
        xml.append("\r\n");
        xml.append("</page>");
        xml.append("\r\n");
        xml.append("\r\n");
        //管理列表结束

        //表单开始
        xml.append("<page id=\""+formPageid+"\">");
        xml.append("\r\n");
        xml.append("  <report id=\"report1\" type=\"editabledetail\">");
        xml.append("\r\n");
        xml.append("    <display>");
        xml.append("\r\n");
        xml.append("      <col column=\""+key+"\" displaytype=\"hidden\"/>");
        xml.append("\r\n");
        for(int i=0;i<fieldList.size();i++){
            String field=fieldList.get(i);
            String br="";
            if(i%2==1){
                br="br=\"true\"";
            }
            xml.append("      <col column=\""+field+"\" label=\""+field+"\" "+br+"/>");
            xml.append("\r\n");
        }
        xml.append("    </display>");
        xml.append("\r\n");
        xml.append("    <sql>");
        xml.append("\r\n");
        xml.append("      <select>");
        xml.append("\r\n");
        xml.append("      <value><![CDATA[");
        xml.append("      select * from "+tableName+" where {#condition#}");
        xml.append("       ]]></value>");
        xml.append("\r\n");
        xml.append("      <condition name=\""+key+"\" hidden=\"true\">");
        xml.append("\r\n");
        xml.append("      <value><![CDATA["+key+"=#data#]]></value>");
        xml.append("\r\n");
        xml.append("      </condition>");
        xml.append("\r\n");
        xml.append("      </select>");
        xml.append("\r\n");
        xml.append("      <insert>");
        xml.append("\r\n");
        xml.append("      <![CDATA[");
        xml.append("\r\n");
        xml.append("      insert into "+tableName+"("+key+"=uuid{},");
        xml.append("\r\n");
        for(int i=0;i<fieldList.size();i++){
            xml.append("@{"+fieldList.get(i)+"}");
            if(i<fieldList.size()-1)
            xml.append(",");
        }
        xml.append(")");
        xml.append("\r\n");
        xml.append("      ]]>");
        xml.append("\r\n");
        xml.append("      </insert>");
        xml.append("\r\n");
        xml.append("      <update>");
        xml.append("\r\n");
        xml.append("      <![CDATA[");
        xml.append("\r\n");
        xml.append("      update "+tableName+"(");
        for(int i=0;i<fieldList.size();i++){
            xml.append("@{"+fieldList.get(i)+"}");
            if(i<fieldList.size()-1)
                xml.append(",");
        }
        xml.append(")");
        xml.append("\r\n");
        xml.append("      where "+key+"=@{"+key+"}");
        xml.append("\r\n");
        xml.append("      ]]>");
        xml.append("\r\n");
        xml.append("      </update>");
        xml.append("\r\n");
        xml.append("    </sql>");
        xml.append("\r\n");
        xml.append("  </report>");
        xml.append("\r\n");
        xml.append("</page>");
        xml.append("\r\n");
        xml.append("\r\n");
        //表单结束

        //查看页面
        xml.append("<page id=\""+detailPageid+"\">");
        xml.append("\r\n");
        xml.append("  <report id=\"report1\" extends=\""+formPageid+".report1\"  type=\"detail\"/>");
        xml.append("\r\n");
        xml.append("</page>");
        xml.append("\r\n");
        xml.append("</applications>");
        xml.append("\r\n");
        return xml.toString();
    }


    /**
     * 通过表名生成xml
     * */
    public static String createTableXml2(String tableName,String key){

        //pageid
        String listPageid=tableName+"_list";
        //字段列表
        List<String> fieldList =  dbUtil.getFields(tableName);
        fieldList.remove(key);

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("\r\n");
        xml.append("<applications xmlns=\"http://www.wabacus.com\"");
        xml.append("\r\n");
        xml.append("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        xml.append("\r\n");
        xml.append("  xsi:schemaLocation=\"http://www.wabacus.com ../xsd/wabacus.applications.xsd\">");
        xml.append("\r\n");

        //管理列表开始
        xml.append("<page id=\""+listPageid+"\">");
        xml.append("\r\n");
        xml.append("  <report id=\"report1\"  type=\"editablelist2\" rowselect=\"checkbox\"  dataexport=\"richexcel\" intercept=\"com.ggs.intercept.AbsInterceptor\">");
        xml.append("\r\n");
        xml.append("    <display>");
        xml.append("\r\n");
        xml.append("      <col column=\"{sequence:1}\" label=\"序号\" />");
        xml.append("\r\n");
        xml.append("      <col column=\""+key+"\" displaytype=\"hidden\"/>");
        xml.append("\r\n");
        for(String field:fieldList){
            xml.append("      <col column=\""+field+"\" label=\""+field+"\"/>");
            xml.append("\r\n");
        }
        xml.append("    </display>");
        xml.append("\r\n");
        xml.append("    <sql>");
        xml.append("\r\n");
        xml.append("      <select>");
        xml.append("\r\n");
        xml.append("      <value><![CDATA[");
        xml.append("      select * from "+tableName);
        xml.append("       ]]></value>");
        xml.append("\r\n");
        xml.append("      </select>");
        xml.append("\r\n");
        xml.append("      <insert>");
        xml.append("\r\n");
        xml.append("      <![CDATA[");
        xml.append("\r\n");
        xml.append("      insert into "+tableName+"("+key+"=uuid{},");
        xml.append("\r\n");
        for(int i=0;i<fieldList.size();i++){
            xml.append("@{"+fieldList.get(i)+"}");
            if(i<fieldList.size()-1)
                xml.append(",");
        }
        xml.append(")");
        xml.append("\r\n");
        xml.append("      ]]>");
        xml.append("\r\n");
        xml.append("      </insert>");
        xml.append("\r\n");
        xml.append("      <update>");
        xml.append("\r\n");
        xml.append("      <![CDATA[");
        xml.append("\r\n");
        xml.append("      update "+tableName+"(");
        for(int i=0;i<fieldList.size();i++){
            xml.append("@{"+fieldList.get(i)+"}");
            if(i<fieldList.size()-1)
                xml.append(",");
        }
        xml.append(")");
        xml.append("\r\n");
        xml.append("      where "+key+"=@{"+key+"}");
        xml.append("\r\n");
        xml.append("      ]]>");
        xml.append("\r\n");
        xml.append("      </update>");
        xml.append("\r\n");
        xml.append("      <delete><![CDATA[delete from "+tableName+" where id=@{"+key+"}]]></delete>");
        xml.append("\r\n");
        xml.append("    </sql>");
        xml.append("\r\n");
        xml.append("  </report>");
        xml.append("\r\n");
        xml.append("</page>");
        xml.append("\r\n");
        xml.append("\r\n");
        //管理列表结束
        xml.append("</applications>");
        xml.append("\r\n");
        return xml.toString();
    }
}
