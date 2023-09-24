package com.github.balcon.backpack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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

    public Equipment addBackpack(Backpack backpack) {
        backpacks.add(backpack);
        return this;
    }

    public Equipment removeBackpack(Backpack backpack) {
        backpacks.remove(backpack);
        return this;
    }
}
