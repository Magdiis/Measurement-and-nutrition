INSERT INTO public."user" (id,height,username,physical_activity,age,sex) VALUES ('a986e9fa-4cc7-479a-aee8-f700c035327b',180,'Anna', 'easy',40,'woman');
INSERT INTO public."user" (id,height,username,physical_activity,age,sex) VALUES ('a766e9fa-4cc7-479a-abb8-f712c035327b',167,'Misa', 'normal',25,'woman');

-- Misa
INSERT INTO public.measurement (id, date, weight, user_id) VALUES (31,'2024-01-01',56.0,'a766e9fa-4cc7-479a-abb8-f712c035327b');
-- Anna
INSERT INTO public.measurement (id, date, weight, user_id)
VALUES
    (1, '2021-12-01', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (2, '2021-12-02', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (3, '2021-12-03', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (4, '2021-12-04', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (5, '2021-12-05', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (6, '2021-12-06', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (7, '2021-12-07', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (8, '2021-12-08', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (9, '2021-12-09', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (10, '2021-12-10', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (11, '2021-12-11', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (12, '2021-12-12', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (13, '2021-12-13', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (14, '2021-12-14', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (15, '2021-12-15', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (16, '2021-12-16', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (17, '2021-12-17', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (18, '2021-12-18', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (19, '2021-12-19', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (20, '2021-12-20', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (21, '2021-12-21', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (22, '2021-12-22', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (23, '2021-12-23', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (24, '2021-12-24', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (25, '2021-12-25', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (26, '2021-12-26', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (27, '2021-12-27', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (28, '2021-12-28', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (29, '2021-12-29', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b'),
    (30, '2021-12-30', 90.0 + (random() * 5.0), 'a986e9fa-4cc7-479a-aee8-f700c035327b');


-- a few products
INSERT INTO public.product (id,name, calories,fats,proteins,carbohydrates)
VALUES
    (1,'pecans', 200, 0.21,0.30,0.42),
    (2,'eggs', 520, 0.3,0.45,0.25),
    (3,'bread', 320, 0.48,0.27,0.65),
    (4,'carrot', 90, 0.09,0.1,0.08),
    (5,'cheese', 480, 0.78,0.66,0.44),
    (6,'salami', 370, 0.66,0.5,0.39);

-- pecans and eggs to Misa
INSERT INTO public.food (id, date, quantity, product_id, user_id)
VALUES
    (1, '2024-01-01', 100.0, 1, 'a766e9fa-4cc7-479a-abb8-f712c035327b'),
    (2,'2024-01-01', 100.0, 2,'a766e9fa-4cc7-479a-abb8-f712c035327b');