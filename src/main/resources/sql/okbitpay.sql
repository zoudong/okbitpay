/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : okbitpay

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 12/01/2017 00:59:25 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `pay_order`
-- ----------------------------
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '订单编码',
  `amount` decimal(65,8) NOT NULL COMMENT '支付金额',
  `receive_address` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '比特币收款地址',
  `send_address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '比特币发送来源',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `retry_count` bigint(255) DEFAULT NULL COMMENT '重试收款次数',
  `last_retry_time` datetime DEFAULT NULL COMMENT '最后重试时间',
  `pay_status` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '支付状态',
  `pay_description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '支付用途',
  `product_id` bigint(255) DEFAULT NULL COMMENT '产品ID',
  `product_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '产品名称',
  `product_number` bigint(255) DEFAULT NULL COMMENT '产品数量',
  `account_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '账户ID',
  `account_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '账户名称',
  `status` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '订单状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `pay_order`
-- ----------------------------
BEGIN;
INSERT INTO `pay_order` VALUES ('1', '25bdded6-9251-435d-9b05-b8b6158c9d54', '1.00000000', '16VKk2FYYn1nVCtZ16QGSJZ6bq7ZEgBziM', null, null, '0', null, 'pending', null, null, null, null, null, null, 'enable', '2017-12-01 00:57:56', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
