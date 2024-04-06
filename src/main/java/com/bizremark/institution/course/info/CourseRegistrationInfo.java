package com.bizremark.institution.course.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistrationInfo {
    private Long id;
    private Long studentId;
    private Long courseId;
}
