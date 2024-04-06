package com.bizremark.institution.course.mapper;

import com.bizremark.institution.course.info.CourseInfo;
import com.bizremark.institution.course.model.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseInfoMapper {
    Course courseInfoToCourse(CourseInfo courseInfo);

    CourseInfo courseToCourseInfo(Course course);

}
