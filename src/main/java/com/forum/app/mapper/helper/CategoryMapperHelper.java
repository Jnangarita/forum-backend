package com.forum.app.mapper.helper;

import com.forum.app.dto.request.CategoryInput;
import com.forum.app.entity.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperHelper {
    @AfterMapping
    public void handleCreatedBy(CategoryInput categoryInput, @MappingTarget Category category) {
        if (categoryInput.getCreatedBy() != null) {
            category.setCreatedBy(categoryInput.getCreatedBy());
        }
    }
}