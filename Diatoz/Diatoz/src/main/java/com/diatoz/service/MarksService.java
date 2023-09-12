package com.diatoz.service;

import com.diatoz.Model.Marks;
import com.diatoz.dtos.MarksDto;
import com.diatoz.dtos.StudentResponse;

public interface MarksService {


    public String addMarksToStudent(String id, MarksDto marksDto);

    public StudentResponse getStudentMarks(String id);



}
