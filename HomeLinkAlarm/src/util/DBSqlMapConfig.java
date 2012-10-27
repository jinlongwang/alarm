package util;

import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.log4j.Logger;


import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class DBSqlMapConfig {

	private static final Logger logger = Logger.getLogger(DBSqlMapConfig.class);
	protected static final SqlMapClient sqlMap;
	static {
		try {
			// ----数据库配置文件信息

			Properties props = Resources
					.getResourceAsProperties("datasource.properties");
			// String password = props.getProperty("password");
			// password = AppEncrypter.getInstance().decrypt(password);
			// if (password == null || password.equals(""))
			// password = props.getProperty("password");
			//			
			// // System.err.println(password);
			//			
			// //---将解密后的密码重新写入到Props中
			// props.setProperty("password", password);

			// ---必须设置字符集，否则.sql文件中存在中文时，会出问题。 例如 insert tabA(a)
			// values('中文')，会乱码或出错
			//Resources.setCharset(Charset.forName("utf-8"));

			Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader, props);
			reader.close();
		} catch (Exception e) {
			logger.error(DBSqlMapConfig.class, e);
			throw new RuntimeException("初始数据源时错误: " + e);
		}
	}

	public static SqlMapClient getSqlMapInstance() {
		return sqlMap;
	}

}
