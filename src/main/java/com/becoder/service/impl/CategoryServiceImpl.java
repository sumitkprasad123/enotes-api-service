package com.becoder.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.CategoryRepository;
import com.becoder.service.CategoryService;
import com.becoder.util.Validation;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;
	

	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		//Validation Checking
		validation.categoryValidation(categoryDto);
		Category category = mapper.map(categoryDto, Category.class);
		
		if(ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
//			category.setCreatedBy(1);
//			category.setCreatedOn(new Date());
		}else{
			updateCategory(category);
		}

		
		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	private void updateCategory(Category category) {
         Optional<Category> categoryExist = categoryRepo.findById(category.getId());
         if(categoryExist.isPresent()) {
        	 Category getCategoryExists = categoryExist.get();
        	 category.setCreatedBy(getCategoryExists.getCreatedBy());
        	 category.setCreatedOn(getCategoryExists.getCreatedOn());
        	 category.setIsDeleted(getCategoryExists.getIsDeleted());
//        	 category.setUpdatedBy(1);
//        	 category.setUpdatedOn(new Date());
         }
	}

	@Override
	public List<CategoryDto> getAllCategory() {

		List<Category> categories = categoryRepo.findByIsDeletedFalse();
		List<CategoryDto> categoryDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> activeCategories = categories.stream()
				.map(cat -> mapper.map(cat, CategoryResponse.class)).toList();
		return activeCategories;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) throws Exception {

		Category category = categoryRepo.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id=" + id));

		if (!ObjectUtils.isEmpty(category)) {
			if(category.getName() == null) {
				throw new IllegalArgumentException("name is null");
			}
			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
		Optional<Category> findByCatgeory = categoryRepo.findById(id);

		if (findByCatgeory.isPresent()) {
			Category category = findByCatgeory.get();
			category.setIsDeleted(true);
			categoryRepo.save(category);
			return true;
		}
		return false;
	}

}
