package springAnnotation.ex02;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller("loginController")
public class LoginController {

	@RequestMapping(value = {"/test/loginForm.do", "/test/loginForm2.do"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//�� �޼ҵ忡 �ΰ��� mapping �̸��� ���� �� �ִ�
		//���� ����� ������ ���� �� �ִ�
		ModelAndView mav=new ModelAndView();
		mav.setViewName("loginForm");
		return mav;
	}
	/*
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("result");
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		mav.addObject("id", id);
		mav.addObject("pwd", pwd);
		return mav;
	}*/
	
	/*id, pwd��� �Ű������� ���� ��, path�� id, pwd������ ���� ����Ŵ
	 * @RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@RequestParam("id") String id, @RequestParam("pwd") String pwd, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("result");
		mav.addObject("id", id);
		mav.addObject("pwd", pwd);
		return mav;
	}*/
	
	/*
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@RequestParam(value="id", required = false) String id, @RequestParam(value="pwd", required = false) String pwd, 
			@RequestParam(value="email", required = false) String email, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("result");
		mav.addObject("id", id);
		mav.addObject("pwd", pwd);
		mav.addObject("email", email);
		return mav;
	}*/
	
	/*
	//���� �޾Ƽ� �ѱ�� �� X, 
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(Model model,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		model.addAttribute("id", "hong");
		model.addAttribute("pwd", "hong123");
		model.addAttribute("email", "hong@example.com");
		return "result";
	}*/
	
	/*//Map���� ���� ����
	 * @RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@RequestParam Map<String, String> memInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("result");
		mav.addObject("memInfo", memInfo);
		return mav;
	}*/
	
	/*
	//VO Ŭ���� Ȱ��
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@ModelAttribute("memInfo") LoginVO loginVO,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("result");
		return mav;
	}*/
	
	
	
	
}
