package memberMVC.ex02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService {
	BoardDAO boardDAO;
	public BoardService() {
		boardDAO = new BoardDAO(); //�����ڿ��� BoardDAO ��ü ����
	}
	
	public List<ArticleVO> listArticles() {
		List<ArticleVO> articleList = boardDAO.selectAllArticles();
		
		return articleList;
	}
	
	public Map listArticles(Map<String, Integer> paginMap) {
		Map articleMap=new HashMap();
		List<ArticleVO> articleList = boardDAO.selectAllArticles(paginMap);
		int totArticles=boardDAO.selectToArticles();
		//���� 100�� ������ ���� section ǥ������ �ʱ� ���� ��ü �� ���� ������
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
