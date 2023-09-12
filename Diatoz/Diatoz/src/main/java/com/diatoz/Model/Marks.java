package com.diatoz.Model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "marks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Marks {

    @Id
    private String id;
    private  String semester;

    private Integer subjectOneMarks;
    private Integer subjectTwoMarks;
    private Integer subjectThreeMarks;
    private Integer subjectFourMarks;
    private Integer subjectFiveMarks;

    @DBRef
    private UserModel student;





}
