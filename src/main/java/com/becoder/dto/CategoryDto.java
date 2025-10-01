package com.becoder.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDto {

	private Integer id;

//	@NotBlank
//	@Min(value = 10, message = "min 10")
//	@Max(value = 100)
	private String name;

//	@NotBlank
//	@Min(value = 10)
//	@Max(value = 100)
	private String description;

//	@NotNull
	private Boolean isActive;

	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;
}
