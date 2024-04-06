package com.redmath.institution.course.mapper;
import com.redmath.institution.course.info.CourseRegistrationInfo;
import com.redmath.institution.course.model.entity.CourseRegistration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseRegistrationInfoMapper {
    CourseRegistration courseRegistrationInfoToCourseRegistration(CourseRegistrationInfo courseRegistrationInfo);

    CourseRegistrationInfo courseRegistrationToCourseRegistrationInfo(CourseRegistration courseRegistration);

}