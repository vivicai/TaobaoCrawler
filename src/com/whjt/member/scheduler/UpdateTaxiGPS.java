package com.whjt.member.scheduler;

/**
 * Created by Kevin.Li on 2017/6/14.
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whjt.core.DataBaseToolService;
import com.whjt.util.BaiduLocation;
import com.whjt.util.HttpRequestUtils;

public class UpdateTaxiGPS implements Runnable {
    private String params;
    private String url;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public UpdateTaxiGPS(String params,String url){
    	this.params=params;
    	this.url=url;
    }
    /**
     * 实时输出日志信息
     */
    public void run() {
        try {
        	 List<BaiduLocation> location=HttpRequestUtils.readHTmlByHtmlUnitMany(params,url);
        	 List<String> sqls=new ArrayList<String>();
        	 for(BaiduLocation loc:location){
        		 //更新信息
        		 String sql="update dm_taxi_location_realtime set baidu_longitude='"+
        				 loc.getLng()+"',"+
        				 "baidu_latitude='"+loc.getLat()+"',"+
        				 "baidu_x='"+loc.getX()+"',"+
        				 "baidu_y='"+loc.getY()+"',"+
        				 "transform_status='1',"+
        				 "transform_time='"+dateFormat.format(new Date())+"'"
        				 +" where sim='"+loc.getSim()+"'";
        		 sqls.add(sql);
        	 }
        	 DataBaseToolService.excuteBySqlBatch(sqls);
        	 System.out.println("批量更新成功!");
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

}
