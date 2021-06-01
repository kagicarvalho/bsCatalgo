package com.bearsystems.bsCatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bearsystems.bsCatalog.dto.CategoryDTO;
import com.bearsystems.bsCatalog.dto.ProductDTO;
import com.bearsystems.bsCatalog.entities.Category;
import com.bearsystems.bsCatalog.entities.Product;
import com.bearsystems.bsCatalog.repositories.CategoryRepository;
import com.bearsystems.bsCatalog.repositories.ProductRepository;
import com.bearsystems.bsCatalog.services.exceptions.DatabaseException;
import com.bearsystems.bsCatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll(){
		List<Product> list = repository.findAll();		
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
		
		//List<ProductDTO> listDto = new ArrayList<>();
		//for(Product cat : list)
		//{
		//	listDto.add(new ProductDTO(cat));
		//}
		//return listDto;
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {

		Page<Product> list = repository.findAll(pageable);	
		return list.map(x -> new ProductDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado"));
		
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional(readOnly = true)
	public ProductDTO insert(ProductDTO dto) {

		Product entity = new Product();
		
		//entity.setName(dto.getName());		
		copyDtoToEntity(dto, entity);
		
		entity = repository.save(entity);
		
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		
		try {
			
			Product entity = repository.getOne(id);
			
			//entity.setName(dto.getName());		
			copyDtoToEntity(dto, entity);
			
			entity= repository.save(entity);
			
			return new ProductDTO(entity, entity.getCategories());
			
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado" + id);
		}
	}

	public void delete(Long id) {
		
		try {

			repository.deleteById(id);
			
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado" + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
				
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		
		for(CategoryDTO catDto : dto.getCategories())
		{
			Category category = categoryRepository.getOne(catDto.getId());
			entity.getCategories().add(category);			
		}
		
	}
}