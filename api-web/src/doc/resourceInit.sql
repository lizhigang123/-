---root resources--
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000001', '项目管理', '/admin/project', null, '项目管理',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000002', '类目管理', '/admin/category', null, '类目管理',  1, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000003', '接口管理', '/admin/interface', null, '接口管理',  2, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000004', '进度管理', '/admin/progress', null, '进度管理',  3, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000005', '用户管理', '/admin/user', null, '用户管理',  4, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000006', '角色管理', '/admin/role', null, '角色管理',  5, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000007', '资源管理', '/admin/resource/show', null, '资源管理',  6, now(), 'sysytem', now(), 'sysytem', '0');


---项目管理----
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000011', '查看', '/admin/project', '000001', '查看项目详细信息',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000012', '新增', '/admin/project/submit', '000001', '新增项目',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000013', '修改', '/admin/project/submit', '000001', '修改项目',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000014', '分支管理', '/admin/project/sub', '000001', '分支管理',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000015', '属性管理', '/admin/project/properties', '000001', '属性管理',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000016', '参数模板管理', '/admin/project/template', '000001', '参数目管理',  0, now(), 'sysytem', now(), 'sysytem', '0');

--分支管理--
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('00000141', '查看', '/admin/project/sub', '0000014', '分支的查看',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('00000142', '新增', '/admin/project/submit]', '0000014', '分支的新增',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('00000143', '修改', '/admin/project/submit]', '0000014', '分支的修改',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('00000144', '分配开发人员', '/admin/project/updateProjectUsers]', '0000014', '分配开发人员',  0, now(), 'sysytem', now(), 'sysytem', '0');


--属性管理--
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('00000151', '查看', '/admin/project/properties', '0000015', '属性的查看',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('00000152', '新增', '/admin/project/properties/submit]', '0000015', '属性的新增',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('00000153', '修改', '/admin/project/properties/submit]', '0000015', '属性的修改',  0, now(), 'sysytem', now(), 'sysytem', '0');


--参数模板管理--
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('00000164', '查看', '/admin/project/paramtemplate/show', '0000016', '参数模板的查看',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('00000162', '新增', '/admin/project/paramtemplate/submit]', '0000016', '参数模板的新增',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('00000163', '修改', '/admin/project/paramtemplate/submit]', '0000016', '参数模板的修改',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('00000161', '参数管理', '/admin/project/param/list', '0000016', '参数管理',  0, now(), 'sysytem', now(), 'sysytem', '0');


---参数管理----
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000001611', '删除', '/admin/param/del', '00000161', '删除',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000001612', '修改', '/admin/param/edit', '00000161', '修改',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000001613', '查看', '/admin/param/show', '00000161', '查看',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000001614', '移除', '/admin/param/move', '00000161', '移除',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000001615', '查看入参', '/admin/param/inputParams', '00000161', '查看入参',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('000001616', '查看出参', '/admin/param/outputParams', '00000161', '查看出参',  0, now(), 'sysytem', now(), 'sysytem', '0');


---接口管理----
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000031', '查看', '/admin/interface/findOne', '000003', '接口的查看',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000032', '新增', '/admin/interface/submit', '000003', '新增接口',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000033', '修改', '/admin/interface/submit', '000004', '修改接口',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000034', '发布', '/admin/interface/release', '000003', '接口发布',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000035', '作废', '/admin/interface/revoke', '000003', '接口作废',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000036', '查询', '/admin/interface', '000003', '查询',  0, now(), 'sysytem', now(), 'sysytem', '0');



---类目管理----
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000021', '查看', '/admin/category/list', '000002', '类目的查看',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000022', '新增', '/admin/category/submit', '000002', '类目新增',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000023', '修改', '/admin/category/submit', '000002', '类目修改',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000024', '查询', '/admin/category/list', '000002', '查询',  0, now(), 'sysytem', now(), 'sysytem', '0');



---进度管理----
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000041', '查看', '/admin/category/list', '000004', '进度的查看',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000042', '更新进度', '/admin/category/submit', '000004', '更新进度',  0, now(), 'sysytem', now(), 'sysytem', '0');



---角色管理----
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000061', '查看', '/admin/role', '000006', '角色的查看',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000062', '新增', '/admin/role/create', '000006', '角色新增',  0, now(), 'sysytem', now(), 'sysytem', '0');

---角色管理----
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000051', '查看', '/admin/user', '000005', '用户的查看',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000052', '新增', '/admin/user/submit', '000005', '用户新增',  0, now(), 'sysytem', now(), 'sysytem', '0');


---资源管理----
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000071', '查看', '/admin/resource/show', '000007', '资源的查看',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000072', '删除', '/admin/category/delete', '000007', '资源删除',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000073', '移除', '/admin/category/move', '000007', '资源移除',  0, now(), 'sysytem', now(), 'sysytem', '0');
insert into api_t_resource(id, name, url, parent_id, description, ordered, create_date, create_user,  last_update_date, last_update_user, version) values ('0000074', '分配权限', '/admin/category/allocating', '000007', '分配权限',  0, now(), 'sysytem', now(), 'sysytem', '0');


