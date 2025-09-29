package com.becoder.dto;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotesDto {
	
	 private Integer id;
	  
	  private String title;
	   
	  private String description;
	  
	  private CategoryDto category;

	  private Integer createdBy;

	  private Date createdOn;

	  private Integer updatedBy;

	  private Date updatedOn;
	  
	    @Getter
		@Setter
		@NoArgsConstructor
		@AllArgsConstructor
		public static class CategoryDto {
			private Integer id;
			private String name;
		}
}
