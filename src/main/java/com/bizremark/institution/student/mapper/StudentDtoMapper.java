package com.bizremark.institution.student.mapper;

import com.bizremark.institution.student.dto.StudentDto;
import com.bizremark.institution.student.info.StudentInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface StudentDtoMapper {
    StudentDto studentInfoToStudentDto(StudentInfo studentInfo);

    StudentInfo studentDtoToStudentInfo(StudentDto studentDto);
}
