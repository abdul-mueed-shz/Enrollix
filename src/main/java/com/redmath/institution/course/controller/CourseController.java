package com.redmath.institution.course.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redmath.institution.common.constants.EndpointConstants;
import com.redmath.institution.course.info.CourseInfo;
import com.redmath.institution.course.mapper.CourseDtoMapper;
import com.redmath.institution.course.service.CourseService;
import com.redmath.institution.course.dto.CourseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = EndpointConstants.API_VER + '/' + EndpointConstants.COURSE)
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseDtoMapper courseDtoMapper;


    /**
     * Get All Courses.
     *
     * @return Courses
     */

    @GetMapping
    public ResponseEntity<List<CourseInfo>> getCourses() {
        List<CourseInfo> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping(path = EndpointConstants.COURSE_ID_PATH_QUERY)
    public ResponseEntity<CourseInfo> getCourse(@PathVariable(EndpointConstants.COURSE_ID) Long courseId) {
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseInfo registerNewCourse(@Valid @RequestBody CourseDto courseDto) throws JsonProcessingException {
        CourseInfo courseInfo = courseDtoMapper.courseDtoToCourseInfo(courseDto);
        return courseService.addNewCourse(courseInfo);
    }


    @DeleteMapping(path = EndpointConstants.COURSE_ID_PATH_QUERY)
    public ResponseEntity<Void> deleteCourse(@PathVariable(EndpointConstants.COURSE_ID) Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(path = EndpointConstants.COURSE_ID_PATH_QUERY)
    public ResponseEntity<CourseInfo> updateCourse(
            @PathVariable(EndpointConstants.COURSE_ID) Long courseId,
            @RequestBody CourseDto courseDto
    ) {
        CourseInfo courseInfo = courseDtoMapper.courseDtoToCourseInfo(courseDto);
        CourseInfo updatedCourseInfo = courseService.updateCourse(courseId, courseInfo);
        return ResponseEntity.ok(updatedCourseInfo);
    }
}
