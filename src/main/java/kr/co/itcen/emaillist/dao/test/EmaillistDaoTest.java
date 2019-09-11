package kr.co.itcen.emaillist.dao.test;

import java.util.List;

import kr.co.itcen.emaillist.dao.EmaillistDao;
import kr.co.itcen.emaillist.vo.EmaillistVo;

public class EmaillistDaoTest {
	public static void main(String[] args) {
		insertTest();
		getListTest();
		
		//deleteAll();
	}
	private static void deleteAll() {
		new EmaillistDao().delete();		
	}
	
	public static void insertTest() {
		EmaillistVo vo = new EmaillistVo();		
		vo.setFirstName("현곤");
		vo.setLastName("김");
		vo.setEmail("kimhg93@gmail.com");
		
		new EmaillistDao().insert(vo);
		
	}
	
	public static void getListTest() {
		List<EmaillistVo> list = new EmaillistDao().getList();
		for(EmaillistVo vo : list) {
			System.out.println(vo);
		}
	}
}
