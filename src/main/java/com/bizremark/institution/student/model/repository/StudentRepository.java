package com.bizremark.institution.student.model.repository;

import com.bizremark.institution.common.constants.IgniteConstants;
import com.bizremark.institution.student.model.entity.Student;
import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;

import javax.cache.Cache;
import java.util.List;

@RepositoryConfig(cacheName = IgniteConstants.STUDENT_CACHE_NAME)
public interface StudentRepository extends IgniteRepository<Student, Long> {
    List<Cache.Entry<Long, Student>> findByName(String name);

    Cache.Entry<Long, Student> findByRollNumber(String rollNumber);

    @Query("SELECT * FROM Student")
    List<Cache.Entry<Long, Student>> findAllStudents();

    @Query("SELECT * FROM Student where id = ?")
    Cache.Entry<Long, Student> findStudentById(Long studentId);
}
