package com.bizremark.institution.course.service;

import com.bizremark.institution.common.constants.ErrorConstants;
import com.bizremark.institution.common.constants.IgniteConstants;
import com.bizremark.institution.common.service.IgniteHelperService;
import com.bizremark.institution.course.info.CourseInfo;
import com.bizremark.institution.course.info.CourseRegistrationInfo;
import com.bizremark.institution.course.mapper.CourseInfoMapper;
import com.bizremark.institution.course.mapper.CourseRegistrationInfoMapper;
import com.bizremark.institution.course.model.dao.CourseRegistrationDao;
import com.bizremark.institution.course.model.entity.Course;
import com.bizremark.institution.course.model.entity.CourseRegistration;
import com.bizremark.institution.course.model.repository.CourseRegistrationRepository;
import com.bizremark.institution.course.model.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ignite.IgniteException;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseRegistrationService {
    public final CourseRegistrationDao courseRegistrationDao;

    public void registerCourseToStudent(CourseRegistrationInfo courseRegistrationInfo) {
        if (courseRegistrationDao.isStudentCourseRegistered(courseRegistrationInfo.getStudentId(), courseRegistrationInfo.getCourseId())) {
            throw new ApplicationContextException(ErrorConstants.COURSE_ALREADY_REGISTERED);
        }
        courseRegistrationDao.registerCourseToStudent(courseRegistrationInfo);
    }

    public void unRegisterStudentFromCourse(Long studentId, Long courseId) {
        if (!courseRegistrationDao.isStudentCourseRegistered(studentId, courseId)) {
            throw new ApplicationContextException(ErrorConstants.INVALID_STUDENT_OR_COURSE_ID);
        }
        courseRegistrationDao.unRegisterStudentFromCourse(studentId, courseId);
    }

    public List<CourseInfo> getRegisteredCourses(Long studentId) {
        return courseRegistrationDao.getRegisteredCoursesForStudent(studentId);
    }
}
