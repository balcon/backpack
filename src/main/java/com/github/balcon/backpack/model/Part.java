package com.github.balcon.backpack.model;

import lombok.*;

//@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Part extends BaseEntity {
    private String name;
    private int weight;
}
