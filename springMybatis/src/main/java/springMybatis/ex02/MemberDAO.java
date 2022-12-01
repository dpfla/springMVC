package springMybatis.ex02;

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
	
	public String selectId() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		String id=null;
		id=session.selectOne("mapper.member.selectId");
		/* member.xml���� mapper�±��� namespace(�̸�)�� mapper.member�� �����ߴ�
		 * mapper.member ���� ������ selectAllMemberlist�� �����´�
		 */
		return id;
	}
	public String selectPwd() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		String pwd=null;
		pwd=session.selectOne("mapper.member.selectPwd");
		/* member.xml���� mapper�±��� namespace(�̸�)�� mapper.member�� �����ߴ�
		 * mapper.member ���� ������ selectAllMemberlist�� �����´�
		 */
		return pwd;
	}
}
