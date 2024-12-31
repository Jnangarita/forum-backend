package com.forum.app.mapper.helper;

import com.forum.app.dto.request.IdValueInput;
import com.forum.app.utils.Utility;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UtilMapperHelper {
    private final Utility utility;

    public UtilMapperHelper(Utility utility) {
        this.utility = utility;
    }

    @Named("mapToDate")
    public LocalDateTime mapToDate(Object value) {
        return utility.convertToLocalDateTime(value);
    }

    @Named("mapToIdValueInputList")
    public List<IdValueInput> mapToIdValueInputList(Object value) {
        String categoriesJson = (String) value;
        return utility.convertJsonToIdValueInputList(categoriesJson);
    }

    @Named("mapToBoolean")
    public Boolean mapToBoolean(Object value) {
        return Boolean.parseBoolean(value.toString());
    }

    @Named("mapToLong")
    public Long mapToLong(Object value) {
        return value != null ? ((Number) value).longValue() : null;
    }

    @Named("mapToInteger")
    public Integer mapToInteger(Object value) {
        return value != null ? ((Number) value).intValue() : null;
    }

    @Named("mapToString")
    public String mapToString(Object value) {
        return value != null ? value.toString() : null;
    }
}