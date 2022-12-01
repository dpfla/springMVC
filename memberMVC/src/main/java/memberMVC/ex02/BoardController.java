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
		System.out.println("요청 경로: " + action);
		PrintWriter writer;
		
		try {
			List<ArticleVO> articleList=new ArrayList<ArticleVO>();
			if(action == null || action.equals("/listArticles.do")) {
				//페이징 구현
				String _section=request.getParameter("section");
				//페이징 단위 설정, 
				//first 1 2 3 4 5 next  => section=5
				String _pageNum=request.getParameter("pageNum");
				int section=Integer.parseInt(_section== null ? "1" : _section);
				int pageNum=Integer.parseInt(_pageNum== null ? "1" : _pageNum);
				Map<String, Integer> pagingMap=new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articleMap=boardService.listArticles(pagingMap);
				//게시글 10개와 전체 글 갯수 가짐
				articleMap.put("section", section);
				articleMap.put("pageNum", pageNum);
				//클라이언트가 클릭한 페이지수
				request.setAttribute("articleMap", articleMap);
				
				/*페이징 없을 때
				 * articleList=boardService.listArticles();
				request.setAttribute("articleList", articleList);*/
				nextPage = "/boardInfo/listArticles.jsp";
				
			} else if(action.equals("/articleForm.do")) {
				nextPage="/boardInfo/articleForm.jsp";
				//글쓰기창 나타나도록 함
				
			} else if(action.equals("/addArticle.do")) {
				int articleNo = 0;
				//파일 업로드 기능을 사용하기 위해 upload 함수 요청
				
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
					destDir.mkdir(); //글 번호를 이름으로 한 폴더 생성
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					//srcFile의 파일 destDir로 이동, true: 이동한다
					srcFile.delete();
				}
				writer=response.getWriter();
				writer.print("<script>alert('새글을 추가했습니다');" + 
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
					destDir.mkdir(); //글 번호를 이름으로 한 폴더 생성
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					//srcFile의 파일 destDir로 이동, true: 이동한다
					File oldFile=new File(ART_IMAGE_REPO + "\\" + articleNo + "\\" + originalFileName);
					if(!oldFile.delete()){
						System.out.println(originalFileName + " 파일 삭제 실패");
					}
				}
				writer=response.getWriter();
				writer.print("<script>alert('글을 수정했습니다');" + 
						"location.href='" + request.getContextPath()+
						"/board/viwerArticle.do?articleNo=" + articleNo +"';</script>");
				return ;
				
			} else if(action.equals("/removeArticle.do")) {
				int articleNo=Integer.parseInt(request.getParameter("articleNo"));
				List<Integer> articleNoList=boardService.removeArticle(articleNo);
				//삭제된 글의 이미지를 삭제하기 위해 글 번호 가져옴
				for(int no:articleNoList) {
					File imgDir=new File(ART_IMAGE_REPO + "\\" + no);
					//모든 글에 이미지가 있는게 아니기 때문에 if로 있는지 확인
					if(imgDir.exists()) {
						FileUtils.deleteDirectory(imgDir);
					}
				}
				writer=response.getWriter();
				writer.print("<script>alert('게시글이 삭제되었습니다.');" + 
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
				writer.print("<script>alert('답글이 추가되었습니다.');" + 
						"location.href='" + request.getContextPath()
						+"/board/viwerArticle.do?articleNo=" + articleNo +"';</script>");
				return ;
				
			}
			RequestDispatcher dispatcher=request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println("controller 오류: " + e.getMessage());
		}

	}
	
	//이미지 파일 업로드
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
					//파일 업로드로 같이 전송된 새 글 관련 (제목, 내용) 관련 매개변수를 저장 후 반환
					System.out.println(fileItem.getFieldName() + " = " + fileItem.getString(encoding));
					articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				} else {
					//파일 업로드
					System.out.println("파라미터 이름: " + fileItem.getFieldName());
					System.out.println("파일 이름: " + fileItem.getName());
					System.out.println("파일 크기: " + fileItem.getSize()+"bytes");
					if(fileItem.getSize() > 0) {
						//폴더에 업로드 하는 기능
						int index = fileItem.getName().lastIndexOf("\\");
						if(index == -1) {
							index = fileItem.getName().lastIndexOf("/");
						}
						String fileName=fileItem.getName().substring(index+1);
						articleMap.put(fileItem.getFieldName(), fileName);
						File uploadFile=new File(currentDirPath + "\\temp\\" + fileName);
						//temp 폴더에 임시 업로드
						fileItem.write(uploadFile);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("이미지 업로드 오류: " + e.getMessage());
		}
		
		return articleMap;
	}
}
