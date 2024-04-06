package com.redmath.institution.student.model.dao;

import com.redmath.institution.common.constants.ErrorConstants;
import com.redmath.institution.common.constants.IgniteConstants;
import com.redmath.institution.common.service.IgniteHelperService;
import com.redmath.institution.course.info.CourseInfo;
import com.redmath.institution.course.model.entity.CourseRegistration;
import com.redmath.institution.student.info.StudentInfo;
import com.redmath.institution.student.info.StudentResponse;
import com.redmath.institution.student.mapper.StudentInfoMapper;
import com.redmath.institution.student.model.entity.Student;
import com.redmath.institution.student.model.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.cache.Cache;
import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentDao {
    private final StudentRepository studentRepository;
    private final StudentInfoMapper studentInfoMapper;
    private final IgniteHelperService igniteHelperService;

    private final Ignite ignite;
    private static final String GET_STUDENTS = """
            SELECT STUDENT.ID AS StudentID,STUDENT.NAME, STUDENT.DOB, STUDENT.ROLLNUMBER, COURSE.ID AS CourseID,
            COURSE.Title, COURSE.Description FROM INSTITUTION.STUDENT AS STUDENT
            LEFT JOIN INSTITUTION.COURSEREGISTRATION AS COURSE_REGISTRATIONS ON 
            STUDENT.ID = COURSE_REGISTRATIONS.STUDENTID LEFT JOIN INSTITUTION.COURSE AS COURSE 
            ON COURSE_REGISTRATIONS.COURSEID = COURSE.ID""";
    ;
    private final GenericConversionService genericConversionService;


    public boolean studentExistsByRollNumber(String rollNumber) {
        Cache.Entry<Long, Student> student = studentRepository.findByRollNumber(rollNumber);
        return !(student == null);
    }

    public boolean studentExistsById(Long studentId) {
        return studentRepository.existsById(studentId);
    }

    private CourseInfo handleCourseInfoCreation(List<?> studentInfo) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setId(genericConversionService.convert(studentInfo.get(4), Long.class));
        courseInfo.setTitle(genericConversionService.convert(studentInfo.get(5), String.class));
        courseInfo.setDescription(genericConversionService.convert(studentInfo.get(6), String.class));
        return courseInfo;
    }

    private StudentResponse handleStudentResponseCreation(List<?> studentInfo) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(genericConversionService.convert(studentInfo.get(0), Long.class));
        studentResponse.setName(genericConversionService.convert(studentInfo.get(1), String.class));
        studentResponse.setDob(genericConversionService.convert(studentInfo.get(2), Timestamp.class));
        studentResponse.setRollNumber(genericConversionService.convert(studentInfo.get(3), String.class));
        return studentResponse;
    }

    private void appendCourseToStudentCoures(Long courseId, StudentResponse studentResponse, List<?> studentInfo) {
        CourseInfo courseInfo = handleCourseInfoCreation(studentInfo);
        studentResponse.appendCourseInfo(courseInfo);
    }

    public StudentInfo addNewStudent(StudentInfo studentInfo) {
        Long id = igniteHelperService.getIgniteAtomicLongSequence(IgniteConstants.STUDENT_CACHE_NAME);
        studentInfo.setId(id);
        studentInfo.setRollNumber(studentInfo.getRollNumber().toLowerCase());
        Student student = studentInfoMapper.studentInfoToStudent(studentInfo);
        studentRepository.save(id, student);
        studentInfo.setAge(student.getAge());
        return studentInfo;
    }

    @Transactional
    public StudentInfo updateStudent(Long studentId, StudentInfo studentInfo) throws ApplicationContextException {
        studentInfo.setId(studentId);
        studentInfo.setRollNumber(studentInfo.getRollNumber().toLowerCase());
        Student updatedStudent = studentInfoMapper.studentInfoToStudent(studentInfo);
        studentRepository.save(studentId, updatedStudent);
        studentInfo.setAge(updatedStudent.getAge());
        return studentInfo;
    }


    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public StudentResponse getStudent(Long id) {
        String fetchRegisteredCoursesForStudentsQuery = "%s WHERE COURSE_REGISTRATIONS.STUDENTID = ?".formatted(GET_STUDENTS);
        SqlFieldsQuery getStudentQuery = new SqlFieldsQuery(fetchRegisteredCoursesForStudentsQuery).setArgs(id);
        FieldsQueryCursor<List<?>> fetchRegisteredStudentCoursesList = ignite.cache(IgniteConstants.STUDENT_CACHE_NAME)
                .query(getStudentQuery);

        StudentResponse studentResponse = null;

        for (List<?> eachEntry : fetchRegisteredStudentCoursesList) {
            Long studentId = genericConversionService.convert(eachEntry.get(0), Long.class);
            Long courseId = genericConversionService.convert(eachEntry.get(4), Long.class);

            if (!Objects.equals(studentId, id)) {
                continue;
            }
            if (studentResponse == null) {
                studentResponse = handleStudentResponseCreation(eachEntry);
                appendCourseToStudentCoures(courseId, studentResponse, eachEntry);
            } else {
                appendCourseToStudentCoures(courseId, studentResponse, eachEntry);
            }
        }
        return studentResponse;
    }

    public List<StudentResponse> getStudents() {
        SqlFieldsQuery getStudentsQuery = new SqlFieldsQuery(GET_STUDENTS);
        FieldsQueryCursor<List<?>> fetchRegisteredStudentCoursesList = ignite.cache(IgniteConstants.STUDENT_CACHE_NAME)
                .query(getStudentsQuery);

        Map<Long, StudentResponse> studentMap = new HashMap<>();
        for (List<?> eachEntry : fetchRegisteredStudentCoursesList) {
            Long studentId = genericConversionService.convert(eachEntry.get(0), Long.class);
            Long courseId = genericConversionService.convert(eachEntry.get(4), Long.class);
            if (studentMap.containsKey(studentId)) {
                StudentResponse studentResponse = studentMap.get(studentId);
                appendCourseToStudentCoures(courseId, studentResponse, eachEntry);
            } else {
                StudentResponse studentResponse = handleStudentResponseCreation(eachEntry);
                appendCourseToStudentCoures(courseId, studentResponse, eachEntry);
                studentMap.put(studentId, studentResponse);
            }
        }
        return new ArrayList<>(studentMap.values());
    }
}
