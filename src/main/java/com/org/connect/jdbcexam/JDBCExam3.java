package com.org.connect.jdbcexam;

import java.util.List;

import com.org.connect.jdbcexam.dao.RoleDao;

public class JDBCExam3 {
    public static void main(String[] args) {
        RoleDao dao=new RoleDao();
        List<Role> list = dao.getRoles();
        for(Role role : list) {
            System.out.println(role);
        }
    }
}
