package com.leeyerim.springBoard.member.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.leeyerim.springBoard.member.vo.MemberVO;

public interface MemberDAO {
	public List selectAllMemberList() throws DataAccessException;
	public int insertMember(MemberVO memberVO) throws DataAccessException;
	public int deleteMember(String id) throws DataAccessException;
	public MemberVO findMember(String id) throws DataAccessException;
	public int updateMember(MemberVO memberVO) throws DataAccessException;
	public MemberVO loginCheck(MemberVO memberVO) throws DataAccessException;
}
