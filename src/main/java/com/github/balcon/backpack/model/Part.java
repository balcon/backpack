package com.github.balcon.backpack.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Part extends BaseEntity<Integer> {
    private String name;
    private int weight;
}
