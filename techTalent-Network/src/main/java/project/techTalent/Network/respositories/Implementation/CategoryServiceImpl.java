package project.techTalent.Network.respositories.Implementation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.techTalent.Network.exceptions.ResourceNotFoundException;
import project.techTalent.Network.payloads.CategoryDto;
import project.techTalent.Network.respositories.CategoryRepo;
import project.techTalent.Network.services.CategoryService;
import project.techTalent.Network.entities.Category;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private  ModelMapper modelMapper;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category=  this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		 Category oldCategory = this.categoryRepo.findById(categoryId)
					.orElseThrow( () -> new ResourceNotFoundException( "Category" , "Category Id" , categoryId ));
		 oldCategory.setCategoryTitle(categoryDto.getCategoryTitle());
		 oldCategory.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory =  this.categoryRepo.save(oldCategory);
		 
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
		
		//fetch category by id from repositories & then set its propertities, save it  returns in dto forms.
	}

	@Override
	public void deleteCategory(Integer categoryId) 
	{
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Category Id",categoryId));
         this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(() -> new ResourceNotFoundException("Category","Category Id",categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories =this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map
		((cat) -> this.modelMapper.map(categories, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos  ;
	}

}
