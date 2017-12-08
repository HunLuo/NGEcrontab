package com.nge.statis;

import com.nge.dao.WrDao;
import com.nge.vo.Content;
import com.nge.vo.NameVO;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InterWeightUpdate {
    private static WrDao wrDao = new WrDao();
    private static final String CRONTAB_TIME_7 = "07";
    private static final String CRONTAB_TIME_12 = "12";
    private static final Logger logger = Logger.getLogger(Contentupd.class);

    public static void task() {


        logger.info("update content inter_weight...");
        // 获取当前服务器时间小时整点数
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hour = sdf.format(date);
        System.out.println(hour);
        NameVO nameinfo = wrDao.secNameData(2);
        System.out.println(nameinfo.getName());
        String[] nameList = nameinfo.getName().split(";");

        for (String name : nameList) {
//            System.out.println("\n"+name+"\n");
            Content t_content = wrDao.secData(name);
            if (t_content != null) {

                String content = t_content.getContent();
//                System.out.println("\n"+content+"\n");
                if (content.contains("\"inter_weight\"")) {
                    if (hour.equals(CRONTAB_TIME_7)) {
                        String newContent = content.substring(content.indexOf("\"inter_weight\"") + 16);
                        System.out.println("newContent:"+newContent);
                        String adxVal = newContent.substring(0, newContent.indexOf("\""));
                        System.out.println("adxval:"+adxVal);
                        content = content.replace("\"inter_weight\":\"" + adxVal + "\"", "\"inter_weight\":\"2,1,3,4\"");
                        t_content.setContent(content);
                        wrDao.updateData(t_content);
                    }
                    if (hour.equals(CRONTAB_TIME_12)) {
                        String newContent = content.substring(content.indexOf("\"inter_weight\"") + 16);
                        String adxVal = newContent.substring(0, newContent.indexOf("\""));
                        content = content.replace("\"inter_weight\":\"" + adxVal + "\"", "\"inter_weight\":\"1,2,3,4\"");
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
