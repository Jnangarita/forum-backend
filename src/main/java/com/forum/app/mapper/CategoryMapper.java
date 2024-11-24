package com.forum.app.mapper;

import com.forum.app.dto.request.CategoryInput;
import com.forum.app.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category convertDtoToEntity(CategoryInput categoryInput);
    void updateCategoryFromDto(CategoryInput categoryInput, @MappingTarget Category category);
}