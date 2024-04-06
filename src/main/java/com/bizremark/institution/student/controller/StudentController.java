package com.bizremark.institution.student.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bizremark.institution.common.constants.EndpointConstants;
import com.bizremark.institution.student.dto.StudentDto;
import com.bizremark.institution.student.info.StudentInfo;
import com.bizremark.institution.student.info.StudentResponse;
import com.bizremark.institution.student.mapper.StudentDtoMapper;
import com.bizremark.institution.student.model.dao.StudentDao;
import com.bizremark.institution.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

import java.util.List;

@CrossOrigin("*")

@RestController
@RequestMapping(path = EndpointConstants.API_VER + '/' + EndpointConstants.STUDENT)
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentDtoMapper studentDtoMapper;


    /**
     * Get All Students.
     *
     * @return Students
     */

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping(path = EndpointConstants.STUDENT_ID_PATH_QUERY)
    public ResponseEntity<StudentResponse> getStudent(@PathVariable(EndpointConstants.STUDENT_ID) Long studentId) {
        return ResponseEntity.ok(studentService.getStudent(studentId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentInfo registerNewStudent(@Valid @RequestBody StudentDto studentDto) throws JsonProcessingException {
        StudentInfo studentInfo = studentDtoMapper.studentDtoToStudentInfo(studentDto);
        return studentService.addNewStudent(studentInfo);
    }


    @DeleteMapping(path = EndpointConstants.STUDENT_ID_PATH_QUERY)
    public ResponseEntity<Void> deleteStudent(@PathVariable(EndpointConstants.STUDENT_ID) Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(path = EndpointConstants.STUDENT_ID_PATH_QUERY)
    public ResponseEntity<StudentInfo> updateStudent(
            @PathVariable(EndpointConstants.STUDENT_ID) Long studentId,
            @RequestBody StudentDto studentDto
    ) {
        StudentInfo studentInfo = studentDtoMapper.studentDtoToStudentInfo(studentDto);
        StudentInfo updatedStudentInfo = studentService.updateStudent(studentId, studentInfo);
        return ResponseEntity.ok(updatedStudentInfo);
    }
}
