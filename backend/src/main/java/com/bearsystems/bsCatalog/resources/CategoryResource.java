package com.bearsystems.bsCatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bearsystems.bsCatalog.dto.CategoryDTO;
import com.bearsystems.bsCatalog.entities.Category;
import com.bearsystems.bsCatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/Categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAl()
	{
		List<CategoryDTO> list = service.findAll();		
		return ResponseEntity.ok().body(list);
	}
	
}
