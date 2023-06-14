
# 课程
INSERT INTO cour_course
(id, course_id, name, `type`, author, url, price, detail, create_by, create_time, update_time, update_by)
VALUES(1, '0001', '心理咨询师测试', 1, 'kyle', 'https://ssg-1316267898.cos.ap-beijing.myqcloud.com/preview_20230316155333A003.png', 0.01, '<p>123</p>', NULL, NULL, '2023-03-15 10:58:34', NULL);
INSERT INTO cour_course
(id, course_id, name, `type`, author, url, price, detail, create_by, create_time, update_time, update_by)
VALUES(2, '0002', '咨询师', 1, 'sss', NULL, 0.01, '<p>ssssssssssss</p>', NULL, '2023-03-16 11:17:42', NULL, NULL);

# Banner
INSERT INTO psychology.cour_course_banner_config
(id, banner_url, link_url, banner_type, create_by, create_time)
VALUES(1, '/profile/upload/2023/03/16/a7d74bd87a4bae81d70caea56f79c401_20230316110711A002.jpg', '/pages/curriculum/course?id=0002', 0, NULL, '2023-03-14 17:56:33');
INSERT INTO psychology.cour_course_banner_config
(id, banner_url, link_url, banner_type, create_by, create_time)
VALUES(2, '/profile/upload/2023/03/16/0d8b3c057f5225a4b0c1e4511b648e80_20230316104652A002.jpg', '/pages/curriculum/course?id=0002', 0, NULL, '2023-03-16 10:48:02');
INSERT INTO psychology.cour_course_banner_config
(id, banner_url, link_url, banner_type, create_by, create_time)
VALUES(3, '/profile/upload/2023/03/16/9a06e4283687e4e24170fe98e992d770_20230316104808A003.jpeg', '/pages/curriculum/course?id=0002', 1, NULL, '2023-03-16 10:48:17');
INSERT INTO psychology.cour_course_banner_config
(id, banner_url, link_url, banner_type, create_by, create_time)
VALUES(4, '/profile/upload/2023/03/16/8352034f5042643a2ecae8ef864022e3_20230316104823A004.jpeg', '/pages/curriculum/course?id=0002', 2, NULL, '2023-03-16 10:48:31');
INSERT INTO psychology.cour_course_banner_config
(id, banner_url, link_url, banner_type, create_by, create_time)
VALUES(5, '/profile/upload/2023/03/16/a1ef47275f0a2d8f90d48c8e679e3dc6_20230316104838A005.jpeg', '/pages/curriculum/course?id=0002', 2, NULL, '2023-03-16 10:48:49');
INSERT INTO psychology.cour_course_banner_config
(id, banner_url, link_url, banner_type, create_by, create_time)
VALUES(7, '/profile/upload/2023/03/16/43118b983815779201e533fd552c5b0c_20230316110736A003.jpg', '/pages/curriculum/course?id=0002', 1, NULL, '2023-03-16 11:07:51');


# 标签
INSERT INTO psychology.cour_course_label
(id, course_id, course_label)
VALUES(1, 1, 1);
INSERT INTO psychology.cour_course_label
(id, course_id, course_label)
VALUES(2, 2, 1);


# 课程类型
INSERT INTO psychology.cour_course_type
(type_id, name)
VALUES(1, '智商测试');
INSERT INTO psychology.cour_course_type
(type_id, name)
VALUES(2, '情商测试');

# 课程订单
INSERT INTO psychology.cour_order
(id, order_id, create_time, status, user_id, amount, course_id)
VALUES(1, '00001', '2023-03-15 09:11:02', 0, '32', 111.00, '0001');
INSERT INTO psychology.cour_order
(id, order_id, create_time, status, user_id, amount, course_id)
VALUES(2, '2023031710474531583203836', '2023-03-17 10:47:45', 1, '1', 111.00, '0002');
INSERT INTO psychology.cour_order
(id, order_id, create_time, status, user_id, amount, course_id)
VALUES(3, '2023032018002026058613181', '2023-03-20 18:00:20', 1, '23', 12.36, '111');
INSERT INTO psychology.cour_order
(id, order_id, create_time, status, user_id, amount, course_id)
VALUES(4, '2023032018002038512734739', '2023-03-20 18:00:20', 1, '23', 12.36, '111');
INSERT INTO psychology.cour_order
(id, order_id, create_time, status, user_id, amount, course_id)
VALUES(5, '2023032018024087636005384', '2023-03-20 18:02:41', 1, '23', 12.36, '123131');
INSERT INTO psychology.cour_order
(id, order_id, create_time, status, user_id, amount, course_id)
VALUES(6, '2023032109253879150850124', '2023-03-21 09:25:39', 0, '1', 123.00, '1');
INSERT INTO psychology.cour_order
(id, order_id, create_time, status, user_id, amount, course_id)
VALUES(7, '2023032109573840866548298', '2023-03-21 09:57:38', 0, '1', 123.00, '1');
INSERT INTO psychology.cour_order
(id, order_id, create_time, status, user_id, amount, course_id)
VALUES(8, '2023032109592790808778937', '2023-03-21 09:59:28', 0, '1', 123.00, '1');
INSERT INTO psychology.cour_order
(id, order_id, create_time, status, user_id, amount, course_id)
VALUES(9, '2023032210064952185053920', '2023-03-22 10:06:50', 0, '33', 111.00, '2');

#课程章节
INSERT INTO psychology.cour_section
(id, section_id, topic, duration, `type`, content, content_url, course_id, content_type)
VALUES(1, '00001', '测试章节1', 600, 0, '<p>1234</p>', 'test', '0001', 0);
INSERT INTO psychology.cour_section
(id, section_id, topic, duration, `type`, content, content_url, course_id, content_type)
VALUES(2, '00002', '测试章节2', 600, 0, NULL, '/profile/upload/2023/03/16/test_20230316175555A001.mp4', '0001', 0);
INSERT INTO psychology.cour_section
(id, section_id, topic, duration, `type`, content, content_url, course_id, content_type)
VALUES(3, '00021', '课程二章节一', 600, 0, NULL, 'test', '0002', 1);

#用户-课程-章节
INSERT INTO psychology.cour_user_course_section
(id, user_id, course_id, section_id, end_time, finish_status)
VALUES(1, 1, 1, 1, 600, 1);
INSERT INTO psychology.cour_user_course_section
(id, user_id, course_id, section_id, end_time, finish_status)
VALUES(2, 1, 1, 2, 150, 0);
