package com.diatoz.dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MarksResponse {


    private String id;
    private  String semester;
    private Integer subjectOneMarks;
    private Integer subjectTwoMarks;
    private Integer subjectThreeMarks;
    private Integer subjectFourMarks;
    private Integer subjectFiveMarks;

}
