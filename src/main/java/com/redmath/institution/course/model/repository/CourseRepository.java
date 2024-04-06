package com.redmath.institution.course.model.repository;

import com.redmath.institution.common.constants.IgniteConstants;
import com.redmath.institution.course.model.entity.Course;
import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;

import javax.cache.Cache;
import java.util.List;

@RepositoryConfig(cacheName = IgniteConstants.COURSE_CACHE_NAME)
public interface CourseRepository extends IgniteRepository<Course, Long> {
    Cache.Entry<Long, Course> findByTitle(String title);

    @Query("SELECT * FROM Course")
    List<Cache.Entry<Long, Course>> findAllCourses();

    @Query("SELECT * FROM Course where id = ?")
    Cache.Entry<Long, Course> findCourseById(Long courseId);
}
