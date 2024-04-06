package com.bizremark.institution.course.mapper;
import com.bizremark.institution.course.info.CourseRegistrationInfo;
import com.bizremark.institution.course.model.entity.CourseRegistration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseRegistrationInfoMapper {
    CourseRegistration courseRegistrationInfoToCourseRegistration(CourseRegistrationInfo courseRegistrationInfo);

    CourseRegistrationInfo courseRegistrationToCourseRegistrationInfo(CourseRegistration courseRegistration);

}