package project.techTalent.Network.payloads;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto 
{
	private Integer categoryId;
	
	// Only validate if the field is provided
	@Size(min=4, message = "Title must be at least 4 characters long")
	private String categoryTitle;
	
	private String categoryDescription;
}
