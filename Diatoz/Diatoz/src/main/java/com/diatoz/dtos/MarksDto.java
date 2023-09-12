package com.diatoz.dtos;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MarksDto {


    private  String semester;
    private Integer subjectOneMarks;
    private Integer subjectTwoMarks;
    private Integer subjectThreeMarks;
    private Integer subjectFourMarks;
    private Integer subjectFiveMarks;

}
