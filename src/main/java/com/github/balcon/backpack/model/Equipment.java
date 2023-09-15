package com.github.balcon.backpack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class Equipment extends BaseEntity<Integer> {
    private String name;
    private String manufacturer;
    private int weight;

//    @ManyToOne
//    private Type type;
//
//    @Builder.Default
//    @ElementCollection
//    @CollectionTable(name = "property", joinColumns = @JoinColumn(name = "equipnemt_id"))
//    @MapKeyColumn(name = "name")
//    @Column(name = "property_value")
//    private Map<String, String> properties = new HashMap<>();
//
//    @Builder.Default
//    @ToString.Exclude
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "equipment_id")
//    private List<Part> parts = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "equipment")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Backpack> backpacks = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
}
