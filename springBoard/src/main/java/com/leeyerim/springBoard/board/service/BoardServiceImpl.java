package com.leeyerim.springBoard.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.leeyerim.springBoard.board.dao.BoardDAO;
import com.leeyerim.springBoard.board.vo.ArticleVO;
import com.leeyerim.springBoard.board.vo.ImageVO;

@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardDAO boardDAO;
	
	
	public List<ArticleVO> listArticles() {
		List<ArticleVO> articleList = boardDAO.selectAllArticleList();
		return articleList;
	}
	
	/*public Map listArticles(Map<String, Integer> paginMap) {
		Map articleMap=new HashMap();
		List<ArticleVO> articleList = boardDAO.selectAllArticles(paginMap);
		int totArticles=boardDAO.selectToArticles();
		//���� 100�� ������ ���� section ǥ������ �ʱ� ���� ��ü �� ���� ������
		articleMap.put("articleList", articleList);
		articleMap.put("totArticles", totArticles);
		
		return articleMap;
	}*/
	
	/*public int addArticle(ArticleVO article) {
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
	}*/

	/*//�Ѱ� �̹��� �߰�
	 * @Override
	public int addNewArticle(Map articleMap) throws Exception {
		return boardDAO.insertNewArticle(articleMap);
	}*/
	
	//���� �̹��� �߰�
	@Override
	public int addNewArticle(Map articleMap) throws Exception {
		int articleNo = boardDAO.insertNewArticle(articleMap);
		articleMap.put("articleNo", articleNo);
		boardDAO.insertNewImage(articleMap);
		return articleNo;
	}

	/*//�� ���� �̹��� �����ֱ�
	@Override
	public ArticleVO viewArticle(int articleNo) throws Exception {
		return boardDAO.selectArticle(articleNo);
	}*/
	//���� �̹��� �����ֱ�
	@Override
	public Map viewArticle(int articleNo) throws Exception {
		Map articleMap = new HashMap();
		ArticleVO articleVO = boardDAO.selectArticle(articleNo);
		List<ImageVO> imageFileList = boardDAO.selectImageFileList(articleNo);
		articleMap.put("article", articleVO);
		articleMap.put("imageFileList", imageFileList);
		return articleMap;
	}

	@Override
	public void modArticle(Map articleMap) throws Exception {
		boardDAO.updateArticle(articleMap);
		
	}

	@Override
	public void removeArticle(int articleNo) throws Exception {
		boardDAO.deleteArticle(articleNo);
		
	}
}
