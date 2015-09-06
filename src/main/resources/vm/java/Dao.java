package $!{packageName};

import java.util.List;

/**
 * 数据库访问对象通用接口
 * @author $!{author}
 * @createTime $!{createTime}
 */
public interface Dao<T> {
	void insert(T t);
	int update(T t);
	T findByKey(Object key);
	List<T> findByObject(T t);
	int deleteByKey(Object Key);
	int deleteByObject(T t);

}
