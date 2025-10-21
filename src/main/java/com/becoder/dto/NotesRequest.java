package com.becoder.dto;

import com.becoder.dto.NotesDto.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotesRequest {

	private String title;

	private String description;

	private CategoryDto category;

}
