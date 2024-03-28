CREATE SCHEMA test;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`        bigint(20) PRIMARY KEY NOT NULL COMMENT '主键',
    `name`      varchar(50) COMMENT '姓名',
    `phone`     varchar(100) COMMENT '手机号',
    `sex`       varchar(2) COMMENT '性别',
    `id_number` varchar(18) COMMENT '身份证号',
    `avatar`    varchar(500) COMMENT '头像',
    `status`    int(11) DEFAULT '0' COMMENT '状态 0:禁用，1:正常',
    PRIMARY KEY (`id`)
) COMMENT ='用户信息';



-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`
(
    `id`            bigint(20) PRIMARY KEY NOT NULL COMMENT '主键',
    `user_id`       bigint(20) COMMENT '用户id',
    `consignee`     varchar(50)            NOT NULL COMMENT '收货人',
    `sex`           tinyint(4)             NOT NULL COMMENT '性别 0 女 1 男',
    `phone`         varchar(11)            NOT NULL COMMENT '手机号',
    `province_code` varchar(12) COMMENT '省级区划编号',
    `province_name` varchar(32) COMMENT '省级名称',
    `city_code`     varchar(12) COMMENT '市级区划编号',
    `city_name`     varchar(32) COMMENT '市级名称',
    `district_code` varchar(12) COMMENT '区级区划编号',
    `district_name` varchar(32) COMMENT '区级名称',
    `detail`        varchar(200) COMMENT '详细地址',
    `label`         varchar(100) COMMENT '标签',
    `is_default`    tinyint(4)             NOT NULL DEFAULT '0' COMMENT '默认 0 否 1是',
    `create_time`   datetime               NOT NULL COMMENT '创建时间',
    `update_time`   datetime               NOT NULL COMMENT '更新时间',
    `create_user`   bigint(20)             NOT NULL COMMENT '创建人',
    `update_user`   bigint(20)             NOT NULL COMMENT '修改人',
    `is_deleted`    tinyint(4)             NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) COMMENT ='地址管理';










