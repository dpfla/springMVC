package memberMVC.ex02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService {
	BoardDAO boardDAO;
	public BoardService() {
		boardDAO = new BoardDAO(); //생성자에서 BoardDAO 객체 생성
	}
	
	public List<ArticleVO> listArticles() {
		List<ArticleVO> articleList = boardDAO.selectAllArticles();
		
		return articleList;
	}
	
	public Map listArticles(Map<String, Integer> paginMap) {
		Map articleMap=new HashMap();
		List<ArticleVO> articleList = boardDAO.selectAllArticles(paginMap);
		int totArticles=boardDAO.selectToArticles();
		//글이 100개 이하일 때는 section 표시하지 않기 위해 전체 글 갯수 가져옴
		articleMap.put("articleList", articleList);
		articleMap.put("totArticles", totArticles);
		
		return articleMap;
	}
	
	public int addArticle(ArticleVO article) {
		return boardDAO.insertNewArticle(article);
	}
	
	public ArticleVO viewSrticle(int articleNo) {
		ArticleVO article=null;
		article = boardDAO.selectArticle(articleNo);
		return article;
	}
	
	public void modArticle(ArticleVO article) {
		boardDAO.updateArticle(article);
	}
	
	public List<Integer> removeArticle(int articleNo){
		List<Integer> articleNoList = boardDAO.selectRemoveArticles(articleNo);
		boardDAO.deleteArticle(articleNo);
		return articleNoList;
	}
	
	public int addReply(ArticleVO article) {
		return boardDAO.insertNewArticle(article);
	}
}
