package com.rimi.ctibet.web.dao.daoimpl;

public class StudentTst {
		private String name;
		private String sex;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public static void main(String[] args) throws ClassNotFoundException {
			System.out.println(Class.forName("com.rimi.ctibet.web.dao.daoimpl.StudentTst"));
		}
		
}
