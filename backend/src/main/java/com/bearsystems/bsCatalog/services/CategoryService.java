package com.bearsystems.bsCatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bearsystems.bsCatalog.dto.CategoryDTO;
import com.bearsystems.bsCatalog.entities.Category;
import com.bearsystems.bsCatalog.repositories.CategoryRepository;
import com.bearsystems.bsCatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repository.findAll();		
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
		//List<CategoryDTO> listDto = new ArrayList<>();
		//for(Category cat : list)
		//{
		//	listDto.add(new CategoryDTO(cat));
		//}
		//return listDto;		
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado"));
		
		return new CategoryDTO(entity);
	}

	@Transactional(readOnly = true)
	public CategoryDTO insert(CategoryDTO dto) {

		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		
		return new CategoryDTO(entity);
	}


}