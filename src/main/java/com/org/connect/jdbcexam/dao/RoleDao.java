package com.org.connect.jdbcexam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.org.connect.jdbcexam.Role;

public class RoleDao {
	 private static String dburl = "null";
	 private static String dbUser = "null";
	 private static String dbpasswd = "null";
	
	public Role getRole(Integer roleId) {
		Role role = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //메모리에 driver 로드
			conn = DriverManager.getConnection(dburl,dbUser,dbpasswd); //db연결
			String sql = "SELECT role_id, description FROM role WHERE role_id = ?"; //sql
			ps = conn.prepareStatement(sql);
			ps.setInt(1, roleId);
			rs = ps.executeQuery();
			if(rs.next()) {
				int id = rs.getInt(1);
				String description = rs.getString(2);
				role = new Role(id, description);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return role;
	}
	
	//Insert
	public int addRole(Role role) {
		int insertCount=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO role (role_id, description) VALUES(?,?)";
		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, role.getRoleId());
			ps.setString(2,  role.getDescription());
			insertCount = ps.executeUpdate();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return insertCount;
	}
	
	public List<Role> getRoles() {
		List<Role> list = new ArrayList<>();
		//메모리에 Driver 설치
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql = "SELECT description, role_id FROM role order by role_id desc"; //쿼리문 작성
		try(Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			PreparedStatement ps = conn.prepareStatement(sql)) {
			try(ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					String description = rs.getString(1);
					int id = rs.getInt("role_id");
					Role role = new Role(id, description);
					list.add(role);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return list;
	}
	
	public int deleteRole(int roleId) {
		int deleteCount = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			String sql = "DELETE FROM role WHERE role_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, roleId);
			deleteCount = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (Exception ex) {
					
				}
			}
		}
		return deleteCount;
	}
	
	public int updateRole(Role role) {
		int updateCount = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			String sql = "UPDATE ROLE SET DESCRIPTION = ? WHERE role_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, role.getDescription());
			ps.setInt(2, role.getRoleId());
			updateCount = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch(Exception ex) {
					
				}
			}
			
			if(conn!=null) {
				try {
					conn.close();
				} catch(Exception ex) {}
			}
		}
		
		return updateCount;
	}
	
}
