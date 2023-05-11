/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 127.0.0.1:3306
 Source Schema         : pt

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 09/12/2022 22:18:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货地址区域',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES (1, '东区');
INSERT INTO `area` VALUES (2, '西区');

-- ----------------------------
-- Table structure for area_address
-- ----------------------------
DROP TABLE IF EXISTS `area_address`;
CREATE TABLE `area_address`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `area_id` bigint(20) NULL DEFAULT NULL COMMENT 'area表id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教学楼或宿舍楼',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of area_address
-- ----------------------------
INSERT INTO `area_address` VALUES (1, 1, '1教学楼');
INSERT INTO `area_address` VALUES (2, 1, '2教学楼');
INSERT INTO `area_address` VALUES (3, 1, '3教学楼');
INSERT INTO `area_address` VALUES (4, 1, '4教学楼');
INSERT INTO `area_address` VALUES (5, 1, '1栋宿舍楼');
INSERT INTO `area_address` VALUES (6, 1, '2栋宿舍楼');
INSERT INTO `area_address` VALUES (7, 1, '11教学楼');
INSERT INTO `area_address` VALUES (8, 2, '5教学楼');
INSERT INTO `area_address` VALUES (9, 2, '6教学楼');
INSERT INTO `area_address` VALUES (10, 2, '7教学楼');
INSERT INTO `area_address` VALUES (11, 2, '8教学楼');
INSERT INTO `area_address` VALUES (12, 2, '3栋宿舍楼');
INSERT INTO `area_address` VALUES (13, 2, '4栋宿舍楼');
INSERT INTO `area_address` VALUES (14, 2, '5栋宿舍楼');

-- ----------------------------
-- Table structure for auth_file
-- ----------------------------
DROP TABLE IF EXISTS `auth_file`;
CREATE TABLE `auth_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NULL DEFAULT NULL COMMENT 'user表id',
  `sfz_z` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证正面图片',
  `sfz_f` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证反面图片',
  `xsz_f` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学生证封面图片',
  `xsz_x` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学生证个人信息图片',
  `rlz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人脸照',
  `create_time` datetime NULL DEFAULT NULL COMMENT '提交审核时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_file
-- ----------------------------
INSERT INTO `auth_file` VALUES (1, 2, 'https://xypt.imgs.space/images/2/54ba7d613ef94f2ebe58050f39d8bafb.jpg', 'https://xypt.imgs.space/images/2/eb7ed89732ee432b96ab9a9030724fbc.jpg', 'https://xypt.imgs.space/images/2/bb32f0deb82c496290dd8e9bfdb439b7.jpg', 'https://xypt.imgs.space/images/2/311c560cffc04a78817a0ec93c1097c8.jpg', 'https://xypt.imgs.space/images/2/0a6389d5d59d44b2a659b1359aba44e8.png', '2022-04-18 20:19:35');

-- ----------------------------
-- Table structure for chat_list
-- ----------------------------
DROP TABLE IF EXISTS `chat_list`;
CREATE TABLE `chat_list`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(255) NULL DEFAULT NULL COMMENT 'user表id',
  `friend_id` bigint(255) NULL DEFAULT NULL COMMENT '好友id,user表id',
  `friend_window` bit(1) NULL DEFAULT NULL COMMENT '好友是否在窗口,true-在',
  `unread` bigint(255) NULL DEFAULT NULL COMMENT '自己的消息未读数',
  `status` bit(1) NULL DEFAULT NULL COMMENT '列表状态，是否删除,true-是',
  `weight` int(11) NULL DEFAULT NULL COMMENT '消息权重，根据此排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat_list
-- ----------------------------
INSERT INTO `chat_list` VALUES (1, 2, 1, b'0', 1, b'0', 9);
INSERT INTO `chat_list` VALUES (2, 1, 2, b'0', 2, b'0', 1);
INSERT INTO `chat_list` VALUES (3, 2, 3, b'0', 0, b'1', 8);
INSERT INTO `chat_list` VALUES (4, 3, 2, b'0', 4, b'0', 1);
INSERT INTO `chat_list` VALUES (5, 2, 3, b'0', 0, b'0', 10);

-- ----------------------------
-- Table structure for chat_message
-- ----------------------------
DROP TABLE IF EXISTS `chat_message`;
CREATE TABLE `chat_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_id` bigint(255) NULL DEFAULT NULL COMMENT '发送方id',
  `to_id` bigint(255) NULL DEFAULT NULL COMMENT '接收方id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `send_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `type` int(255) NULL DEFAULT NULL COMMENT '消息类型,0-文本，1-图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat_message
-- ----------------------------
INSERT INTO `chat_message` VALUES (1, 2, 1, '6.29', '2022-04-07 18:29:35', 0);
INSERT INTO `chat_message` VALUES (2, 2, 1, '6.30', '2022-04-07 18:29:51', 0);
INSERT INTO `chat_message` VALUES (3, 2, 1, '6.31', '2022-04-07 18:30:03', 0);
INSERT INTO `chat_message` VALUES (4, 2, 1, '6.30', '2022-04-07 18:31:04', 0);
INSERT INTO `chat_message` VALUES (5, 2, 1, '6.31', '2022-04-07 18:32:21', 0);
INSERT INTO `chat_message` VALUES (6, 1, 2, '6.33', '2022-04-07 18:33:26', 0);
INSERT INTO `chat_message` VALUES (7, 1, 2, '6.34', '2022-04-07 18:34:31', 0);
INSERT INTO `chat_message` VALUES (8, 1, 2, '11.59', '2022-04-07 23:59:28', 0);
INSERT INTO `chat_message` VALUES (9, 2, 1, '12.00', '2022-04-07 23:59:45', 0);
INSERT INTO `chat_message` VALUES (10, 2, 1, '10.52', '2022-04-08 22:52:09', 0);
INSERT INTO `chat_message` VALUES (11, 2, 1, '10.54', '2022-04-08 22:54:40', 0);
INSERT INTO `chat_message` VALUES (12, 2, 1, '10.56', '2022-04-08 22:56:31', 0);
INSERT INTO `chat_message` VALUES (13, 2, 1, '10.57', '2022-04-08 22:57:57', 0);
INSERT INTO `chat_message` VALUES (14, 2, 1, '11.00', '2022-04-08 23:00:07', 0);
INSERT INTO `chat_message` VALUES (15, 2, 1, '11.00', '2022-04-08 23:00:47', 0);
INSERT INTO `chat_message` VALUES (16, 2, 1, '11.01', '2022-04-08 23:01:26', 0);
INSERT INTO `chat_message` VALUES (17, 2, 1, '11.02', '2022-04-08 23:02:08', 0);
INSERT INTO `chat_message` VALUES (18, 2, 1, '7.14', '2022-04-09 19:14:50', 0);
INSERT INTO `chat_message` VALUES (19, 2, 1, '7.16', '2022-04-09 19:16:27', 0);
INSERT INTO `chat_message` VALUES (20, 2, 1, '7.19', '2022-04-09 19:19:56', 0);
INSERT INTO `chat_message` VALUES (21, 2, 1, '7.20', '2022-04-09 19:20:54', 0);
INSERT INTO `chat_message` VALUES (22, 2, 1, 'https://xypt.imgs.space/images/2/d1b6c5ac693e49629242ddf7a3e1273c.jpg', '2022-04-10 11:28:27', 1);
INSERT INTO `chat_message` VALUES (23, 2, 1, 'https://xypt.imgs.space/images/2/1e394ef15de3487eaafbaf5362de64ae.jpg', '2022-04-10 12:54:39', 1);
INSERT INTO `chat_message` VALUES (24, 2, 1, 'https://xypt.imgs.space/images/2/f666aa274bfc4e63ba1606abc815297b.png', '2022-04-10 12:54:59', 1);
INSERT INTO `chat_message` VALUES (25, 2, 1, '😍', '2022-04-10 12:59:03', 0);
INSERT INTO `chat_message` VALUES (26, 2, 1, '😋你好', '2022-04-10 12:59:16', 0);
INSERT INTO `chat_message` VALUES (27, 2, 1, '😋 测试', '2022-04-10 13:00:27', 0);
INSERT INTO `chat_message` VALUES (28, 2, 1, '😍 ', '2022-04-10 16:38:42', 0);
INSERT INTO `chat_message` VALUES (29, 2, 3, '你好', '2022-04-11 17:15:46', 0);
INSERT INTO `chat_message` VALUES (30, 2, 3, '5', '2022-04-11 17:20:43', 0);
INSERT INTO `chat_message` VALUES (31, 2, 3, '6', '2022-04-11 17:21:22', 0);
INSERT INTO `chat_message` VALUES (32, 2, 3, '5.34', '2022-04-11 17:34:49', 0);
INSERT INTO `chat_message` VALUES (33, 2, 3, '6.36', '2022-04-11 17:36:11', 0);
INSERT INTO `chat_message` VALUES (34, 2, 3, '6.37', '2022-04-11 17:37:43', 0);
INSERT INTO `chat_message` VALUES (35, 2, 3, '5.38', '2022-04-11 17:38:33', 0);
INSERT INTO `chat_message` VALUES (36, 3, 2, '11', '2022-04-11 17:42:12', 0);
INSERT INTO `chat_message` VALUES (37, 3, 2, '22', '2022-04-11 17:42:31', 0);
INSERT INTO `chat_message` VALUES (38, 3, 2, '33', '2022-04-11 17:42:46', 0);
INSERT INTO `chat_message` VALUES (39, 3, 2, '11', '2022-04-11 17:43:29', 0);
INSERT INTO `chat_message` VALUES (40, 2, 3, '6.15', '2022-04-11 18:15:37', 0);
INSERT INTO `chat_message` VALUES (41, 2, 3, '6.17', '2022-04-11 18:15:58', 0);
INSERT INTO `chat_message` VALUES (42, 2, 3, '6.19', '2022-04-11 18:16:52', 0);
INSERT INTO `chat_message` VALUES (43, 2, 3, '6.17', '2022-04-11 18:17:15', 0);
INSERT INTO `chat_message` VALUES (44, 2, 3, '666', '2022-04-11 18:17:29', 0);
INSERT INTO `chat_message` VALUES (45, 2, 3, '1', '2022-04-11 18:17:35', 0);
INSERT INTO `chat_message` VALUES (46, 2, 3, '6.24', '2022-04-11 18:24:45', 0);
INSERT INTO `chat_message` VALUES (47, 3, 2, '😆 ', '2022-04-11 18:26:56', 0);
INSERT INTO `chat_message` VALUES (48, 2, 3, '😝 ', '2022-04-11 18:32:49', 0);
INSERT INTO `chat_message` VALUES (49, 3, 2, '你说啥', '2022-04-12 17:07:29', 0);
INSERT INTO `chat_message` VALUES (50, 2, 3, '5.08', '2022-04-12 17:08:23', 0);
INSERT INTO `chat_message` VALUES (51, 2, 3, '5.09', '2022-04-12 17:08:37', 0);
INSERT INTO `chat_message` VALUES (52, 2, 3, '5.10', '2022-04-12 17:09:40', 0);
INSERT INTO `chat_message` VALUES (53, 2, 3, '5.12', '2022-04-12 17:12:24', 0);
INSERT INTO `chat_message` VALUES (54, 2, 3, '5.13', '2022-04-12 17:13:12', 0);
INSERT INTO `chat_message` VALUES (55, 2, 3, '😳 ', '2022-04-12 22:42:26', 0);
INSERT INTO `chat_message` VALUES (56, 2, 3, 'https://xypt.imgs.space/images/2/50309991652a42548c18f58726214d9d.png', '2022-04-12 22:49:46', 1);
INSERT INTO `chat_message` VALUES (57, 2, 3, 'https://xypt.imgs.space/images/2/0cfe69beef3e4826ad6fe7488d591f29.jpg', '2022-04-12 22:57:43', 1);
INSERT INTO `chat_message` VALUES (58, 2, 3, 'https://xypt.imgs.space/images/2/b9ad7814577140aebb2b10e1821e6736.jpg', '2022-04-12 22:59:36', 1);
INSERT INTO `chat_message` VALUES (59, 2, 3, 'https://xypt.imgs.space/images/2/cad02a1d846a435ebbbbc1e339f2f4b8.jpg', '2022-04-12 23:00:34', 1);
INSERT INTO `chat_message` VALUES (60, 2, 3, 'https://xypt.imgs.space/images/2/de8ef840ae59400eacdd282105169dc1.png', '2022-04-12 23:02:51', 1);
INSERT INTO `chat_message` VALUES (61, 2, 3, 'https://xypt.imgs.space/images/2/7430ac3acc084e49bc8def1fb046d093.jpg', '2022-04-12 23:03:30', 1);
INSERT INTO `chat_message` VALUES (62, 2, 3, 'https://xypt.imgs.space/images/2/ef32040236e643708788d898baa89b18.jpg', '2022-04-12 23:05:59', 1);
INSERT INTO `chat_message` VALUES (63, 2, 3, 'https://xypt.imgs.space/images/2/88386871c65d45ca9ba83f7c5e0067e3.png', '2022-04-12 23:15:27', 1);
INSERT INTO `chat_message` VALUES (64, 2, 3, '1', '2022-04-12 23:19:55', 0);
INSERT INTO `chat_message` VALUES (65, 2, 3, '1', '2022-04-13 17:02:45', 0);
INSERT INTO `chat_message` VALUES (66, 2, 3, 'https://xypt.imgs.space/images/2/1e665fba57f14dcfb531ea1f3930144e.jpg', '2022-04-13 17:10:11', 1);
INSERT INTO `chat_message` VALUES (67, 3, 2, '1', '2022-04-13 18:31:24', 0);
INSERT INTO `chat_message` VALUES (68, 3, 2, '22\n', '2022-04-13 18:36:44', 0);
INSERT INTO `chat_message` VALUES (69, 3, 2, '1', '2022-04-13 18:36:49', 0);
INSERT INTO `chat_message` VALUES (70, 3, 2, '1', '2022-04-13 18:41:03', 0);
INSERT INTO `chat_message` VALUES (71, 3, 2, 'gggggg', '2022-04-13 18:41:06', 0);
INSERT INTO `chat_message` VALUES (72, 3, 2, 'hhhhhhhh', '2022-04-13 18:46:06', 0);
INSERT INTO `chat_message` VALUES (73, 3, 2, '4653454354', '2022-04-13 18:47:56', 0);
INSERT INTO `chat_message` VALUES (74, 3, 2, '😍 ', '2022-04-13 21:23:48', 0);
INSERT INTO `chat_message` VALUES (75, 3, 2, '😂 ', '2022-04-13 21:23:56', 0);
INSERT INTO `chat_message` VALUES (76, 3, 2, '😜 ', '2022-04-13 21:42:19', 0);
INSERT INTO `chat_message` VALUES (77, 3, 2, '1', '2022-04-14 23:00:37', 0);
INSERT INTO `chat_message` VALUES (78, 3, 2, '2', '2022-04-14 23:01:09', 0);
INSERT INTO `chat_message` VALUES (79, 3, 2, '嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎', '2022-04-14 23:01:51', 0);
INSERT INTO `chat_message` VALUES (80, 3, 2, '一个', '2022-04-14 23:02:44', 0);
INSERT INTO `chat_message` VALUES (81, 3, 2, '呱呱', '2022-04-14 23:05:18', 0);
INSERT INTO `chat_message` VALUES (82, 2, 3, 'https://xypt.imgs.space/images/2/2563321f3b814b70bf25111819fdff94.jpg', '2022-04-14 23:06:05', 1);
INSERT INTO `chat_message` VALUES (83, 2, 3, '😂 ', '2022-04-18 23:56:47', 0);
INSERT INTO `chat_message` VALUES (84, 2, 3, '😞 ', '2022-04-18 23:57:08', 0);
INSERT INTO `chat_message` VALUES (85, 2, 1, '😜 ', '2022-04-18 23:57:58', 0);
INSERT INTO `chat_message` VALUES (86, 2, 3, '😜 ', '2022-04-19 13:50:02', 0);
INSERT INTO `chat_message` VALUES (87, 2, 1, '👿 ', '2022-04-19 13:50:13', 0);
INSERT INTO `chat_message` VALUES (88, 2, 3, '你好😋 ', '2022-04-20 21:31:44', 0);
INSERT INTO `chat_message` VALUES (89, 2, 1, '你好😋 ', '2022-04-20 22:38:48', 0);
INSERT INTO `chat_message` VALUES (90, 1, 2, '嘿嘿，还在吗(⊙o⊙)！', '2022-04-20 23:39:51', 0);
INSERT INTO `chat_message` VALUES (91, 1, 2, '👍 ', '2022-04-20 23:40:11', 0);
INSERT INTO `chat_message` VALUES (92, 2, 1, '准备休息了🍉 ', '2022-04-20 23:57:26', 0);
INSERT INTO `chat_message` VALUES (93, 2, 1, '晚安，玛卡玛卡', '2022-04-20 23:57:37', 0);
INSERT INTO `chat_message` VALUES (94, 1, 2, 'bug很多', '2022-04-20 23:57:38', 0);
INSERT INTO `chat_message` VALUES (95, 2, 1, '是啊，太赶了', '2022-04-20 23:58:12', 0);
INSERT INTO `chat_message` VALUES (96, 2, 1, '慢慢改😭 ', '2022-04-20 23:58:34', 0);
INSERT INTO `chat_message` VALUES (97, 1, 2, '辛苦了😂 ', '2022-04-20 23:59:15', 0);

-- ----------------------------
-- Table structure for open
-- ----------------------------
DROP TABLE IF EXISTS `open`;
CREATE TABLE `open`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NULL DEFAULT NULL COMMENT 'user表主键id',
  `wx_openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信小程序用户唯一标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of open
-- ----------------------------
INSERT INTO `open` VALUES (1, 1, 'ocjBf41q4xm_VaLXb3zKbVnkpSx4');
INSERT INTO `open` VALUES (2, 2, 'ocjBf4zJ97bqdbwKihkngNX9oBOU');
INSERT INTO `open` VALUES (3, 3, 'ocjBf45SI_hjB7BQewcrAMR1dtEs');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限表达式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '修改昵称', 'user:nickname-edit');
INSERT INTO `permission` VALUES (2, '修改头像', 'user:avatar-edit');

-- ----------------------------
-- Table structure for receive_address
-- ----------------------------
DROP TABLE IF EXISTS `receive_address`;
CREATE TABLE `receive_address`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NULL DEFAULT NULL COMMENT 'user表id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `area_id` bigint(20) NULL DEFAULT NULL COMMENT '区域id，东区/西区',
  `area_address_id` bigint(20) NULL DEFAULT NULL COMMENT 'area_address表id',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `defaulted` bit(1) NULL DEFAULT NULL COMMENT '是否被设为默认地址,1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of receive_address
-- ----------------------------
INSERT INTO `receive_address` VALUES (1, 1, 'R', '17379525931', 1, 6, 'D209', b'1');
INSERT INTO `receive_address` VALUES (2, 2, '秦风', '15182814678', 1, 6, 'C409', b'0');
INSERT INTO `receive_address` VALUES (3, 2, '秦风', '16172894326', 1, 6, 'C408', b'1');
INSERT INTO `receive_address` VALUES (8, 2, '秦风', '12121', 1, 5, 'B701', b'0');

-- ----------------------------
-- Table structure for recharge_order
-- ----------------------------
DROP TABLE IF EXISTS `recharge_order`;
CREATE TABLE `recharge_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transaction_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易单号',
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `uid` bigint(20) NULL DEFAULT NULL COMMENT '支付者',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `money` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '充值金额，单位元',
  `status` int(1) NULL DEFAULT NULL COMMENT '订单状态，0-待支付，1-已完成，2-失败',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原因',
  `create_time` datetime NULL DEFAULT NULL COMMENT '订单创建时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recharge_order
-- ----------------------------
INSERT INTO `recharge_order` VALUES (1, NULL, '1513556939009703936', 1, '钱包充值-0.01', '0.01', 2, '用户取消支付', '2022-04-12 00:37:50', '2022-04-12 00:37:54');
INSERT INTO `recharge_order` VALUES (2, '4200001302202204124925895422', '1513557118924374016', 1, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-12 00:38:33', '2022-04-12 00:39:02');
INSERT INTO `recharge_order` VALUES (3, '4200001314202204120582339680', '1513558105760219136', 1, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-12 00:42:28', '2022-04-12 00:42:43');
INSERT INTO `recharge_order` VALUES (4, NULL, '1513559899412463616', 1, '钱包充值-0.01', '0.01', 2, '用户取消支付', '2022-04-12 00:49:36', '2022-04-12 00:49:40');
INSERT INTO `recharge_order` VALUES (5, '4200001316202204127028526234', '1513560407275569152', 1, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-12 00:51:37', '2022-04-12 00:51:51');
INSERT INTO `recharge_order` VALUES (6, NULL, '1513694441590857728', 1, '钱包充值-0.01', '0.01', 2, '用户取消支付', '2022-04-12 09:44:13', '2022-04-12 09:44:17');
INSERT INTO `recharge_order` VALUES (7, '4200001307202204121064136495', '1513694765076553728', 1, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-12 09:45:30', '2022-04-12 09:45:43');
INSERT INTO `recharge_order` VALUES (8, NULL, '1513695449599447040', 1, '钱包充值-0.01', '0.01', 0, '待支付', '2022-04-12 09:48:13', '2022-04-12 09:48:13');
INSERT INTO `recharge_order` VALUES (9, '4200001323202204120636104573', '1513695452682260480', 1, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-12 09:48:14', '2022-04-12 09:48:27');
INSERT INTO `recharge_order` VALUES (10, '4200001320202204129483341640', '1513698415991640064', 1, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-12 10:00:01', '2022-04-12 10:00:23');
INSERT INTO `recharge_order` VALUES (11, '4200001324202204124276007429', '1513733959932973056', 1, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-12 12:21:15', '2022-04-12 12:22:04');
INSERT INTO `recharge_order` VALUES (12, '4200001322202204124459891047', '1513736189767606272', 1, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-12 12:30:07', '2022-04-12 12:30:21');
INSERT INTO `recharge_order` VALUES (13, NULL, '1513744170563428352', 1, '钱包充值-0.01', '0.01', 0, '待支付', '2022-04-12 13:01:49', '2022-04-12 13:01:49');
INSERT INTO `recharge_order` VALUES (14, NULL, '1513745503706177536', 2, '钱包充值-0.01', '0.01', 0, '待支付', '2022-04-12 13:07:07', '2022-04-12 13:07:07');
INSERT INTO `recharge_order` VALUES (15, NULL, '1513747024288178176', 2, '钱包充值-0.01', '0.01', 0, '待支付', '2022-04-12 13:13:10', '2022-04-12 13:13:10');
INSERT INTO `recharge_order` VALUES (16, NULL, '1513747140793360384', 2, '钱包充值-0.02', '0.02', 0, '待支付', '2022-04-12 13:13:37', '2022-04-12 13:13:37');
INSERT INTO `recharge_order` VALUES (17, NULL, '1513747733641453568', 2, '钱包充值-0.05', '0.05', 0, '待支付', '2022-04-12 13:15:59', '2022-04-12 13:15:59');
INSERT INTO `recharge_order` VALUES (18, NULL, '1513747812985102336', 2, '钱包充值-0.09', '0.09', 0, '待支付', '2022-04-12 13:16:18', '2022-04-12 13:16:18');
INSERT INTO `recharge_order` VALUES (19, NULL, '1513747869331382272', 2, '钱包充值-0.19', '0.19', 0, '待支付', '2022-04-12 13:16:31', '2022-04-12 13:16:31');
INSERT INTO `recharge_order` VALUES (20, NULL, '1513747931600019456', 2, '钱包充值-1.21', '1.21', 0, '待支付', '2022-04-12 13:16:46', '2022-04-12 13:16:46');
INSERT INTO `recharge_order` VALUES (21, NULL, '1513748145350139904', 2, '钱包充值-10.12', '10.12', 0, '待支付', '2022-04-12 13:17:37', '2022-04-12 13:17:37');
INSERT INTO `recharge_order` VALUES (22, NULL, '1513748267354054656', 2, '钱包充值-10.01', '10.01', 0, '待支付', '2022-04-12 13:18:06', '2022-04-12 13:18:06');
INSERT INTO `recharge_order` VALUES (23, NULL, '1513748778165755904', 1, '钱包充值-0.01', '0.01', 0, '待支付', '2022-04-12 13:20:08', '2022-04-12 13:20:08');
INSERT INTO `recharge_order` VALUES (24, NULL, '1513754598404358144', 1, '钱包充值-10.00', '10.00', 2, '用户取消支付', '2022-04-12 13:43:16', '2022-04-12 13:43:45');
INSERT INTO `recharge_order` VALUES (25, NULL, '1513754778600046592', 1, '钱包充值-10.00', '10.00', 2, '用户取消支付', '2022-04-12 13:43:59', '2022-04-12 13:44:02');
INSERT INTO `recharge_order` VALUES (26, NULL, '1513754818995388416', 1, '钱包充值-10.10', '10.10', 2, '用户取消支付', '2022-04-12 13:44:08', '2022-04-12 13:44:12');
INSERT INTO `recharge_order` VALUES (27, NULL, '1513754936695947264', 1, '钱包充值-10000.00', '10000.00', 2, '用户取消支付', '2022-04-12 13:44:36', '2022-04-12 13:44:56');
INSERT INTO `recharge_order` VALUES (28, NULL, '1513755050000875520', 1, '钱包充值-10000', '10000', 2, '用户取消支付', '2022-04-12 13:45:03', '2022-04-12 13:45:16');
INSERT INTO `recharge_order` VALUES (29, NULL, '1513763705693765632', 2, '钱包充值-10.00', '10.00', 0, '待支付', '2022-04-12 14:19:27', '2022-04-12 14:19:27');
INSERT INTO `recharge_order` VALUES (30, NULL, '1513763869397450752', 2, '钱包充值-10', '10', 0, '待支付', '2022-04-12 14:20:06', '2022-04-12 14:20:06');
INSERT INTO `recharge_order` VALUES (31, NULL, '1513764149090418688', 2, '钱包充值-20', '20', 0, '待支付', '2022-04-12 14:21:13', '2022-04-12 14:21:13');
INSERT INTO `recharge_order` VALUES (32, NULL, '1513766286549680128', 2, '钱包充值-0.01', '0.01', 0, '待支付', '2022-04-12 14:29:42', '2022-04-12 14:29:42');
INSERT INTO `recharge_order` VALUES (33, NULL, '1513767017910468608', 2, '钱包充值-10', '10', 0, '待支付', '2022-04-12 14:32:37', '2022-04-12 14:32:37');
INSERT INTO `recharge_order` VALUES (34, NULL, '1513768552727937024', 2, '钱包充值-0.01', '0.01', 0, '待支付', '2022-04-12 14:38:43', '2022-04-12 14:38:43');
INSERT INTO `recharge_order` VALUES (35, NULL, '1513768705287356416', 2, '钱包充值-0.01', '0.01', 0, '待支付', '2022-04-12 14:39:19', '2022-04-12 14:39:19');
INSERT INTO `recharge_order` VALUES (36, NULL, '1513769650167578624', 2, '钱包充值-0.01', '0.01', 0, '待支付', '2022-04-12 14:43:04', '2022-04-12 14:43:04');
INSERT INTO `recharge_order` VALUES (37, NULL, '1513769881454084096', 2, '钱包充值-10', '10', 0, '待支付', '2022-04-12 14:43:59', '2022-04-12 14:43:59');
INSERT INTO `recharge_order` VALUES (38, NULL, '1513770060550864896', 2, '钱包充值-10', '10', 2, 'requestPayment:fail cancel', '2022-04-12 14:44:42', '2022-04-12 14:44:46');
INSERT INTO `recharge_order` VALUES (39, NULL, '1513770160677289984', 2, '钱包充值-10', '10', 2, 'requestPayment:fail cancel', '2022-04-12 14:45:06', '2022-04-12 14:45:10');
INSERT INTO `recharge_order` VALUES (40, NULL, '1513770626962259968', 2, '钱包充值-10', '10', 2, 'requestPayment:fail cancel', '2022-04-12 14:46:57', '2022-04-12 14:47:01');
INSERT INTO `recharge_order` VALUES (41, NULL, '1513771201418330112', 2, '钱包充值-10', '10', 2, 'requestPayment:fail cancel', '2022-04-12 14:49:14', '2022-04-12 14:49:18');
INSERT INTO `recharge_order` VALUES (42, NULL, '1513771424513359872', 2, '钱包充值-10', '10', 2, 'requestPayment:fail cancel', '2022-04-12 14:50:07', '2022-04-12 14:50:11');
INSERT INTO `recharge_order` VALUES (43, NULL, '1513771786129473536', 2, '钱包充值-10', '10', 2, 'requestPayment:fail cancel', '2022-04-12 14:51:33', '2022-04-12 14:51:37');
INSERT INTO `recharge_order` VALUES (44, '4200001311202204127192138470', '1513771964597108736', 2, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-12 14:52:16', '2022-04-12 14:52:28');
INSERT INTO `recharge_order` VALUES (45, NULL, '1513772875763515392', 2, '钱包充值-0.01', '0.01', 2, 'requestPayment:fail cancel', '2022-04-12 14:55:53', '2022-04-12 14:56:06');
INSERT INTO `recharge_order` VALUES (46, '4200001307202204127712902279', '1513773102570504192', 2, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-12 14:56:47', '2022-04-12 14:56:57');
INSERT INTO `recharge_order` VALUES (47, NULL, '1513782500307075072', 1, '钱包充值-10000', '10000', 2, '用户取消支付', '2022-04-12 15:34:08', '2022-04-12 15:35:44');
INSERT INTO `recharge_order` VALUES (48, NULL, '1514454072067919872', 2, '钱包充值-0.1', '0.1', 2, 'requestPayment:fail cancel', '2022-04-14 12:02:43', '2022-04-14 12:02:47');
INSERT INTO `recharge_order` VALUES (49, NULL, '1516644548116226048', 3, '钱包充值-10', '10', 2, '支付取消', '2022-04-20 13:06:53', '2022-04-20 13:06:57');
INSERT INTO `recharge_order` VALUES (50, NULL, '1516767994577973248', 3, '钱包充值-10', '10', 2, '支付取消', '2022-04-20 21:17:25', '2022-04-20 21:17:29');
INSERT INTO `recharge_order` VALUES (51, '4200001317202204207231322001', '1516768711686516736', 2, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-20 21:20:16', '2022-04-20 21:20:30');
INSERT INTO `recharge_order` VALUES (52, NULL, '1516774299736764416', 2, '钱包充值-0.01', '0.01', 2, '支付取消', '2022-04-20 21:42:28', '2022-04-20 21:42:36');
INSERT INTO `recharge_order` VALUES (53, '4200001467202204205731705771', '1516787381959684096', 2, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-20 22:34:28', '2022-04-20 22:34:39');
INSERT INTO `recharge_order` VALUES (54, NULL, '1516811561732173824', 3, '钱包充值-0.01', '0.01', 2, '支付取消', '2022-04-21 00:10:32', '2022-04-21 00:10:37');
INSERT INTO `recharge_order` VALUES (55, NULL, '1516814016465367040', 1, '钱包充值-0.01', '0.01', 2, '支付取消', '2022-04-21 00:20:18', '2022-04-21 00:20:28');
INSERT INTO `recharge_order` VALUES (56, '4200001324202204215526108171', '1516814094265511936', 1, '钱包充值-0.01', '0.01', 1, '支付成功', '2022-04-21 00:20:36', '2022-04-21 00:20:45');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '认证用户', 'Authed');
INSERT INTO `role` VALUES (2, '未认证用户', 'UnAuth');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` bigint(32) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(32) NULL DEFAULT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (2, 1);
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (2, 2);

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tid` bigint(20) NULL DEFAULT NULL COMMENT 'thing表id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES (1, 1, '菜鸟驿站15号店');
INSERT INTO `store` VALUES (2, 1, '顺丰快递');
INSERT INTO `store` VALUES (3, 1, '百世');
INSERT INTO `store` VALUES (4, 1, '京东');
INSERT INTO `store` VALUES (5, 1, '菜鸟驿站4号铺');
INSERT INTO `store` VALUES (6, 1, '邮政');
INSERT INTO `store` VALUES (7, 2, '一品豆花');
INSERT INTO `store` VALUES (8, 2, '茶念念');
INSERT INTO `store` VALUES (9, 2, '水果捞');
INSERT INTO `store` VALUES (10, 3, '印象汇');
INSERT INTO `store` VALUES (11, 3, '东区1栋旁边那疙瘩');
INSERT INTO `store` VALUES (12, 4, '自己手写地址');
INSERT INTO `store` VALUES (13, 5, '自己手写地址');
INSERT INTO `store` VALUES (14, 6, '自己手写地址');

-- ----------------------------
-- Table structure for take_order
-- ----------------------------
DROP TABLE IF EXISTS `take_order`;
CREATE TABLE `take_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `uid` bigint(20) NULL DEFAULT NULL COMMENT 'user表id，下单用户',
  `receive_uid` bigint(20) NULL DEFAULT NULL COMMENT 'user表id，接单用户',
  `thing_id` bigint(20) NULL DEFAULT NULL COMMENT 'thing表id',
  `amount` int(20) NULL DEFAULT NULL COMMENT '物品总数量',
  `upstairs` bit(1) NULL DEFAULT NULL COMMENT '是否送上楼，true-是',
  `weight` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '快递重量',
  `ps` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `pick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取货地址的人名',
  `pick_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取货地址的手机号',
  `pick_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取货地址',
  `receive_id` bigint(20) NULL DEFAULT NULL COMMENT 'receive_address表id',
  `receive_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '订单过期时间，到点取消订单',
  `money` decimal(64, 2) NULL DEFAULT NULL COMMENT '订单总额',
  `pay_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式',
  `receive_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '下单时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '接单时间',
  `status` int(10) NULL DEFAULT NULL COMMENT '订单状态，0-待接单，1-已接单，配送中，2-已完成，3-已取消',
  `complete_time` datetime NULL DEFAULT NULL COMMENT '完成订单时间',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '取消订单时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of take_order
-- ----------------------------
INSERT INTO `take_order` VALUES (1, '1515198462017228800', 1, 1, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-17 13:20:39', 0.01, '钱包', 'Y52N', '2022-04-16 13:20:39', '2022-04-18 09:44:38', 2, '2022-04-18 18:52:37', NULL);
INSERT INTO `take_order` VALUES (3, '1515608538712424448', 2, NULL, 2, 3, b'1', '1kg以内', '最近喉咙不舒服，西瓜汁去冰。', 'Me', '15182814906', '一品豆花、茶念念', 2, '东区11教学楼C408', '2022-04-18 16:54:09', 0.01, '钱包', 'PX2B', '2022-04-17 16:30:09', NULL, 3, NULL, '2022-04-18 22:07:05');
INSERT INTO `take_order` VALUES (4, '1515926319630770176', 2, NULL, 1, 2, b'0', '1kg以内', '快点', 'Me', '15182814906', '顺丰快递,京东', 2, '东区2栋宿舍楼C408', '2022-04-19 13:56:54', 1.00, '钱包', 'MGVF', '2022-04-18 13:32:54', NULL, 3, NULL, '2022-04-18 22:06:10');
INSERT INTO `take_order` VALUES (5, '1516057469007273984', 1, 2, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-19 22:14:02', 0.01, '钱包', '8CBR', '2022-04-18 22:14:02', '2022-04-18 22:17:08', 1, NULL, NULL);
INSERT INTO `take_order` VALUES (6, '1516057490620522496', 1, 2, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-19 22:14:07', 0.01, '钱包', '336S', '2022-04-18 22:14:07', '2022-04-18 22:16:50', 1, NULL, NULL);
INSERT INTO `take_order` VALUES (7, '1516059938382782464', 1, 2, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-19 22:23:51', 0.01, '钱包', 'PNT9', '2022-04-18 22:23:51', '2022-04-18 22:25:38', 1, NULL, NULL);
INSERT INTO `take_order` VALUES (8, '1516059947018854400', 1, 2, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-19 22:23:53', 0.01, '钱包', 'EF0X', '2022-04-18 22:23:53', '2022-04-18 22:29:29', 1, NULL, NULL);
INSERT INTO `take_order` VALUES (9, '1516059954144976896', 1, 2, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-19 22:23:55', 0.01, '钱包', 'P2M4', '2022-04-18 22:23:55', '2022-04-18 22:32:52', 1, NULL, NULL);
INSERT INTO `take_order` VALUES (10, '1516063386343940096', 2, NULL, 1, 1, b'1', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-19 23:01:33', 1.00, '钱包', 'KKAT', '2022-04-18 22:37:33', NULL, 3, NULL, '2022-04-18 23:16:58');
INSERT INTO `take_order` VALUES (11, '1516069356272984064', 1, 2, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-19 23:01:16', 0.01, '钱包', 'SFYA', '2022-04-18 23:01:16', '2022-04-19 16:08:04', 2, '2022-04-19 22:50:36', NULL);
INSERT INTO `take_order` VALUES (12, '1516074641704095744', 2, 1, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-19 23:46:17', 0.01, '钱包', 'HG7J', '2022-04-18 23:22:17', '2022-04-18 23:39:18', 2, '2022-04-19 23:00:14', NULL);
INSERT INTO `take_order` VALUES (13, '1516079075997782016', 2, NULL, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-20 00:03:54', 0.01, '钱包', 'XVQJ', '2022-04-18 23:39:54', NULL, 3, NULL, '2022-04-19 00:00:14');
INSERT INTO `take_order` VALUES (14, '1516084833003106304', 2, NULL, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-20 00:26:46', 0.02, '钱包', '5DWJ', '2022-04-19 00:02:46', NULL, 3, NULL, '2022-04-19 00:03:08');
INSERT INTO `take_order` VALUES (15, '1516085340979458048', 2, NULL, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-20 00:28:47', 0.01, '钱包', '7KX6', '2022-04-19 00:04:47', NULL, 3, NULL, '2022-04-19 00:05:08');
INSERT INTO `take_order` VALUES (16, '1516085585570295808', 2, NULL, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-20 00:29:46', 0.01, '钱包', '94YY', '2022-04-19 00:05:46', NULL, 3, NULL, '2022-04-19 00:06:03');
INSERT INTO `take_order` VALUES (18, '1516357447185358848', 2, NULL, 1, 1, b'1', '1kg以内', '', 'Me', '15182814906', '顺丰快递', 2, '东区2栋宿舍楼C408', '2022-04-20 18:30:03', 0.01, '钱包', '09YQ', '2022-04-19 18:06:03', NULL, 3, NULL, '2022-04-19 21:03:45');
INSERT INTO `take_order` VALUES (19, '1516357580392259584', 2, NULL, 2, 1, b'1', '1kg以内', '', 'Me', '15182814906', '一品豆花', 2, '东区2栋宿舍楼C408', '2022-04-20 18:30:34', 0.02, '钱包', 'P2N8', '2022-04-19 18:06:34', NULL, 3, NULL, '2022-04-19 21:03:34');
INSERT INTO `take_order` VALUES (20, '1516357720251326464', 2, NULL, 1, 2, b'1', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店,顺丰快递', 2, '东区2栋宿舍楼C408', '2022-04-20 18:31:08', 0.04, '钱包', 'BNCW', '2022-04-19 18:07:08', NULL, 3, NULL, '2022-04-19 20:27:56');
INSERT INTO `take_order` VALUES (21, '1516434745108471808', 2, NULL, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-20 23:37:12', 0.02, '钱包', 'SRJ5', '2022-04-19 23:13:12', NULL, 3, NULL, '2022-04-20 00:12:59');
INSERT INTO `take_order` VALUES (22, '1516450061855236096', 2, NULL, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-21 00:38:04', 0.01, '钱包', '9FPW', '2022-04-20 00:14:04', NULL, 3, NULL, '2022-04-20 00:14:17');
INSERT INTO `take_order` VALUES (23, '1516450453460623360', 2, NULL, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-21 00:39:37', 0.01, '钱包', 'AWDC', '2022-04-20 00:15:37', NULL, 3, NULL, '2022-04-20 00:15:46');
INSERT INTO `take_order` VALUES (24, '1516451098217422848', 2, NULL, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-21 00:42:11', 0.01, '钱包', 'P3RY', '2022-04-20 00:18:11', NULL, 3, NULL, '2022-04-20 00:18:21');
INSERT INTO `take_order` VALUES (25, '1516451458663325696', 2, NULL, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-21 00:43:37', 0.01, '钱包', 'EMFS', '2022-04-20 00:19:37', NULL, 3, NULL, '2022-04-20 00:20:09');
INSERT INTO `take_order` VALUES (26, '1516452499253370880', 2, 1, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '菜鸟驿站15号店', 2, '东区2栋宿舍楼C408', '2022-04-21 00:47:45', 0.01, '钱包', 'YF0V', '2022-04-20 00:23:45', '2022-04-20 23:52:22', 1, NULL, NULL);
INSERT INTO `take_order` VALUES (27, '1516604930557747200', 2, 1, 1, 1, b'0', '1kg以内', '', 'Me', '15182814906', '顺丰快递', 2, '东区2栋宿舍楼C408', '2022-04-21 10:53:27', 1.00, '钱包', 'M6RY', '2022-04-20 10:29:27', '2022-04-20 22:07:26', 1, NULL, NULL);
INSERT INTO `take_order` VALUES (28, '1516610476081946624', 2, NULL, 2, 2, b'1', '1kg以内', '感冒了，去冰。', 'Me', '15182814906', '一品豆花,茶念念', 2, '东区2栋宿舍楼C408', '2022-04-21 11:15:29', 2.00, '钱包', '8Q3G', '2022-04-20 10:51:29', NULL, 3, NULL, '2022-04-20 22:02:12');
INSERT INTO `take_order` VALUES (29, '1516733745825062912', 1, 2, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 19:01:19', 0.01, '钱包', 'BVRB', '2022-04-20 19:01:19', '2022-04-20 19:41:05', 2, '2022-04-20 19:43:12', NULL);
INSERT INTO `take_order` VALUES (30, '1516733753613885440', 1, 2, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 19:01:21', 0.01, '钱包', 'K35E', '2022-04-20 19:01:21', '2022-04-20 19:59:11', 2, '2022-04-20 22:50:10', NULL);
INSERT INTO `take_order` VALUES (31, '1516733758634467328', 1, 2, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 19:01:22', 0.01, '钱包', 'B5MJ', '2022-04-20 19:01:22', '2022-04-20 22:37:33', 1, NULL, NULL);
INSERT INTO `take_order` VALUES (32, '1516733762493227008', 1, NULL, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 19:01:23', 0.01, '钱包', 'MQR2', '2022-04-20 19:01:23', NULL, 3, NULL, '2022-04-21 15:31:58');
INSERT INTO `take_order` VALUES (33, '1516733906626289664', 1, NULL, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 19:01:58', 0.01, '钱包', 'PGJQ', '2022-04-20 19:01:58', NULL, 0, NULL, NULL);
INSERT INTO `take_order` VALUES (34, '1516733910468272128', 1, NULL, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 19:01:58', 0.01, '钱包', 'XMBR', '2022-04-20 19:01:58', NULL, 0, NULL, NULL);
INSERT INTO `take_order` VALUES (35, '1516733913978904576', 1, NULL, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 19:01:59', 0.01, '钱包', '8AD8', '2022-04-20 19:01:59', NULL, 0, NULL, NULL);
INSERT INTO `take_order` VALUES (36, '1516733917304987648', 1, NULL, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 19:02:00', 0.01, '钱包', '83FS', '2022-04-20 19:02:00', NULL, 0, NULL, NULL);
INSERT INTO `take_order` VALUES (37, '1516749859086974976', 1, NULL, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 20:05:21', 0.01, '钱包', '3898', '2022-04-20 20:05:21', NULL, 0, NULL, NULL);
INSERT INTO `take_order` VALUES (38, '1516749867727241216', 1, NULL, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 20:05:23', 0.01, '钱包', '2761', '2022-04-20 20:05:23', NULL, 0, NULL, NULL);
INSERT INTO `take_order` VALUES (39, '1516749872299032576', 1, NULL, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 20:05:24', 0.01, '钱包', '2465', '2022-04-20 20:05:24', NULL, 0, NULL, NULL);
INSERT INTO `take_order` VALUES (40, '1516749876153597952', 1, NULL, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 20:05:25', 0.01, '钱包', '7008', '2022-04-20 20:05:25', NULL, 0, NULL, NULL);
INSERT INTO `take_order` VALUES (41, '1516749879878139904', 1, NULL, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 20:05:26', 0.01, '钱包', '4039', '2022-04-20 20:05:26', NULL, 3, NULL, '2022-04-21 00:01:53');
INSERT INTO `take_order` VALUES (42, '1516749883481047040', 1, NULL, 1, 4, b'1', '1kg以内', '快递贵重，请注意轻拿轻放', '禹王穆', '17379525931', '菜鸟驿站15号店,顺丰快递', 1, '东区2栋宿舍楼D209', '2022-04-21 20:05:27', 0.01, '钱包', '1644', '2022-04-20 20:05:27', NULL, 0, NULL, NULL);
INSERT INTO `take_order` VALUES (43, '1516788068873433088', 2, NULL, 1, 3, b'1', '1~5kg以内', '备注', 'Me1', '15182814906', '菜鸟驿站15号店,顺丰快递', 3, '东区11教学楼C408', '2022-04-21 23:01:11', 2.00, '钱包', '2539', '2022-04-20 22:37:11', NULL, 3, NULL, '2022-04-20 22:50:42');
INSERT INTO `take_order` VALUES (46, '1516806672851431424', 1, NULL, 6, 3, b'0', '1kg以内', '谢谢啦😜', 'R', '17379525931', '老食堂2楼', 1, '东区2栋宿舍楼D209', '2022-04-22 00:15:06', 3.50, '钱包', '0553', '2022-04-20 23:51:06', NULL, 0, NULL, NULL);
INSERT INTO `take_order` VALUES (47, '1517417579591880704', 2, NULL, 1, 1, b'1', '1kg以内', '贵重物品', 'Me', '15182814906', '菜鸟驿站15号店', 8, '东区1栋宿舍楼B701', '2022-04-23 16:42:38', 1.00, '钱包', '1616', '2022-04-22 16:18:38', NULL, 0, NULL, NULL);

-- ----------------------------
-- Table structure for take_order_thing
-- ----------------------------
DROP TABLE IF EXISTS `take_order_thing`;
CREATE TABLE `take_order_thing`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `take_order_id` bigint(20) NULL DEFAULT NULL COMMENT 'take_order表id',
  `store_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of take_order_thing
-- ----------------------------
INSERT INTO `take_order_thing` VALUES (1, 1, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (2, 1, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (3, 2, '菜鸟驿站15号店', '8-1234');
INSERT INTO `take_order_thing` VALUES (4, 3, '一品豆花', '椰果豆花|芒果豆花');
INSERT INTO `take_order_thing` VALUES (5, 3, '茶念念', '西瓜汁');
INSERT INTO `take_order_thing` VALUES (6, 4, '顺丰快递', '1-1212');
INSERT INTO `take_order_thing` VALUES (7, 4, '京东', '9-1212');
INSERT INTO `take_order_thing` VALUES (8, 5, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (9, 5, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (10, 6, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (11, 6, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (12, 7, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (13, 7, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (14, 8, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (15, 8, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (16, 9, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (17, 9, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (18, 10, '菜鸟驿站15号店', '8-1234');
INSERT INTO `take_order_thing` VALUES (19, 11, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (20, 11, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (21, 12, '菜鸟驿站15号店', '8-13');
INSERT INTO `take_order_thing` VALUES (22, 13, '菜鸟驿站15号店', '1-2312');
INSERT INTO `take_order_thing` VALUES (23, 14, '菜鸟驿站15号店', '1-1212');
INSERT INTO `take_order_thing` VALUES (24, 15, '菜鸟驿站15号店', '1-1212');
INSERT INTO `take_order_thing` VALUES (25, 16, '菜鸟驿站15号店', '1-121');
INSERT INTO `take_order_thing` VALUES (26, 17, '菜鸟驿站15号店', '9-1231');
INSERT INTO `take_order_thing` VALUES (27, 18, '顺丰快递', '1-2345');
INSERT INTO `take_order_thing` VALUES (28, 19, '一品豆花', '椰果豆花');
INSERT INTO `take_order_thing` VALUES (29, 20, '菜鸟驿站15号店', '9-1234');
INSERT INTO `take_order_thing` VALUES (30, 20, '顺丰快递', '1-2345');
INSERT INTO `take_order_thing` VALUES (31, 21, '菜鸟驿站15号店', '9-1221');
INSERT INTO `take_order_thing` VALUES (32, 22, '菜鸟驿站15号店', '9-1212');
INSERT INTO `take_order_thing` VALUES (33, 23, '菜鸟驿站15号店', '1-1212');
INSERT INTO `take_order_thing` VALUES (34, 24, '菜鸟驿站15号店', '8-1212');
INSERT INTO `take_order_thing` VALUES (35, 25, '菜鸟驿站15号店', '9-1212');
INSERT INTO `take_order_thing` VALUES (36, 26, '菜鸟驿站15号店', '1-1212');
INSERT INTO `take_order_thing` VALUES (37, 27, '顺丰快递', '12121');
INSERT INTO `take_order_thing` VALUES (38, 28, '一品豆花', '椰果豆花');
INSERT INTO `take_order_thing` VALUES (39, 28, '茶念念', '西瓜汁');
INSERT INTO `take_order_thing` VALUES (40, 29, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (41, 29, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (42, 30, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (43, 30, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (44, 31, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (45, 31, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (46, 32, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (47, 32, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (48, 33, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (49, 33, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (50, 34, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (51, 34, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (52, 35, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (53, 35, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (54, 36, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (55, 36, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (56, 37, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (57, 37, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (58, 38, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (59, 38, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (60, 39, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (61, 39, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (62, 40, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (63, 40, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (64, 41, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (65, 41, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (66, 42, '菜鸟驿站15号店', '1-12-2345|2-6-4310');
INSERT INTO `take_order_thing` VALUES (67, 42, '顺丰快递', 'SF1234|SF3456');
INSERT INTO `take_order_thing` VALUES (68, 43, '菜鸟驿站15号店', '1-1212|2-1234');
INSERT INTO `take_order_thing` VALUES (69, 43, '顺丰快递', '3-1242');
INSERT INTO `take_order_thing` VALUES (72, 46, '老食堂2楼', '3两饭|2个菜|一素一荤');
INSERT INTO `take_order_thing` VALUES (73, 47, '菜鸟驿站15号店', '2-1212');

-- ----------------------------
-- Table structure for thing
-- ----------------------------
DROP TABLE IF EXISTS `thing`;
CREATE TABLE `thing`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物品类别',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of thing
-- ----------------------------
INSERT INTO `thing` VALUES (1, '快递');
INSERT INTO `thing` VALUES (2, '美食');
INSERT INTO `thing` VALUES (3, '文件');
INSERT INTO `thing` VALUES (4, '衣物');
INSERT INTO `thing` VALUES (5, '电子');
INSERT INTO `thing` VALUES (6, '其它');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户唯一id',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gender` bit(1) NULL DEFAULT NULL COMMENT '性别/0女',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `balance` decimal(64, 2) NULL DEFAULT NULL COMMENT '钱包余额，单位元',
  `passwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '钱包密码',
  `login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `login_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `auth` int(10) NULL DEFAULT NULL COMMENT '跑腿认证/0未认证/1待认证/2已认证',
  `exist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否存在该用户/0不存在',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'pt260443067068', 'R', b'0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoibRicbS3jM4oibFIzukicw2nBYr6VRznAtocSV2sGMMuwlpqF8zeCCd0KbDZXYcVKq13aOaxtXD7HwA/132', '17379525931', 46.41, '1a445c3e4fa3dc4aa50195ac1e426f41', '192.168.1.2', '2022-04-21 00:01:22', '2022-03-30 17:51:17', 2, '1');
INSERT INTO `user` VALUES (2, 'pt720155230296', 'Me', b'0', 'https://xypt.imgs.space/images/2/dc9ab36783994024a1f9a87bb445c80d.jpeg', '15182814906', 91.82, '14e1b600b1fd579f47433b88e8d85291', '192.168.1.2', '2022-04-22 13:52:05', '2022-03-31 12:27:32', 2, '1');
INSERT INTO `user` VALUES (3, 'pt234607464684', 'Eric1', b'0', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqdvumwXHh5gWzpHpFpQTLiaHVM4icia4hyrEA8zEc62luv6jIHL2TSu9Uicscic8DSkhGx5Z2td1dmGJQ/132', NULL, 0.00, '14e1b600b1fd579f47433b88e8d85291', '192.168.1.2', '2022-04-21 00:10:16', '2022-04-11 17:13:19', 0, '1');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `uid` bigint(20) NULL DEFAULT NULL COMMENT 'user表id',
  `rid` bigint(20) NULL DEFAULT NULL COMMENT 'role表id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 2);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (3, 2);

SET FOREIGN_KEY_CHECKS = 1;
