-- ----------------------------
-- Records of api_t_resource
-- ----------------------------
INSERT INTO `api_t_resource` VALUES ('000001', '项目管理', '', '项目管理', null, '0', '2016-04-15 11:38:02', 'system', '2016-05-05 13:39:02', 'system', '20');
INSERT INTO `api_t_resource` VALUES ('0000012', '新增和修改', '/admin/project/submit', '新增和修改项目', '000001', '1', '2016-04-15 11:38:02', 'system', '2016-05-13 12:51:46', 'f2ee3723-33b0-4b1e-878a-d2847087b202', '12');
INSERT INTO `api_t_resource` VALUES ('0000014', '分支管理', '', '分支管理', '000001', '3', '2016-04-15 11:38:02', 'system', '2016-05-13 12:51:56', 'f2ee3723-33b0-4b1e-878a-d2847087b202', '10');
INSERT INTO `api_t_resource` VALUES ('00000142', '新增和修改', '/admin/project/submit', '分支的新增和修改', '0000014', '2', '2016-04-15 11:38:02', 'system', '2016-05-04 09:42:59', 'system', '4');
INSERT INTO `api_t_resource` VALUES ('0000015', '属性管理', '', '属性管理', '000001', '4', '2016-04-15 11:38:02', 'system', '2016-05-13 12:51:51', 'f2ee3723-33b0-4b1e-878a-d2847087b202', '10');
INSERT INTO `api_t_resource` VALUES ('00000152', '新增和修改', '/admin/project/properties/submit', '属性的新增和修改', '0000015', '0', '2016-04-15 11:38:02', 'system', '2016-05-04 09:43:38', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('0000016', '参数模板管理', '', '参数目管理', '000001', '5', '2016-04-15 11:38:02', 'system', '2016-05-13 12:51:46', 'f2ee3723-33b0-4b1e-878a-d2847087b202', '12');
INSERT INTO `api_t_resource` VALUES ('00000162', '新增、修改和添加下级', '/admin/project/paramtemplate/submit', '参数模板的新增、修改和添加下级', '0000016', '2', '2016-04-15 11:38:02', 'system', '2016-05-04 16:06:28', 'system', '7');
INSERT INTO `api_t_resource` VALUES ('00000164', '查看', '/admin/project/paramtemplate/show,/admin/project/paramtemplate/list', '参数模板的查看', '0000016', '1', '2016-04-15 11:38:02', 'system', '2016-05-04 14:53:56', 'system', '6');
INSERT INTO `api_t_resource` VALUES ('00000165', '移动', '/admin/param/paramtemplate/move', '移动', '0000016', '3', '2016-04-19 19:27:47', 'system', '2016-05-04 16:50:48', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('000002', '类目管理', '', '类目管理', null, '2', '2016-04-15 11:38:02', 'system', '2017-02-28 09:46:20', 'fa5f4ea4fe', '27');
INSERT INTO `api_t_resource` VALUES ('0000021', '列表查看', '/admin/category,/admin/category/list', '列表查看', '000002', '0', '2016-04-15 11:38:02', 'system', '2016-05-04 16:02:37', 'system', '4');
INSERT INTO `api_t_resource` VALUES ('0000022', '新增和修改', '/admin/category/submit', '类目新增和修改', '000002', '1', '2016-04-15 11:38:02', 'system', '2016-05-04 16:02:37', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('000003', '接口管理', '', '接口管理', null, '1', '2016-04-15 11:38:02', 'system', '2017-02-28 09:46:20', 'fa5f4ea4fe', '23');
INSERT INTO `api_t_resource` VALUES ('0000031', '查看', '/admin/interface/findOne', '接口的查看', '000003', '1', '2016-04-15 11:38:02', 'system', '2016-05-03 17:38:52', 'system', '1');
INSERT INTO `api_t_resource` VALUES ('0000032', '新增', '/admin/interface/submit', '新增接口', '000003', '2', '2016-04-15 11:38:02', 'system', '2016-05-03 17:38:57', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('0000034', '发布', '/admin/interface/release', '接口发布', '000003', '3', '2016-04-15 11:38:02', 'system', '2016-05-03 17:39:01', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('0000035', '作废', '/admin/interface/revoke', '接口作废', '000003', '4', '2016-04-15 11:38:02', 'system', '2016-05-03 17:39:04', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('0000036', '列表查看', '/admin/interface,/admin/interface/list', '列表查看', '000003', '0', '2016-04-15 11:38:02', 'system', '2016-04-25 13:53:41', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('0000037', '参数管理', '', '接口参数管理', '000003', '5', '2016-04-19 13:23:41', 'system', '2016-05-03 17:39:04', 'system', '8');
INSERT INTO `api_t_resource` VALUES ('00000371', '删除', '/admin/param/del', '删除', '0000037', '4', '2016-04-15 11:38:02', 'system', '2016-05-04 16:40:02', 'system', '7');
INSERT INTO `api_t_resource` VALUES ('00000372', '新增、修改和添加下级', '/admin/param/submit', '参数新增、修改和添加下级', '0000037', '1', '2016-04-15 11:38:02', 'system', '2016-05-04 16:06:36', 'system', '12');
INSERT INTO `api_t_resource` VALUES ('00000374', '移动', '/admin/param/move', '移动', '0000037', '3', '2016-04-15 11:38:02', 'system', '2016-05-04 16:40:09', 'system', '10');
INSERT INTO `api_t_resource` VALUES ('000004', '进度管理', '', '进度管理', null, '3', '2016-04-15 11:38:02', 'system', '2016-05-04 14:55:39', 'system', '19');
INSERT INTO `api_t_resource` VALUES ('0000042', '更新进度', '/admin/progress/submit', '更新进度', '000004', '3', '2016-04-15 11:38:02', 'system', '2016-05-03 17:31:10', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('000005', '用户管理', '', '用户管理', null, '4', '2016-04-15 11:38:02', 'system', '2016-05-04 14:55:45', 'system', '18');
INSERT INTO `api_t_resource` VALUES ('0000051', '查看', '/admin/user', '用户的查看', '000005', '1', '2016-04-15 11:38:02', 'system', '2016-05-03 17:42:59', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('0000052', '新增和修改', '/admin/user/submit', '用户新增和修改', '000005', '2', '2016-04-15 11:38:02', 'system', '2016-05-04 09:45:19', 'system', '3');
INSERT INTO `api_t_resource` VALUES ('0000053', '分配角色', '/admin/user/editrole,/admin/user/saveRoleIds', '分配角色', '000005', '4', '2016-04-19 13:40:04', 'system', '2016-05-03 17:43:08', 'system', '5');
INSERT INTO `api_t_resource` VALUES ('000006', '角色管理', '', '角色管理', null, '5', '2016-04-15 11:38:02', 'system', '2016-05-04 14:55:48', 'system', '18');
INSERT INTO `api_t_resource` VALUES ('0000061', '查看和编辑', '/admin/role/edit,/admin/resource/roleResources', '角色查看和编辑', '000006', '1', '2016-04-15 11:38:02', 'system', '2016-05-04 15:26:34', 'system', '6');
INSERT INTO `api_t_resource` VALUES ('0000062', '新增', '/admin/role/create,/admin/resource/listResource', '角色新增', '000006', '2', '2016-04-15 11:38:02', 'system', '2016-05-03 17:31:36', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('0000064', '删除', '/admin/role/delete', '角色删除', '000006', '4', '2016-04-19 14:07:53', 'system', '2016-05-03 17:31:30', 'system', '1');
INSERT INTO `api_t_resource` VALUES ('000007', '资源管理', '', '资源管理', null, '6', '2016-04-15 11:38:02', 'system', '2016-05-04 14:55:48', 'system', '19');
INSERT INTO `api_t_resource` VALUES ('0000071', '列表查看', '/admin/resource,/admin/resource/list,/admin/role/submit', '列表查看', '000007', '0', '2016-04-15 11:38:02', 'system', '2016-04-25 14:06:15', 'system', '6');
INSERT INTO `api_t_resource` VALUES ('0000072', '删除', '/admin/resource/delete', '资源删除', '000007', '3', '2016-04-15 11:38:02', 'system', '2016-04-21 16:11:22', 'system', '4');
INSERT INTO `api_t_resource` VALUES ('0000073', '移动', '/admin/resource/move', '资源移动', '000007', '2', '2016-04-15 11:38:02', 'system', '2016-04-21 16:11:22', 'system', '5');
INSERT INTO `api_t_resource` VALUES ('0178e844-a51b-455d-ad27-13f182f8adcc', '分配开发人员', '/admin/project/updateProjectUsers,/admin/user/match,/admin/project/curentDevelopers', '分配开发人员', '000001', '2', '2016-05-13 12:51:40', 'f2ee3723-33b0-4b1e-878a-d2847087b202', '2016-05-13 12:51:56', 'f2ee3723-33b0-4b1e-878a-d2847087b202', '3');
INSERT INTO `api_t_resource` VALUES ('01f43156-e358-47a0-b317-9879fd78ddb2', '查看进度日志', '/admin/progresslog/list', '查看进度日志', '000004', '4', '2016-04-27 10:45:39', 'system', '2016-05-03 17:31:10', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('1fdf4d84-f4dd-4af2-b6d1-9b1dbc8198d7', '删除', '/admin/param/delet/template', '删除', '0000016', '4', '2016-04-22 11:24:47', 'system', '2016-05-04 14:53:50', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('282f1fb5-f701-46ca-a6a1-6abb69db055b', '列表查看', '/admin/role,/admin/role/list', '列表查看', '000006', '0', '2016-04-20 16:20:07', 'system', '2016-05-03 17:31:39', 'system', '4');
INSERT INTO `api_t_resource` VALUES ('370ff3f6-dcab-42c8-8311-31ee6d1fe512', '新增、修改和添加下级', '/admin/resource/submit', '资源参数新增、修改和添加下级', '000007', '1', '2016-04-20 16:23:07', 'system', '2016-05-04 16:10:14', 'system', '5');
INSERT INTO `api_t_resource` VALUES ('401c6f77-f2ff-46ee-b332-d644d0108084', '参数列表', '/admin/param/show,/admin/param/list', '列表查看', '0000037', '0', '2016-04-29 10:44:41', 'system', '2016-05-04 17:19:13', 'system', '8');
INSERT INTO `api_t_resource` VALUES ('4dac7fca-5a98-465e-99aa-4a903cd4d849', '属性查看', '/admin/project/properties,/admin/project/properties/list', '列表查看', '0000015', '0', '2016-04-29 10:45:53', 'system', '2016-04-29 10:45:53', 'system', '0');
INSERT INTO `api_t_resource` VALUES ('57253d01-7aa1-4fc2-97b2-dfd4ef93250d', '密码修改', '/admin/user/modifypassword', '用户密码修改', '000005', '5', '2016-05-05 11:06:24', 'system', '2016-05-05 11:06:24', 'system', '0');
INSERT INTO `api_t_resource` VALUES ('74d59932-abda-4748-9fea-25d7ec5952a8', '分支查看', '/admin/project/sub,/admin/project/sub/list', '列表查看', '0000014', '0', '2016-04-29 10:40:29', 'system', '2016-04-29 10:40:29', 'system', '0');
INSERT INTO `api_t_resource` VALUES ('7e6192df-708d-49fe-bb2b-578a3cca7038', '列表查看', '/admin/user,/admin/user/list', '列表查看', '000005', '0', '2016-04-20 14:09:25', 'system', '2016-04-25 13:53:16', 'system', '2');
INSERT INTO `api_t_resource` VALUES ('838a63db-7510-4e3f-871f-4de78466b6ed', '列表查看', '/admin/progress,/admin/progress/list', '列表查看', '000004', '0', '2016-04-20 15:51:48', 'system', '2016-05-03 17:30:59', 'system', '5');
INSERT INTO `api_t_resource` VALUES ('a9344871-9372-472c-96e2-bbc84042e88c', '参数模版列表', '/admin/project/template,/admin/project/template/list', '列表查看', '0000016', '0', '2016-04-29 10:46:36', 'system', '2016-05-04 14:53:56', 'system', '3');
INSERT INTO `api_t_resource` VALUES ('cece0a2e-dc71-4d91-b2b6-fffd5e2d8545', '批量插入参数', '/admin/param/match,/admin/param/saveParams', '批量插入参数', '0000037', '4', '2016-05-13 12:50:51', 'f2ee3723-33b0-4b1e-878a-d2847087b202', '2016-05-13 12:50:51', 'f2ee3723-33b0-4b1e-878a-d2847087b202', '0');
INSERT INTO `api_t_resource` VALUES ('d216946d-bf91-4b15-aee9-4add8309cc08', '列表查看', '/admin/project,/admin/project/list', '列表查看', '000001', '0', '2016-04-20 16:18:04', 'system', '2016-05-03 16:57:50', 'system', '12');
INSERT INTO `api_t_resource` VALUES ('e5e1d477-6d40-485f-a60d-c097ed63df11', '删除', '/admin/user/delete', '删除', '000005', '5', '2016-04-21 14:55:41', 'system', '2016-05-03 17:42:59', 'system', '1');
INSERT INTO `api_t_resource` VALUES ('eff64a61-1a30-4741-b4d2-e0bb2c189cc7', '搜索参数', '/admin/param/listall', '搜索参数', '0000037', '4', '2016-05-13 12:51:10', 'f2ee3723-33b0-4b1e-878a-d2847087b202', '2016-05-13 12:51:10', 'f2ee3723-33b0-4b1e-878a-d2847087b202', '0');
INSERT INTO `api_t_resource` VALUES ('f9801924-3f7b-4f6f-9c19-7b7872dfe1ec', '添加模版参数', '/admin/param/submit/templat,/admin/project/template/lists', '添加模版参数', '0000037', '2', '2016-05-04 15:30:47', 'system', '2016-05-04 17:08:55', 'system', '7');


-- ----------------------------
-- Records of api_t_role
-- ----------------------------
INSERT INTO `api_t_role` VALUES ('62ab746a-658b-4859-af71-116959960855', '开发人员', '2016-04-22 17:45:28', 'a7a93596-5d6b-43da-8216-513f9209fee4', '2016-04-22 17:45:28', 'a7a93596-5d6b-43da-8216-513f9209fee4', '0');
INSERT INTO `api_t_role` VALUES ('797083c9-1b86-482b-8979-fff0967f3fce', '管理员', '2016-04-18 10:37:36', 'aef9a6d54a6f', '2016-04-19 20:10:46', 'aef9a6d54a6f', '1');
INSERT INTO `api_t_role` VALUES ('ad5cbea5-c6b8-4758-b6e7-f65e80f9a674', '资源管理员', '2016-04-22 17:35:59', 'a7a93596-5d6b-43da-8216-513f9209fee4', '2016-04-22 17:35:59', 'a7a93596-5d6b-43da-8216-513f9209fee4', '0');
INSERT INTO `api_t_role` VALUES ('df3dd98c-9a41-4427-bf30-0ace220fdeae', '项目负责人', '2016-04-22 17:45:44', 'a7a93596-5d6b-43da-8216-513f9209fee4', '2016-04-22 17:46:40', 'a7a93596-5d6b-43da-8216-513f9209fee4', '1');


-- ----------------------------
-- Records of api_t_role_resource_relation
-- ----------------------------
INSERT INTO `api_t_role_resource_relation` VALUES ('00460bbd-f309-42a6-b9af-9134234709c3', '0000034', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('03524484-5806-475f-a89e-ae29bb592fab', '0000073', 'ad5cbea5-c6b8-4758-b6e7-f65e80f9a674');
INSERT INTO `api_t_role_resource_relation` VALUES ('0394e2a4-9b9b-46f2-a366-2094f55215ef', '01f43156-e358-47a0-b317-9879fd78ddb2', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('06bd8902-6f33-4423-9194-7c04793fbac4', '0178e844-a51b-455d-ad27-13f182f8adcc', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('0c4dab73-7436-4780-9eef-4f2bc0a3e07f', '401c6f77-f2ff-46ee-b332-d644d0108084', '62ab746a-658b-4859-af71-116959960855');
INSERT INTO `api_t_role_resource_relation` VALUES ('105eeec0-025f-476b-9e75-8a847da63044', '838a63db-7510-4e3f-871f-4de78466b6ed', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('10c0cde6-d9e1-4dda-80a5-4b54b86ac52c', '0000062', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('10ffd754-4920-4c73-b504-9b6233e5064a', '0000034', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('11dbf49d-e88c-49e3-bf98-45a7221ec53a', '000002', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('13262916-c4a7-4384-b0a2-76e3192d1c68', '0000031', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('13c3d5b8-c2bc-44b6-bf3a-dccf055fdff2', '4dac7fca-5a98-465e-99aa-4a903cd4d849', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('15b18817-a6d1-4afc-ba43-b7f74648d899', '0000035', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('1600d433-cb8f-404e-bc5b-912df2246d98', '00000164', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('16dbf8d5-c8da-4153-b683-018f8605a59b', 'eff64a61-1a30-4741-b4d2-e0bb2c189cc7', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('18b58a56-36f5-4c2f-825d-e82339fc4179', '00000142', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('1974b283-4550-4ef0-8c40-8b2be3ab49c0', '0000052', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('19765cb4-7cec-4d8f-9d2e-6182e11bb32d', '00000374', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('1b1f74b7-40a5-47aa-8312-981a081bd25b', '00000164', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('1ffcc806-88da-466d-8b47-f47b1aa7b0f1', '0000064', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('21788a1f-0583-4d16-a4c4-865b37a3def7', '00000162', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('23170814-660b-4b51-a945-55427a9ac64d', 'a9344871-9372-472c-96e2-bbc84042e88c', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('2ec11786-7f4b-4656-bef3-7ddac90a6155', '0000073', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('31c35259-60d7-4292-be26-e7b38c232542', '000003', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('324a5da3-b7f5-45bb-9a66-5a7fe56a913c', '0000072', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('32970511-b832-4565-aed1-5243d15ef4ef', '00000164', '62ab746a-658b-4859-af71-116959960855');
INSERT INTO `api_t_role_resource_relation` VALUES ('36891f86-8e02-4483-afb3-d23dbf549117', '0000022', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('3b27df84-d3ff-4360-b0b4-a546c4930e05', '000003', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('3c1e3540-091d-448d-bfea-a7f0072975db', '00000142', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('41669c92-d6be-4e78-93f4-2e62159b096a', '0000061', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('4219e38a-639f-4d45-8bdc-038f0f18d674', '000005', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('429d1bdf-5a2d-453a-b6a9-ca0ac6cae71e', '1fdf4d84-f4dd-4af2-b6d1-9b1dbc8198d7', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('458d9418-2e3c-4ace-98d0-4b441ecb7b4b', 'e5e1d477-6d40-485f-a60d-c097ed63df11', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('461fc9ad-481a-420e-bfc0-7346950b7b5f', '838a63db-7510-4e3f-871f-4de78466b6ed', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('47b4f43d-ebc9-401e-a3f7-424b7fdfbac6', '0000036', '62ab746a-658b-4859-af71-116959960855');
INSERT INTO `api_t_role_resource_relation` VALUES ('47e1ff00-0dfe-4fe0-885f-c781c3d5f231', 'f9801924-3f7b-4f6f-9c19-7b7872dfe1ec', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('47f8adf5-cfa6-465b-a011-84bbc6165a0a', '0000016', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('48373e86-b098-418f-aaa2-6f2dc783e084', '838a63db-7510-4e3f-871f-4de78466b6ed', '62ab746a-658b-4859-af71-116959960855');
INSERT INTO `api_t_role_resource_relation` VALUES ('486b09db-c855-45e5-9988-2bce1e04ad5a', '00000371', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('4ae1e13b-c3bd-431f-b568-925a1f42de2e', '0000021', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('4c8f7876-ccc1-4b8c-a66b-a26d7143285d', '00000162', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('4cabe668-2fa1-49de-af7a-84e8834607e4', 'f9801924-3f7b-4f6f-9c19-7b7872dfe1ec', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('4e876ba8-a78f-422a-a27d-1bed877ebabf', '401c6f77-f2ff-46ee-b332-d644d0108084', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('4fa6c97b-4254-44f1-8552-6850ca88b81e', '0000015', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('537da9f7-174d-40cd-bc2a-5f4beab1b3cd', '00000165', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('54a3623b-7bd0-4477-8310-31e38907eb4e', '1fdf4d84-f4dd-4af2-b6d1-9b1dbc8198d7', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('55430152-26a8-4131-8984-55cb7fe1603b', '0000042', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('55d1e4b4-73b4-4474-a109-4a4c4ea26349', '0000014', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('5b5da89f-cc81-4cfc-b4b0-3f78c73c7464', '0000042', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('619ea6ec-8f63-4e20-b192-535fee24eae6', '000007', 'ad5cbea5-c6b8-4758-b6e7-f65e80f9a674');
INSERT INTO `api_t_role_resource_relation` VALUES ('62320e5a-1bc3-4563-a2f5-6d366ad752db', '282f1fb5-f701-46ca-a6a1-6abb69db055b', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('68b9e03c-a85f-4dda-bce9-a61c0f7f8cf6', '370ff3f6-dcab-42c8-8311-31ee6d1fe512', 'ad5cbea5-c6b8-4758-b6e7-f65e80f9a674');
INSERT INTO `api_t_role_resource_relation` VALUES ('69ab3ba7-bddb-46f2-985d-e569d458e6a2', '0000022', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('71b1de66-3e04-4bfe-9da0-7d1067186c59', '000007', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('71d4b70c-bbc1-40cd-9c13-bfe58f466ac4', 'a9344871-9372-472c-96e2-bbc84042e88c', '62ab746a-658b-4859-af71-116959960855');
INSERT INTO `api_t_role_resource_relation` VALUES ('73292e82-3602-4ac9-bd9f-a675346fa859', '00000152', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('786bb22a-f9ac-448a-acb6-c8d69dd41c30', '74d59932-abda-4748-9fea-25d7ec5952a8', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('7b313bf1-5370-4653-9865-be7d518fef5f', '00000374', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('81970f53-8c7c-4bfd-afd6-13b0a8cbc43c', '000004', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('83fe1433-319b-4a12-94fb-f26cca68e62a', '0000036', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('87d95bff-52e3-4c2b-a636-ec4c3b98b42f', '000001', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('8a47e0c5-65c5-4a25-b756-648293c25833', '0000072', 'ad5cbea5-c6b8-4758-b6e7-f65e80f9a674');
INSERT INTO `api_t_role_resource_relation` VALUES ('8b224c7a-f587-4a73-b416-850d660e7aa8', '0000071', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('8e8004eb-696d-4684-92ad-7f855280621f', '0000051', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('8ee4cf21-9dd4-435b-8358-133c6328cd8c', 'a9344871-9372-472c-96e2-bbc84042e88c', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('8f8efcda-7263-4a9e-8838-87deaec73283', '57253d01-7aa1-4fc2-97b2-dfd4ef93250d', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('9021b87d-d3ab-471f-bfba-59802537afa7', '370ff3f6-dcab-42c8-8311-31ee6d1fe512', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('9035745d-87ee-45b8-9dc6-ababb505171f', '7e6192df-708d-49fe-bb2b-578a3cca7038', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('98268f7b-d6d9-4260-8f92-a3699b31bb7e', '0000021', '62ab746a-658b-4859-af71-116959960855');
INSERT INTO `api_t_role_resource_relation` VALUES ('98274cf8-41ea-4021-a5c0-ce0d2c38372d', '0000032', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('a0af27c3-aea7-4bb5-8956-3e5064165fda', 'd216946d-bf91-4b15-aee9-4add8309cc08', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('a198d493-b7f5-4d96-987c-8edf03fe8493', '0000021', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('a3e48772-78c4-42e2-acd9-9150a6013b28', '0000016', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('a6c6ac27-3854-446c-b465-9613569fb0b2', '0000012', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('aa784e8e-efaf-4c0d-b791-169bc5293f16', '0000037', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('aad6c573-dd83-4058-b816-a3ae7f1ddfb1', 'd216946d-bf91-4b15-aee9-4add8309cc08', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('ad0df9b1-7375-4768-9294-acd5fcd9aeb7', '00000372', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('b0ce0367-4fe2-4836-b1b9-fe588459d314', '0000071', 'ad5cbea5-c6b8-4758-b6e7-f65e80f9a674');
INSERT INTO `api_t_role_resource_relation` VALUES ('b2f01eca-9688-4a11-8170-db284dea4322', '0000037', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('b74ef82b-39e3-4a7d-9e49-f9323e45bd0a', '0000012', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('bbab4e19-a120-4e87-a014-5d3c5cd8c06e', 'd216946d-bf91-4b15-aee9-4add8309cc08', '62ab746a-658b-4859-af71-116959960855');
INSERT INTO `api_t_role_resource_relation` VALUES ('bca1dee6-9819-4b3a-abf5-d53b8a0646c4', '0000032', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('bda3eb37-5cf4-4b65-8a12-591939c72521', '01f43156-e358-47a0-b317-9879fd78ddb2', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('c09e6a5c-4078-4dde-bcf6-a75b36e36024', '0000015', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('c24b5e07-3d8e-477b-8cfb-6d56984f7092', 'cece0a2e-dc71-4d91-b2b6-fffd5e2d8545', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('c288c20d-ea6a-4ce3-8af9-a16593388389', '0000031', '62ab746a-658b-4859-af71-116959960855');
INSERT INTO `api_t_role_resource_relation` VALUES ('c368cc90-611e-4547-a3a8-e99e900b9762', '4dac7fca-5a98-465e-99aa-4a903cd4d849', '62ab746a-658b-4859-af71-116959960855');
INSERT INTO `api_t_role_resource_relation` VALUES ('cfe927e8-2470-411a-bd95-c88f5325502b', '0000036', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('d0aa1fe4-af7c-4343-81d0-aef8bd2035a6', '74d59932-abda-4748-9fea-25d7ec5952a8', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('d1e07562-cce1-4139-8e40-d1aae69661a1', '74d59932-abda-4748-9fea-25d7ec5952a8', '62ab746a-658b-4859-af71-116959960855');
INSERT INTO `api_t_role_resource_relation` VALUES ('d2b348b3-4a6f-42c5-b8fa-f9d22ae3818d', '0000014', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('d33a38e6-a6b7-47fb-927e-9546f2a24ed1', '000004', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('d7dab547-ba26-46b0-b523-5f7dfd5ca917', '000002', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('dacc554f-78b1-47d7-b7c4-c1b1d1839f48', '0000031', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('dbd86d3c-9a15-4aab-ad3a-c55deb3e2b7e', '0000035', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('ddcc7810-a7d9-4f3b-8104-2cfa574ceb4e', '000006', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('e206c857-15a2-46ca-89e7-23535a09aa3e', '00000372', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('e298c17d-465d-4160-a65a-e32a1fcf5455', '0000053', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('e97b5178-61f0-4762-bfa0-a88450729845', '00000371', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('ea9f3072-0bd8-4f1e-888e-f3fdcf529412', '00000165', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('ec29699a-e505-419c-905c-297fc64c2cce', '401c6f77-f2ff-46ee-b332-d644d0108084', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');
INSERT INTO `api_t_role_resource_relation` VALUES ('eda8f12c-5ac7-4daf-a42e-073557de1275', '01f43156-e358-47a0-b317-9879fd78ddb2', '62ab746a-658b-4859-af71-116959960855');
INSERT INTO `api_t_role_resource_relation` VALUES ('edcd04c2-7169-4d2d-9020-8992941cf5dc', '4dac7fca-5a98-465e-99aa-4a903cd4d849', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('ee71cb6f-5c8a-49d4-970c-6e80bd89e4b1', '00000152', '797083c9-1b86-482b-8979-fff0967f3fce');
INSERT INTO `api_t_role_resource_relation` VALUES ('f86d48f2-7ba0-4a08-bb78-e12f61f8261c', '000001', 'df3dd98c-9a41-4427-bf30-0ace220fdeae');