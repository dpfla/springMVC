package com.spring.member.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.spring.member.vo.MemberVO;

public class MemberDAOImpl implements MemberDAO {
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate=new JdbcTemplate(dataSource);
		//DB 연결
		//WEB-INF/config/action-dataSource.xml 에서 dataSource로 정의했음
	}
	@Override
	public List selectAllMembers() throws DataAccessException {
		String query="select * from membertbl order by joinDate desc";
		List memberList = new ArrayList();
		memberList = this.jdbcTemplate.query(query, new RowMapper() {
			//RowMapper : ResultSet과 같은 역할, 레코드 수만큼 반복
			//
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberVO memberVO = new MemberVO();
				memberVO.setId(rs.getString("id"));
				memberVO.setPwd(rs.getString("pwd"));
				memberVO.setName(rs.getString("name"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setJoinDate(rs.getDate("joinDate"));
				return memberVO;
			}
		});
		
		return memberList;
	}
	
	@Override
	public int addMember(MemberVO memberVO) throws DataAccessException {
		String id=memberVO.getId();
		String pwd=memberVO.getPwd();
		String name=memberVO.getName();
		String email=memberVO.getEmail();
		
		String query = "insert into membertbl(id, pwd, name, email) values('"
				+ id + "','" + pwd + "','"+ name + "','"+ email + "')"; 
		System.out.println(query);
		int result=this.jdbcTemplate .update(query);
		
		return result;
	}
}
