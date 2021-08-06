<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@page import="java.rmi.dgc.VMID"%>
<%@ include file="include.jsp" %>
<%@ page import="test.dao.*" %>
<%
	String title = "";
	String remark = "";
	String isSubmit = request.getParameter("isSubmit");
	String purchaseId = request.getParameter("purchaseId");
	String taskId = request.getParameter("taskId");
	BusinessDAO dao = new BusinessDAO();
	String issueperson = request.getSession().getAttribute("loginuser").toString();
	if(isSubmit!=null&&isSubmit.equals("true")){
		title = request.getParameter("title");
		remark = request.getParameter("remark");
		String sql = null;
		JbpmContext jbpmContext = dao.createJbpmContext();
		if(purchaseId!=null&&!purchaseId.equals("")){
			//�޸Ĳɹ�����
			sql = "update test_purchase set title=?,remark=? where id=?";
			dao.saveOrUpdateOrDelete(sql,new Object[]{purchaseId,title,remark});
			
			//�����޸�����
			TaskInstance ti = jbpmContext.getTaskInstance(Long.parseLong(taskId));
			ti.end();
			out.println("<h1>�޸Ĳɹ��������ɹ�</h1>");
		}else{
			//�����ɹ���
			sql = "insert into test_purchase values(?,?,?)";
			purchaseId = new VMID().toString();
			dao.saveOrUpdateOrDelete(sql,new Object[]{purchaseId,title,remark});
			
			//��ʼ����
			//���õ�ǰ�ĵ�¼�û�Ϊissueperson
			jbpmContext.setActorId(issueperson);
			
			//��ȡ����Ϊpurchase������ģ��
			ProcessDefinition pd = jbpmContext.getGraphSession().findLatestProcessDefinition("purchase");
			ProcessInstance pi = pd.createProcessInstance();
			ContextInstance ci = pi.getContextInstance();
			
			//���ñ����ύ��Ϊissueperson
			ci.setVariable("issueperson",issueperson);
			
			//������ʼ�ڵ��TaskInstance
			TaskInstance ti = pi.getTaskMgmtInstance().createStartTaskInstance();
			//������ʵ������д����ر���
			ti.setVariable("purchaseId",purchaseId);
			ti.end();
			
			out.println("<h1>�����ɹ��������ɹ�</h1>");
		}
		jbpmContext.close();
		return;
	}else{
		//�Ե�ǰ�ɹ������޸ģ�ȡ����ǰ�ɹ���������
		if(purchaseId!=null&&!purchaseId.equals("")){
			String sql = "select * from test_purchase where id=?";
			Map map = (Map)dao.query(sql,new Object[]{purchaseId}).get(0);
			title = map.get("title").toString();
			remark = map.get("remark").toString();
		}else{
			purchaseId = "";
		}
	}
 %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>�ɹ�����ҳ��</title>
    
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
    <form action="write-purchase.jsp" method="post"> 
    	�ɹ����⣺<input type="text" name="title" value="<%=title %>"/><br/>
    	�ɹ����ݣ�<input type="text" name="remark" value="<%=remark %>"/><br/>
    	<input type="hidden" name="isSubmit" value="true"/>
    	<input type="hidden" name="taskId" value="<%=taskId %>"/>
    	<input type="hidden" name="purchaseId" value="<%=purchaseId %>"/>
    	<input type="submit" value="�ύ�ɹ���"/>
    </form>
  </body>
</html>
