package memberMVC.ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	
	public MemberDAO() {
		try {
			Context ctx=new InitialContext();
			Context envContext=(Context)ctx.lookup("java:/comp/env");
			dataFactory=(DataSource)envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			System.out.println("커넥션 연결실패: "+e.getMessage());
		}
	}
	
	public List listMembers(){
		List<MemberVO> memberList = new ArrayList();

		try {
			conn = dataFactory.getConnection();
			String query="select * from membertbl order by joindate desc";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rSet = pstmt.executeQuery();
			while (rSet.next()) {
				String id=rSet.getString("id");
				String pwd=rSet.getString("pwd");
				String name=rSet.getString("name");
				String email=rSet.getString("email");
				Date joinDate=rSet.getDate("joindate");
				MemberVO vo=new MemberVO(id, pwd, name, email, joinDate);
				memberList.add(vo);
			}
			rSet.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("DB 조회 실패: " + e.getMessage());
		}
		
		return memberList;
	}
	
	//회원 등록
	public void addMember(MemberVO mem) {
		try {
			conn = dataFactory.getConnection();
			String id= mem.getId();
			String pwd=mem.getPwd();
			String name=mem.getName();
			String email=mem.getEmail();
			
			String queryString="insert into membertbl(id,pwd,name,email) values(?, ?, ?, ?) ";
			pstmt=conn.prepareStatement(queryString);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			System.out.println(queryString);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println("회원 등록 오류: " + e.getMessage());
		}
	}
	
	//수정할 회원정보 찾기
	public MemberVO findMember(String _id) {
		MemberVO memFindInfMemberVO = null;
		try {
			conn=dataFactory.getConnection();
			String queryString = "select * from membertbl where id=?";
			pstmt = conn.prepareStatement(queryString);
			pstmt.setString(1, _id);
			System.out.println(_id);
			System.out.println(queryString);
			ResultSet rs=pstmt.executeQuery();
			rs.next();
			String id=rs.getString("id");
			String pwd=rs.getString("pwd");
			String name=rs.getString("name");
			String email=rs.getString("email");
			Date joinDate=rs.getDate("joindate");
			memFindInfMemberVO = new MemberVO(id, pwd, name, email, joinDate);
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("DB 수정 자료 탐색 중 에러: " + e.getMessage());
		}
		
		return memFindInfMemberVO;
	}
	
	//회원정보 수정
	public void modMember(MemberVO memberVO) {
		String id= memberVO.getId();
		String pwd=memberVO.getPwd();
		String name=memberVO.getName();
		String email=memberVO.getEmail();
		try {
			conn=dataFactory.getConnection();
			String queryString = "update membertbl set pwd=?, name=?, email=? where id=?";
			pstmt=conn.prepareStatement(queryString);
			System.out.println(queryString);
			pstmt.setString(1, pwd);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			pstmt.setString(4, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println("DB 수정중 에러: " + e.getMessage());
		}
	}
	
	public void deleteMember(String _id) {
		try {
			conn = dataFactory.getConnection();
			String query="delete membertbl where id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, _id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("회원 정보 삭제 오류: " + e.getMessage());
		}
	}
}