use o2o;
create table `tb_person_info`(
	`user_id` int(10) NOT NULL AUTO_INCREMENT,
    `name` varchar(32) DEFAULT NULL,
    `profile_img` varchar(1024) DEFAULT NULL,
    `email` varchar(1024) DEFAULT NULL,
    `gender` varchar(2) DEFAULT NULL,
    `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '0:FOBIDDEN,1:ALLOWED',
    `user_type` int(2) NOT NULL DEFAULT '1' COMMENT '1:CUSTOMER,2:SHOPPER,3:SUPERVISOR',
    `create_time` datetime DEFAULT NULL,
    `last_deit_time` datetime DEFAULT NULL,
    primary key(`user_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8