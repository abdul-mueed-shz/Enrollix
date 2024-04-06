package com.bizremark.institution.course.mapper;

import com.bizremark.institution.course.dto.CourseRegistrationDto;
import com.bizremark.institution.course.info.CourseRegistrationInfo;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")

public interface CourseRegistrationDtoMapper {
    CourseRegistrationDto courseRegistrationInfoToCourseRegistrationDto(CourseRegistrationInfo courseRegistrationInfo);

    CourseRegistrationInfo courseRegistrationDtoToCourseRegistrationInfo(CourseRegistrationDto courseDto);

}
