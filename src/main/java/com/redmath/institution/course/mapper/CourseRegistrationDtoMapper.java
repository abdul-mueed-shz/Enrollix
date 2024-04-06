package com.redmath.institution.course.mapper;

import com.redmath.institution.course.dto.CourseRegistrationDto;
import com.redmath.institution.course.info.CourseRegistrationInfo;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")

public interface CourseRegistrationDtoMapper {
    CourseRegistrationDto courseRegistrationInfoToCourseRegistrationDto(CourseRegistrationInfo courseRegistrationInfo);

    CourseRegistrationInfo courseRegistrationDtoToCourseRegistrationInfo(CourseRegistrationDto courseDto);

}
