package com.github.balcon.backpack.web.rest;

import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.model.Equipment;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TestData {
    private static final int TENT_ID = 1;
    private static final int SLEEPING_BAG_ID = 2;
    private static final int SLEEPING_PAD_ID = 3;

    private static final int BACKPACK_1_ID = 50;
    private static final int BACKPACK_2_ID = 51;


    public Equipment tent = Equipment.builder()
            .id(TENT_ID)
            .name("Tent")
            .manufacturer("MSR")
            .weight(2500).build();

    public Equipment sleepingBag = Equipment.builder()
            .id(SLEEPING_BAG_ID)
            .name("Arctic")
            .manufacturer("Red Fox")
            .weight(1500).build();

    public Equipment sleepingPad = Equipment.builder()
            .id(SLEEPING_PAD_ID)
            .name("Prolite Regular")
            .manufacturer("Therm-a-Rest")
            .weight(500).build();

    public Backpack backpack1 = Backpack.builder()
            .id(BACKPACK_1_ID)
            .name("Backpack 1")
            .equipment(List.of(sleepingBag, sleepingPad)).build();

    public Backpack backpack2 = Backpack.builder()
            .id(BACKPACK_2_ID)
            .name("Backpack 2")
            .equipment(List.of(tent, sleepingBag, sleepingPad)).build();
}
