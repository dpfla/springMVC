package memberMVC.ex02;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;

public class ArticleVO {
	private int level; 
	//ex) 원본글(0) -> 의 답글(1) ->의 답글(2)
	private int articleNo;
	private int parentNo; 
	private String title;
	private String content;
	private String imageFileName;
	private Date writeDate;
	private String id;
	
	public ArticleVO() {
		// TODO Auto-generated constructor stub
	}
	
	public ArticleVO(int level, int articleNo, int parentNp, String title, 
			String content, String imageFileName, String id) {
		this.level=level;
		this.articleNo=articleNo;
		this.parentNo=parentNp;
		this.title=title;
		this.content=content;
		this.imageFileName=imageFileName;
		this.id=id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public int getParentNo() {
		return parentNo;
	}

	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageFileName() {
		try {
			if(imageFileName != null && imageFileName.length() != 0) {
				imageFileName=URLDecoder.decode(imageFileName, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("파일 이름 가져오기 실패");
		}
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		try {
			if(imageFileName != null && imageFileName.length() != 0) {
				this.imageFileName = URLEncoder.encode(imageFileName, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("파일 이름 셋팅 실패");
		}
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}