package $!{daoImplPackageName};

import org.springframework.stereotype.Repository;

import $!{daoPackageName}.AbstractDao;
import $!{daoPackageName}.$!{table.entityName}Dao;
import $!{entityPackageName}.$!{table.entityName};

/**
 * $!{table.entityName}Dao数据库访问对象接口
 * @author $!{author}
 * @createTime $!{createTime}
 */
@Repository
public class $!{table.entityName}DaoImpl extends AbstractDao<$!{table.entityName}> implements $!{table.entityName}Dao {

}
