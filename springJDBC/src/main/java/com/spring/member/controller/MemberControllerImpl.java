package com.spring.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.spring.member.service.MemberService;
import com.spring.member.vo.MemberVO;

public class MemberControllerImpl extends MultiActionController implements MemberController{
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		List memberList = memberService.listMembers();
		ModelAndView mav=new ModelAndView(viewName);
		mav.addObject("memberList", memberList);
		return mav;
	}
	
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		ModelAndView mav=new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@Override
	public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberVO memberVO=new MemberVO();
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		
		memberVO.setId(id);
		memberVO.setPwd(pwd);
		memberVO.setName(name);
		memberVO.setEmail(email);
		int result=memberService.addMember(memberVO);
		
		List memberList = memberService.listMembers();
		ModelAndView mav=new ModelAndView("listMembers");
		//�׳� jsp�� ������ ����� �ҷ����� �ʱ� ������ ����� �޾ƿͼ� ModelAndView�� �Ѱ��ش�
		mav.addObject("memberList", memberList);
		return mav;
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
		//localhost:8090/~~~/aa.do�� �����´� �Ӽ�=�� ������ �κ��� ������ �κ�

		if(fileName.lastIndexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf(".")); //localhost:8090/~~~/aa.do��� .do������ ���ϸ��� ����
		}
		if(fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length()); // localhost:8090/~~~/aa���� aa�� �����´�
			//��û�� ȹ��
		}
		
		
		return fileName;
		
	}
}
