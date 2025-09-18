/*
 Navicat Premium Data Transfer

 Source Server         : 172.17.17.137
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 172.17.17.137:3306
 Source Schema         : logger-center

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 18/09/2025 14:24:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ts_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `ts_oper_log`;
CREATE TABLE `ts_oper_log`  (
  `id` bigint(11) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` tinyint(1) NULL DEFAULT 0 COMMENT '业务类型（0其它 1查询 2新增 3修改 4删除 5上传 6下载 7导出 8导入 9强退）',
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` tinyint(1) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回参数',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `cost_time` bigint(20) NULL DEFAULT 0 COMMENT '消耗时间',
  `create_user_id` bigint(11) NULL DEFAULT NULL COMMENT '操作人ID',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '操作时间',
  `dept_id` bigint(11) NULL DEFAULT NULL COMMENT '部门ID',
  `dept_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
