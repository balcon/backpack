package com.github.balcon.backpack.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@ToString(callSuper = true)
public class Equipment extends BaseEntity<Integer> {
    private String name;
    private String manufacturer;
    private int weight;

    @ManyToOne
    private Type type;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "property", joinColumns = @JoinColumn(name = "equipnemt_id"))
    @MapKeyColumn(name = "name")
    @Column(name = "property_value")
    private Map<String, String> properties = new HashMap<>();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private List<Part> parts = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "equipment")
    private List<Backpack> backpacks = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Person owner;
}
