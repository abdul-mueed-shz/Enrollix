package com.bizremark.institution.student.mapper;

import com.bizremark.institution.student.info.StudentInfo;
import com.bizremark.institution.student.model.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentInfoMapper {
    Student studentInfoToStudent(StudentInfo studentInfo);

    StudentInfo studentToStudentInfo(Student student);

}
