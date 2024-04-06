package com.bizremark.institution.course.model.repository;

import com.bizremark.institution.common.constants.IgniteConstants;
import com.bizremark.institution.course.model.entity.CourseRegistration;
import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;

import javax.cache.Cache;
import java.util.List;

@RepositoryConfig(cacheName = IgniteConstants.COURSE_REGISTRATION_CACHE_NAME)
public interface CourseRegistrationRepository extends IgniteRepository<CourseRegistration, Long> {
    List<Cache.Entry<Long, CourseRegistration>> findByStudentId(Long studentId);

    List<Cache.Entry<Long, CourseRegistration>> findByCourseId(Long studentId);

    @Query("SELECT * FROM CourseRegistration WHERE studentId = ? AND courseId = ?")
    Cache.Entry<Long, CourseRegistration> findByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("DELETE FROM CourseRegistration WHERE studentId = ? AND courseId = ?")
    List<CourseRegistration> deleteByStudentIdAndCourseId(Long studentId, Long courseId);
}
