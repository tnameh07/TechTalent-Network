package project.techTalent.Network.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// For showing customized message at runtime exception, this class created
public class ApiResponse
{
	
	private String message;
	private boolean success;

}
