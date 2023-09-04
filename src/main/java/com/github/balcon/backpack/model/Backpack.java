package com.github.balcon.backpack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Backpack extends BaseEntity<Integer> {
    private String name;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Equipment> equipment;
}