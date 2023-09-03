package com.github.balcon.backpack.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Equipment extends BaseEntity<Integer> {
    private String name;
    private String manufacturer;
    private int weight;

    @ManyToOne
    private Type type;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private List<Property> properties;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private List<Part> parts;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "equipment")
    private List<Backpack> backpacks;
}
