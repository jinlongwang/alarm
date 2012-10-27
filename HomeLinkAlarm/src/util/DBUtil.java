package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBUtil extends DBSqlMapConfig {

	/**
	 * 将对象转换为string,为null则返回""
	 * 
	 * @param obj
	 * @return
	 */
	private static String objToStr(Object obj) {
		if (obj == null)
			return "";
		else
			return obj.toString();
	}

	/**
	 * 返回列表，对sqlMap.queryForList的调用
	 * 
	 * @param sqlName
	 *            待执行的sql名称
	 * @return
	 * @throws Exception
	 */
	public static List<?> queryForList(String sqlName) throws Exception {
		
		return sqlMap.queryForList(sqlName);
	}

	/**
	 * 返回列表，对sqlMap.queryForList的调用
	 * 
	 * @param sqlName
	 *            待执行的sql名称
	 * @param para
	 *            参数对象
	 * @return
	 * @throws Exception
	 */
	public static List<?> queryForList(String sqlName, Object para)
			throws Exception {

		return sqlMap.queryForList(sqlName, para);
	}

	/**
	 * 返回查询对象，对sqlMap.queryForObject的调用
	 * 
	 * @param sqlName
	 *            待执行的sql名称
	 * @return
	 * @throws Exception
	 */
	public static Object queryForObject(String sqlName) throws Exception {
		Object obj = sqlMap.queryForObject(sqlName);

		return obj;
	}

	/**
	 * 返回查询对象，对sqlMap.queryForObject的调用
	 * 
	 * @param sqlName
	 *            待执行的sql名称
	 * @param para
	 *            参数对象
	 * @return
	 * @throws Exception
	 */
	public static Object queryForObject(String sqlName, Object para)
			throws Exception {
		Object obj = sqlMap.queryForObject(sqlName, para);

		return obj;
	}

	/**
	 * 返回查询对象，对sqlMap.queryForObject的调用，为null则返回""
	 * 
	 * @param sqlName
	 *            待执行的sql名称
	 * @return
	 * @throws Exception
	 */
	public static String queryForString(String sqlName) throws Exception {
		return objToStr(queryForObject(sqlName));
	}

	/**
	 * 返回查询对象，对sqlMap.queryForObject的调用，为null则返回""
	 * 
	 * @param sqlName
	 *            待执行的sql名称
	 * @param para
	 *            参数对象
	 * @return
	 * @throws Exception
	 */
	public static String queryForString(String sqlName, Object para)
			throws Exception {
		return objToStr(queryForObject(sqlName, para));
	}

	/**
	 * 返回查询对象，对sqlMap.queryForObject的调用，返回的结果必须是int型记录
	 * 
	 * @param sqlName
	 * @return
	 * @throws Exception
	 */
	public static int queryForInt(String sqlName) throws Exception {
		return Integer.parseInt(queryForString(sqlName));
	}

	/**
	 * 返回查询对象，对sqlMap.queryForObject的调用，返回的结果必须是int型记录
	 * 
	 * @param sqlName
	 *            待执行的sql名称
	 * @param para
	 *            参数对象
	 * @return
	 * @throws Exception
	 */
	public static int queryForInt(String sqlName, Object para) throws Exception {
		return Integer.parseInt(queryForString(sqlName, para));
	}

	/**
	 * 执行插入，对sqlMap.insert的调用
	 * 
	 * @param arg0
	 *            待执行的sql名称
	 * @return
	 * @throws Exception
	 */
	public static Object insert(String sqlName) throws Exception {
		return sqlMap.insert(sqlName);
	}

	/**
	 * 执行插入，对sqlMap.insert的调用
	 * 
	 * @param arg0
	 *            待执行的sql名称
	 * @param arg1
	 *            参数对象
	 * @return
	 * @throws Exception
	 */
	public static Object insert(String sqlName, Object arg0) throws Exception {
		return sqlMap.insert(sqlName, arg0);
	}

	/**
	 * 执行Update，对sqlMap.update的调用
	 * 
	 * @param arg0
	 *            待执行的sql名称
	 * @return
	 * @throws Exception
	 */
	public static int update(String sqlName) throws Exception {
		return sqlMap.update(sqlName);
	}

	/**
	 * 执行Update，对sqlMap.update的调用
	 * 
	 * @param arg0
	 *            待执行的sql名称
	 * @param arg1
	 *            参数对象
	 * @return
	 * @throws Exception
	 */
	public static int update(String sqlName, Object arg0) throws Exception {
		return sqlMap.update(sqlName, arg0);
	}

	/**
	 * 执行Del，对sqlMap.delete的调用
	 * 
	 * @param arg0
	 *            待执行的sql名称
	 * @return
	 * @throws Exception
	 */
	public static int delete(String sqlName) throws Exception {
		return sqlMap.delete(sqlName);
	}

	/**
	 * 执行Del，对sqlMap.delete的调用
	 * 
	 * @param arg0
	 *            待执行的sql名称
	 * @param arg1
	 *            参数对象
	 * @return
	 * @throws Exception
	 */
	public static int delete(String sqlName, Object arg0) throws Exception {
		return sqlMap.delete(sqlName, arg0);
	}

	/**
	 * 返回列表，分页返回
	 * 
	 * @param cntSqlID
	 *            获取记录条数的sql名称
	 * @param firstPageSqlID
	 *            获取第一页数据的sql名称
	 * @param pageSqlID
	 *            获取第n页(第二页及以上)数据的sql名称
	 * @param map
	 *            传入参数，至少应该包含pageNo、limit等分页参数信息
	 * @return 返回 resultMap.totalPage, resultMap.pageNo, resultMap.limit,
	 *         resultMap.totalCnt, resultMap.resultList(结果集List)
	 * @throws Exception
	 */
	public static HashMap<String,Object> getPage(String cntSqlID, String firstPageSqlID,
			String pageSqlID, HashMap<String,Object> map) throws Exception {
		HashMap<String,Object> resultMap = new HashMap<String,Object>();

		int totalCnt = Integer.parseInt(DBUtil.queryForString(cntSqlID, map));
		resultMap.put("totalCnt", totalCnt);

		int totalPage = 1;
		int pageNo = (Integer) map.get("pageNo");
		int limit = (Integer) map.get("limit"); // limit : pageSize，每页显示条数
		if (pageNo <= 0)
			pageNo = 1;
		if (limit <= 0)
			limit = 10;

		map.put("pageNo", pageNo);
		map.put("limit", limit);
		List<?> resultList = new ArrayList<Object>();
		if (totalCnt > 0) {
			if (totalCnt % limit == 0)
				totalPage = totalCnt / limit;
			else
				totalPage = totalCnt / limit + 1;

			if (pageNo > totalPage)
				pageNo = totalPage;

			int start = (pageNo - 1) * limit;
			map.put("start", start);

			
			if (pageNo == 1)
				resultList = DBUtil.queryForList(firstPageSqlID, map);
			else
				resultList = DBUtil.queryForList(pageSqlID, map);

			
		}
		resultMap.put("resultList", resultList);
		resultMap.put("totalPage", totalPage);
		resultMap.put("pageNo", pageNo);
		resultMap.put("limit", limit);

		return resultMap;
	}
	
	public static void main(String[] args) throws Exception {

		System.out.println(DBUtil.queryForList("data")+"------------------------------------------");
	}

}
