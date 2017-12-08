package com.nge.statis;

import com.nge.dao.WrDao;
import com.nge.vo.Content;
import com.nge.vo.NameVO;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AutoClickUpdate {
    private static WrDao wrDao = new WrDao();
    private static final String CRONTAB_TIME_3 = "03";
    private static final String CRONTAB_TIME_14 = "14";
    private static final Logger logger = Logger.getLogger(Contentupd.class);

    public static void task() {


        logger.info("update content auto_click...");
        // 获取当前服务器时间小时整点数
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hour = sdf.format(date);
        System.out.println(hour);
        NameVO nameinfo = wrDao.secNameData(3);
        System.out.println(nameinfo.getName());
        String[] nameList = nameinfo.getName().split(";");

        for (String name : nameList) {
//            System.out.println("\n"+name+"\n");
            Content t_content = wrDao.secData(name);
            if (t_content != null) {

                String content = t_content.getContent();
//                System.out.println("\n"+content+"\n");
                if (content.contains("\"auto_click\"")) {
                    if (hour.equals(CRONTAB_TIME_3)) {
                        String newContent = content.substring(content.indexOf("\"auto_click\"") + 14);
                        System.out.println("newContent:"+newContent);
                        String adxVal = newContent.substring(0, newContent.indexOf("\""));
                        System.out.println("adxval:"+adxVal);
                        content = content.replace("\"auto_click\":\"" + adxVal + "\"", "\"auto_click\":\"1\"");
                        t_content.setContent(content);
                        wrDao.updateData(t_content);
                    }
                    if (hour.equals(CRONTAB_TIME_14)) {
                        String newContent = content.substring(content.indexOf("\"auto_click\"") + 14);
                        System.out.println("newContent:"+newContent);
                        String adxVal = newContent.substring(0, newContent.indexOf("\""));
                        System.out.println("adxval:"+adxVal);
                        content = content.replace("\"auto_click\":\"" + adxVal + "\"", "\"auto_click\":\"0\"");
                        t_content.setContent(content);
                        wrDao.updateData(t_content);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        task();

        // commit and close session
        wrDao.sessionClose();
    }
}
