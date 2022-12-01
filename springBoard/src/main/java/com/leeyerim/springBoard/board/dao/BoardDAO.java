package com.leeyerim.springBoard.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.leeyerim.springBoard.board.vo.ArticleVO;

public interface BoardDAO {
	public List selectAllArticleList() throws DataAccessException;
	public int insertNewArticle(Map articleMap) throws DataAccessException;
	public ArticleVO selectArticle(int articleNo) throws DataAccessException;
	public void updateArticle(Map articleMap) throws DataAccessException;
	public void deleteArticle(int articleNo) throws DataAccessException;
	public void insertNewImage(Map articleMap) throws DataAccessException;
	public List selectImageFileList(int articleNo) throws DataAccessException;
}
