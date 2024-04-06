package com.bizremark.institution.course.model.dao;

import com.bizremark.institution.common.constants.ErrorConstants;
import com.bizremark.institution.common.constants.IgniteConstants;
import com.bizremark.institution.common.service.IgniteHelperService;
import com.bizremark.institution.course.info.CourseInfo;
import com.bizremark.institution.course.mapper.CourseInfoMapper;
import com.bizremark.institution.course.model.entity.Course;
import com.bizremark.institution.course.model.repository.CourseRepository;
import com.bizremark.institution.student.model.entity.Student;
import lombok.RequiredArgsConstructor;
import org.apache.ignite.Ignite;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseDao {
    private final Ignite ignite;
    private final IgniteHelperService igniteHelperService;

    private final CourseRepository courseRepository;
    private final CourseInfoMapper courseInfoMapper;

    public boolean courseExistsByTitle(String title) {
        Cache.Entry<Long, Course> course = courseRepository.findByTitle(title);
        return !(course == null);
    }

    public boolean courseExistsById(Long courseId) {
        return courseRepository.existsById(courseId);
    }

    public CourseInfo addNewCourse(CourseInfo courseInfo) {
        Long id = igniteHelperService.getIgniteAtomicLongSequence(IgniteConstants.COURSE_CACHE_NAME);
        courseInfo.setId(id);
        Course course = courseInfoMapper.courseInfoToCourse(courseInfo);
        courseRepository.save(id, course);
        return courseInfo;
    }


    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    public List<CourseInfo> getAllCourses() {
        List<CourseInfo> courses = new ArrayList<>();
        List<Cache.Entry<Long, Course>> courseList = courseRepository.findAllCourses();
        if (courseList.isEmpty()) {
            return courses;
        }
        courseList.forEach(course -> {
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.setId(course.getKey());
            courseInfo.setTitle(course.getValue().getTitle());
            courseInfo.setDescription(course.getValue().getDescription());
            courses.add(courseInfo);
        });
        return courses;
    }

    public CourseInfo getCourse(Long courseId) {
        Cache.Entry<Long, Course> course = courseRepository.findCourseById(courseId);
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setId(course.getKey());
        courseInfo.setTitle(course.getValue().getTitle());
        courseInfo.setDescription(course.getValue().getDescription());
        return courseInfo;
    }

    @Transactional
    public CourseInfo updateCourse(Long courseId, CourseInfo courseInfo) {
        courseInfo.setId(courseId);
        Course updatedCourse = courseInfoMapper.courseInfoToCourse(courseInfo);
        courseRepository.save(courseId, updatedCourse);
        return courseInfo;
    }
}