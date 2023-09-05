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

    public Backpack backpack1 = new Backpack("Backpack 1",
            List.of(sleepingBag, sleepingPad));
    public Backpack backpack2 = new Backpack("Backpack 2",
            List.of(tent, sleepingBag, sleepingPad));
}
