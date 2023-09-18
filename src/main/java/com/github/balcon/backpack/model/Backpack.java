package com.github.balcon.backpack.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@ToString(callSuper = true)
public class Backpack extends BaseEntity {
    @Setter
    private String name;

    @Builder.Default
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "backpack_equipment",
            joinColumns = @JoinColumn(name = "backpack_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Equipment> equipment = new ArrayList<>();

    @Setter
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
}
