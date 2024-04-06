package com.redmath.institution.course.mapper;

import com.redmath.institution.course.info.CourseInfo;
import com.redmath.institution.course.model.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseInfoMapper {
    Course courseInfoToCourse(CourseInfo courseInfo);

    CourseInfo courseToCourseInfo(Course course);

}
