package com.whjt.member.scheduler;

/**
 * Created by Kevin.Li on 2017/6/14.
 */
import com.whjt.core.DataBaseToolService;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;

public class LogReader implements Runnable {
    private File logFile = null;
    private long lastTimeFileSize = 0; // 涓婃鏂囦欢澶у皬
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public LogReader(File logFile) {
        this.logFile = logFile;
        lastTimeFileSize = logFile.length();
        //lastTimeFileSize = 0;
    }

    /**
     * 瀹炴椂杈撳嚭鏃ュ織淇℃伅
     */
    public void run() {
        String taxisql="select * from dm_taxi_location_realtime where 1<>1 ";
        String operationsql="select * from taxi_operation_information_realtime where 1<>1 ";
        String trucksql="select * from dm_truck_location_realtime where 1<>1 ";
        while (true) {
            RandomAccessFile randomFile=null;
            try {
                randomFile = new RandomAccessFile(logFile, "r");
                randomFile.seek(lastTimeFileSize);
                String tmp = null;
                String[] atmp;
                String[][] taxi,truck,tp;
                String sql="",dailysql="";
                while ((tmp = randomFile.readLine()) != null) {
                    tmp=new String(tmp.getBytes("8859_1"), "utf-8");//缂栫爜杞崲
                    //System.out.println(tmp);
                    atmp = tmp.split(",");
                    try{
                        if (atmp[1].equals("TAXI")){
                            if (atmp[2].equals("LOCATION")){
                                //insert the record to daily table
                                dailysql="insert into taxi_location_information_daily(recivetime,sim,satellitetime,islocation,longitude,latitude,speed,direction,taxistatus,alarmstatus,mileage) values ("
                                        +"'"+atmp[0]+"',"
                                        +"'"+atmp[3]+"',"
                                        +"'"+atmp[4]+"',"
                                        +"'"+atmp[5]+"',"
                                        +"'"+atmp[6]+"',"
                                        +"'"+atmp[7]+"',"
                                        +"'"+atmp[8]+"',"
                                        +"'"+atmp[9]+"',"
                                        +"'"+atmp[10]+"',"
                                        +"'"+atmp[11]+"',"
                                        +"'"+atmp[12]+"')";
                                DataBaseToolService.excuteBySql(dailysql);
                                //
                                sql=taxisql+"or sim='"+atmp[3]+"'";
                                taxi=DataBaseToolService.getQueryResultBySql(sql);
                                if (taxi.length>0){
                                    //update
                                    sql="update dm_taxi_location_realtime set satellitetime='"+
                                            atmp[4]+"',"+
                                            "islocation='"+atmp[5]+"',"+
                                            "longitude='"+atmp[6]+"',"+
                                            "latitude='"+atmp[7]+"',"+
                                            "speed='"+atmp[8]+"',"+
                                            "direction='"+atmp[9]+"',"+
                                            "taxistatus='"+atmp[10]+"',"+
                                            "alarmstatus='"+atmp[11]+"',"+
                                            "mileage='"+atmp[12]+"',"+
                                            "recivetime='"+atmp[0]+"' where sim='"+atmp[3]+"'";
                                    DataBaseToolService.excuteBySql(sql);
                                }else
                                {
                                    //insert
                                    sql="insert into dm_taxi_location_realtime(recivetime,sim,satellitetime,islocation,longitude,latitude,speed,direction,taxistatus,alarmstatus,mileage) values ("
                                        +"'"+atmp[0]+"',"
                                            +"'"+atmp[3]+"',"
                                            +"'"+atmp[4]+"',"
                                            +"'"+atmp[5]+"',"
                                            +"'"+atmp[6]+"',"
                                            +"'"+atmp[7]+"',"
                                            +"'"+atmp[8]+"',"
                                            +"'"+atmp[9]+"',"
                                            +"'"+atmp[10]+"',"
                                            +"'"+atmp[11]+"',"
                                            +"'"+atmp[12]+"')";
                                    DataBaseToolService.excuteBySql(sql);
                                }
                            }
                            if (atmp[2].equals("OPERATION")){
                                sql=operationsql+"or sim='"+atmp[3]+"'";
                                tp=DataBaseToolService.getQueryResultBySql(sql);
                                if (tp.length>0){
                                    //update
                                    sql="update taxi_operation_information_realtime set time='"+
                                            atmp[4]+"',"+
                                            "empty='"+atmp[5]+"',"+
                                            "heavy='"+atmp[6]+"',"+
                                            "price='"+atmp[7]+"',"+
                                            "totalSum='"+atmp[8]+"',"+
                                            "aboardtime='"+atmp[9]+"',"+
                                            "debustime='"+atmp[10]+"',"+
                                            "SYS_CREATED_TIME='"+atmp[0]+
                                            "' where sim='"+atmp[3]+"'";
                                    DataBaseToolService.excuteBySql(sql);
                                }else{
                                    //insert
                                    sql="insert into taxi_operation_information_realtime(sim,time,empty,heavy,price,totalSum,aboardtime,debustime,SYS_CREATED_TIME) values ("
                                            +"'"+atmp[3]+"',"
                                            +"'"+atmp[4]+"',"
                                            +"'"+atmp[5]+"',"
                                            +"'"+atmp[6]+"',"
                                            +"'"+atmp[7]+"',"
                                            +"'"+atmp[8]+"',"
                                            +"'"+atmp[9]+"',"
                                            +"'"+atmp[10]+"',"
                                            +"'"+atmp[0]+"')";
                                    DataBaseToolService.excuteBySql(sql);
                                }
                            }
                            if (atmp[2].equals("TRANSFER")){

                            }
                        }else if(atmp[1].equals("TRUCK")){
                            if (atmp[2].equals("LOCATION")){
                                sql=trucksql+"or vehicle_no='"+atmp[4]+"'";
                                truck=DataBaseToolService.getQueryResultBySql(sql);
                                if (truck.length>0){
                                    //update
                                    sql="update dm_truck_location_realtime set satellitetime='"+
                                            atmp[3]+"',"+
                                            "dirver_name='"+atmp[5]+"',"+
                                            "dirver_phone_number='"+atmp[6]+"',"+
                                            "lon='"+atmp[7]+"',"+
                                            "lat='"+atmp[8]+"',"+
                                            "speed='"+atmp[9]+"',"+
                                            "total_mileage='"+atmp[10]+"',"+
                                            "state='"+atmp[11]+"',"+
                                            "alarm='"+atmp[12]+"',"+
                                            "SYS_CREATED_TIME='"+atmp[0]+"' where vehicle_no='"+atmp[4]+"'";
                                    DataBaseToolService.excuteBySql(sql);
                                }else
                                {
                                    //insert
                                    sql="insert into dm_truck_location_realtime(SYS_CREATED_TIME,vehicle_no,satellitetime,dirver_name,dirver_phone_number,lon,lat,speed,total_mileage,state,alarm) values ("
                                            +"'"+atmp[0]+"',"
                                            +"'"+atmp[4]+"',"
                                            +"'"+atmp[3]+"',"
                                            +"'"+atmp[5]+"',"
                                            +"'"+atmp[6]+"',"
                                            +"'"+atmp[7]+"',"
                                            +"'"+atmp[8]+"',"
                                            +"'"+atmp[9]+"',"
                                            +"'"+atmp[10]+"',"
                                            +"'"+atmp[11]+"',"
                                            +"'"+atmp[12]+"')";
                                    DataBaseToolService.excuteBySql(sql);
                                }
                            }
                        }
                    }
                    catch (Exception exp){
                        System.out.println(exp);
                    }
                }
                lastTimeFileSize = randomFile.length();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println(e);
            }finally {
                try {
                    if (randomFile != null) {
                        randomFile.close();
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

}
