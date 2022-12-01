package com.myspring.mavenSTS.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.myspring.mavenSTS.member.dao.MemberDAO;
import com.myspring.mavenSTS.member.vo.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;


	@Override
	public List listMembers() throws DataAccessException {
		List memberList = null;
		memberList = memberDAO.selectAllMemberList();
		
		return memberList;
	}
	
	@Override
	public int addMember(MemberVO memberVO) throws DataAccessException {
		
		return memberDAO.insertMember(memberVO);
	}
	
	@Override
	public int removeMember(String id) throws DataAccessException {
		
		return memberDAO.deleteMember(id);
	}

	@Override
	public MemberVO findMember(String id) throws DataAccessException {
		MemberVO memberVO = null;
		memberVO = memberDAO.findMember(id);
		return memberVO;
	}

	@Override
	public int updateMember(MemberVO memberVO) throws DataAccessException {
		return memberDAO.updateMember(memberVO);
	}
	
	@Override
	public MemberVO login(MemberVO memberVO) throws DataAccessException {
		
		return memberDAO.loginCheck(memberVO);
	}
	
}
