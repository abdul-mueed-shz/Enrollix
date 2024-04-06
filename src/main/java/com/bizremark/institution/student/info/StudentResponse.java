package com.bizremark.institution.student.info;

import com.bizremark.institution.course.info.CourseInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long id;

    private String name;
    private String rollNumber;
    private Timestamp dob;
    List<CourseInfo> courseInfos = new ArrayList<>();

    public void appendCourseInfo(CourseInfo courseInfo) {
        courseInfos.add(courseInfo);
    }

}
