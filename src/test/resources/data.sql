INSERT INTO users (id, email, name, role, password)
VALUES (100, 'admin@mail.ru', 'Admin', 'ADMIN', '{noop}pass'),
       (101, 'user@mail.ru', 'User', 'USER', '{noop}pass');

INSERT INTO equipment (id, name, manufacturer, weight, owner_id)
VALUES (1, 'Huba Tour 2', 'MSR', 2500, 101),
       (2, 'Explorer -20', 'Red Fox', 1500, 101),
       (3, 'Prolite Regular', 'Therm-a-Rest', 500, 101),
       (4, 'Explorer V2', 'Red Fox', 2500, 100),
       (5, 'Green Kazoo', 'North Face', 1500, 100);

INSERT INTO property (name, property_value, equipment_id)
VALUES ('Color', 'Grey', 1),
       ('Capacity', '2', 1),
       ('Color', 'Red', 3),
       ('Capacity', '2', 4),
       ('Extreme temp', '-39', 5),
       ('Comfort temp', '-17', 5);

INSERT INTO backpack (id, name, owner_id)
VALUES (50, 'Backpack 1', 101),
       (51, 'Backpack 2', 101),
       (52, 'Backpack 3', 100);

INSERT INTO backpack_equipment (backpack_id, equipment_id)
VALUES (50, 2),
       (50, 3),
       (51, 1),
       (51, 2),
       (51, 3),
       (52, 4),
       (52, 5);
