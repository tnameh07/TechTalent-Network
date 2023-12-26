package project.techTalent.Network.exceptions;

public class ResourceNotFoundException extends RuntimeException
{
	String resourceName;
	String fieldName;
	long fieldValue;
	
	//customized user exception
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format( "%s not found with %s : %s " , resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
