package cn.wqydj.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.wqydj.dao.UserDao;
import cn.wqydj.domain.User;
import cn.wqydj.utils.DbUtil;

public class LoginServlet extends HttpServlet{
	private User user;
	private UserDao userDao = new UserDao();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html,charset=utf-8");
		HttpSession session = req.getSession();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");
		Connection conn = DbUtil.getConn();
		try {
			user = new User();
			user.setUserName(username);
			user.setPassword(password);
			 User resultUser = userDao.login(conn, user);
			if(resultUser==null){
				req.setAttribute("user", user);
				req.setAttribute("error", "用户名或密码错误！");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			}else{
				if("remember-me".equals(remember)){
					rememberMe(username,password,resp);
				}
				session.setAttribute("resultUser", resultUser);
				resp.sendRedirect("main.jsp");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtil.close(conn);
		}
		
	}
	private void rememberMe(String userName,String password,HttpServletResponse response){
		Cookie user=new Cookie("user",userName+"-"+password);
		user.setMaxAge(1*60*60*24*7);
		response.addCookie(user);
	}
}
