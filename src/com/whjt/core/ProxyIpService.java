package com.whjt.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enterprise.support.utility.Validation;

public class ProxyIpService {

    public enum ProxyIPType {
        item, transaction, comment, shop, collNum, browseNum
    };

    public static String getProxyIpInfo() {
        String[][] ipList = null;
        try {
            ipList = DataBaseToolService
                    .getQueryResultBySql("select top 1 id,ipAddress from laoa..proxyIpList WITH(NOLOCK)  where status=1  order by newid()");
            return ipList[0][1];
        } catch (Exception e) {
            e.printStackTrace();
            return "210.22.86.58:8080";
        }
    }

    public synchronized static String getProxyIpInfo_TR() {
        try {
            String[][] ipList = null;
            ipList = DataBaseToolService
                    .getQueryResultBySql("select top 1 id,ipAddress from laoa..proxyIp WITH(NOLOCK)  where status=1 and isUse=0 and taobaoStatus=1 order by newid()");
            if (ipList.length > 0) {
                DataBaseToolService
                        .excuteBySql(" update laoa..[proxyIp] set isUse=1 where id="
                                + ipList[0][0]);
                return ipList[0][1];
            } else {
                DataBaseToolService
                        .excuteBySql(" update laoa..[proxyIp] set isUse=0 where status=1");
                return getProxyIpInfo_TR();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "210.22.86.58:8080";
        }
    }

    public synchronized static String getProxyIpInfoTR() {
        try {
            String[][] ipList = null;
            ipList = DataBaseToolService
                    .getQueryResultBySql("select top 1 id,ipAddress from laoa..proxyIp WITH(NOLOCK)  where status=1 and isUse=0 order by newid()");
            if (ipList.length > 0) {
                DataBaseToolService
                        .excuteBySql(" update laoa..[proxyIp] set isUse=1 where id="
                                + ipList[0][0]);
                return ipList[0][1];
            } else {
                DataBaseToolService
                        .excuteBySql(" update laoa..[proxyIp] set isUse=0 where status=1");
                return getProxyIpInfoTR();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "210.22.86.58:8080";
        }
    }

    /**
     * 获取指定类型的IP
     * 
     * @param ipType
     * @return
     */
    public static String getProxyIpInfo(ProxyIPType ipType) {
        String sql = "SELECT TOP 1 id,ipAddress from laoa..proxyIpList_Test WITH(NOLOCK) where ";
        switch (ipType) {
        case item:
            sql += "  isItem=0";
            break;
        case transaction:
            sql += "  isTransaction=1";
            break;
        case comment:
            sql += "  isComment=1";
            break;
        case shop:
            sql += "  isShop=1";
            break;
        case collNum:
            sql += "  isCollNum=1";
            break;
        case browseNum:
            sql += "  isBrowseNum=1";
            break;
        default:
            break;
        }
        sql += " order by newid()";

        try {
            String[][] ipList = null;
            ipList = DataBaseToolService.getQueryResultBySql(sql);
            return ipList[0][1];
        } catch (Exception e) {
            e.printStackTrace();
            return "210.22.86.58:8080";
        }
    }

    public static List<String> getValidIpAddressAndPort() {
        List<String> list = new ArrayList<String>();
        String ipContent = getProxyIpInfo();
        if (!Validation.isEmpty(ipContent)) {
            String[] ipAdd = ipContent.split(":");
            list.add(ipAdd[0]);
            list.add(ipAdd[1]);
            return list;
        } else
            return null;
    }

    /**
     * 将可用IP插入到数据库
     * 
     * @param ips
     *            带端口号的IP列表
     */
    public static void insertProxyIPs(List<String> ips) {
        StringBuilder sb = new StringBuilder();
        String t = "";
        sb.append("INSERT INTO laoa..proxyIpList_Test1 (ipAddress,status)");
        for (int i = 0, size = ips.size(); i < size; i++) {
            sb.append(t);
            sb.append("SELECT '");
            sb.append(ips.get(i));
            sb.append("',0");
            t = " UNION ALL ";
        }
        try {
            DataBaseToolService.excuteBySql(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有状态为1的IP，减少查询数据库的次数
     * 
     * @return
     */
    public static String[][] getAllUsableProxyIp() {

        String[][] ipList = null;

        try {
            ipList = DataBaseToolService
                    .getQueryResultBySql("select id, ipAddress from laoa..proxyIp WITH(NOLOCK) where status = 1 order by newid()");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ipList;
    }

    /**
     * 获取指定数量状态为1的IP，减少查询数据库的次数
     * 
     * @return
     */
    public synchronized static String[][] getSomeUsableProxyIp(int num) {

        String[][] ipList = null;

        try {
            // ipList = DataBaseToolService
            // .getQueryResultBySql("select top "
            // + num
            // + " id, ipAddress from OPENDATASOURCE("
            // + "'SQLOLEDB',"
            // +
            // "'Data Source=112.65.211.70,49483;User ID=LaoAdeveloper;Password=Laoadevelop2014'"
            // + ").LaoA.dbo.proxyIpList where status = 1 order by newid()");

            // 查询status = 1、isUse = 0和taobaoStatus = 1的IP
            ipList = DataBaseToolService
                    .getQueryResultBySql("select top "
                            + num
                            + " id, ipAddress from OPENDATASOURCE("
                            + "'SQLOLEDB',"
                            + "'Data Source=101.231.74.130,49281;User ID=sa;Password=Datamining2014'"
                            + ").ProxyIP8.dbo.proxyIp where status = 1 and isUse = 0 and taobaoStatus = 1 order by newid()");

            // 更新获取到的IP为正在使用状态isUse = 1，淘宝不允许IP重复使用
            StringBuilder ips = new StringBuilder("");
            String updateSQL = "";
            if (0 < ipList.length) {

                ips.append("(");

                for (int i = 0; i < ipList.length; i++) {

                    if (i == ipList.length - 1) {

                        ips.append(ipList[i][0] + ")");
                    } else {

                        ips.append(ipList[i][0] + ", ");
                    }
                }

                // 拿到之后把isUse置为1，标志该IP正在使用
                updateSQL = "update OPENDATASOURCE("
                        + "'SQLOLEDB',"
                        + "'Data Source=101.231.74.130,49281;User ID=sa;Password=Datamining2014'"
                        + ").ProxyIP8.dbo.proxyIp" + " set isUse = 1"
                        + " where id in" + ips;

                DataBaseToolService.excuteBySql(updateSQL);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ipList;
    }

    /**
     * 
     * @Title: updateIPStatus
     * @Description: 当IP不能对淘宝使用时，更新其isUse=0,
     *               taobaoStatus=0，并将taobaoUpdateTime设为最新时间
     * @author Grass
     * @param ip
     * @throws
     */
    public synchronized static void updateIPStatus(String ip) {

        String sql = "update OPENDATASOURCE("
                + "'SQLOLEDB',"
                + "'Data Source=101.231.74.130,49281;User ID=sa;Password=Datamining2014'"
                + ").ProxyIP8.dbo.proxyIp"
                + " set isUse = 0, taobaoStatus = 0, taobaoUpdateTime = getDate()"
                + " where ipAddress = '" + ip + "'";

        try {
            DataBaseToolService.excuteBySql(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(ProxyIpService.getProxyIpInfo(ProxyIPType.item));
    }
}
