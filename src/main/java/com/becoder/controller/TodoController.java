package com.becoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.TodoDto;
import com.becoder.endpoint.TodoEndpoint;
import com.becoder.service.TodoService;
import com.becoder.util.CommonUtil;

@RestController
public class TodoController implements TodoEndpoint {

	@Autowired
	private TodoService todoService;

	@Override
	public ResponseEntity<?> saveTodo(TodoDto todoDto) throws Exception {

		Boolean saveTodo = todoService.saveTodo(todoDto);
		if (saveTodo) {
			return CommonUtil.createBuildResponseMessage("Todo save successfully", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("Todo not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getTodoById(Integer todoId) throws Exception {

		TodoDto todoDto = todoService.getTodoById(todoId);
		return CommonUtil.createBuildResponse(todoDto, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getTodoByUser() {
		List<TodoDto> allTodo = todoService.getTodoByUser();

		if (CollectionUtils.isEmpty(allTodo)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(allTodo, HttpStatus.OK);
	}
}
