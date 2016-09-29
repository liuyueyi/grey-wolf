## The table Info of this project

### 1. user
> create table, 选择 utf8mb4编码,支持emoj表情

```sql
CREATE TABLE `user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(30) DEFAULT '' COMMENT '用户登录名',
  `nickname` varchar(30) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `password` varchar(50) DEFAULT '' COMMENT '用户登录密码 & 密文根式',
  `address` text COMMENT '用户地址',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `phone` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户手机号',
  `img` varchar(100) DEFAULT '' COMMENT '用户头像',
  `extra` text,
  `isDeleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL,
  PRIMARY KEY (`userId`),
  KEY `idx_username` (`username`),
  KEY `idx_nickname` (`nickname`),
  KEY `idx_email` (`email`),
  KEY `idx_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
```

插入两条数据

```sql
INSERT INTO `user` (`userId`, `username`, `nickname`, `password`, `address`, `email`, `phone`, `img`, `extra`, `isDeleted`, `created`, `updated`)
VALUES
	(1, '大灰狼', '大灰狼', '123456', '中国浙江杭州西湖', 'greywolf@xxx.com', 15971112301, 'https://avatars0.githubusercontent.com/u/5125892?v=3&s=466', NULL, 0, 1474732800, 1474732800),
	(2, '小灰灰', '小灰灰', '123456', '中国浙江杭州西湖😄', 'greywolf@xxx.com', 15971112301, 'https://avatars0.githubusercontent.com/u/5125892?v=3&s=466', NULL, 0, 1474732800, 1474732800);

```