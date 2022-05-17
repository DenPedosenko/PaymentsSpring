# UserTypes inserts
INSERT INTO user_types(name)
VALUES ('User');
INSERT INTO user_types(name)
VALUES ('Admin');

#user_statuses inserts
INSERT INTO user_statuses(name)
VALUES ('Active');
INSERT INTO user_statuses(name)
VALUES ('Blocked');

#Locale_user_statuses inserts
INSERT INTO localized_user_statuses(id, locale, name)
VALUES (1, 'en', 'Active');
INSERT INTO localized_user_statuses(id, locale, name)
VALUES (1, 'ua', 'Активний');
INSERT INTO localized_user_statuses(id, locale, name)
VALUES (2, 'en', 'Blocked');
INSERT INTO localized_user_statuses(id, locale, name)
VALUES (2, 'ua', 'Заблокований');

# Users inserts
INSERT INTO users(first_name, last_name, email, user_password, user_type_id, user_status_id)
VALUES ('Gendalf', 'Gray', 'the_gratest_mag@example.com', '$2a$10$09KLGzlOPfsMgkRE//DD7ezB/QT814i6cs/yc41QiI4VfmFSdaipi', 2, 1);
INSERT INTO users(first_name, last_name, email, user_password, user_type_id, user_status_id)
VALUES ('Aragorn', 'Elessar', 'elessar@example.com', '$2a$10$9BupTB3F1RXcMFRc8ALwW.HxRM2TakJYnEdI16Kx0tbA66FMOiMDS', 2, 1);
INSERT INTO users(first_name, last_name, email, user_password, user_type_id, user_status_id)
VALUES ('Frodo', 'Bagins', 'mister_frodo@example.com', '$2a$10$9BupTB3F1RXcMFRc8ALwW.HxRM2TakJYnEdI16Kx0tbA66FMOiMDS', 1, 1);
INSERT INTO users(first_name, last_name, email, user_password, user_type_id, user_status_id)
VALUES ('Sauron', 'Maia', 'the_dark_lord@example.com', '$2a$10$9BupTB3F1RXcMFRc8ALwW.HxRM2TakJYnEdI16Kx0tbA66FMOiMDS', 1, 2);

#Account_status inserts
INSERT INTO account_statuses(name)
VALUES ('Active');
INSERT INTO Account_statuses(name)
VALUES ('Blocked');

#Locale_Account_status inserts
INSERT INTO localized_account_statuses(id, locale, name)
VALUES (1, 'en', 'Active');
INSERT INTO localized_account_statuses(id, locale, name)
VALUES (1, 'ua', 'Активний');
INSERT INTO localized_account_statuses(id, locale, name)
VALUES (2, 'en', 'Blocked');
INSERT INTO localized_account_statuses(id, locale, name)
VALUES (2, 'ua', 'Заблокований');

#Account inserts
INSERT INTO accounts(name, user_id, account_status_id, balance)
VALUES ('Payment',  3, 1, 100);
INSERT INTO accounts(name, user_id, account_status_id, balance)
VALUES ('Bonus',  3, 1, 100);
INSERT INTO accounts(name, user_id, account_status_id, balance)
VALUES ('Payment', 4, 2, 100);
INSERT INTO accounts(name, user_id, account_status_id, balance)
VALUES ('Bonus', 4, 2, 100);

#Locale_Accounts inserts
INSERT INTO localized_accounts(id, locale, name)
VALUES (1, 'en', 'Payment');
INSERT INTO localized_accounts(id, locale, name)
VALUES (1, 'ua', 'Платіжний');
INSERT INTO localized_accounts(id, locale, name)
VALUES (2, 'en', 'Bonus');
INSERT INTO localized_accounts(id, locale, name)
VALUES (2, 'ua', 'Бонусний');
INSERT INTO localized_accounts(id, locale, name)
VALUES (3, 'en', 'Payment');
INSERT INTO localized_accounts(id, locale, name)
VALUES (3, 'ua', 'Платіжний');
INSERT INTO localized_accounts(id, locale, name)
VALUES (4, 'en', 'Bonus');
INSERT INTO localized_accounts(id, locale, name)
VALUES (4, 'ua', 'Бонусний');

#Card inserts
INSERT INTO cards(card_number, exp_date, cvv, account_id)
VALUES ('1111111111111111', '12/12', '205', 1);
INSERT INTO cards(card_number, exp_date, cvv, account_id)
VALUES ('1111111111111111', '12/12', '205', 2);
INSERT INTO cards(card_number, exp_date, cvv, account_id)
VALUES ('1111111111111111', '12/12', '205', 3);
INSERT INTO cards(card_number, exp_date, cvv, account_id)
VALUES ('1111111111111111', '12/12', '205', 4);

# PaymentTypes inserts
INSERT INTO payment_types(name)
VALUES ('Public utilities');
INSERT INTO payment_types(name)
VALUES ('Mobile communications');
INSERT INTO payment_types(name)
VALUES ('Help for Ukrainians');
INSERT INTO payment_types(name)
VALUES ('Internet');
INSERT INTO payment_types(name)
VALUES ('Television');
INSERT INTO payment_types(name)
VALUES ('Distribution');
INSERT INTO payment_types(name)
VALUES ('Security service');
INSERT INTO payment_types(name)
VALUES ('Kindergartens');
INSERT INTO payment_types(name)
VALUES ('Transport');
INSERT INTO payment_types(name)
VALUES ('Games');

# LocalizedPaymentTypes inserts
INSERT INTO localized_payment_types(id, locale, name)
VALUES (1, 'en','Public utilities');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (1, 'ua', 'Комунальні послуги');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (2, 'en', 'Mobile communications');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (2, 'ua', 'Поповнення мобільного');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (3, 'en', 'Help for Ukrainians');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (3, 'ua', 'Допомога українцям');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (4, 'en', 'Internet');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (4, 'ua', 'Інтернет');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (5, 'en', 'Television');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (5, 'ua', 'Телебачення');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (6, 'en', 'Distribution');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (6, 'ua', 'Дистрибуція');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (7, 'en', 'Security service');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (7, 'ua', 'Охорона');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (8, 'en', 'Kindergartens');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (8, 'ua', 'Дитячі садки');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (9, 'en', 'Transport');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (9, 'ua', 'Транспорт');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (10, 'en', 'Games');
INSERT INTO localized_payment_types(id, locale, name)
VALUES (10, 'ua', 'Ігри');


# Payment_status inserts
INSERT INTO payment_statuses(name)
VALUES ('Prepeared');
INSERT INTO payment_statuses(name)
VALUES ('Sent');

# Payment_status_localized inserts
INSERT INTO localized_payment_statuses(id, locale, name)
VALUES (1, 'ua', 'Підготовлений');
INSERT INTO localized_payment_statuses(id, locale, name)
VALUES (2 , 'ua', 'Відправлений');
INSERT INTO localized_payment_statuses(id, locale, name)
VALUES (1, 'en', 'Prepeared');
INSERT INTO localized_payment_statuses(id, locale, name)
VALUES (2 , 'en', 'Sent');

# Payment inserts
INSERT INTO payments(user_id, account_id, creating_date,  payment_status_id, payment_type_id, amount)
VALUES (3, 1,  '2020-10-9', 1, 1, 250.00);
INSERT INTO payments(user_id, account_id, creating_date,  payment_status_id, payment_type_id, amount)
VALUES (3, 1,  '2020-10-9 12:00:00', 1, 1, 250.00);
INSERT INTO payments(user_id, account_id, creating_date,  payment_status_id, payment_type_id, amount)
VALUES (3, 2,  '2020-10-15', 2, 1, 100.00);

# Request_status inserts
INSERT INTO request_statuses(name)
VALUES ('Active');
INSERT INTO request_statuses(name)
VALUES ('Closed');

# Request_status inserts
INSERT INTO localized_request_statuses(id, locale, name)
VALUES (1, 'en', 'Active');
INSERT INTO localized_request_statuses(id, locale, name)
VALUES (1, 'ua', 'Активний');
INSERT INTO localized_request_statuses(id, locale, name)
VALUES (2, 'en', 'Closed');
INSERT INTO localized_request_statuses(id, locale, name)
VALUES (2, 'ua', 'Закритий');

# Requests inserts
INSERT INTO requests(user_id, account_id, creating_date,  request_status_id)
VALUES (4, 4,  '2020-10-9', 1);