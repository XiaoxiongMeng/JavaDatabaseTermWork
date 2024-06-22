# 数据库大作业
### 这是基于JDBC使用Java编写的一个个人信息管理系统：包括记账、记事。

数据库有四张表：bills，posts，user_table，userinfo

这是bills的建表语句：
```sql
CREATE TABLE `bills` (
  `bid` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `title` varchar(12) NOT NULL,
  `content` varchar(100) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```
触发器:
```
create definer = root@localhost trigger after_bill_insert
    after insert
    on bills
    for each row
BEGIN

    UPDATE userinfo
    SET balance = balance - NEW.amount
    WHERE uid = NEW.uid;
END;
```

这是posts的建表语句：
```sql
CREATE TABLE `posts` (
  `tid` int NOT NULL,
  `title` varchar(60) DEFAULT NULL,
  `content` varchar(6000) DEFAULT NULL,
  `uid` int NOT NULL COMMENT '作者的UID',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```
触发器：
```
//1
create definer = root@localhost trigger after_post_insert
    after insert
    on posts
    for each row
BEGIN  

    UPDATE userinfo
    SET title_amount = title_amount + 1
    WHERE uid = NEW.uid;
END;

//2
create definer = root@localhost trigger after_post_delete
    after delete
    on posts
    for each row
BEGIN  
    UPDATE userinfo
    SET title_amount = title_amount - 1
    WHERE uid = OLD.uid;
END;

```


这是user_table的建表语句：
```sql
CREATE TABLE `user_table` (
  `uid` int NOT NULL,
  `username` varchar(12) NOT NULL,
  `password` varchar(400) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```
触发器：
```
create definer = root@localhost trigger after_user_insert
    after insert
    on user_table
    for each row
BEGIN

    INSERT INTO userinfo (uid, username, shows, title_amount, balance) VALUES (NEW.uid, NEW.username, '这个人很懒,什么都没留下.', 0, 0);
END;

```

这是userinfo的建表语句：
```sql
CREATE TABLE `userinfo` (
  `uid` int NOT NULL,
  `username` varchar(12) NOT NULL,
  `shows` varchar(300) DEFAULT NULL COMMENT '个性签名',
  `title_amount` int DEFAULT NULL COMMENT '贴子数',
  `balance` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

存储过程：
```
// 1
create
    definer = root@localhost procedure GetBillCount(IN p_uid int)
BEGIN
    SELECT COUNT(*) FROM bills WHERE uid = p_uid;
END;


// 2
create
    definer = root@localhost procedure GetNewBillId()
BEGIN
    SELECT MAX(bid) FROM bills;
END;


//3
create
    definer = root@localhost procedure GetNewPostId()
BEGIN
    SELECT MAX(tid) FROM posts;
END;


//4
create
    definer = root@localhost procedure GetNewUserId()
BEGIN
    SELECT MAX(uid) FROM user_table;
END;


//5
create
    definer = root@localhost procedure GetUserBalance(IN p_uid int)
BEGIN

    SELECT balance  FROM userinfo WHERE uid = p_uid;

END;


//6
create
    definer = root@localhost procedure GetUserPostAmount(IN p_uid int)
BEGIN

    SELECT title_amount  FROM userinfo WHERE uid = p_uid;

END;
```
