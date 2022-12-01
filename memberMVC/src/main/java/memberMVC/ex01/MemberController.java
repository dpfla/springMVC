package memberMVC.ex01;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	MemberDAO memberDAO;

	@Override
	public void init() throws ServletException {
		memberDAO = new MemberDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String nextPage=null;
		String action=request.getPathInfo();
		//요청이 들어온 URL의 마지막 경로 반환
		System.out.println("요청 경로: " + action);
		
		if(action == null || action.equals("/listMember.do")) {
			List<MemberVO> memberList = memberDAO.listMembers();
			request.setAttribute("memberList", memberList);
			nextPage = "/memInfo/listMembers.jsp";
			
		} else if(action.equals("/addMember.do")) {
			String id=request.getParameter("id");
			String pwd=request.getParameter("pwd");
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			MemberVO memberVO=new MemberVO(id, pwd, name, email);
			memberDAO.addMember(memberVO);
			
			request.setAttribute("msg", "addMember");
			nextPage="/member/listMembers.do";
			
		} else if(action.equals("/memberForm.do")) {
			nextPage = "/memInfo/memberForm.jsp";
			
		} else if(action.equals("/modMemberForm.do")) {
			String id=request.getParameter("id");
			MemberVO memInfo=memberDAO.findMember(id);
			request.setAttribute("memFindInfo", memInfo);
			nextPage = "/memInfo/modMemberForm.jsp";
			
		} else if(action.equals("/modMember.do")) {
			String id=request.getParameter("id"); //수정할 정보의 조건 따직 위해
			String pwd=request.getParameter("pwd");
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			MemberVO memberVO=new MemberVO(id, pwd, name, email);
			memberDAO.modMember(memberVO);
			
			request.setAttribute("msg", "modified");
			nextPage="/member/listMembers.do";
			
		} else if(action.equals("/delMember.do")) { 
			String id = request.getParameter("id");
			memberDAO.deleteMember(id);
			request.setAttribute("msg", "deleted");
			nextPage="/member/listMembers.do";
		} else {
			List<MemberVO> memberList = memberDAO.listMembers();
			request.setAttribute("memberList", memberList);
			nextPage = "/memInfo/listMembers.jsp";
		}
		
		RequestDispatcher dispatcher=request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}
}
