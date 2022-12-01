package com.leeyerim.springBoard.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ViewNameInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//���� ������ �޾� ��Ʈ�ѷ��� ���� �Ǳ� ���� ���ͼ�Ʈ �ؼ� �۾� ����
		try {
			String viewName=getViewName(request);
			request.setAttribute("viewName", viewName);
		} catch (Exception e) {
			System.out.println("���ͼ�Ʈ�� ����: " + e.getMessage());
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
	
	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath=request.getContextPath();
		String uri=(String)request.getAttribute("javax.servlet.include.request_uri");
		
		if (uri == null || uri.trim().equals("")) {
			uri=request.getRequestURI();
		}
		int begin=0, end;
		if(!(contextPath == null) || "".equals(contextPath)) {
			begin=contextPath.length();
		}
		if(uri.indexOf(";") != -1) {
			end=uri.indexOf(";"); //��û uri�� ;�� ������ ��ġ ����
		} else if(uri.indexOf("?") != -1) {
			end=uri.indexOf("?");
		} else {
			end=uri.length();
		}
		
		String fileName=uri.substring(begin, end);
		if(fileName.lastIndexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf(".")); //localhost:8090/~~~/aa.do��� .do������ ���ϸ��� ����
		}
		if(fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/", 1), fileName.length()); // localhost:8090/~~~/aa���� aa�� �����´�
			//��û�� ȹ��
		}
		
		return fileName;
		
	}
}
