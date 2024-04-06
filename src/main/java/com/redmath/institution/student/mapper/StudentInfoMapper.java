package com.redmath.institution.student.mapper;

import com.redmath.institution.student.info.StudentInfo;
import com.redmath.institution.student.model.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentInfoMapper {
    Student studentInfoToStudent(StudentInfo studentInfo);

    StudentInfo studentToStudentInfo(Student student);

}
