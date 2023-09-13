package com.github.balcon.backpack.web.rest;

import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.model.Equipment;
import com.github.balcon.backpack.model.User;
import com.github.balcon.backpack.model.Role;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TestData {
    public static final int USER_TENT_ID = 1;
    public static final int USER_SLEEPING_BAG_ID = 2;
    public static final int USER_SLEEPING_PAD_ID = 3;
    public static final int ADMIN_TENT_ID = 4;
    public static final int ADMIN_SLEEPING_BAG_ID = 5;

    public static final int USER_BACKPACK_1_ID = 50;
    public static final int USER_BACKPACK_2_ID = 51;
    public static final int ADMIN_BACKPACK_1_ID = 52;

    public static final int ADMIN_ID = 100;
    public static final int USER_ID = 101;

    public User admin = User.builder()
            .id(ADMIN_ID)
            .email("admin@mail.ru")
            .name("Admin")
            .role(Role.ADMIN).build();

    public User user = User.builder()
            .id(USER_ID)
            .email("user@mail.ru")
            .name("User")
            .role(Role.USER).build();

    public Equipment userTent = Equipment.builder()
            .id(USER_TENT_ID)
            .name("Huba Tour 2")
            .manufacturer("MSR")
            .weight(2500)
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
            .owner(user).build();

    public Equipment adminTent = Equipment.builder()
            .id(ADMIN_TENT_ID)
            .name("Explorer V2")
            .manufacturer("Red Fox")
            .weight(2500)
            .owner(admin).build();

    public Equipment adminSleepingBag = Equipment.builder()
            .id(ADMIN_SLEEPING_BAG_ID)
            .name("Green Kazoo")
            .manufacturer("North Face")
            .weight(1500)
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
