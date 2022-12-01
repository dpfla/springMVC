package springMybatis.ex01;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MemberDAO {
	public static SqlSessionFactory sqlMapper=null;
	
	private static SqlSessionFactory getInstance() {
		//DB연결 함수
		if(sqlMapper == null) {
			try {
				String resource="mybatis/SqlMapConfig.xml";
				Reader reader=Resources.getResourceAsReader(resource);
				//DB정보를 읽어온다
				sqlMapper=new SqlSessionFactoryBuilder().build(reader);
				reader.close();
			} catch (Exception e) {
				System.out.println("DB연결 중 에러: " + e.getMessage());
			}
		}
		
		return sqlMapper;
	}
	
	public List<MemberVO> selectAllMemberlist() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memlist=null;
		memlist=session.selectList("mapper.member.selectAllMemberlist");
		/* member.xml에서 mapper태그의 namespace(이름)를 mapper.member로 설정했다
		 * mapper.member 에서 정의한 selectAllMemberlist을 가져온다
		 */
		return memlist;
	}
}
