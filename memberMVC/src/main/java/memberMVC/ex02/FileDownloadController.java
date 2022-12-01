package memberMVC.ex02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/download.do")
public class FileDownloadController extends HttpServlet {
	private static String ART_IMAGE_REPO="C:\\LeeYeRim\\board\\article_image";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String articleNo=request.getParameter("articleNo");
		String imageFileName=request.getParameter("imageFileName");
		OutputStream out=response.getOutputStream();
		String path = ART_IMAGE_REPO + "\\" + articleNo + "\\" + imageFileName;
		File imageFile = new File(path);
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment;fileName=" + imageFileName);
		FileInputStream fis=new FileInputStream(imageFile);
		byte[] buffer=new byte[1024*8];
		//버퍼를 이용하여 한 번에 전송되는 크기 (1024(1k)*8=)8k
		//작으면 여러번 전송되고 크면 담는데 오래 걸리기 때문에 적당한 값으로 버퍼 크기 지정
		while(true) {
			int count = fis.read(buffer);
			if(count == -1) break;
			out.write(buffer, 0, count);
		}
		
		fis.close();
		out.close();
		
	}
}
