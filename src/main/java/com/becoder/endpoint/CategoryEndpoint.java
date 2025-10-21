package com.becoder.endpoint;

import static com.becoder.util.Constants.ROLE_ADMIN;
import static com.becoder.util.Constants.ROLE_ADMIN_USER;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.becoder.dto.CategoryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Category", description = "All category operation API")
@RequestMapping("api/v1/category")
public interface CategoryEndpoint {

	@Operation(summary = "save category", tags = { "Category" }, description = "Admin will be save the category")
	@PostMapping("/save")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);

	@Operation(summary = "get all categories", tags = {
			"Category" }, description = "User will be get all the categories")
	@GetMapping("/get")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllCategory();

	@Operation(summary = "get all active categories", tags = {
			"Category" }, description = "Admin and user will be get all the active categories")
	@GetMapping("/active")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> activeCategory();

	@Operation(summary = "get category by id", tags = { "Category" }, description = "Admin will be get category by Id")
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategortDetailsById(@PathVariable Integer id) throws Exception;

	@Operation(summary = "delete category", tags = {
			"Category" }, description = "Admin will be able to delete the category")
	@DeleteMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id);
}
