-- start
DROP TABLE IF EXISTS plm_users CASCADE;
DROP TABLE IF EXISTS plm_role CASCADE;
DROP TABLE IF EXISTS plm_user_role CASCADE;
DROP TABLE IF EXISTS plm_menus CASCADE;
DROP TABLE IF EXISTS plm_role_menu CASCADE;
DROP TABLE IF EXISTS plm_properties CASCADE;
DROP TABLE IF EXISTS plm_codelookup CASCADE;

DROP SEQUENCE IF EXISTS plm_roleseq;
DROP SEQUENCE IF EXISTS  plm_user_roleseq;
DROP SEQUENCE IF EXISTS  plm_menusseq;
DROP SEQUENCE IF EXISTS  plm_role_menu;
DROP SEQUENCE IF EXISTS  plm_propertiesseq;

-- base user
CREATE TABLE plm_users (
  userid                 VARCHAR(25)  NOT NULL,
  name               VARCHAR(50)  NOT NULL, 
  email              VARCHAR(100) NOT NULL,
  phone              VARCHAR(25)  ,
  password           VARCHAR(25)  , 
  active             CHAR(1)      DEFAULT 'Y',
  lastlogin          DATE         NOT NULL 
);

ALTER TABLE plm_users
   ADD CONSTRAINT users_PK Primary Key (
      userid);
       

-- base role
CREATE TABLE plm_role (
  roleid   Integer  NOT NULL, 
  name VARCHAR(255) NOT NULL
);

-- base userrole
CREATE TABLE plm_user_role (
  id Integer NOT NULL,
  userid     VARCHAR(25) NOT NULL,
  roleid Integer NOT NULL
);

-- base menu      
CREATE TABLE plm_menus (
  menuid         Integer NOT NULL,
  parentid   Integer NOT NULL,
  name       VARCHAR(25) NOT NULL,
  code       VARCHAR(50) NOT NULL,
  path       VARCHAR(50) ,
  icon       VARCHAR(50) ,
  createdate DATE         ,
  active     CHAR(1)      DEFAULT 'Y' NOT NULL,
  comments   VARCHAR(25) 
);     

ALTER TABLE plm_menus
   ADD CONSTRAINT menus_PK Primary Key (
      menuid);
      
-- base rolemenu
CREATE TABLE plm_role_menu (
  id Integer NOT NULL,
  roleid  Integer NOT NULL,
  menuid  Integer NOT NULL
);      


-- base properties
CREATE TABLE plm_properties (
  propertyid    Integer  NOT NULL,
  property      VARCHAR(255) NOT NULL,
  value         VARCHAR(255) NOT NULL,
  env           VARCHAR(5) NOT NULL,
  propcategory  VARCHAR(255) NOT NULL,
  override      VARCHAR(255) DEFAULT 'default',
  priority      Integer DEFAULT 10
);

ALTER TABLE plm_properties
   ADD CONSTRAINT plm_properties_PK Primary Key (
      propertyid);
      
-- base code
CREATE TABLE plm_codelookup (
  codetype VARCHAR(255) NOT NULL,
  "DECODE"   VARCHAR(255) NOT NULL,
  active   CHAR(1)       DEFAULT 'Y' NOT NULL,
  codeid   Integer  NOT NULL
);

ALTER TABLE plm_codelookup
   ADD CONSTRAINT codelookup_PK Primary Key (
      codeid); 

CREATE SEQUENCE plm_roleseq;
CREATE SEQUENCE plm_user_roleseq;
CREATE SEQUENCE plm_menusseq;
CREATE SEQUENCE plm_role_menu;
CREATE SEQUENCE plm_propertiesseq;

