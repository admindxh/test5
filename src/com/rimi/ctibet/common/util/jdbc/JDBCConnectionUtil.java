package com.rimi.ctibet.common.util.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alibaba.druid.pool.DruidDataSource;

public class JDBCConnectionUtil {

	private static DruidDataSource dataSource;

	public static DruidDataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DruidDataSource dataSource) {
		JDBCConnectionUtil.dataSource = dataSource;
	}

	/**
	 * 获取通用连接
	 * 
	 * @return
	 */
	public static Connection getConnention() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 释放连接
	 */
	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null) {
				rs.close(); // 关闭结果集
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close(); // 关闭Statement
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close(); // 关闭连接
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
