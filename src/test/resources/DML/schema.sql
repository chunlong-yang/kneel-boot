-- start
DROP TABLE IF EXISTS sys_user CASCADE;
DROP TABLE IF EXISTS sys_role CASCADE;
DROP TABLE IF EXISTS sys_user_role CASCADE;
DROP TABLE IF EXISTS sys_privilege CASCADE;
DROP TABLE IF EXISTS sys_role_privilege CASCADE;
DROP TABLE IF EXISTS sys_menus CASCADE;
DROP TABLE IF EXISTS sys_properties CASCADE;
DROP TABLE IF EXISTS sys_codelookup CASCADE; 
DROP TABLE IF EXISTS sys_rpt_reportconfig CASCADE;
DROP TABLE IF EXISTS sys_rpt_columnconfig CASCADE;
DROP TABLE IF EXISTS plm_rpt_columnconfig CASCADE;
DROP TABLE IF EXISTS plm_rpt_reportconfig CASCADE;
DROP TABLE IF EXISTS item CASCADE;


DROP SEQUENCE IF EXISTS  sys_userseq;
DROP SEQUENCE IF EXISTS  sys_roleseq;
DROP SEQUENCE IF EXISTS  sys_user_roleseq;
DROP SEQUENCE IF EXISTS  sys_privilegeseq;
DROP SEQUENCE IF EXISTS  sys_role_privilegeseq;
DROP SEQUENCE IF EXISTS  sys_menusseq;
DROP SEQUENCE IF EXISTS  sys_propertiesseq;
DROP SEQUENCE IF EXISTS  sys_rpt_columnconfigseq;
DROP SEQUENCE IF EXISTS  plm_rpt_columnconfigseq; 


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
  id Integer NOT NULL,
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
  id Integer NOT NULL,
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
      
-- base code      
CREATE TABLE sys_rpt_reportconfig (
  reportid     Integer  NOT NULL,
  reportname   VARCHAR(100) NOT NULL,
  dbobjectname VARCHAR(100) ,
  dbobjecttype VARCHAR(3)   ,
  reporttype   VARCHAR(3)   NOT NULL,
  exctype      VARCHAR(3)   ,
  retroflag    CHAR(1)       DEFAULT 'N' NOT NULL,
  acctactive   CHAR(1)       DEFAULT 'N' NOT NULL
);

-- base code 
CREATE TABLE sys_rpt_columnconfig (
  columnid           Integer   NOT NULL,
  reportid           Integer   NOT NULL,
  columncode         VARCHAR(100)  NOT NULL,
  columndecode       VARCHAR(100)  NOT NULL,
  datatype           VARCHAR(50)   NOT NULL,
  istimezoneneeded   CHAR(1)        DEFAULT 'N' NOT NULL,
  isfilterable       CHAR(1)        DEFAULT 'N' NOT NULL,
  isfilterrequired   CHAR(1)        ,
  filtertype         VARCHAR(10)   ,
  filterservicename  VARCHAR(50)   ,
  islazyloaded       CHAR(1)        DEFAULT 'N' NOT NULL,
  defaultfiltervalue VARCHAR(100)  ,
  ishidden           CHAR(1)        DEFAULT 'N' NOT NULL,
  translatesql       VARCHAR(2000) ,
  orderbyfield       VARCHAR(100)  ,
  statfunc           VARCHAR(100)  
);

-- plm code  
CREATE TABLE plm_rpt_columnconfig (
  columnid           Integer   NOT NULL,
  reportid           Integer   NOT NULL,
  columncode         VARCHAR(100)  NOT NULL,
  columndecode       VARCHAR(100)  NOT NULL,
  datatype           VARCHAR(50)   NOT NULL,
  istimezoneneeded   CHAR(1)        DEFAULT 'N' NOT NULL,
  isfilterable       CHAR(1)        DEFAULT 'N' NOT NULL,
  isfilterrequired   CHAR(1)        ,
  filtertype         VARCHAR(10)   ,
  filterservicename  VARCHAR(50)   ,
  islazyloaded       CHAR(1)        DEFAULT 'N' NOT NULL,
  defaultfiltervalue VARCHAR(100)  ,
  ishidden           CHAR(1)        DEFAULT 'N' NOT NULL,
  translatesql       VARCHAR(2000) ,
  orderbyfield       VARCHAR(100)  ,
  statfunc           VARCHAR(100)  
);

-- plm code
CREATE TABLE plm_rpt_reportconfig (
  reportid     Integer  NOT NULL,
  reportname   VARCHAR(100) NOT NULL,
  dbobjectname VARCHAR(100) ,
  dbobjecttype VARCHAR(3)   ,
  reporttype   VARCHAR(3)   NOT NULL,
  exctype      VARCHAR(3)   ,
  retroflag    CHAR(1)       DEFAULT 'N' NOT NULL,
  acctactive   CHAR(1)       DEFAULT 'N' NOT NULL
);

-- plm code
CREATE TABLE item (
  corr_acc_no    Integer  DEFAULT 0 NOT NULL,
  gin            Integer  DEFAULT 0 NOT NULL,
  accrued_days   Integer   DEFAULT 0 NOT NULL,
  agent_code     VARCHAR(60)  DEFAULT ' ' NOT NULL,
  alias_1        Integer  DEFAULT 0 NOT NULL,
  alias_2        Integer  DEFAULT 0 NOT NULL,
  alias_3        Integer  DEFAULT 0 NOT NULL,
  alias_4        Integer  DEFAULT 0 NOT NULL,
  amount         Integer        DEFAULT 0 NOT NULL,
  amount_10      Integer        DEFAULT 0 NOT NULL,
  amount_11      Integer        DEFAULT 0 NOT NULL,
  amount_12      Integer        DEFAULT 0 NOT NULL,
  amount_13      Integer        DEFAULT 0 NOT NULL,
  amount_14      Integer        DEFAULT 0 NOT NULL,
  amount_15      Integer        DEFAULT 0 NOT NULL,
  amount_16      Integer        DEFAULT 0 NOT NULL,
  amount_17      Integer        DEFAULT 0 NOT NULL,
  amount_18      Integer        DEFAULT 0 NOT NULL,
  amount_19      Integer        DEFAULT 0 NOT NULL,
  amount_2       Integer        DEFAULT 0 NOT NULL,
  amount_20      Integer        DEFAULT 0 NOT NULL,
  amount_21      Integer        DEFAULT 0 NOT NULL,
  amount_22      Integer        DEFAULT 0 NOT NULL,
  amount_23      Integer        DEFAULT 0 NOT NULL,
  amount_24      Integer        DEFAULT 0 NOT NULL,
  amount_25      Integer        DEFAULT 0 NOT NULL,
  amount_26      Integer        DEFAULT 0 NOT NULL,
  amount_27      Integer        DEFAULT 0 NOT NULL,
  amount_3       Integer        DEFAULT 0 NOT NULL,
  amount_4       Integer        DEFAULT 0 NOT NULL,
  amount_5       Integer        DEFAULT 0 NOT NULL,
  amount_6       Integer        DEFAULT 0 NOT NULL,
  amount_7       Integer        DEFAULT 0 NOT NULL,
  amount_8       Integer        DEFAULT 0 NOT NULL,
  amount_9       Integer        DEFAULT 0 NOT NULL,
  bran_code      VARCHAR(8)   DEFAULT ' ' NOT NULL,
  claim_flg      Integer  DEFAULT 0 NOT NULL,
  currency       VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_10    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_11    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_12    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_13    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_14    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_15    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_16    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_17    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_18    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_19    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_2     VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_20    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_21    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_22    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_23    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_24    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_25    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_26    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_27    VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_3     VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_5     VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_6     VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_7     VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_8     VARCHAR(3)   DEFAULT ' ' NOT NULL,
  currency_9     VARCHAR(3)   DEFAULT ' ' NOT NULL,
  date_10        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_11        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_12        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_13        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_14        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_15        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_16        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_17        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_18        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_19        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_20        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_21        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_22        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_23        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_24        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_25        DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_3         DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_4         DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_5         DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_6         DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_7         DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_8         DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  date_9         DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  entry_date     DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  audit_id       Integer        DEFAULT 0 NOT NULL,
  flag_1         Integer   DEFAULT 0 NOT NULL,
  flag_10        Integer  DEFAULT 0 NOT NULL,
  flag_11        Integer  DEFAULT 0 NOT NULL,
  flag_12        Integer  DEFAULT 0 NOT NULL,
  flag_13        Integer  DEFAULT 0 NOT NULL,
  flag_14        Integer  DEFAULT 0 NOT NULL,
  flag_15        Integer  DEFAULT 0 NOT NULL,
  flag_16        Integer  DEFAULT 0 NOT NULL,
  flag_17        Integer  DEFAULT 0 NOT NULL,
  flag_18        Integer  DEFAULT 0 NOT NULL,
  flag_19        Integer  DEFAULT 0 NOT NULL,
  flag_2         Integer  DEFAULT 0 NOT NULL,
  flag_20        Integer  DEFAULT 0 NOT NULL,
  flag_21        Integer  DEFAULT 0 NOT NULL,
  flag_22        Integer  DEFAULT 0 NOT NULL,
  flag_23        Integer  DEFAULT 0 NOT NULL,
  flag_24        Integer  DEFAULT 0 NOT NULL,
  flag_25        Integer  DEFAULT 0 NOT NULL,
  flag_26        Integer  DEFAULT 0 NOT NULL,
  flag_27        Integer  DEFAULT 0 NOT NULL,
  flag_28        Integer  DEFAULT 0 NOT NULL,
  flag_29        Integer  DEFAULT 0 NOT NULL,
  flag_3         Integer  DEFAULT 0 NOT NULL,
  flag_30        Integer  DEFAULT 0 NOT NULL,
  flag_31        Integer  DEFAULT 0 NOT NULL,
  flag_32        Integer  DEFAULT 0 NOT NULL,
  flag_33        Integer  DEFAULT 0 NOT NULL,
  flag_34        Integer  DEFAULT 0 NOT NULL,
  flag_35        Integer  DEFAULT 0 NOT NULL,
  flag_36        Integer  DEFAULT 0 NOT NULL,
  flag_37        Integer  DEFAULT 0 NOT NULL,
  flag_38        Integer  DEFAULT 0 NOT NULL,
  flag_39        Integer  DEFAULT 0 NOT NULL,
  flag_4         Integer  DEFAULT 0 NOT NULL,
  flag_40        Integer  DEFAULT 0 NOT NULL,
  flag_41        Integer  DEFAULT 0 NOT NULL,
  flag_42        Integer  DEFAULT 0 NOT NULL,
  flag_43        Integer  DEFAULT 0 NOT NULL,
  flag_44        Integer  DEFAULT 0 NOT NULL,
  flag_45        Integer  DEFAULT 0 NOT NULL,
  flag_46        Integer  DEFAULT 0 NOT NULL,
  flag_47        Integer  DEFAULT 0 NOT NULL,
  flag_48        Integer  DEFAULT 0 NOT NULL,
  flag_49        Integer  DEFAULT 0 NOT NULL,
  flag_5         Integer  DEFAULT 0 NOT NULL,
  flag_50        Integer  DEFAULT 0 NOT NULL,
  flag_51        Integer  DEFAULT 0 NOT NULL,
  flag_52        Integer  DEFAULT 0 NOT NULL,
  flag_53        Integer  DEFAULT 0 NOT NULL,
  flag_54        Integer  DEFAULT 0 NOT NULL,
  flag_55        Integer  DEFAULT 0 NOT NULL,
  flag_6         Integer  DEFAULT 0 NOT NULL,
  flag_7         Integer  DEFAULT 0 NOT NULL,
  flag_8         Integer  DEFAULT 0 NOT NULL,
  flag_9         Integer  DEFAULT 0 NOT NULL,
  fund_code      VARCHAR(1)   DEFAULT ' ' NOT NULL,
  item_status    Integer  DEFAULT 0 NOT NULL,
  ls_type        Integer  DEFAULT 0 NOT NULL,
  match_no       Integer  DEFAULT 0 NOT NULL,
  note_flg       Integer  DEFAULT 0 NOT NULL,
  query_flg      Integer  DEFAULT 0 NOT NULL,
  reference      VARCHAR(210) DEFAULT ' ' NOT NULL,
  rev_flg        Integer  DEFAULT 0 NOT NULL,
  sec_match_no   Integer  DEFAULT 0 NOT NULL,
  sfield_7       VARCHAR(16)  DEFAULT ' ' NOT NULL,
  sfield_8       VARCHAR(140) DEFAULT ' ' NOT NULL,
  sfield_9       VARCHAR(140) DEFAULT ' ' NOT NULL,
  short_no       VARCHAR(3)   DEFAULT ' ' NOT NULL,
  stmt_date      DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  stmt_item_ref  Integer   DEFAULT 0 NOT NULL,
  stmt_no        Integer  DEFAULT 0 NOT NULL,
  stmt_pg        Integer  DEFAULT 0 NOT NULL,
  stock_id       Integer  DEFAULT 0 NOT NULL,
  stock_type     VARCHAR(4)   DEFAULT ' ' NOT NULL,
  string_1       VARCHAR(16)  DEFAULT ' ' NOT NULL,
  string_10      VARCHAR(180) DEFAULT ' ' NOT NULL,
  string_11      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_12      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_13      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_14      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_15      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_16      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_17      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_18      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_19      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_20      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_21      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_22      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_23      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_24      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_25      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_26      VARCHAR(35)  DEFAULT ' ' NOT NULL,
  string_27      VARCHAR(210) DEFAULT ' ' NOT NULL,
  string_28      VARCHAR(210) DEFAULT ' ' NOT NULL,
  string_29      VARCHAR(255) DEFAULT ' ' NOT NULL,
  string_30      VARCHAR(255) DEFAULT ' ' NOT NULL,
  string_4       VARCHAR(180) DEFAULT ' ' NOT NULL,
  string_5       VARCHAR(16)  DEFAULT ' ' NOT NULL,
  string_6       VARCHAR(180) DEFAULT ' ' NOT NULL,
  string_7       VARCHAR(100) DEFAULT ' ' NOT NULL,
  string_8       VARCHAR(180) DEFAULT ' ' NOT NULL,
  string_9       VARCHAR(180) DEFAULT ' ' NOT NULL,
  sys_entry_date DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  tran_code      VARCHAR(4)   DEFAULT ' ' NOT NULL,
  value_date     DATE          DEFAULT to_date('01/01/1900','DD/MM/YYYY') NOT NULL,
  event_group_id Integer        DEFAULT 0 
);



      
CREATE SEQUENCE  sys_userseq;
CREATE SEQUENCE  sys_roleseq;
CREATE SEQUENCE  sys_user_roleseq;
CREATE SEQUENCE  sys_privilegeseq;
CREATE SEQUENCE  sys_role_privilegeseq;
CREATE SEQUENCE  sys_menusseq;
CREATE SEQUENCE  sys_propertiesseq; 
CREATE SEQUENCE  sys_rpt_columnconfigseq; 
CREATE SEQUENCE  plm_rpt_columnconfigseq;

