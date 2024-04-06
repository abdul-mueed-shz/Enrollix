package com.bizremark.institution.student.service;

import com.bizremark.institution.common.constants.ErrorConstants;
import com.bizremark.institution.common.constants.IgniteConstants;
import com.bizremark.institution.common.service.IgniteHelperService;
import com.bizremark.institution.student.info.StudentInfo;
import com.bizremark.institution.student.info.StudentResponse;
import com.bizremark.institution.student.mapper.StudentInfoMapper;
import com.bizremark.institution.student.model.dao.StudentDao;
import com.bizremark.institution.student.model.entity.Student;
import com.bizremark.institution.student.model.repository.StudentRepository;
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
public class StudentService {
    private final StudentDao studentDao;

    public StudentInfo addNewStudent(StudentInfo studentInfo) {
        String rollNumber = studentInfo.getRollNumber().strip();
        if (studentDao.studentExistsByRollNumber(rollNumber)) {
            throw new ApplicationContextException(ErrorConstants.STUDENT_WITH_ROLL_NUMBER_ALREADY_EXISTS.formatted(rollNumber));
        }
        return studentDao.addNewStudent(studentInfo);
    }


    public void deleteStudent(Long studentId) throws ApplicationContextException {
        if (!studentDao.studentExistsById(studentId)) {
            throw new ApplicationContextException(ErrorConstants.STUDENT_WITH_ID_DOES_NOT_EXISTS.formatted(studentId));
        }
        studentDao.deleteStudent(studentId);
    }

    public List<StudentResponse> getAllStudents() {
        return studentDao.getStudents();
    }

    public StudentResponse getStudent(Long id) throws ApplicationContextException {
        StudentResponse studentResponse = studentDao.getStudent(id);
        if (studentResponse == null) {
            throw new ApplicationContextException(ErrorConstants.STUDENT_WITH_ID_DOES_NOT_EXISTS.formatted(id));
        }
        return studentResponse;
    }

    public StudentInfo updateStudent(Long studentId, StudentInfo studentInfo) {
        if (!studentDao.studentExistsById(studentId)) {
            throw new ApplicationContextException(ErrorConstants.STUDENT_WITH_ID_DOES_NOT_EXISTS.formatted(studentId));
        }
        return studentDao.updateStudent(studentId, studentInfo);
    }
}
