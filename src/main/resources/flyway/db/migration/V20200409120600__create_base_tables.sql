SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

USE user_security;

CREATE TABLE ROLE (
  CREATE_DT datetime default CURRENT_TIMESTAMP NOT NULL,
  CREATE_FUNCTION_NAME varchar(100) default 'RoleRepository' NOT NULL,
  UPDATE_DT datetime default CURRENT_TIMESTAMP NOT NULL,
  UPDATE_FUNCTION_NAME varchar(100) default 'RoleRepository' NOT NULL,
  ROLE_UID bigint(20) NOT NULL AUTO_INCREMENT,
  ROLE_NAME varchar(30) NOT NULL,
  PRIMARY KEY (ROLE_UID),
  CONSTRAINT UK_ROLE_ROLENAME UNIQUE(ROLE_NAME)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE USER (
  CREATE_DT datetime default CURRENT_TIMESTAMP NOT NULL,
  CREATE_FUNCTION_NAME varchar(100) default 'UserRepository' NOT NULL,
  UPDATE_DT datetime default CURRENT_TIMESTAMP NOT NULL,
  UPDATE_FUNCTION_NAME varchar(100) default 'UserRepository' NOT NULL,
  USER_UID bigint(20) NOT NULL AUTO_INCREMENT,
  ROLE_UID bigint(20) NOT NULL,
  USERNAME varchar(50) NOT NULL,
  PASSWORD varchar(255) NOT NULL,
  ACTIVE char(1) DEFAULT 'Y' NOT NULL,
  PRIMARY KEY (USER_UID),
  CONSTRAINT UK_USER_USERNAME UNIQUE(USERNAME),
  CONSTRAINT FK_USER_ROLE FOREIGN KEY (ROLE_UID) REFERENCES ROLE (ROLE_UID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE USER_INFO (
  CREATE_DT datetime default CURRENT_TIMESTAMP NOT NULL,
  CREATE_FUNCTION_NAME varchar(100) default 'CustomerRepository' NOT NULL,
  UPDATE_DT datetime default CURRENT_TIMESTAMP NOT NULL,
  UPDATE_FUNCTION_NAME varchar(100) default 'CustomerRepository' NOT NULL,
  USER_INFO_UID bigint(20) NOT NULL AUTO_INCREMENT,
  USER_UID bigint(20) NOT NULL,
  NAME varchar(255) NOT NULL,
  LAST_NAME varchar(255)  NOT NULL,
  EMAIL varchar(50) NOT NULL,
  PRIMARY KEY (USER_INFO_UID),
  CONSTRAINT FK_USER_INFO_USER FOREIGN KEY (USER_UID) REFERENCES USER (USER_UID),
  CONSTRAINT UK_USER_INFO_EMAIL UNIQUE(EMAIL)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;