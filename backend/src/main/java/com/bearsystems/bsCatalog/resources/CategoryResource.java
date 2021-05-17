package com.bearsystems.bsCatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bearsystems.bsCatalog.entities.Category;

@RestController
@RequestMapping(value = "/Categories")
public class CategoryResource {
	
	@GetMapping
	public ResponseEntity<List<Category>> findAl()
	{
		List<Category> list = new ArrayList<>();
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Eletronics"));
		
		return ResponseEntity.ok().body(list);
	}
	
}
