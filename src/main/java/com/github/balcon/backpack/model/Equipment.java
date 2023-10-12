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
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@ToString(callSuper = true)
public class Equipment extends BaseEntity {
    private String name;
    private String manufacturer;
    private int weight;

    @Builder.Default
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "equipment")
    private List<Backpack> backpacks = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "property", joinColumns = @JoinColumn(name = "equipment_id"))
    @MapKeyColumn(name = "name")
    @Column(name = "property_value")
    private Map<String, String> properties = new HashMap<>();

    public Equipment addBackpack(Backpack backpack) {
        backpacks.add(backpack);
        return this;
    }

    public Equipment removeBackpack(Backpack backpack) {
        backpacks.remove(backpack);
        return this;
    }
}
