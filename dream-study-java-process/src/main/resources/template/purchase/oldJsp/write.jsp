<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>��д������</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <%@include file="include.jsp" %>
  
  <%
  	String isSubmit = request.getParameter("isSubmit");
  	if(isSubmit!=null&&isSubmit.equals("true")){
  		//�����ύ�ı�����
  		String title = request.getParameter("title");
  		String money_count = request.getParameter("money_count");
  		String remark = request.getParameter("remark");
  		String issueperson = request.getSession().getAttribute("loginuser").toString();
  		
  		JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
  		try{
  			//���õ�ǰ��¼�û�Ϊissueperson
  			jbpmContext.setActorId(issueperson);
  			
  			//��ȡ��Ϊpayment����ģ��
  			ProcessDefinition pd = jbpmContext.getGraphSession().findLatestProcessDefinition("payment");
  			ProcessInstance pi = pd.createProcessInstance();
  			ContextInstance ci = pi.getContextInstance();
  			
  			//���ñ����ύ��Ϊissueperson
  			ci.setVariable("issueperson",issueperson);
  			
  			//������ʼ�ڵ������ʵ��TaskInstance
  			TaskInstance ti = pi.getTaskMgmtInstance().createStartTaskInstance();
  			//������ʵ������д����ر���
  			ti.setVariable("title",title);
  			ti.setVariable("money_count",money_count);
  			ti.setVariable("remark",remark);
  			
  			//��������ʵ�������̵�Token�ͽ��벿�ž��������ڵ�
  			ti.end();
  		}finally{
  			jbpmContext.close();
  		}
  		out.print("<h1>���������ύ�ɹ�</h1>");
  		return;
  	}
   %>
  
  
  <body>
    <form action="write.jsp" method="post">
    	�������⣺<input type="text" name="title"/><br/>
    	������<input type="text" name="money_count"/><br/>
    	����˵����<input type="text" name="remark"/><br/>
    	<input type="hidden" name="isSubmit" value="true"/>
    	<input type="submit" value="�ύ����"/>
    </form>
  </body>
</html>
