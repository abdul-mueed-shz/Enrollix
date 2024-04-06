package com.redmath.institution.course.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redmath.institution.common.constants.EndpointConstants;
import com.redmath.institution.course.dto.CourseRegistrationDto;
import com.redmath.institution.course.info.CourseInfo;
import com.redmath.institution.course.info.CourseRegistrationInfo;
import com.redmath.institution.course.mapper.CourseRegistrationDtoMapper;
import com.redmath.institution.course.service.CourseRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = EndpointConstants.API_VER + '/' + EndpointConstants.STUDENT)
@RequiredArgsConstructor
public class CourseRegistrationController {

    private final CourseRegistrationService courseRegistrationService;
    private final CourseRegistrationDtoMapper courseRegistrationDtoMapper;

    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewCourse(@Valid @RequestBody CourseRegistrationDto courseRegistrationDto) throws JsonProcessingException {
        CourseRegistrationInfo courseInfo = courseRegistrationDtoMapper.courseRegistrationDtoToCourseRegistrationInfo(courseRegistrationDto);
        courseRegistrationService.registerCourseToStudent(courseInfo);
    }

    @GetMapping("{studentId}/registration")
    public ResponseEntity<List<CourseInfo>> getRegisteredCourses(@Valid @PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok(courseRegistrationService.getRegisteredCourses(studentId));
    }


    @DeleteMapping("{studentId}/registration/course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public void unRegisterStudentFromCourse(@Valid @PathVariable("studentId") Long studentId, @Valid @PathVariable("courseId") Long courseId) {
        courseRegistrationService.unRegisterStudentFromCourse(studentId, courseId);
    }
}
