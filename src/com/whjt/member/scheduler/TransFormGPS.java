package com.whjt.member.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.JsonKit;
import com.whjt.core.DataBaseToolService;
import com.whjt.util.Block;

public class TransFormGPS implements Runnable {
	   private String url="";
	   private static SimpleDateFormat dateFormat = new SimpleDateFormat(
	            "yyyy-MM-dd HH:mm:ss");
	   public TransFormGPS(String url){
		   this.url=url;
	   }

    /**
     * 2017年7月20日12:51:44
     * zuoqb
     * 实时查询未转换GPS坐标的出租车信息
     */
    public void run() {
    	String taxisql="select sim,longitude,latitude from dm_taxi_location_realtime where transform_status='0' ";
    	//String taxisql="select sim,longitude,latitude from dm_taxi_location_realtime  ";
    	  String[][] taxi;
          List<Block> list=new ArrayList<Block>();
          int pageSize=10,totalPage=0;
          try {
               taxi=DataBaseToolService.getQueryResultBySql(taxisql);
               if (taxi.length>0){
              	 for(int x=0;x<taxi.length;x++){
              		 Block block=new Block(taxi[x][0],taxi[x][1],taxi[x][2]);
              		 list.add(block);
              	 }
               };
               //每10个为一组调用api
               totalPage=list.size()/pageSize;
               if(list.size()%pageSize>0){
              	 totalPage++;
               }
               for(int page=0;page<totalPage;page++){
              	 List<Block> currentData=new ArrayList<Block>();
              	 if(page==totalPage-1){
              		 currentData=list.subList(page*pageSize,list.size());
              	 }else{
              		 currentData=list.subList(page*pageSize, page*pageSize+pageSize);
              	 }
              	 String params=JsonKit.toJson(currentData);
              	 //通过线程 并发执行  但是由于并发太多 需要主动休眠（效率比顺序执行高）
              	 
              	 Thread rthread = new Thread(new UpdateTaxiGPS(params,url));
              	 rthread.start();
              	 //必须休眠 不然线程太多会报错
              	 Thread.sleep(2000);
              	 //下面是顺序执行 速度慢
          		/* String sql="";
              	 List<BaiduLocation> location=HttpRequestUtils.readHTmlByHtmlUnitMany(params,url);
              	 for(BaiduLocation loc:location){
              		 //更新信息
              		 sql="update dm_taxi_location_realtime set baidu_longitude='"+
              				 loc.getLng()+"',"+
              				 "baidu_latitude='"+loc.getLat()+"',"+
              				 "baidu_x='"+loc.getX()+"',"+
              				 "baidu_y='"+loc.getY()+"',"+
              				 "transform_status='1',"+
              				 "transform_time='"+dateFormat.format(new Date())+"'"
              				 +" where sim='"+loc.getSim()+"'";
              		 DataBaseToolService.excuteBySql(sql);
              		 System.out.println("更新成功!出租车sim为:"+loc.getSim());
              	 }*/
               }
               //线程休眠5分钟
              // Thread.sleep(1000*60*10);
			} catch (Exception e) {
				e.printStackTrace();
			}
    }

}
