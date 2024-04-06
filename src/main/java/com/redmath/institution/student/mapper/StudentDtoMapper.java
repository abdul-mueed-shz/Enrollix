package com.redmath.institution.student.mapper;

import com.redmath.institution.student.dto.StudentDto;
import com.redmath.institution.student.info.StudentInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface StudentDtoMapper {
    StudentDto studentInfoToStudentDto(StudentInfo studentInfo);

    StudentInfo studentDtoToStudentInfo(StudentDto studentDto);
}
