package memberMVC.ex02;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class BoardDAO {
	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	
	public BoardDAO() {
		try {
			Context ctx=new InitialContext();
			Context envContext=(Context)ctx.lookup("java:/comp/env");
			dataFactory=(DataSource)envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			System.out.println("DB 연결 오류: "+e.getMessage());
		}
	}
	
	//글 목록 페이징
	public List<ArticleVO> selectAllArticles(Map<String, Integer> pagingMap) {
		List<ArticleVO> articleList=new ArrayList<ArticleVO>();
		int section=(Integer)pagingMap.get("section");
		int pageNum=(Integer)pagingMap.get("pageNum");
		
		try {
			conn=dataFactory.getConnection();
			String query="select * from"
				+ " (select rownum as recNum, lvl, articleNo, parentNo, title, id, writeDate from"
				+ " (select level as lvl, articleNo, parentNo, title, id, writeDate from boardtbl"
				+ " start with parentNo=0 CONNECT BY PRIOR articleNo=parentNo"
				+ " ORDER SIBLINGS BY articleNo DESC))"
				+ " where recNum between (?-1)*100+(?-1)*10+1 and (?-1)*100+?*10";
				//100개 -> section=10 이라 설정해서 섹션당 100개, 
				//10개 -> pageNum=10, 한 페이지당 10개씩 보여준다는 의미
				//level(as lvl): 계층형 DB에서 레벨을 알려주는 가상컬럼, 원글=1, 의 답글=2, 의 답글=3 
				System.out.println(query);
				pstmt=conn.prepareStatement(query);
				pstmt.setInt(1, section);
				pstmt.setInt(2, pageNum);
				pstmt.setInt(3, section);
				pstmt.setInt(4, pageNum);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next()) {
					int level=rs.getInt("lvl");
					int articleNo=rs.getInt("articleNo");
					int parentNo=rs.getInt("parentNo");
					String title=rs.getString("title");
					String id=rs.getString("id");
					Date writeDate=rs.getDate("writeDate");
					
					ArticleVO article=new ArticleVO(); //글 정보 설정
					article.setLevel(level);
					article.setArticleNo(articleNo);
					article.setParentNo(parentNo);
					article.setTitle(title);
					article.setId(id);
					article.setWriteDate(writeDate);
					
					articleList.add(article);
				}
				
				rs.close();
				pstmt.close();
				conn.close();
		} catch (Exception e) {
			System.out.println("페이징 목록 조회중 오류: " + e.getMessage());
		}
		
		return articleList;
	}
	
	public List<ArticleVO> selectAllArticles() {
		List<ArticleVO> articleList = new ArrayList<ArticleVO>();
		
		try {
			conn=dataFactory.getConnection();
			String query="select LEVEL, articleNo, parentNo, title, content, writeDate, id" + 
					" from boardtbl START WITH parentNo=0 CONNECT BY PRIOR" +
					" articleNo=parentNo ORDER SIBLINGS BY articleNo DESC";
				//START WITH: 최상위 층을 식별하는 조건
				//CONNECT BY: 계층형 DB가 연결된 구조 설명
				/*
				 * parentNo=0이면 최사위 계층
				 * articleNo=parentNo 면 같은 형제로(묶음)으로 보고 articleNo기준으로 내림차순 정렬
				 */
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int level=rs.getInt("level");//각 글의 계층(깊이) level 속성 지정
				int articleNo=rs.getInt("articleNo");
				int parentNo=rs.getInt("parentNo");
				String title=rs.getString("title");
				String content=rs.getString("content");
				String id=rs.getString("id");
				Date writeDate=rs.getDate("writeDate");
				
				ArticleVO article=new ArticleVO(); //글 정보 설정
				article.setLevel(level);
				article.setArticleNo(articleNo);
				article.setParentNo(parentNo);
				article.setTitle(title);
				article.setContent(content);
				article.setId(id);
				article.setWriteDate(writeDate);
				
				articleList.add(article);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("전체 목록 조회 중 오류: " + e.getMessage());
		}
		
		return articleList;
	}
	
	//글 번호 생성 메서드
	private int getArticleNo() {
		try {
			conn=dataFactory.getConnection();
			String query="select max(articleNo) as max from boardtbl";
			//가장 큰 글 번호 반환
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				return (rs.getInt("max")+1);
			}
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("글 번호 생성 중 오류: " + e.getMessage());
		}
		return -1;
	}
	
	//게시글 추가
	public int insertNewArticle(ArticleVO article) {
		int articleNo=getArticleNo();
		
		try {
			conn=dataFactory.getConnection();
			int parentNo=article.getParentNo();
			String title=article.getTitle();
			String content=article.getContent();
			String id=article.getId();
			String imageFileName=article.getImageFileName();
			String query="insert into boardtbl(articleNo, parentNo, title, content, imageFileName, id)" + 
					" values(?,?,?,?,?,?)";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, articleNo);
			pstmt.setInt(2, parentNo);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setString(5, imageFileName);
			pstmt.setString(6, id);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("게시글 추가중 오류: " + e.getMessage());
		}
		
		return articleNo;
	}
	
	
	//게시글 상세 내용
	public ArticleVO selectArticle(int articleNo) {
		ArticleVO article = new ArticleVO();
		
		try {
			conn=dataFactory.getConnection();
			String query="select articleNo, parentNo, title, content, " 
					+ "nvl(imageFileName,'null') as imageFileName"
					+ ", id, writeDate from boardtbl where articleNo=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, articleNo);
			ResultSet rs=pstmt.executeQuery();
			rs.next();
			int _articleNo = rs.getInt("articleNo");
			int parentNo=rs.getInt("parentNo");
			String title=rs.getString("title");
			String content=rs.getString("content");
			String imageFileName=URLEncoder.encode(rs.getString("imageFileName"), "utf-8");
			String id=rs.getString("id");
			Date writeDate=rs.getDate("writeDate");
			
			article.setArticleNo(_articleNo);
			article.setParentNo(parentNo);
			article.setTitle(title);
			article.setContent(content);
			article.setImageFileName(imageFileName);
			article.setId(id);
			article.setWriteDate(writeDate);
			
			rs.close();
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println("글 내용 조회 중 오류: " + e.getMessage());
		}
		
		return article;
	}
	
	//게시글 수정
	public void updateArticle(ArticleVO article) {
		int articleNo=article.getArticleNo();
		String title=article.getTitle();
		String content=article.getContent();
		String imageFileName=article.getImageFileName();
		try {
			conn=dataFactory.getConnection();
			String query="update boardtbl set title=?, content=?";
			if(imageFileName != null && imageFileName.length() != 0) {
				query += ", imageFileName=?";
			}
			query += " where articleNo=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			if(imageFileName != null && imageFileName.length() != 0) {
				pstmt.setString(3, imageFileName);
				pstmt.setInt(4, articleNo);
			} else {
				pstmt.setInt(3, articleNo);
			}
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("글 수정 중 오류: " + e.getMessage());
		}
	}
	
	//삭제할 글 번호 목록 가져오기 ( 폴더와 함께 이미지를 삭제하기 위해서)
	public List<Integer> selectRemoveArticles(int articleNo){
		List<Integer> articleNoList=new ArrayList<Integer>(); 
		try {
			conn=dataFactory.getConnection();
			String query="select articleNo from boardtbl START WITH articleNo=?";
			query+=" CONNECT BY PRIOR articleNo=parentNo";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, articleNo);
			ResultSet rs=pstmt.executeQuery();
			while (rs.next()) {
				articleNo=rs.getInt("articleNo");
				articleNoList.add(articleNo);
			}
			rs.close();
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println("글 번호 처리 중 에러: " + e.getMessage());
		}
		
		return articleNoList;
	}
	
	//받아온 글 번호 삭제
	public void deleteArticle(int articleNo) {
		try {
			conn=dataFactory.getConnection();
			String query="delete from boardtbl where articleNo in";
			query+= " (select articleNo from boardtbl START WITH articleNo=?";
			query+=" CONNECT BY PRIOR articleNo=parentNo)";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, articleNo);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("글 삭제중 오류: " + e.getMessage());
		}
	}
	
	//전체 글 갯수 반환
	public int selectToArticles() {
		try {
			conn=dataFactory.getConnection();
			String query="select count(articleNo) from boardtbl";
			pstmt=conn.prepareStatement(query);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				return (rs.getInt(1));
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("글 목록 수 처리중 오류: " + e.getMessage());
		}
		//자료가 하나도 없을 때
		return 0;
	}
}
