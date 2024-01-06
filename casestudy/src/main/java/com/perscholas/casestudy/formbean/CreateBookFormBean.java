package com.perscholas.casestudy.formbean;

import com.perscholas.casestudy.database.entity.Categories;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateBookFormBean {
    private Integer id;
    @NotEmpty(message = "First Name is required.")
    @Length(max = 45, message = " Name must be less than 45 characters.")
    private String name;
    @Length(max = 1000, message = "imageURL must be less than 1000 characters.")
    private  String imageUrl;
    @NotEmpty(message = "author is required.")
    @Length(max = 45, message = "Last Name must be less than 45 characters.")
    private  String author;
    @NotNull(message = "Category is required.")
    private Integer categoryId;


}
