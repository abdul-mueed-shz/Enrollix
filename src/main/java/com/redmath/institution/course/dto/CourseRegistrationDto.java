package com.redmath.institution.course.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegistrationDto {
    private Long id;
    private Long studentId;
    private Long courseId;
}
