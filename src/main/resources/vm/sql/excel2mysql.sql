-- tableName $!{table.tableName}
-- author $!{author}
-- createTime $!{createTime}

-- 判断表是否存在
DROP TABLE IF EXISTS $!{table.tableName};

-- Create table
CREATE TABLE $!{table.tableName} (
#foreach( $column in $columns )
$!{column.filedNameEn} $!{column.sqlType} COMMENT '$!{column.filedNameCn}  $!{column.desc}'#if($!{velocityCount} != $!{columns.size()}),#end

#end
);

-- Add comments to the table 
ALTER TABLE $!{table.tableName} COMMENT='$!{table.tableNameCn}';

-- Create/Recreate primary
#foreach( $column in $columns )
#if ( $column.isPrimaryKey == 'true')
alter table $!{column.tableName} add primary key ($!{column.filedNameEn});
#end
#end

-- Add default
#foreach( $column in $columns )
#if ("" != $column.deVlaue)
alter table $!{column.tableName} alter column $!{column.filedNameEn} set default $!{column.deVlaue};
#end
#end

-- Add not null
#foreach( $column in $columns )
#if ( $column.isCanNull == 'false' )
alter table $!{column.tableName} add constraint $!{column.filedNameEn} check($!{column.filedNameEn} is not null);
#end
#end

-- Add auto_increment
#foreach( $column in $columns )
#if ( $column.isIdentity == 'true' )
alter table $!{column.tableName} change $!{column.filedNameEn} $!{column.filedNameEn} $!{column.sqlType} auto_increment;
#end
#end

