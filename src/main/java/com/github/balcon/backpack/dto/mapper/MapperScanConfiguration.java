package com.github.balcon.backpack.dto.mapper;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(includeFilters =
@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Mapper.class))
public class MapperScanConfiguration {
}
