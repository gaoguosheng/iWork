package com.ggs.util;

import com.wabacus.config.Config;

import java.sql.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: GGS
 * Date: 13-7-1
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public class DBUtil {
    public  Connection getConn(){
        return  Config.getInstance().getDataSource("ds")
                .getConnection();
    }


    /**
     * 关闭连接
     *
     * @param conn
     * @param stm
     * @param stm
     * @param rs
     */
    public void closeConn(Connection conn, Statement stm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接数据库返回一个List记录集的方法
     */
    public List queryForList(String sql) {
        List list = new ArrayList();
        Connection conn = this.getConn();
        Statement stm = null;
        ResultSet rs = null;
        ResultSetMetaData rms = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rms = rs.getMetaData();
            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= rms.getColumnCount(); i++) {
                    String field = rms.getColumnName(i).toLowerCase();
                    map.put(field, rs.getString(field));
                }
                list.add(map);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            this.closeConn(conn, stm, rs);
        }
        return list;
    }

    /**
     * 连接数据库返回一个List记录集的方法
     */
    public List queryForList(String sql, Object... o) {
        List list = new ArrayList();
        Connection conn = this.getConn();
        PreparedStatement stm = null;
        ResultSet rs = null;
        ResultSetMetaData rms = null;
        try {
            stm = conn.prepareStatement(sql);
            if (o != null) {
                for (int i = 0; i < o.length; i++) {
                    stm.setObject(i + 1, o[i]);
                }
            }
            rs = stm.executeQuery();
            rms = rs.getMetaData();
            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= rms.getColumnCount(); i++) {
                    String field = rms.getColumnName(i).toLowerCase();
                    map.put(field, rs.getString(field));
                }
                list.add(map);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            this.closeConn(conn, stm, rs);

        }
        return list;
    }


    /**
     * 连接数据库返回一条Map记录的方法
     */
    public Map queryForMap(String sql) {
        Map map = new HashMap();
        Connection conn = this.getConn();
        Statement stm = null;
        ResultSet rs = null;
        ResultSetMetaData rms = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            rms = rs.getMetaData();
            if (rs.next()) {
                for (int i = 1; i <= rms.getColumnCount(); i++) {
                    String field = rms.getColumnName(i).toLowerCase();
                    map.put(field, rs.getString(field));
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            this.closeConn(conn, stm, rs);

        }
        return map;
    }

    /**
     * 连接数据库返回一条Map记录的方法
     */
    public Map queryForMap(String sql, Object... o) {
        Map map = new HashMap();
        Connection conn = this.getConn();
        PreparedStatement stm = null;
        ResultSet rs = null;
        ResultSetMetaData rms = null;
        try {
            stm = conn.prepareStatement(sql);
            if (o != null) {
                for (int i = 0; i < o.length; i++) {
                    stm.setObject(i + 1, o[i]);
                }
            }
            rs = stm.executeQuery();
            rms = rs.getMetaData();
            if (rs.next()) {
                for (int i = 1; i <= rms.getColumnCount(); i++) {
                    String field = rms.getColumnName(i).toLowerCase();
                    map.put(field, rs.getString(field));
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            this.closeConn(conn, stm, rs);

        }
        return map;
    }

    /**
     * 统计sql记录数
     *
     * @param sql
     *            如select * from table
     * @return 返回记录数
     */
    public int executeCount(String sql) {
        int n = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConn();
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            rs.last();
            n = rs.getRow();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.closeConn(conn, stmt, rs);
        }
        return n;
    }

    /**
     * 统计sql记录数
     *
     * @param sql
     *            如select count(0) from table1
     * @return 返回记录数
     *
     */
    public int queryForInt(String sql) {
        int n = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next())
                n = rs.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.closeConn(conn, stmt, rs);
        }
        return n;
    }

    /**
     * 统计sql记录数
     *
     * @param sql
     *            如select count(0) from table1
     * @return 返回记录数
     *
     */
    public int queryForInt(String sql, Object... o) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int n = 0;
        try {
            conn = this.getConn();
            stmt = conn.prepareStatement(sql);
            if (o != null) {
                for (int i = 0; i < o.length; i++) {
                    stmt.setObject(i + 1, o[i]);
                }
            }
            rs = stmt.executeQuery();
            if (rs.next()) {
                n = rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.closeConn(conn, stmt, rs);
        }
        return n;
    }

    /**
     * 返回一个操作数据库更新的方法
     *
     * @param sql
     *            sql语句
     * @return 执行是否成功
     */
    public int update(String sql, Object... o) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int flag = 0;
        try {
            conn = this.getConn();
            stmt = conn.prepareStatement(sql);
            if (o != null) {
                for (int i = 0; i < o.length; i++) {
                    stmt.setObject(i + 1, o[i]);
                }
            }
            flag = stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = 0;
        } finally {
            this.closeConn(conn, stmt, null);
        }
        return flag;
    }

    /**
     * 返回一个操作数据库更新的方法
     *
     * @param sql
     *            sql语句
     * @return 返回是否执行成功
     */
    public int update(String sql) {
        Connection conn = null;
        Statement stmt = null;
        int flag = 0;
        try {
            conn = this.getConn();
            stmt = conn.createStatement();
            flag = stmt.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = 0;
        } finally {
            this.closeConn(conn, stmt, null);
        }
        return flag;
    }

    /**
     * 事务处理的方法
     *
     * @param sql
     *            多条sql数组
     * @return 返回是否成功
     */
    public boolean batchUpdate(String[] sql) {
        Connection conn = null;
        Statement stmt = null;
        boolean t = false;
        try {
            conn = this.getConn();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            for (int i = 0; i < sql.length; i++) {
                if (sql[i] != null) {
                    stmt.addBatch(sql[i]);
                }
            }
            int[] rtn = stmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
            t = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        } finally {
            this.closeConn(conn, stmt, null);
        }
        return t;
    }

    /**
     * 取所有字段名及类型
     *
     * @param sql
     *            sql语句
     * @return 返回结果集
     */
    public List getMetaData(String sql) {
        ArrayList list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData r = rs.getMetaData();
            String vFieldName = "";
            String vFieldValue = "";
            while (rs.next()) {
                Hashtable vTmp = new Hashtable();
                for (int i = 1; i <= r.getColumnCount(); i++) {
                    // 读取字段名
                    vFieldName = r.getColumnName(i).toLowerCase();
                    // 读取字段名相应值
                    switch (r.getColumnType(i)) {
                        // 字符型
                        case 12:
                            vFieldValue = rs.getString(i);
                            break;
                        // 数据型
                        case 2:
                            vFieldValue = rs.getString(i);
                            break;
                        // 日期型
                        case 93:
                            vFieldValue = rs.getString(i);
                            break;
                        default:
                            vFieldValue = rs.getString(i);
                            break;
                    }
                    if (vFieldValue == null) {
                        vFieldValue = "";
                    }
                    vTmp.put(vFieldName, vFieldValue);
                }
                list.add(vTmp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.closeConn(conn, stmt, rs);

        }
        return list;
    }

    public List getFields(String tableName) {
        ArrayList list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from "+tableName);
            ResultSetMetaData r = rs.getMetaData();
            for (int i = 1; i <= r.getColumnCount(); i++) {
                // 读取字段名
                String vFieldName = r.getColumnName(i).toLowerCase();
                list.add(vFieldName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.closeConn(conn, stmt, rs);

        }
        return list;
    }

}
