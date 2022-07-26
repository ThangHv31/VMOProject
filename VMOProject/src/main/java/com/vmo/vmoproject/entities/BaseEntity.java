package com.vmo.vmoproject.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Id
    private String id;
    @Field(name = "created_date")
    private Instant createdDate = Instant.now();
    @Field(name = "updated_date")
    private Instant updatedDate = Instant.now();

    public BaseEntity(String id) {
        this.id = id;
    }
}
