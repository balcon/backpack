package com.github.balcon.backpack.web.rest;

import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.model.Equipment;
import com.github.balcon.backpack.model.Part;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TestData {
    public Equipment tent = Equipment.builder()
            .name("Tent")
            .manufacturer("MSR")
            .parts(List.of(
                    new Part("Outer tent", 1000),
                    new Part("Footprint", 400)))
            .weight(2500).build();

    public Equipment sleepingBag = Equipment.builder()
            .name("Arctic")
            .manufacturer("Red Fox")
            .weight(1500).build();

    public Equipment sleepingPad = Equipment.builder()
            .name("Prolite Regular")
            .manufacturer("Therm-a-Rest")
            .weight(500).build();

    public Backpack backpack1 = Backpack.builder()
            .name("Backpack 1")
            .equipment(List.of(sleepingBag, sleepingPad)).build();

    public Backpack backpack2 = Backpack.builder()
            .name("Backpack 2")
            .equipment(List.of(tent, sleepingBag, sleepingPad)).build();
}
