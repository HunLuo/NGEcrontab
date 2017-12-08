package com.nge.statis;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nge.vo.Content;
import org.apache.log4j.Logger;

import com.nge.dao.WrDao;
import com.nge.vo.NameVO;

public class Contentupd {

	private static WrDao wrDao = new WrDao();
	private static final String CRONTAB_TIME_2 = "02";
	private static final String CRONTAB_TIME_17 = "17";
	private static final Logger logger = Logger.getLogger(Contentupd.class);
	
	public static void task() {

		
		logger.info("update content...");
		// 获取当前服务器时间小时整点数
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String hour = sdf.format(date);
        System.out.println(hour);
        NameVO nameinfo = wrDao.secNameData(1);
		System.out.println(nameinfo.getName());
		String[] nameList = nameinfo.getName().split(";");

		for (String name : nameList) {
//            System.out.println("\n"+name+"\n");
            Content t_content = wrDao.secData(name);
			if (t_content != null) {
				
				String content = t_content.getContent();
//				System.out.println("\n"+content+"\n");
				if (content.contains("\"auto_click_adx\"")) {
					if (hour.equals(CRONTAB_TIME_2)) {
						System.out.println(content);
						String newContent = content.substring(content.indexOf("\"auto_click_adx\"") + 18);
						String adxVal = newContent.substring(0, newContent.indexOf("\""));
						content = content.replace("\"auto_click_adx\":\"" + adxVal + "\"", "\"auto_click_adx\":\"1\"");

						t_content.setContent(content);
						wrDao.updateData(t_content);
					}
					if (hour.equals(CRONTAB_TIME_17)) {
						System.out.println(content);
						String newContent = content.substring(content.indexOf("auto_click_adx") + 17);
						String adxVal = newContent.substring(0, newContent.indexOf("\""));
						content = content.replace("\"auto_click_adx\":\"" + adxVal + "\"", "\"auto_click_adx\":\"0\"");

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
