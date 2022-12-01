package com.leeyerim.springBoard.board.dao;

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

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.leeyerim.springBoard.board.vo.ArticleVO;
import com.leeyerim.springBoard.board.vo.ImageVO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List selectAllArticleList() throws DataAccessException {
		List<ArticleVO> articleList = sqlSession.selectList("mapper.board.selectAllArticleList");
		return articleList;
	}

	@Override
	public ArticleVO selectArticle(int articleNo) throws DataAccessException {
		return  sqlSession.selectOne("mapper.board.selectArticle", articleNo);
	}

	@Override
	public int insertNewArticle(Map articleMap) throws DataAccessException {
		int articleNo=selectNewArticleNo();
		articleMap.put("articleNo", articleNo);
		sqlSession.insert("mapper.board.insertNewArticle", articleMap);
		return articleNo;
	}

	private int selectNewArticleNo() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewArticleNo");
	}
	
	@Override
	public void updateArticle(Map articleMap) throws DataAccessException {
		sqlSession.update("mapper.board.updateArticle", articleMap);
		
	}

	@Override
	public void deleteArticle(int articleNo) throws DataAccessException {
		sqlSession.delete("mapper.board.deleteArticle", articleNo);
		
	}

	@Override
	public void insertNewImage(Map articleMap) throws DataAccessException {
		List<ImageVO> imageFileList = (ArrayList)(articleMap.get("imageFileList"));
		int articleNo = (Integer) articleMap.get("articleNo");
		int imageFileNo=selectNewImageFileNo();
		for(ImageVO imageVO: imageFileList) {
			imageVO.setImageFileNo(imageFileNo++);
			imageVO.setArticleNo(articleNo);
		}
		sqlSession.insert("mapper.board.insertNewImage", imageFileList);
	}
	private int selectNewImageFileNo() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewImageFileNo");
	}

	@Override
	public List selectImageFileList(int articleNo) throws DataAccessException {
		List<ImageVO> imageFileList =null;
		imageFileList = sqlSession.selectList("mapper.board.selectImageFileList", articleNo);
		return imageFileList;
	}

}
