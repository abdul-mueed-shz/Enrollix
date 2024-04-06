package com.redmath.institution.course.mapper;

import com.redmath.institution.course.dto.CourseDto;
import com.redmath.institution.course.info.CourseInfo;
import com.redmath.institution.course.dto.CourseDto;
import com.redmath.institution.course.info.CourseInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CourseDtoMapper {
    CourseDto courseInfoToCourseDto(CourseInfo courseInfo);

    CourseInfo courseDtoToCourseInfo(CourseDto courseDto);
}
