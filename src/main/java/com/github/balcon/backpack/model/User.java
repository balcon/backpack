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
@Table(name = "users")
public class User extends BaseEntity<Integer> {
    private String email;

    @Setter
    private String name;

    @Builder.Default
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Equipment> equipment = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Backpack> backpacks = new ArrayList<>();
}
