/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.89
Source Server Version : 50518
Source Host           : 192.168.0.89:3306
Source Database       : gxb

Target Server Type    : MYSQL
Target Server Version : 50518
File Encoding         : 65001

Date: 2015-11-13 14:35:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_feedback`
-- ----------------------------
DROP TABLE IF EXISTS `sys_feedback`;
CREATE TABLE `sys_feedback` (
  `fb_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '反馈id',
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `fb_time` varchar(20) NOT NULL COMMENT '反馈时间',
  `fb_content` varchar(300) NOT NULL COMMENT '反馈内容',
  PRIMARY KEY (`fb_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_feedback
-- ----------------------------
INSERT INTO `sys_feedback` VALUES ('1', '1', '2015-09-29 14:43:29', '\'测试反馈意见\'');
INSERT INTO `sys_feedback` VALUES ('2', '1', '2015-09-29 15:26:02', '测试反馈意见3');

-- ----------------------------
-- Table structure for `sys_instructions`
-- ----------------------------
DROP TABLE IF EXISTS `sys_instructions`;
CREATE TABLE `sys_instructions` (
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT '关于我们id',
  `ist_nm` varchar(20) NOT NULL COMMENT '名称',
  `remo` longtext NOT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_instructions
-- ----------------------------
INSERT INTO `sys_instructions` VALUES ('1', '关于我们', '<span style=\"color:#333333;font-family:宋体, Arial;font-size:14px;font-style:normal;font-weight:normal;line-height:28px;\">　　<span style=\"color:#999999;font-size:16px;\">2015\r\n年8月22日消息，北京阅兵村训练基地。“9·3”抗战胜利70周年阅兵，是中国第一次组织纪念抗日战争暨世界反法西斯战争胜利专题阅兵。本次阅兵将有\r\n50个方（梯）队受阅，其中两个抗战老同志方队、11个徒步方队、27个装备方队、10个空中梯队，另抽组联合军乐团和合唱团，参阅总兵力超过1万人</span><span style=\"font-size:16px;color:#999999;\">。</span></span>');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id_key` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `menu_nm` varchar(30) NOT NULL COMMENT '菜单名称',
  `menu_cd` varchar(10) NOT NULL COMMENT '菜单编号(必填)(唯一)',
  `menu_url` varchar(30) DEFAULT NULL COMMENT '连接地址',
  `menu_cls` varchar(10) DEFAULT NULL COMMENT '菜单样式',
  `p_id` int(11) NOT NULL COMMENT '父菜单(必填) 0为第一级菜单',
  `menu_tp` tinyint(4) NOT NULL COMMENT '菜单类型(必填)  0--功能菜单  1-功能按钮',
  `menu_seq` tinyint(4) NOT NULL COMMENT '菜单排序',
  `is_use` tinyint(4) NOT NULL COMMENT '是否启用(必填)  0--停用   1--使用',
  `menu_leaf` tinyint(4) NOT NULL COMMENT '是否明细菜单  0--否  1--是',
  `menu_remo` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id_key`),
  UNIQUE KEY `Index_1` (`menu_cd`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', 'ssmg', '', '', '0', '0', '1', '1', '0', 'sdfsdf');
INSERT INTO `sys_menu` VALUES ('2', '角色管理', 'ssjs', 'queryrole', 'dd1', '1', '0', '1', '1', '1', '');
INSERT INTO `sys_menu` VALUES ('3', '菜单管理', 'menu', 'sysMenu', 'dd2', '1', '0', '0', '1', '1', '');
INSERT INTO `sys_menu` VALUES ('7', '商家信息管理', 'qyxxgl', '', '', '0', '0', '2', '1', '1', '');
INSERT INTO `sys_menu` VALUES ('88', '基本设置', 'qyxx', 'toopermerchantqy', 'dd6', '7', '0', '1', '1', '1', '');
INSERT INTO `sys_menu` VALUES ('89', '后台用户管理', 'htyhgl', 'queryuserht', 'dd1', '1', '0', '2', '1', '1', '');
INSERT INTO `sys_menu` VALUES ('92', '关于我们', 'gywm', 'queryinstructions', 'dd7', '1', '0', '19', '1', '1', '');
INSERT INTO `sys_menu` VALUES ('107', '意见反馈', 'feedback', 'queryfeedback', 'dd8', '1', '0', '18', '1', '0', '');
INSERT INTO `sys_menu` VALUES ('108', '版本更新', 'appbbgx', 'queryVersion', 'dd5', '1', '0', '23', '1', '1', '');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id_key` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_nm` varchar(50) NOT NULL COMMENT '角色名称',
  `create_dt` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `remo` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id_key`),
  UNIQUE KEY `is` (`id_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '平台管理员', '2014-04-10 16:50:28', '1', '');
INSERT INTO `sys_role` VALUES ('5', '普通管理员', '2015-11-13', '82', '');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `menu` (`menu_id`),
  CONSTRAINT `sys_role_menu_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id_key`),
  CONSTRAINT `sys_role_menu_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('5', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('5', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('5', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '7');
INSERT INTO `sys_role_menu` VALUES ('1', '88');
INSERT INTO `sys_role_menu` VALUES ('1', '89');
INSERT INTO `sys_role_menu` VALUES ('5', '89');
INSERT INTO `sys_role_menu` VALUES ('1', '92');
INSERT INTO `sys_role_menu` VALUES ('1', '107');
INSERT INTO `sys_role_menu` VALUES ('1', '108');

-- ----------------------------
-- Table structure for `sys_role_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `usr_id` (`user_id`),
  CONSTRAINT `sys_role_user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '82');
INSERT INTO `sys_role_user` VALUES ('5', '88');
INSERT INTO `sys_role_user` VALUES ('5', '92');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id_key` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_no` varchar(100) NOT NULL COMMENT '用户账号',
  `user_nm` varchar(200) NOT NULL COMMENT '用户名称',
  `user_pwd` varchar(50) NOT NULL COMMENT '用户密码',
  `tel` varchar(50) DEFAULT NULL COMMENT '手机号',
  `longitude` varchar(50) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(50) DEFAULT NULL COMMENT '纬度',
  `regtime` datetime DEFAULT NULL COMMENT '注册时间',
  `is_sj` int(11) DEFAULT '1' COMMENT '用户类型（1后台用户；2商家）',
  `sort_id` int(11) DEFAULT '0' COMMENT '自定义排序号',
  PRIMARY KEY (`id_key`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('82', 'admin', '平台管理员', '202cb962ac59075b964b07152d234b70', '15678965569', null, null, '2015-11-02 18:09:27', '1', '0');
INSERT INTO `sys_user` VALUES ('88', 'aa', '小白', '202cb962ac59075b964b07152d234b70', '15980494200', null, null, '2015-11-15 20:56:17', '1', '0');
INSERT INTO `sys_user` VALUES ('92', 'bb', 'bb', 'd41d8cd98f00b204e9800998ecf8427e', '', null, null, '2015-11-13 14:12:40', '1', '0');

-- ----------------------------
-- Table structure for `sys_version`
-- ----------------------------
DROP TABLE IF EXISTS `sys_version`;
CREATE TABLE `sys_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `version_name` varchar(100) NOT NULL COMMENT '版本名称',
  `version_time` varchar(100) NOT NULL COMMENT '版本更新时间',
  `version_user` int(11) NOT NULL COMMENT '版本更新发布人',
  `version_type` char(255) NOT NULL COMMENT '版本系统类型：0:android 1:ios',
  `version_content` text COMMENT '更新信息',
  `version_url` varchar(255) NOT NULL COMMENT '下载地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_version
-- ----------------------------
INSERT INTO `sys_version` VALUES ('21', '3.7', '2015-10-27 11:03:03', '82', '0', '', 'http://www.scbstone.com:8080/web/wapdl');
INSERT INTO `sys_version` VALUES ('22', '3.0', '2015-10-28 16:52:18', '82', '1', '', 'http://www.scbstone.com:8080/web/wapdl');
