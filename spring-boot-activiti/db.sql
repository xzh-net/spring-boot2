/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : shiro_action

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 05/06/2019 21:26:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept`  (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `dept_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `parent_id` int(11) NOT NULL COMMENT '上级部门ID',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept`(`dept_id`, `dept_name`, `parent_id`, `order_num`, `create_time`, `modify_time`) VALUES (1, '开发部', 0, 1, '2019-03-17 16:15:02', '2019-03-31 19:05:00');
INSERT INTO `dept`(`dept_id`, `dept_name`, `parent_id`, `order_num`, `create_time`, `modify_time`) VALUES (3, '运维部', 0, 3, '2019-03-17 16:15:12', '2019-03-31 19:04:50');
INSERT INTO `dept`(`dept_id`, `dept_name`, `parent_id`, `order_num`, `create_time`, `modify_time`) VALUES (4, '开发一组', 1, 4, '2019-03-17 16:15:23', '2019-03-31 18:53:47');
INSERT INTO `dept`(`dept_id`, `dept_name`, `parent_id`, `order_num`, `create_time`, `modify_time`) VALUES (5, '开发二组', 1, 5, '2019-03-17 16:15:27', '2019-03-31 18:53:47');
INSERT INTO `dept`(`dept_id`, `dept_name`, `parent_id`, `order_num`, `create_time`, `modify_time`) VALUES (8, '运维一组', 3, 8, '2019-03-17 16:22:41', '2019-03-31 18:53:47');
INSERT INTO `dept`(`dept_id`, `dept_name`, `parent_id`, `order_num`, `create_time`, `modify_time`) VALUES (9, '运维二组', 3, 9, '2019-03-17 16:22:44', '2019-03-31 18:53:47');
INSERT INTO `dept`(`dept_id`, `dept_name`, `parent_id`, `order_num`, `create_time`, `modify_time`) VALUES (10, '运维三组', 3, 10, '2019-03-17 16:22:47', '2019-03-31 18:53:47');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单 ID',
  `parent_id` int(11) NOT NULL,
  `menu_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单 URL',
  `perms` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识符',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu`(`menu_id`, `parent_id`, `menu_name`, `url`, `perms`, `order_num`, `create_time`, `modify_time`, `icon`) VALUES (1, 0, '权限管理', '#', '#', 0, '2018-12-02 07:51:17', '2019-05-08 20:20:05', 'layui-icon-picker-securityscan');
INSERT INTO `menu`(`menu_id`, `parent_id`, `menu_name`, `url`, `perms`, `order_num`, `create_time`, `modify_time`, `icon`) VALUES (2, 1, '用户管理', '/user/index', 'user:list', 2, '2018-12-23 19:50:25', '2021-03-01 03:59:08', 'layui-icon-username');
INSERT INTO `menu`(`menu_id`, `parent_id`, `menu_name`, `url`, `perms`, `order_num`, `create_time`, `modify_time`, `icon`) VALUES (3, 1, '角色管理', '/role/index', 'role:list', 3, '2018-12-02 07:51:18', '2021-03-01 03:59:07', 'layui-icon-group');
INSERT INTO `menu`(`menu_id`, `parent_id`, `menu_name`, `url`, `perms`, `order_num`, `create_time`, `modify_time`, `icon`) VALUES (4, 1, '菜单权限', '/menu/index', 'menu:list', 4, '2019-02-07 10:57:06', '2021-03-01 03:59:06', 'layui-icon-list');
INSERT INTO `menu`(`menu_id`, `parent_id`, `menu_name`, `url`, `perms`, `order_num`, `create_time`, `modify_time`, `icon`) VALUES (11, 0, '流程管理', '#', '#', 5, '2019-02-04 15:07:41', '2021-03-01 03:55:19', 'layui-icon-picker-control');
INSERT INTO `menu`(`menu_id`, `parent_id`, `menu_name`, `url`, `perms`, `order_num`, `create_time`, `modify_time`, `icon`) VALUES (20, 11, '流程模型', '/workflow/list', 'work:list', 3, '2018-12-23 15:40:21', '2021-03-01 20:53:35', '');
INSERT INTO `menu`(`menu_id`, `parent_id`, `menu_name`, `url`, `perms`, `order_num`, `create_time`, `modify_time`, `icon`) VALUES (27, 1, '操作权限', '/operator/index', 'operator:list', 6, '2019-02-10 17:39:08', '2021-03-01 03:59:04', NULL);
INSERT INTO `menu`(`menu_id`, `parent_id`, `menu_name`, `url`, `perms`, `order_num`, `create_time`, `modify_time`, `icon`) VALUES (28, 1, '部门管理', '/dept/index', 'dept:list', 1, '2019-03-13 20:58:55', '2021-03-01 03:59:08', NULL);
INSERT INTO `menu`(`menu_id`, `parent_id`, `menu_name`, `url`, `perms`, `order_num`, `create_time`, `modify_time`, `icon`) VALUES (29, 11, '部署管理', '/prodeploy/toWorkFlowDeploy', 'prodeploy:list', 7, '2021-03-02 21:45:51', NULL, '');

-- ----------------------------
-- Table structure for operator
-- ----------------------------
DROP TABLE IF EXISTS `operator`;
CREATE TABLE `operator`  (
  `operator_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单 ID',
  `menu_id` int(11) NOT NULL COMMENT '所属菜单 ID',
  `operator_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源 URL',
  `perms` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识符',
  `http_method` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源需要的 HTTP 请求方法',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`operator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operator
-- ----------------------------
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (1, 2, '删除用户', '/user/*', 'user:delete', 'DELETE', '2019-03-03 16:12:27', '2019-03-03 16:12:50');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (2, 2, '添加用户', '/user', 'user:add', 'POST', '2019-02-19 22:21:17', NULL);
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (3, 2, '授予角色', '/user/*/allocation', 'user:allocation', 'POST', '2019-03-03 16:03:37', '2019-03-03 16:06:59');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (4, 2, '重置密码', '/user/*/reset', 'user:reset', 'POST', '2019-03-10 14:51:58', NULL);
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (5, 3, '新增角色', '/role', 'role:add', 'POST', '2019-03-03 16:07:48', NULL);
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (6, 3, '删除角色', '/role/*', 'role:delete', 'DELETE', '2019-03-03 16:08:39', NULL);
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (7, 3, '授予菜单', '/role/*/grant/menu', 'role:grant:menu', 'POST', '2019-03-03 16:09:13', '2019-03-03 16:17:48');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (8, 3, '授予功能', '/role/*/grant/operator', 'role:grant:operator', 'POST', '2019-03-03 16:12:09', '2019-03-03 16:17:48');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (9, 3, '修改角色', '/role', 'role:update', 'PUT', '2019-03-03 16:27:02', '2019-03-31 18:25:14');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (10, 4, '新增菜单', '/menu', 'menu:add', 'POST', '2019-03-03 16:29:22', NULL);
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (11, 4, '修改菜单', '/menu', 'menu:update', 'PUT', '2019-03-03 16:30:59', '2019-03-31 18:24:46');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (12, 4, '删除菜单', '/menu/*', 'menu:delete', 'DELETE', '2019-03-03 16:31:32', '2019-03-31 18:24:55');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (13, 27, '新增操作点', '/operator', 'operator:add', 'POST', '2019-03-03 16:47:35', NULL);
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (14, 27, '删除操作点', '/operator', 'operator:delete', 'DELETE', '2019-03-03 16:47:45', NULL);
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (15, 27, '修改操作点', '/operator', 'operator:update', 'PUT', '2019-03-03 16:48:01', '2019-03-31 18:26:24');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (16, 28, '新增部门', '/dept', 'dept:add', 'POST', '2019-03-31 18:21:23', NULL);
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (17, 28, '删除部门', '/dept/*', 'dept:delete', 'DELETE', '2019-03-31 18:21:38', NULL);
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (18, 2, '修改用户', '/user', 'user:update', 'PUT', '2019-03-31 18:22:33', '2019-03-31 18:24:26');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (19, 28, '修改部门', '/dept', 'dept:update', 'PUT', '2019-03-31 18:24:11', '2019-03-31 18:24:18');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (21, 20, '新增流程', '/workflow', 'work:add', 'POST', '2021-03-01 08:20:33', '2021-03-01 10:20:26');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (22, 20, '编辑流程', '/workflow/update', 'work:update', 'PUT', '2021-03-01 08:31:44', '2021-03-01 10:20:32');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (23, 20, '删除流程', '/workflow/delete', 'work:delete', 'DELETE', '2021-03-01 08:32:24', '2021-03-01 10:20:35');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (24, 20, '发布流程', '/workflow/deploy', 'work:deploy', 'PUT', '2021-03-01 08:32:56', '2021-03-01 23:22:26');
INSERT INTO `operator`(`operator_id`, `menu_id`, `operator_name`, `url`, `perms`, `http_method`, `create_time`, `modify_time`) VALUES (25, 29, '流程查看', '/workdeploy', 'workdeploy:show', 'GET', '2021-03-02 22:12:03', NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role`(`role_id`, `role_name`, `remark`, `create_time`, `modify_time`) VALUES (1, '管理员', '这是一个管理员', '2018-12-02 07:47:40', '2018-12-02 07:47:45');
INSERT INTO `role`(`role_id`, `role_name`, `remark`, `create_time`, `modify_time`) VALUES (2, '普通用户', '这是一个普通用户', '2018-12-02 10:09:08', '2019-06-05 20:50:41');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (1, 1);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (1, 2);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (1, 3);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (1, 4);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (1, 27);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (1, 28);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (1, 11);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (1, 20);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (2, 1);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (2, 2);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (2, 3);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (2, 4);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (2, 27);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (2, 28);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (2, 11);
INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (2, 20);


-- ----------------------------
-- Table structure for role_operator
-- ----------------------------
DROP TABLE IF EXISTS `role_operator`;
CREATE TABLE `role_operator`  (
  `role_id` int(11) NULL DEFAULT NULL,
  `operator_id` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色-操作关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_operator
-- ----------------------------
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 16);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 19);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 2);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 3);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 4);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 18);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 5);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 7);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 8);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 9);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 10);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 11);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 13);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 15);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 22);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 23);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (2, 24);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 16);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 17);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 19);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 1);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 2);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 3);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 4);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 18);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 5);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 6);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 7);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 8);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 9);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 10);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 11);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 12);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 13);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 14);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 15);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 22);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 23);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 24);
INSERT INTO `role_operator`(`role_id`, `operator_id`) VALUES (1, 25);


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` int(1) NULL DEFAULT NULL COMMENT '账号状态: 0: 未激活, 1: 已激活. ',
  `last_login_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后登录时间',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `active_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册激活码',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`(`user_id`, `username`, `password`, `salt`, `email`, `status`, `last_login_time`, `create_time`, `modify_time`, `active_code`, `dept_id`) VALUES (1, 'admin', 'f51703256a38e6bab3d9410a070c32ea', 'salt', 'root@zhaojun.im', 1, '2021-03-02 23:05:53', '2018-12-02 07:30:52', '2021-03-02 23:05:53', NULL, 9);
INSERT INTO `user`(`user_id`, `username`, `password`, `salt`, `email`, `status`, `last_login_time`, `create_time`, `modify_time`, `active_code`, `dept_id`) VALUES (2, 'user', 'e0f68781b7887b2210715b88c96d15d9', '1559739026345', 'user@qq.com', 1, '2021-03-01 08:14:32', '2019-06-05 20:50:26', '2021-03-01 08:14:32', NULL, 1);

-- ----------------------------
-- Table structure for user_auths
-- ----------------------------
DROP TABLE IF EXISTS `user_auths`;
CREATE TABLE `user_auths`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户 ID',
  `identity_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录类型',
  `identifier` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方登录的用户名',
  `credential` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方登录 token',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (2, 2);
INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (1, 2);


SET FOREIGN_KEY_CHECKS = 1;
