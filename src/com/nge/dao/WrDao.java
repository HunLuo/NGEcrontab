package com.nge.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.nge.vo.Content;
import com.nge.vo.NameVO;

public class WrDao {

	SqlSession session = null;

	public WrDao() {

		// mybatis≈‰÷√Œƒº˛
		String resource = "SqlMapConfig.xml";
		InputStream resourceAsStream = null;
		try {
			resourceAsStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		session = sessionFactory.openSession();
	}

	public List<Content> selectData() {
		// TODO Auto-generated method stub

		// WrDao mapper = session.getMapper(WrDao.class);
		// List<Content> data = mapper.selectData();
		List<Content> data = session.selectList("writeandread.select1");
		session.commit();
		session.close();

		return data;

	}
	
	
	public Content secData(String name) {

		Content data = session.selectOne("writeandread.select2",name);
		return data;
	}

	public NameVO secNameData(int status) {
		NameVO data = session.selectOne("writeandread.selectName",status);
		return data;
	}

	public void updateData(Content content) {

		session.update("writeandread.upd",content);
	}

	public void sessionClose() {
		session.commit();
		session.close();
	}
}
