package com.bearsystems.bsCatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.bearsystems.bsCatalog.entities.Product;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		long exintingId = 1L;
		
		repository.deleteById(exintingId);
		
		Optional<Product> result = repository.findById(exintingId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdNotExists() {

		long notExintingId = 100L;
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> { 
			repository.deleteById(notExintingId);
		});
		
	}
	
}
