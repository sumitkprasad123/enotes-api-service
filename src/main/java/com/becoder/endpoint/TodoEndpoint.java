package com.becoder.endpoint;

import static com.becoder.util.Constants.ROLE_USER;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.becoder.dto.TodoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Todo", description = "All the Todo releted operation")
@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

	@Operation(summary = "save todo", tags = { "Todo" }, description = "user can save todo")
	@PostMapping("/save")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception;

	@Operation(summary = "get todo by id", tags = { "Todo" }, description = "user can get todo by id")
	@GetMapping("/{todoId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoById(@PathVariable Integer todoId) throws Exception;

	@Operation(summary = "get all user todo", tags = { "Todo" }, description = "user can get all its todo")
	@GetMapping("/list")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoByUser();
}
