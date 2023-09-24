package com.github.balcon.backpack.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
public class Backpack extends BaseEntity {
    @Setter
    private String name;

    @Builder.Default
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "backpack_equipment",
            joinColumns = @JoinColumn(name = "backpack_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    private List<Equipment> equipment = new ArrayList<>();

    @Setter
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    public Backpack addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
        return this;
    }

    public Backpack removeEquipment(Equipment equipment) {
        this.equipment.remove(equipment);
        return this;
    }
}
