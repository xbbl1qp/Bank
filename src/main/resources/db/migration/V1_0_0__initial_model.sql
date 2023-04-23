CREATE DATABASE IF NOT EXISTS bank;

CREATE TABLE IF NOT EXISTS `account`
(
   `account_id`  int(17),
   `balance`	numeric(15,4),
   `currency`   varchar(3),
    PRIMARY KEY (`account_id`)
);

INSERT INTO account (account_id,balance,currency) values(100001,122222.22,'INR');
INSERT INTO account (account_id,balance,currency) values(100002,109912.22,'INR');
INSERT INTO account (account_id,balance,currency) values(100003,112132.22,'INR');
INSERT INTO account (account_id,balance,currency) values(100004,131323.22,'INR');

