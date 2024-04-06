package com.bizremark.institution.course.model.dao;

import com.bizremark.institution.common.constants.IgniteConstants;
import com.bizremark.institution.common.service.IgniteHelperService;
import com.bizremark.institution.course.info.CourseInfo;
import com.bizremark.institution.course.info.CourseRegistrationInfo;
import com.bizremark.institution.course.mapper.CourseRegistrationInfoMapper;
import com.bizremark.institution.course.model.entity.CourseRegistration;
import com.bizremark.institution.course.model.repository.CourseRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ignite.Ignite;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseRegistrationDao {
    private final Ignite ignite;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseRegistrationInfoMapper courseRegistrationInfoMapper;
    private final IgniteHelperService igniteHelperService;
    private static final String GET_COURSES = """
            SELECT COURSE.ID, COURSE.TITLE, COURSE.DESCRIPTION FROM %s.COURSE AS COURSE INNER JOIN %s.COURSEREGISTRATION
             AS COURSEREGISTRATION ON COURSE.ID = COURSEREGISTRATION.COURSEID"""
            .formatted(IgniteConstants.INSTITUTION_SCHEMA, IgniteConstants.INSTITUTION_SCHEMA);

    private static final String GET_COURSE_REGISTRATION = "SELECT * FROM %s.COURSEREGISTRATION"
            .formatted(IgniteConstants.INSTITUTION_SCHEMA);

    private final GenericConversionService genericConversionService;

    public boolean isStudentCourseRegistered(Long studentId, Long courseId) {
        Cache.Entry<Long, CourseRegistration> courseRegistration =
                courseRegistrationRepository.findByStudentIdAndCourseId(studentId, courseId);
        return !(courseRegistration == null);
    }

    public void registerCourseToStudent(CourseRegistrationInfo courseRegistrationInfo) {
        Long id = igniteHelperService.getIgniteAtomicLongSequence(IgniteConstants.COURSE_REGISTRATION_CACHE_NAME);
        courseRegistrationInfo.setId(id);
        CourseRegistration course = courseRegistrationInfoMapper.courseRegistrationInfoToCourseRegistration(courseRegistrationInfo);
        courseRegistrationRepository.save(id, course);
    }

    public void unRegisterStudentFromCourse(Long studentId, Long courseId) {
        Cache.Entry<Long, CourseRegistration> courseRegistration = courseRegistrationRepository.findByStudentIdAndCourseId(studentId, courseId);
        courseRegistrationRepository.deleteById(courseRegistration.getKey());
    }

    public List<CourseInfo> getRegisteredCoursesForStudent(Long studentId) {
        String fetchRegisteredCoursesForStudentsQuery = "%s WHERE COURSEREGISTRATION.STUDENTID = ?".formatted(GET_COURSES);
        SqlFieldsQuery getRegisteredStudentCoursesQuery = new SqlFieldsQuery(fetchRegisteredCoursesForStudentsQuery).setArgs(studentId);
        FieldsQueryCursor<List<?>> fetchRegisteredStudentCoursesList = ignite.cache(IgniteConstants.COURSE_REGISTRATION_CACHE_NAME)
                .query(getRegisteredStudentCoursesQuery);
        List<CourseInfo> courseRegistrationInfoList = new ArrayList<>();
        for (List<?> eachEntry : fetchRegisteredStudentCoursesList) {
            CourseInfo courseRegistrationInfo = new CourseInfo();
            courseRegistrationInfo.setId(genericConversionService.convert(eachEntry.get(0), Long.class));
            courseRegistrationInfo.setTitle(genericConversionService.convert(eachEntry.get(1), String.class));
            courseRegistrationInfo.setDescription(genericConversionService.convert(eachEntry.get(2), String.class));
            courseRegistrationInfoList.add(courseRegistrationInfo);
        }
        return courseRegistrationInfoList;
    }
}
