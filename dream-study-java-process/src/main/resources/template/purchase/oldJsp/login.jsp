<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>�û���¼</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <%
  	//�����û���¼��Ϣ
  	String loginflag = request.getParameter("loginflag");
  	if(loginflag!=null&&loginflag.equals("ok")){
  		String username = request.getParameter("username");
  		String userpwd = request.getParameter("userpwd");
  		if(username.equals("user1")||username.equals("user2")||username.equals("manager1")||username.equals("manager2")||username.equals("supermanager")||username.equals("cashier")){
  			request.getSession().setAttribute("loginuser",username);
  			//��¼�ɹ�ת��main.jspҳ��
  			response.sendRedirect("main.jsp");
  		}
  	}
   %>
  <body>
    <form action="login.jsp" method="get">
    	�û�����<input type="text" name="username"/><br/>
    	���룺<input type="password" name="userpwd"/><br/>
    	<input type="hidden" name="loginflag" value="ok"/>
    	<input type="submit" value="��¼"/>
    </form>
  </body>
</html>
