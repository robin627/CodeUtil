package $!{packageName};

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * 数据库访问对象通用基类
 * @author $!{author}
 * @createTime $!{createTime}
 */
public abstract class AbstractDao<T> extends SqlMapClientDaoSupport implements Dao<T> {

	/**
	 * 获取泛型类型名称，如Message
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected String getEntityName(){
		Class <T>  entityClass  =  (Class <T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return entityClass.getSimpleName();
	}
	
	/**
	 * 获取泛型类型实现的接口名称，如MessageDao
	 * @return
	 */
	protected String getBaseInterfaceName(){
		return getClass().getInterfaces()[0].getSimpleName();
	}
	
	public int update(T t) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update(getBaseInterfaceName() + "." + getEntityName() + "-Update", t);

	}

	public void insert(T t) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert(getBaseInterfaceName() + "." + getEntityName() + "-Insert", t);
	}


	public int deleteByKey(Object key) {
		// TODO Auto-generated method stub
		
		return getSqlMapClientTemplate().delete(getBaseInterfaceName() + "." + getEntityName() + "-DeleteByKey", key);
	}
	
	public int deleteByObject(T t) {
		// TODO Auto-generated method stub
		
		return getSqlMapClientTemplate().delete(getBaseInterfaceName() + "." + getEntityName() + "-DeleteByObject", t);
	}
	

	@SuppressWarnings("unchecked")
	public T findByKey(Object key) {
		// TODO Auto-generated method stub
		return (T) getSqlMapClientTemplate().queryForObject(getBaseInterfaceName() + "." + getEntityName() + "-SelectByKey", key);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByObject(T t) {
		// TODO Auto-generated method stub
		return  getSqlMapClientTemplate().queryForList(getBaseInterfaceName() + "." + getEntityName() + "-SelectByObject", t);
	}

}
