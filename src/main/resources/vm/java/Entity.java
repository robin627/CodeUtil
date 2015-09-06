package $!{packageName};

import java.io.Serializable;
import java.util.Date;

/**
 * $!{table.tableComment}
 * @author $!{author}
 * @createTime $!{createTime}
 */
public class $!{table.entityName} implements Serializable {

	private static final long serialVersionUID = 1L;
	
#foreach( $field in $columns )
	protected ${field.propertyTypeName} ${field.camelPropertyName};
	public ${field.propertyTypeName} get${field.pascalPropertyName}() {
		return ${field.camelPropertyName};
	}
	public void set${field.pascalPropertyName}(${field.propertyTypeName} ${field.camelPropertyName}) {
		this.${field.camelPropertyName} = ${field.camelPropertyName};
	}	
	
#end

}
