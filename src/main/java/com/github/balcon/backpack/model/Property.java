package com.github.balcon.backpack.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Property extends BaseEntity<Integer> {
    private String name;
    private String propertyValue;
}
