<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@ include file="include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>����ҳ��</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <%
    	JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
    	try{
    		long taskId = Long.parseLong(request.getParameter("taskId"));
    		TaskInstance ti = jbpmContext.getTaskInstance(taskId);
    		String isSubmit = request.getParameter("isSubmit");
    		if(isSubmit!=null&&isSubmit.equals("true")){
    			//�жϵ�ǰ�ǲ��ž������������ܾ����������ֱ�д�벻ͬ�����̱���
    			String result = request.getParameter("approve_result");
    			if(ti.getDescription().equals("payment.manager.approve")){
    				ti.getContextInstance().setVariable("manager_approve_result",result);
    				if(result.equals("1")){
    					//���ž����������Ϊͬ��
    					ti.end("agree");
    				}else{
    					ti.end("disagree");
    				}
    				out.println("<h1>���ž����������!</h1>");
    			}else{
    				ti.getContextInstance().setVariable("super_manager_approve_result",result);
    				ti.end();
    				out.println("<h1>�ܾ����������!</h1>");
    			}
    		}else{
    			String title = ti.getVariable("title").toString();
    			String money_count = ti.getVariable("money_count").toString();
    			String remark = ti.getVariable("remark").toString();
    			String issueperson = ti.getVariable("issueperson").toString();
    			%>
    			�����ˣ�<%=issueperson %><br/>
    			�������⣺<%=title %><br/>
    			������<%=money_count %><br/>
    			����˵����<%=remark %><br/>
    			
    			<form action="manager-process.jsp" method="post">
    				<input type="radio" name="approve_result" value="1" checked="checked"/>ͬ��&nbsp;&nbsp;
    				<input type="radio" name="approve_result" value="0"/>��ͬ��<br/>
    				<input type="hidden" value="true" name="isSubmit"/>
    				<input type="hidden" value="<%=taskId %>" name="taskId"/>
    				<input type="submit" value="�ύ�������"/>
    			</form>
    			
    			<%
    		}
    	}finally{
    		jbpmContext.close();
    	}
     %>
  </body>
</html>
