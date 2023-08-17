package com.bloggingapp.servicesimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloggingapp.dto.CategoryDto;
import com.bloggingapp.entity.CategoryMaster;
import com.bloggingapp.exception.ResourceNotFoundException;
import com.bloggingapp.repository.CategoryRepository;
import com.bloggingapp.services.CategoryServices;

@Service
public class CategoryServicesImpl implements CategoryServices {

	@Autowired
	private CategoryRepository repo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		CategoryMaster category = null;
		CategoryMaster SaveCategory = null;

		try {
			category = this.categoryDtoToCategory(categoryDto);
			SaveCategory = this.repo.save(category);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.categoryToCategoryDto(SaveCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
		CategoryMaster category = null;
		CategoryDto dto = null;
		try {
			category = this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(" Category ", "id ", id));
			category.setCategoryTitle(categoryDto.getCategoryTitle());
			category.setCategoryDescription(categoryDto.getCategoryDescription());

			CategoryMaster save = this.repo.save(category);
			dto = this.categoryToCategoryDto(save);

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return dto;
	}

	@Override
	public void deleteCategory(Integer id) {

		CategoryMaster category = this.repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(" Category ", " id ", id));
		repo.deleteById(id);
	}

	@Override
	public CategoryDto getCategory(Integer id) {
		CategoryMaster category = null;

		try {
			category = this.repo.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(" Category ", " id ", id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {

		List<CategoryMaster> allCategory = null;
		List<CategoryDto> categoryDto = null;
		allCategory = this.repo.findAll();
		categoryDto = allCategory.stream().map(category -> this.categoryToCategoryDto(category))
				.collect(Collectors.toList());

		return categoryDto;

	}

	public CategoryMaster categoryDtoToCategory(CategoryDto dto) {
		CategoryMaster category = null;
		try {
			category = this.modelMapper.map(dto, CategoryMaster.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;
	}

	public CategoryDto categoryToCategoryDto(CategoryMaster category) {
		CategoryDto categoryDto = null;
		try {
			categoryDto = this.modelMapper.map(category, CategoryDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryDto;
	}

}
