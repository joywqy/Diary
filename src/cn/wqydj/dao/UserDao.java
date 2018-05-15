package cn.wqydj.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.wqydj.domain.User;
import cn.wqydj.utils.MD5Util;
import sun.management.ExtendedPlatformComponent;



public class UserDao {
	public User login(Connection conn,User user) throws Exception{
		User resultUser = null;
		String sql = "select * from user where userName=? and password=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUserName());
		ps.setString(2, MD5Util.EncoderPwdByMd5(user.getPassword()));
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			resultUser = new User();
			resultUser.setUserId(rs.getInt("userId"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
			return resultUser;
		}
		return resultUser;
		
	}
}
