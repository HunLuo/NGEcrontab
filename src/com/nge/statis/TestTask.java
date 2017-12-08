package com.nge.statis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.nge.vo.Content;
import org.apache.log4j.Logger;

import com.nge.dao.WrDao;
import com.nge.util.EncryptionDecryption;

public class TestTask {

	private static List<Content> list = new ArrayList<Content>();
	private static WrDao wrDao = new WrDao();

	private static final Logger logger = Logger.getLogger(TestTask.class);
	private static final String key = "goodluck";
	
	public static void task() throws Exception {

		logger.info("writejson定时任务执行。。。");
		list = wrDao.selectData();
		for (Content m : list) {

			String[] packageNameList = m.getName().split(":");
			String res = m.getContent();

			for (String packageName : packageNameList) {
//				String encodePath = "/data/tools/nginx/html/pro/" + packageName;
				String	path = "/data/tools/nginx/html/app/" + packageName;
//				String path = "D:/result/" + packageName;

//				String xor_encodedStr = XORUtil.getInstance().encode(res, key);

				String encodedStr = EncryptionDecryption.encrypt(key,res);
//				String dencodedStr = EncryptionDecryption.decrypt("goodluck",encodedStr);
//				System.out.println(encodedStr);
//				System.out.println(dencodedStr);

				System.out.println(path);
				File file = new File(path);
				BufferedWriter bw = null;
				File content = new File(path + "/" + "conf.txt");
				if (!file.exists()) {
					file.mkdir();
				}
				if(!content.exists())
					content.createNewFile();

					bw = new BufferedWriter(new FileWriter(new File(path + "/" + "conf.txt")));
					bw.write(res);
					bw.close();
				}


			}
		}


//	public static void main(String[] args) throws Exception {
//
//		task();
//		// ApplicationContext context = new
//		// ClassPathXmlApplicationContext("classpath:/application.xml");
//	}
}
