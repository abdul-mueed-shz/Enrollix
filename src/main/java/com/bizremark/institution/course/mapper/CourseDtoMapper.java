package com.bizremark.institution.course.mapper;

import com.bizremark.institution.course.dto.CourseDto;
import com.bizremark.institution.course.info.CourseInfo;
import com.bizremark.institution.course.dto.CourseDto;
import com.bizremark.institution.course.info.CourseInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CourseDtoMapper {
    CourseDto courseInfoToCourseDto(CourseInfo courseInfo);

    CourseInfo courseDtoToCourseInfo(CourseDto courseDto);
}
