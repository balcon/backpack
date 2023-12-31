package com.github.balcon.backpack.web.rest;

import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.model.Equipment;
import com.github.balcon.backpack.model.Role;
import com.github.balcon.backpack.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

@UtilityClass
public class TestData {
    public static final int USER_TENT_ID = 1;
    public static final int USER_SLEEPING_BAG_ID = 2;
    public static final int USER_SLEEPING_PAD_ID = 3;
    public static final int ADMIN_TENT_ID = 4;
    public static final int ADMIN_SLEEPING_BAG_ID = 5;
    public static final int DUMMY_ID = 0;

    public static final int USER_BACKPACK_1_ID = 50;
    public static final int USER_BACKPACK_2_ID = 51;
    public static final int ADMIN_BACKPACK_1_ID = 52;

    public static final int ADMIN_ID = 100;
    public static final int USER_ID = 101;

    public static final String ADMIN_EMAIL = "admin@mail.ru";
    public User admin = User.builder()
            .id(ADMIN_ID)
            .email(ADMIN_EMAIL)
            .name("Admin")
            .role(Role.ADMIN).build();

    public static final String USER_EMAIL = "user@mail.ru";
    public User user = User.builder()
            .id(USER_ID)
            .email(USER_EMAIL)
            .name("User")
            .role(Role.USER).build();

    public Equipment userTent = Equipment.builder()
            .id(USER_TENT_ID)
            .name("Huba Tour 2")
            .manufacturer("MSR")
            .weight(2500)
            .properties(Map.of(
                    "Color", "Grey",
                    "Capacity", "2"))
            .owner(user).build();

    public Equipment userSleepingBag = Equipment.builder()
            .id(USER_SLEEPING_BAG_ID)
            .name("Explorer -20")
            .manufacturer("Red Fox")
            .weight(1500)
            .owner(user).build();

    public Equipment userSleepingPad = Equipment.builder()
            .id(USER_SLEEPING_PAD_ID)
            .name("Prolite Regular")
            .manufacturer("Therm-a-Rest")
            .weight(500)
            .properties(Map.of("Color", "Red"))
            .owner(user).build();

    public Equipment adminTent = Equipment.builder()
            .id(ADMIN_TENT_ID)
            .name("Explorer V2")
            .manufacturer("Red Fox")
            .weight(2500)
            .properties(Map.of("Capacity", "2"))
            .owner(admin).build();

    public Equipment adminSleepingBag = Equipment.builder()
            .id(ADMIN_SLEEPING_BAG_ID)
            .name("Green Kazoo")
            .manufacturer("North Face")
            .weight(1500)
            .properties(Map.of(
                    "Extreme temp", "-39",
                    "Comfort temp", "-17"))
            .owner(admin).build();

    public Backpack userBackpack1 = Backpack.builder()
            .id(USER_BACKPACK_1_ID)
            .name("Backpack 1")
            .equipment(List.of(userSleepingBag, userSleepingPad))
            .owner(user).build();

    public Backpack userBackpack2 = Backpack.builder()
            .id(USER_BACKPACK_2_ID)
            .name("Backpack 2")
            .equipment(List.of(userTent, userSleepingBag, userSleepingPad))
            .owner(user).build();

    public Backpack adminBackpack = Backpack.builder()
            .id(ADMIN_BACKPACK_1_ID)
            .name("Backpack 1")
            .equipment(List.of(adminTent, adminSleepingBag))
            .owner(admin).build();
}
