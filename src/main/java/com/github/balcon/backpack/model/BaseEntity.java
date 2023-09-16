package com.github.balcon.backpack.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@ToString
public abstract class BaseEntity implements Persistable<Integer> {
    public static final int START_SEQ = 1000;

    @Id
    @SequenceGenerator(name = "main_seq", sequenceName = "main_seq", initialValue = START_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "main_seq")
    private Integer id;

    @Transient
    @Override
    public boolean isNew() {
        return id == null;
    }
}
