package com.github.balcon.backpack.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;

public abstract class BaseEntity<T extends Serializable> extends AbstractPersistable<T> {
}
