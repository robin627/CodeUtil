-- tableName $!{table.tableName}
-- author $!{author}
-- createTime $!{createTime}

-- 判断表是否存在
if exists(select * from dbo.sysobjects where name='$!{table.tableName}')  
drop table userInfo  
GO  

-- Create table
CREATE TABLE $!{table.tableName} (
#foreach( $column in $columns )
$!{column.filedNameEn} $!{column.sqlType}#if($column.isIdentity == 'true') identity(1,1)#end#if($column.isCanNull == 'false') not null#end#if($!{velocityCount} != $!{columns.size()}),#end

#end
)
Go

-- Add comments to the table 
EXECUTE sp_addextendedproperty N'MS_Description', N'$!{table.tableNameCn}', N'user', N'dbo', N'table', $!{table.tableName}, NULL, NULL
GO

-- Add comments to the column
#foreach( $column in $columns )
EXECUTE sp_addextendedproperty N'MS_Description', N'$!{column.filedNameCn}  $!{column.desc}', N'user', N'dbo', N'table', N'$!{column.tableName}', N'column', N'$!{column.filedNameEn}'
GO
#end

-- Create/Recreate primary
#foreach( $column in $columns )
#if ( $column.isPrimaryKey == 'true')
alter table $!{column.tableName} add constraint PK_$!{column.tableName}_$!{column.filedNameEn} primary key($!{column.filedNameEn})
GO
#end
#end

-- Add default
#foreach( $column in $columns )
#if ("" != $column.deVlaue)
ALTER TABLE $!{column.tableName} ADD CONSTRAINT DF_$!{column.filedNameEn} DEFAULT '$!{column.deVlaue}' FOR $!{column.filedNameEn}
GO
#end
#end

