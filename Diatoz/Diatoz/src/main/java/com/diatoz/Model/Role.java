package com.diatoz.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Role {

    @Id
    private String roleId;

    @Field("role")
    private MyRole role;



    public Role(MyRole reader) {
        this.role = reader;
    }

}
