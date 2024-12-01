INSERT IGNORE INTO page(id, price, type) VALUES
(1, 1000, 'NORMAL'),
(2, 2000, 'COLOR');

INSERT IGNORE INTO printer(id, address, date_of_use, status_spso, status_student, timeout) VALUES
(1, "CS1", "2018-07-11 00:00:00.000000", "ACTIVE", "AVAILABILITY", 123456789),
(2, "CS1", "2020-12-05 00:00:00.000000", "CONNECT_FALED", "AVAILABILITY", 98765432),
(3, "CS2", "2024-12-01 00:00:00.000000", "ACTIVE", "AVAILABILITY", 5647382),
(4, "CS2", "2022-03-30 00:00:00.000000", "MAINTENANCE", "AVAILABILITY", 12341234),
(5, "CS1", "2021-10-09 00:00:00.000000", "ACTIVE", "AVAILABILITY", 5647382),
(6, "CS2", "2019-03-02 00:00:00.000000", "ACTIVE", "AVAILABILITY", 12341234),
(7, "CS1", "2020-03-21 00:00:00.000000", "MAINTENANCE", "AVAILABILITY", 5647382),
(8, "CS2", "2024-04-28 00:00:00.000000", "ACTIVE", "AVAILABILITY", 12341234);

INSERT IGNORE INTO print_package(id, name, page_quantity, id_page) VALUES
(1, "Gói in thường số 1", 20, 1),
(2, "Gói in thường số 2", 40, 1),
(3, "Gói in thường số 3", 100, 1),
(4, "Gói in màu số 1", 20, 2),
(5, "Gói in màu số 2", 40, 2),
(6, "Gói in màu số 3", 100, 2);

INSERT IGNORE INTO user(id, active, birthday, email, full_name, password, phone_number, sex) VALUES
(1, b'1', "2004-11-09", "quy.daoquy321323@hcmut.edu.vn", "Đào Duy Quý", "123456", "0373071643", "MALE"),
(2, b'1', "2004-11-09", "phuoc.phanhuu1111@hcmut.edu.vn", "Phan Hữu Phước", "123456", "0585045075", "MALE"),
(3, b'1', "2004-03-17", "phuc.dohoang001@hcmut.edu.vn", "Đỗ Hoàng Phúc", "123456", "0708609225", "MALE"),
(4, b'1', "2004-08-23", "hau.vocong@hcmut.edu.vn", "Võ Công Hậu", "123456", "0947392724", "MALE"),
(5, b'1', "2004-11-09", "anh.nguyen13012004@hcmut.edu.vn", "Nguyễn Thị Mai Anh", "123456", "0585045075", "FEMALE"),
(6, b'1', "2004-05-05", "dung.do050504@hcmut.edu.vn", "Đỗ Huy Minh Dũng", "123456", "0812307577", "MALE"),
(7, b'1', "2004-01-21", "hoang.pham2004@hcmut.edu.vn", "Phạm Đức Hoàng", "123456", "0977482327", "MALE"),
(8, b'1', "2004-11-09", "spso@hcmut.edu.vn", "SPSO", "123", "0123456789", "MALE");

INSERT IGNORE INTO student(id, mssv) VALUES
(1, "2212864"),
(2, "2212720"),
(3, "2212611"),
(4, "2210972"),
(5, "2210103"),
(6, "2210568"),
(7, "2211111");

INSERT IGNORE INTO spso(id) VALUES
(8);

INSERT IGNORE INTO page_student(page_quantity, id_page, id_student) VALUES
(70, 1, 1),
(30, 2, 1),
(70, 1, 2),
(30, 2, 2),
(70, 1, 3),
(30, 2, 3),
(70, 1, 4),
(30, 2, 4),
(70, 1, 5),
(30, 2, 5),
(70, 1, 6),
(30, 2, 6),
(70, 1, 7),
(30, 2, 7);

INSERT IGNORE INTO page_printer(page_quantity, id_page, id_printer) VALUES
(2000, 1, 1),
(1000, 2, 1),
(2000, 1, 2),
(1000, 2, 2),
(2000, 1, 3),
(1000, 2, 3),
(2000, 1, 4),
(1000, 2, 4),
(2000, 1, 5),
(1000, 2, 5),
(2000, 1, 6),
(1000, 2, 6),
(2000, 1, 7),
(1000, 2, 7),
(2000, 1, 8),
(1000, 2, 8);