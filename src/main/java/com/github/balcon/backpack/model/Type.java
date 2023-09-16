package com.github.balcon.backpack.model;

import jakarta.persistence.ManyToOne;
import lombok.*;

//@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Type extends BaseEntity {
    private String name;
    @ManyToOne
    private Type baseType;
}
