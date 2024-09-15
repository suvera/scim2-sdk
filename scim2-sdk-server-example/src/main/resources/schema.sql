create table country(
  ID int not null AUTO_INCREMENT,
  NAME varchar(100) not null,
  PRIMARY KEY ( ID )
);

CREATE TABLE scim_group (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_at datetime(6) NOT NULL,
  updated_at datetime(6) NOT NULL,
  display_name varchar(255) NOT NULL UNIQUE,
  external_id varchar(255),
  PRIMARY KEY (id)
);

CREATE TABLE scim_user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  active bit(1) NOT NULL,
  created_at datetime(6) NOT NULL,
  updated_at datetime(6) NOT NULL,
  external_id varchar(255),
  user_name varchar(255) NOT NULL UNIQUE,
  formatted varchar(255),
  family_name varchar(255),
  given_name varchar(255),
  middle_name varchar(255),
  honorific_prefix varchar(255),
  honorific_suffix varchar(255),
  PRIMARY KEY (id)
);

CREATE TABLE scim_group_scim_user (
  group_id bigint(20) NOT NULL,
  user_id bigint(20) NOT NULL,
  KEY scim_group_scim_user_fk02 (user_id),
  KEY scim_group_scim_user_fk01 (group_id),
  CONSTRAINT scim_group_scim_user_fk01 FOREIGN KEY (group_id) REFERENCES scim_group (id),
  CONSTRAINT scim_group_scim_user_fk02 FOREIGN KEY (user_id) REFERENCES scim_user (id)
);
