INSERT INTO person (id, email, name, role)
VALUES (100, 'admin@mail.ru', 'Admin', 'ADMIN'),
       (101, 'user@mail.ru', 'User', 'USER');

INSERT INTO equipment (id, name, manufacturer, weight, owner_id)
VALUES (1, 'Huba Tour 2', 'MSR', 2500, 101),
       (2, 'Explorer -20', 'Red Fox', 1500, 101),
       (3, 'Prolite Regular', 'Therm-a-Rest', 500, 101),
       (4, 'Explorer V2', 'Red Fox', 2500, 101),
       (5, 'Green Kazoo', 'North Face', 1500, 101);

INSERT INTO backpack (id, name, owner_id)
VALUES (50, 'Backpack 1', 101),
       (51, 'Backpack 2', 101),
       (52, 'Backpack 3', 100);

INSERT INTO backpack_equipment (backpacks_id, equipment_id)
VALUES (50, 2),
       (50, 3),
       (51, 1),
       (51, 2),
       (51, 3),
       (52, 4),
       (52, 5);
