//package com.wy.jbpm;
//
//import org.jbpm.api.model.OpenExecution;
//import org.jbpm.api.task.Assignable;
//import org.jbpm.api.task.AssignmentHandler;
//
///**
// * 此类实现jbmp的AssignmentHandler,当流程的某一步需要赋值时,在流程的jpdl.xml文件的需要赋值的节点增加如下节点:
// * <assignmeng-handler class="com.collogate.jbpm.Assign"></assignmeng-handler>
// * 或者在jbpm流程中获得当前实例的pid对他进行设置,见JbmpEx5
// *
// * @author ParadiseWY
// * @date 2018-03-14 23:39:29
// * @git {@link https://github.com/mygodness100}
// */
//public class SingleAssignHandler implements AssignmentHandler {
//
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public void assign(Assignable assign, OpenExecution exe) throws Exception {
//		assign.setAssignee(exe.getVariable("zongUser").toString());
//	}
//}