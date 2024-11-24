package com.forum.app.mapper;

import com.forum.app.dto.request.CategoryInput;
import com.forum.app.entity.Category;
import com.forum.app.mapper.helper.CategoryMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CategoryMapperHelper.class})
public interface CategoryMapper {
    Category convertDtoToEntity(CategoryInput categoryInput);
    @Mapping(target = "createdBy", ignore = true)
    void updateCategoryFromDto(CategoryInput categoryInput, @MappingTarget Category category);
}