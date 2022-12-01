package memberMVC.ex02;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static String ART_IMAGE_REPO="C:\\LeeYeRim\\board\\article_image";
	BoardService boardService;
	ArticleVO articleVO;
	
   @Override
	public void init(ServletConfig config) throws ServletException {
	   boardService=new BoardService();
	   articleVO= new ArticleVO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage="";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session; 
		String action=request.getPathInfo();
		System.out.println("��û ���: " + action);
		PrintWriter writer;
		
		try {
			List<ArticleVO> articleList=new ArrayList<ArticleVO>();
			if(action == null || action.equals("/listArticles.do")) {
				//����¡ ����
				String _section=request.getParameter("section");
				//����¡ ���� ����, 
				//first 1 2 3 4 5 next  => section=5
				String _pageNum=request.getParameter("pageNum");
				int section=Integer.parseInt(_section== null ? "1" : _section);
				int pageNum=Integer.parseInt(_pageNum== null ? "1" : _pageNum);
				Map<String, Integer> pagingMap=new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articleMap=boardService.listArticles(pagingMap);
				//�Խñ� 10���� ��ü �� ���� ����
				articleMap.put("section", section);
				articleMap.put("pageNum", pageNum);
				//Ŭ���̾�Ʈ�� Ŭ���� ��������
				request.setAttribute("articleMap", articleMap);
				
				/*����¡ ���� ��
				 * articleList=boardService.listArticles();
				request.setAttribute("articleList", articleList);*/
				nextPage = "/boardInfo/listArticles.jsp";
				
			} else if(action.equals("/articleForm.do")) {
				nextPage="/boardInfo/articleForm.jsp";
				//�۾���â ��Ÿ������ ��
				
			} else if(action.equals("/addArticle.do")) {
				int articleNo = 0;
				//���� ���ε� ����� ����ϱ� ���� upload �Լ� ��û
				
				Map<String, String> articleMap=upload(request, response);
				String title=articleMap.get("title");
				String content=articleMap.get("content");
				String imageFileName=articleMap.get("imageFileName");
				
				articleVO.setParentNo(0);
				articleVO.setId("hong");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImageFileName(imageFileName);
				
				articleNo = boardService.addArticle(articleVO);
				
				if(imageFileName != null && imageFileName.length() != 0) {
					File srcFile=new File(ART_IMAGE_REPO+"\\temp\\"+imageFileName);
					File destDir=new File(ART_IMAGE_REPO + "\\" + articleNo);
					destDir.mkdir(); //�� ��ȣ�� �̸����� �� ���� ����
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					//srcFile�� ���� destDir�� �̵�, true: �̵��Ѵ�
					srcFile.delete();
				}
				writer=response.getWriter();
				writer.print("<script>alert('������ �߰��߽��ϴ�');" + 
						"location.href='" + request.getContextPath()+"/board/listArticles.do';"
						+"</script>");
				return ;
				//nextPage="/listArticles.do";
				
			} else if(action.equals("/viwerArticle.do")) {
				String articleNo=request.getParameter("articleNo");
				articleVO = boardService.viewSrticle(Integer.parseInt(articleNo));
				request.setAttribute("article", articleVO);
				nextPage="/boardInfo/viewArticle.jsp";
				
			} else if(action.equals("/modArticle.do")) {
				Map<String, String> articleMap = upload(request, response);
				int articleNo = Integer.parseInt(articleMap.get("articleNo"));
				articleVO.setArticleNo(articleNo);
				String title=articleMap.get("title");
				String content=articleMap.get("content");
				String imageFileName=articleMap.get("imageFileName");
				
				articleVO.setParentNo(0);
				articleVO.setId("hong");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImageFileName(imageFileName);
				
				boardService.modArticle(articleVO);
				
				if(imageFileName != null && imageFileName.length() != 0) {
					String originalFileName=articleMap.get("originalFileName");
					File srcFile=new File(ART_IMAGE_REPO+"\\temp\\"+imageFileName);
					File destDir=new File(ART_IMAGE_REPO + "\\" + articleNo);
					destDir.mkdir(); //�� ��ȣ�� �̸����� �� ���� ����
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					//srcFile�� ���� destDir�� �̵�, true: �̵��Ѵ�
					File oldFile=new File(ART_IMAGE_REPO + "\\" + articleNo + "\\" + originalFileName);
					if(!oldFile.delete()){
						System.out.println(originalFileName + " ���� ���� ����");
					}
				}
				writer=response.getWriter();
				writer.print("<script>alert('���� �����߽��ϴ�');" + 
						"location.href='" + request.getContextPath()+
						"/board/viwerArticle.do?articleNo=" + articleNo +"';</script>");
				return ;
				
			} else if(action.equals("/removeArticle.do")) {
				int articleNo=Integer.parseInt(request.getParameter("articleNo"));
				List<Integer> articleNoList=boardService.removeArticle(articleNo);
				//������ ���� �̹����� �����ϱ� ���� �� ��ȣ ������
				for(int no:articleNoList) {
					File imgDir=new File(ART_IMAGE_REPO + "\\" + no);
					//��� �ۿ� �̹����� �ִ°� �ƴϱ� ������ if�� �ִ��� Ȯ��
					if(imgDir.exists()) {
						FileUtils.deleteDirectory(imgDir);
					}
				}
				writer=response.getWriter();
				writer.print("<script>alert('�Խñ��� �����Ǿ����ϴ�.');" + 
						"location.href='" + request.getContextPath()+"/board/listArticles.do';"
						+"</script>");
				
				return ;
				
			} else if(action.equals("/replyForm.do")) {
				int parentNo=Integer.parseInt(request.getParameter("parentNo"));
				session = request.getSession();
				session.setAttribute("parentNo", parentNo);
				nextPage="/boardInfo/replyForm.jsp";
				
			} else if(action.equals("/addReply.do")) {
				session=request.getSession();
				int parentNo=(Integer)session.getAttribute("parentNo");
				session.removeAttribute("parentNo");
				Map<String, String> articleMap=upload(request, response);
				String title=articleMap.get("title");
				String content=articleMap.get("content");
				String imageFileName=articleMap.get("imageFileName");
				articleVO.setParentNo(parentNo);
				articleVO.setId("dkahsem");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImageFileName(imageFileName);
				int articleNo=boardService.addReply(articleVO);
				if(imageFileName != null && imageFileName.length() != 0) {
					File srcFile=new File(ART_IMAGE_REPO + "\\temp\\" + imageFileName);
					File dirFile=new File(ART_IMAGE_REPO + "\\" + articleNo);
					dirFile.mkdir();
					FileUtils.moveFileToDirectory(srcFile, dirFile, true);
				}
				writer=response.getWriter();
				writer.print("<script>alert('����� �߰��Ǿ����ϴ�.');" + 
						"location.href='" + request.getContextPath()
						+"/board/viwerArticle.do?articleNo=" + articleNo +"';</script>");
				return ;
				
			}
			RequestDispatcher dispatcher=request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println("controller ����: " + e.getMessage());
		}

	}
	
	//�̹��� ���� ���ε�
	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> articleMap=new HashMap<String, String>();
		String encoding="utf-8";
		File currentDirPath=new File(ART_IMAGE_REPO);
		DiskFileItemFactory factory=new DiskFileItemFactory();
		factory.setSizeThreshold(1024*1024);
		ServletFileUpload upload=new ServletFileUpload(factory);
		
		try {
			List items=upload.parseRequest(request);
			for(int i=0; i< items.size(); i++) {
				FileItem fileItem=(FileItem)items.get(i);
				if(fileItem.isFormField()) {
					//���� ���ε�� ���� ���۵� �� �� ���� (����, ����) ���� �Ű������� ���� �� ��ȯ
					System.out.println(fileItem.getFieldName() + " = " + fileItem.getString(encoding));
					articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				} else {
					//���� ���ε�
					System.out.println("�Ķ���� �̸�: " + fileItem.getFieldName());
					System.out.println("���� �̸�: " + fileItem.getName());
					System.out.println("���� ũ��: " + fileItem.getSize()+"bytes");
					if(fileItem.getSize() > 0) {
						//������ ���ε� �ϴ� ���
						int index = fileItem.getName().lastIndexOf("\\");
						if(index == -1) {
							index = fileItem.getName().lastIndexOf("/");
						}
						String fileName=fileItem.getName().substring(index+1);
						articleMap.put(fileItem.getFieldName(), fileName);
						File uploadFile=new File(currentDirPath + "\\temp\\" + fileName);
						//temp ������ �ӽ� ���ε�
						fileItem.write(uploadFile);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("�̹��� ���ε� ����: " + e.getMessage());
		}
		
		return articleMap;
	}
}
