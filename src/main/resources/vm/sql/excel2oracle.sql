-- tableName $!{table.tableName}
-- author $!{author}
-- createTime $!{createTime}

-- Create table
create table $!{table.tableName}
(
#foreach( $column in $columns )
$!{column.filedNameEn} $!{column.sqlType}#if($column.isCanNull == 'false') not null#end#if( $!{velocityCount} != $!{columns.size()}),#end

#end
);

-- Add comments to the table 
comment on table $!{table.tableName} is '$!{table.tableNameCn}';

-- Add comments to the columns 
#foreach( $column in $columns )
comment on column $!{column.tableName}.$!{column.filedNameEn} is '$!{column.filedNameCn}  $!{column.desc}';
#end

-- Create/Recreate primary
#foreach( $column in $columns )
#if ( $column.isPrimaryKey == 'true')
alter table $!{column.tableName} add primary key ($!{column.filedNameEn});
#end
#end

--Add default
#foreach( $column in $columns )
#if ("" != $column.deVlaue)
alter table $!{column.tableName} modify $!{column.filedNameEn} default $!{column.deVlaue};
#end
#end

