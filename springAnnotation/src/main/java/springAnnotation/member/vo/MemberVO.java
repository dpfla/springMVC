package springAnnotation.member.vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

//memberVO라는 이름으로 MemberVO객체 생성
@Component("memberVO")
public class MemberVO {
	
	private String pwd;
	private String id;
	private String name;
	private String email;
	private Date joinDate;
	
	public MemberVO() {
		// TODO Auto-generated constructor stub
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	
}
