INSERT INTO public.m_roles (role_name)
VALUES ('ROLE_USER');
INSERT INTO public.m_roles (role_name)
VALUES ('ROLE_MANAGER');
INSERT INTO public.m_roles (role_name)
VALUES ('ROLE_ADMIN');


INSERT INTO public.m_customers (name, email, password, registration_date, email_verified, is_active)
VALUES ('Anton', 'Jey93@mail.ru', '$2a$10$2WhyFIx5OMjEDYSBaYGX8..LOzzjb1MSJ36erEo5Wu2.hYjJikcdq',
        '2020-12-22 22:20:03.407000', true, true);
INSERT INTO public.m_customers (name, email, password, registration_date, email_verified, is_active)
VALUES ('Vasya', 'mail@mail.com', '$2a$10$JG39PG3NT1ip26TVdUzmce/SABkZ3tzg0ZdmWPrO/sTOLwqmO0IJq',
        '2020-12-22 22:20:03.407000', true, true);
INSERT INTO public.m_customers (name, email, password, registration_date, email_verified, is_active)
VALUES ('Petya', 'mail1@mail.com', '$2a$10$JG39PG3NT1ip26TVdUzmce/SABkZ3tzg0ZdmWPrO/sTOLwqmO0IJq',
        '2020-12-22 22:20:03.407000', true, true);


INSERT INTO public.m_customers_contacts (phone, zip, city, address, customer_id)
VALUES ('+375339379992', 666666, 'Russia', 'Puti',
        (SELECT id FROM m_customers WHERE m_customers.name = 'Vasya'));
INSERT INTO public.m_customers_contacts (phone, zip, city, address, customer_id)
VALUES ('+375449379992', null, null, null,
        (SELECT id FROM m_customers WHERE m_customers.name = 'Petya'));
INSERT INTO public.m_customers_contacts (phone, zip, city, address, customer_id)
VALUES ('375299379992', 246000, 'Uganda', 'do u know de way',
        (SELECT id FROM m_customers WHERE m_customers.name = 'Anton'));


INSERT INTO public.l_customers_roles (customer_id, role_id)
VALUES ((SELECT id FROM m_customers WHERE m_customers.name = 'Anton'),
        (SELECT id FROM m_roles WHERE m_roles.role_name = 'ROLE_USER'));
INSERT INTO public.l_customers_roles (customer_id, role_id)
VALUES ((SELECT id FROM m_customers WHERE m_customers.name = 'Anton'),
        (SELECT id FROM m_roles WHERE m_roles.role_name = 'ROLE_MANAGER'));
INSERT INTO public.l_customers_roles (customer_id, role_id)
VALUES ((SELECT id FROM m_customers WHERE m_customers.name = 'Anton'),
        (SELECT id FROM m_roles WHERE m_roles.role_name = 'ROLE_ADMIN'));
INSERT INTO public.l_customers_roles (customer_id, role_id)
VALUES ((SELECT id FROM m_customers WHERE m_customers.name = 'Vasya'),
        (SELECT id FROM m_roles WHERE m_roles.role_name = 'ROLE_USER'));
INSERT INTO public.l_customers_roles (customer_id, role_id)
VALUES ((SELECT id FROM m_customers WHERE m_customers.name = 'Vasya'),
        (SELECT id FROM m_roles WHERE m_roles.role_name = 'ROLE_MANAGER'));
INSERT INTO public.l_customers_roles (customer_id, role_id)
VALUES ((SELECT id FROM m_customers WHERE m_customers.name = 'Petya'),
        (SELECT id FROM m_roles WHERE m_roles.role_name = 'ROLE_USER'));


INSERT INTO public.m_categories (name)
VALUES ('Amino acids');
INSERT INTO public.m_categories (name)
VALUES ('Protein');
INSERT INTO public.m_categories (name)
VALUES ('Gainers');
INSERT INTO public.m_categories (name)
VALUES ('Isotonic');
INSERT INTO public.m_categories (name)
VALUES ('Creatine');


INSERT INTO public.m_subcategories (name, category_id)
VALUES ('Beta Alanine', (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'));
INSERT INTO public.m_subcategories (name, category_id)
VALUES ('Isolate', (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'));
INSERT INTO public.m_subcategories (name, category_id)
VALUES ('L-Arginine', (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'));
INSERT INTO public.m_subcategories (name, category_id)
VALUES ('Liquid Amino Acids',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'));
INSERT INTO public.m_subcategories (name, category_id)
VALUES ('Creatine monohydrate', (SELECT id FROM m_categories WHERE m_categories.name = 'Creatine'));
INSERT INTO public.m_subcategories (name, category_id)
VALUES ('Carbohydrate drinks', (SELECT id FROM m_categories WHERE m_categories.name = 'Isotonic'));
INSERT INTO public.m_subcategories (name, category_id)
VALUES ('L-Glutamine', (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'));
INSERT INTO public.m_subcategories (name, category_id)
VALUES ('Complex', (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'));
INSERT INTO public.m_subcategories (name, category_id)
VALUES ('Whey', (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'));
INSERT INTO public.m_subcategories (name, category_id)
VALUES ('Isotonic drinks', (SELECT id FROM m_categories WHERE m_categories.name = 'Isotonic'));
INSERT INTO public.m_subcategories (name, category_id)
VALUES ('Casein', (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'));
INSERT INTO public.m_subcategories (name, category_id)
VALUES ('Creatine capsules', (SELECT id FROM m_categories WHERE m_categories.name = 'Creatine'));


INSERT INTO public.m_manufacturers (name)
VALUES ('Scitec Nutrition');
INSERT INTO public.m_manufacturers (name)
VALUES ('Trec Nutrition');
INSERT INTO public.m_manufacturers (name)
VALUES ('BioTech');
INSERT INTO public.m_manufacturers (name)
VALUES ('Weider');
INSERT INTO public.m_manufacturers (name)
VALUES ('Do4a Lab');



INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('100% L-GLUTAMINE (240g)', 44.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'BioTech'), '6020313',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'L-Glutamine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('GLUTAMINE HIGH SPEED (250g)', 35.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '7614331',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'L-Glutamine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('GLUTAMINE HIGH SPEED (500g)', 77.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '6110428',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'L-Glutamine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Mega Glutamine 1400 (90 caps)', 46.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '8070524',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'L-Glutamine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Amino Liquid (1000 ml)', 44.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'BioTech'), '4957557',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Liquid Amino Acids'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Liquid BCAA (1000 ml)', 49.30,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'BioTech'), '2239758',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Liquid Amino Acids'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Amino Liquid 50 (1000 ml)', 70.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '9415407',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Liquid Amino Acids'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Amino Liquid 50 (1000 ml)', 58.20,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '6893584',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Liquid Amino Acids'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('100% WHEY (1500g)', 93.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '4967748',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Whey'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('100% WHEY (1500g)', 62.30,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '3521436',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Whey'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Gold Whey (500g)', 54.99,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Weider'), '4484872',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Whey'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Protein 80 Plus (500g)', 49.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Weider'), '6076193',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Whey'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('100% MILK COMPLEX (2350g)', 161.99,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '4439468',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Whey'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Power Pro (4000g)', 190.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'BioTech'), '5836648',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Whey'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Casein 100 (600g)', 67.90,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '3338847',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Casein'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Beta Alanine (150 caps)', 53.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/d284e873-5eb4-45d3-88cd-06157ec4dc95.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '5301521',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Beta Alanine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Casein Complex (2350g)', 210.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '8380234',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Casein'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Beta Alanine 700 (60 caps)', 17.50,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/a7fb2965-e3a1-49fd-9475-5a1867fee031.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '5230542',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Beta Alanine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Beta Alanine Mega Caps (90 caps)', 39.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'BioTech'), '5445552',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Beta Alanine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Beta Alanine (200g)', 25.50,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Do4a Lab'), '1271994',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Beta Alanine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Beta Alanine 700 (120 caps)', 32.50,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/d462b587-0d55-4499-af09-4a5c455531d7.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '9525915',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Beta Alanine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('AAKG Mega Hardcore (120 caps)', 38.50,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '2786821',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'L-Arginine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Mega Arginine (15 caps)', 8.50,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '5587523',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'L-Arginine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Mega Arginine (140 caps)', 76.50,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '2825713',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'L-Arginine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('AAKG (100 caps)', 40.50,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '1323109',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'L-Arginine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('AAKG (200g)', 44.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Do4a Lab'), '2265444',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'L-Arginine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('L-Arginine (No taste) (200g)', 32.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Do4a Lab'), '8467723',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'L-Arginine'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Casein Complex (900g)', 95.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '7665533',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Casein'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Day & Night Casein (500g)', 51.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Weider'), '5159109',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Casein'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Isolate 100 (750g)', 70.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '5164437',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Isolate'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Iso Whey Zero lactose free (2270g)', 185.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'BioTech'), '6651437',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Isolate'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Nitro Pure Whey (450g)', 38.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'BioTech'), '9924891',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Isolate'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('100% Whey Isolate (4000g)', 329.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '3722597',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Isolate'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Zero Sugar/Zero Fat ISOgreat (2270g)', 268.99,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '6656746',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Isolate'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Fourstar Protein (500g)', 38.50,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '4283502',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Protein'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Complex'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('MAGNUM 8000 (5450g)', 147.60,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '3016859',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Gainers'), null);
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('HYPER MASS 5000 (2300g)', 87.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'BioTech'), '6159903',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Gainers'), null);
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Jumbo (2860g)', 150.05,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '7176937',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Gainers'), null);
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('ISOFASTER (400g)', 19.50,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '4089043',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Isotonic'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Isotonic drinks'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('MULTI HYPOTONIC DRINK (1000ml)', 52.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'BioTech'), '6967891',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Isotonic'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Isotonic drinks'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Atlethic Line IsoTec Race (1800g)', 49.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '2033767',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Isotonic'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Isotonic drinks'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Max Carb (3000g)', 45.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '4825806',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Isotonic'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Carbohydrate drinks'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('MALTODEXTRIN (2500g)', 30.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '1553139',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Isotonic'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Carbohydrate drinks'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Creatine Micronized 200 mesh (60 caps)', 9.50,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Trec Nutrition'), '2096413',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Creatine'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Creatine capsules'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Creatine pHX (90 caps)', 25.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'BioTech'), '6913664',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Creatine'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Creatine capsules'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Creatine Caps (120 caps)', 27.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '3469147',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Creatine'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Creatine capsules'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('100% Creatine Monohydrate (500g)', 35.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'BioTech'), '1561233',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Creatine'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Creatine monohydrate'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Creatine 100% Pure (1000g)', 62.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/8a84991a-aa24-416e-832f-d4fb776700b4.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '3624753',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Creatine'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Creatine monohydrate'));
INSERT INTO public.m_products (name, price, picture_url, manufacturer_id, code, category_id,
                               subcategory_id)
VALUES ('Beta Alanine (120g)', 39.00,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/2bfd3697-8981-41b8-ba87-3524c6cb7b9c.jpg',
        (SELECT id FROM m_manufacturers WHERE m_manufacturers.name = 'Scitec Nutrition'), '0242512',
        (SELECT id FROM m_categories WHERE m_categories.name = 'Amino acids'),
        (SELECT id FROM m_subcategories WHERE m_subcategories.name = 'Beta Alanine'));