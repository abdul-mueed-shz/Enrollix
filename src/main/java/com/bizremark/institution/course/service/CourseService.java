package com.bizremark.institution.course.service;

import com.bizremark.institution.common.constants.ErrorConstants;
import com.bizremark.institution.common.constants.IgniteConstants;
import com.bizremark.institution.common.service.IgniteHelperService;
import com.bizremark.institution.course.info.CourseInfo;
import com.bizremark.institution.course.mapper.CourseInfoMapper;
import com.bizremark.institution.course.model.dao.CourseDao;
import com.bizremark.institution.course.model.entity.Course;
import com.bizremark.institution.course.model.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ignite.IgniteException;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final IgniteHelperService igniteHelperService;

    private final CourseDao courseDao;

    private void validateCourse(Long courseId) {
        if (!courseDao.courseExistsById(courseId)) {
            throw new ApplicationContextException(ErrorConstants.COURSE_WITH_ID_DOES_NOT_EXISTS.formatted(courseId));
        }
    }

    public CourseInfo addNewCourse(CourseInfo courseInfo) {
        String courseTitle = courseInfo.getTitle().strip();
        if (courseDao.courseExistsByTitle(courseTitle)) {
            throw new ApplicationContextException(ErrorConstants.COURSE_WITH_TITLE_ALREADY_EXISTS.formatted(courseTitle));
        }
        return courseDao.addNewCourse(courseInfo);
    }


    public void deleteCourse(Long courseId) {
        validateCourse(courseId);
        courseDao.deleteCourse(courseId);
    }

    public List<CourseInfo> getAllCourses() {
        return courseDao.getAllCourses();
    }

    public CourseInfo getCourse(Long courseId) {
        validateCourse(courseId);
        return courseDao.getCourse(courseId);
    }

    @Transactional
    public CourseInfo updateCourse(Long courseId, CourseInfo courseInfo) {
        validateCourse(courseId);
        return courseDao.updateCourse(courseId, courseInfo);
    }
}
