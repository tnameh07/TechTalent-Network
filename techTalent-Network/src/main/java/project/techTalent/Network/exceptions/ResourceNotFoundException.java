package project.techTalent.Network.exceptions;

public class ResourceNotFoundException extends RuntimeException
{
	String resourceName;
	String fieldName;
	long fieldValue;
	
	//customized resource not found exception
	public ResourceNotFoundException(String resourceName, String fieldName, long string) {
		super(String.format( "%s not found with %s : %s " , resourceName,fieldName,string));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = string;
	}
}
