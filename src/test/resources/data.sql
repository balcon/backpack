INSERT INTO equipment (id, name, manufacturer, weight)
VALUES (1, 'Tent', 'MSR', 2500),
       (2, 'Arctic', 'Red Fox', 1500),
       (3, 'Prolite Regular', 'Therm-a-Rest', 500);

INSERT INTO backpack (id, name)
VALUES (50, 'Backpack 1'),
       (51, 'Backpack 2');

INSERT INTO backpack_equipment (backpacks_id, equipment_id)
VALUES (50, 2),
       (50, 3),
       (51, 1),
       (51, 2),
       (51, 3);
