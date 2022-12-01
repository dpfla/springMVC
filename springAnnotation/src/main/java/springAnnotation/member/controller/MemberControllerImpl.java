package springAnnotation.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.MemberValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import springAnnotation.member.service.MemberService;
import springAnnotation.member.vo.MemberVO;

//action-servlet.xml�� ���� �����ϴ� �κ��� ���� �� ������ annotation�� ����Ѵ�
@Controller("memberController")
public class MemberControllerImpl extends MultiActionController implements MemberController {
	
	//memberService ����, setter �ʿ� X
	/*
	 * <bean id="memberController" class="springMybatis.ex01.member.controller.MemberControllerImpl">
			<property name="methodNameResolver">
				<ref local="methodResolver"/>
			</property>
			<property name="memberService" ref="memberService"/>
		</bean>
	 * */
	@Autowired
	private MemberService memberService;
	
	//new MemberVO() : memberVO ���� �ʿ� X
	//MemberVO.java�� @Component("memberVO")�� ������ ��ü ���� 
	@Autowired
	private MemberVO memberVO;

	//@RequestMapping(value = "mapping ����", method= get, post ���� ���)
	@Override
	@RequestMapping(value = "/member/listMembers.do", 
		method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		List memberList = memberService.listMembers();
		ModelAndView mav=new ModelAndView(viewName);
		mav.addObject("memberList", memberList);
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//@ModelAttribute("memberVO") MemberVO memberVO
		// ->  
		
		request.setCharacterEncoding("utf-8");
		//MemberVO memberVO = new MemberVO(); -> ����, ������̼����� ���Ե� ��ü ���
		// bind(request, memberVO); -> @ModelAttribute �� ���ε� ���ش�
		/*
		 * MultiActionController���� �������ִ� �޼ҵ�
		 * request���� ���� �Ķ���Ͱ��� memberVO�� �־��ش�
		 * �Ķ���͸�� memberVO�� �ʵ���� ���� �͸�
		*/
		int result =  memberService.addMember(memberVO);
		ModelAndView mav=new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//String id=request.getParameter("id"); 
		// -> ������̼����� �޾ƿ´� : @RequestParam("id") String id
		memberService.removeMember(id);
		ModelAndView mav=new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/modMember.do", method = RequestMethod.GET)
	public ModelAndView modMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String viewName=getViewName(request);
		//String id=request.getParameter("id");
		MemberVO memberVO = memberService.findMember(id);
		
		ModelAndView mav=new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("member", memberVO);
		return mav;
	}
	
	@RequestMapping(value = "/member/memberForm.do", method = RequestMethod.GET)
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		ModelAndView mav=new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/updateMember.do",
		method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView updateMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//MemberVO memberVO = new MemberVO();
		//bind(request, memberVO); 

		memberService.updateMember(memberVO);
		ModelAndView mav=new ModelAndView("redirect:/member/listMembers.do");
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
