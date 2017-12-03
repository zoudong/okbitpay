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

 Date: 12/03/2017 01:32:52 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `pay_order`
-- ----------------------------
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '比特币收款地址编码',
  `amount` decimal(65,8) NOT NULL COMMENT '支付金额',
  `receive_address` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '比特币收款地址',
  `send_address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '比特币发送来源',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `retry_count` int(8) DEFAULT NULL COMMENT '重试收款次数',
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
  `callback_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '支付成功或失败后异步回调请求地址url',
  `order_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `pay_order`
-- ----------------------------
BEGIN;
INSERT INTO `pay_order` VALUES ('22', 'cebbc5ed-1f71-4805-8a0e-4ada63ced00b', '1.00000000', '1JdoJDdix9AeGWRvieMzTUzzh4AA4bLs1P', null, null, '10', '2017-12-03 00:54:00', 'pending', null, '0', null, '0', null, null, 'disable', '2017-12-03 00:44:00', null, 'http://127.0.0.1:8080', '0000000000'), ('23', 'aa7eec07-efed-4d92-ae6a-b7861475291e', '1.00000000', '12Kst6V1wk99rjtPRybU3w96LKWQ9XgaFv', null, null, '2', '2017-12-03 01:03:00', 'pending', null, '0', null, '0', null, null, 'enable', '2017-12-03 01:01:53', null, 'http://127.0.0.1:8080', '0000000000');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
