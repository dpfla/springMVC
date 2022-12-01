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
		//DB���� �Լ�
		if(sqlMapper == null) {
			try {
				String resource="mybatis/SqlMapConfig.xml";
				Reader reader=Resources.getResourceAsReader(resource);
				//DB������ �о�´�
				sqlMapper=new SqlSessionFactoryBuilder().build(reader);
				reader.close();
			} catch (Exception e) {
				System.out.println("DB���� �� ����: " + e.getMessage());
			}
		}
		
		return sqlMapper;
	}
	
	public List<MemberVO> selectAllMemberlist() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memlist=null;
		memlist=session.selectList("mapper.member.selectAllMemberlist");
		/* member.xml���� mapper�±��� namespace(�̸�)�� mapper.member�� �����ߴ�
		 * mapper.member ���� ������ selectAllMemberlist�� �����´�
		 */
		return memlist;
	}
}
