INSERT INTO `user` (`id`, `name`, `phone`, `sex`, `id_number`, `avatar`, `status`)
VALUES ('1', '霜霜', '15210354625', '0', '1', 'hello kitty', '0');
INSERT INTO `address_book` (`id`, `user_id`, `consignee`, `sex`, `phone`, `province_code`, `province_name`, `city_code`,
                            `city_name`, `district_code`, `district_name`, `detail`, `label`, `is_default`,
                            `create_time`, `update_time`, `create_user`, `update_user`, `is_deleted`)
VALUES ('1', '1', '霜霜', '0', '15210675046', '001', '河南', '0001', '新乡', '1234', '辉县', '这是一个小县城', '县级市',
        '1', '2024-03-26 15:51:13', '2024-03-21 15:51:13', '1111111', '1111111', '1');
INSERT INTO `address_book` (`id`, `user_id`, `consignee`, `sex`, `phone`, `province_code`, `province_name`, `city_code`,
                            `city_name`, `district_code`, `district_name`, `detail`, `label`, `is_default`,
                            `create_time`, `update_time`, `create_user`, `update_user`, `is_deleted`)
VALUES ('2', '1', '霜霜', '0', '15210675046', '001', '河南', '0001', '新乡', '1234', '辉县', '这是一个小县城', '县级市',
        '1', '2024-03-24 19:04:55', '2024-03-24 19:04:55', '1111111', '1111111', '0');