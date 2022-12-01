package com.leeyerim.springBoard.board.service;

import java.util.List;
import java.util.Map;

import com.leeyerim.springBoard.board.vo.ArticleVO;

public interface BoardService {
	public List<ArticleVO> listArticles() throws Exception;
	public int addNewArticle(Map articleMap) throws Exception;
	//public ArticleVO viewArticle(int articleNo) throws Exception;
	public Map viewArticle(int articleNo) throws Exception;
	public void modArticle(Map articleMap) throws Exception;
	public void removeArticle(int articleNo) throws Exception;
}
