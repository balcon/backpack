package com.github.balcon.backpack.dto.mapper;

public interface Mapper<F, T> {
    T map(F entity);
}
