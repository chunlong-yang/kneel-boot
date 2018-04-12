-- start
DROP TABLE IF EXISTS sys_user CASCADE;
DROP TABLE IF EXISTS sys_role CASCADE;
DROP TABLE IF EXISTS sys_user_role CASCADE;
DROP TABLE IF EXISTS sys_privilege CASCADE;
DROP TABLE IF EXISTS sys_role_privilege CASCADE;
DROP TABLE IF EXISTS sys_menus CASCADE;
DROP TABLE IF EXISTS sys_properties CASCADE;
DROP TABLE IF EXISTS sys_codelookup CASCADE; 

DROP SEQUENCE IF EXISTS  sys_userseq;
DROP SEQUENCE IF EXISTS  sys_roleseq; 
DROP SEQUENCE IF EXISTS  sys_privilegeseq; 
DROP SEQUENCE IF EXISTS  sys_menusseq;
DROP SEQUENCE IF EXISTS  sys_propertiesseq; 

-- base sys_user
CREATE TABLE sys_user (
  id                 Integer  NOT NULL,
  name               VARCHAR(50)  NOT NULL,  
  password           VARCHAR(25)  NOT NULL,  
  active             CHAR(1)      DEFAULT 'Y'
);

ALTER TABLE sys_user
   ADD CONSTRAINT sys_user_PK Primary Key (
      id);
       

-- base sys_role
CREATE TABLE sys_role (
  id   Integer  NOT NULL, 
  name VARCHAR(255) NOT NULL
);

-- base sys_user_role
CREATE TABLE sys_user_role ( 
  user_id     VARCHAR(25) NOT NULL,
  role_id     Integer NOT NULL
);

-- base privilege
CREATE TABLE sys_privilege (
  id   Integer  NOT NULL, 
  name VARCHAR(255) NOT NULL
);

-- base sys_role_privilege
CREATE TABLE sys_role_privilege ( 
  role_id     VARCHAR(25) NOT NULL,
  privilege_id     Integer NOT NULL
);



-- base sys_menus      
CREATE TABLE sys_menus (
  id         Integer NOT NULL,
  parentid   Integer NOT NULL,
  name       VARCHAR(25) NOT NULL,
  code       VARCHAR(50) NOT NULL,
  path       VARCHAR(50) ,
  icon       VARCHAR(50) ,
  createdate DATE        ,
  active     CHAR(1)      DEFAULT 'Y' NOT NULL,
  comments   VARCHAR(25) 
);     

ALTER TABLE sys_menus
   ADD CONSTRAINT sys_menus_PK Primary Key (
      id);
  
-- base sys_properties
CREATE TABLE sys_properties (
  id    Integer  NOT NULL,
  property      VARCHAR(255) NOT NULL,
  value         VARCHAR(255) NOT NULL,
  env           VARCHAR(5) NOT NULL,
  propcategory  VARCHAR(255) NOT NULL,
  override      VARCHAR(255) DEFAULT 'default',
  priority      Integer DEFAULT 10
);

ALTER TABLE sys_properties
   ADD CONSTRAINT sys_properties_PK Primary Key (
      id);
      
-- base code
CREATE TABLE sys_codelookup (
  codetype VARCHAR(255) NOT NULL,
  "DECODE"   VARCHAR(255) NOT NULL,
  active   CHAR(1)       DEFAULT 'Y' NOT NULL,
  id   Integer  NOT NULL
);

ALTER TABLE sys_codelookup
   ADD CONSTRAINT sys_codelookup_PK Primary Key (
      id); 
CREATE SEQUENCE  sys_userseq;
CREATE SEQUENCE  sys_roleseq; 
CREATE SEQUENCE  sys_privilegeseq; 
CREATE SEQUENCE  sys_menusseq;
CREATE SEQUENCE  sys_propertiesseq; 

