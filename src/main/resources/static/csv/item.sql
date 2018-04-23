DECLARE
v_setid NUMBER:= -100;
v_stmtdate DATE := To_Date('10/31/2017','MM/dd/yyyy');
v_stockid NUMBER := 571272; --  571273   571274  571275
v_gin NUMBER:= 0;
v_string_15 VARCHAR2(2):= 'OT'; -- OT/PT pending Trade,  DA/DR DividendReclaim FX FX, PS Position
v_cashFlag BOOLEAN:=false; -- Cash and Position can't be exists in same item.
v_daFlag BOOLEAN:=false;
v_fxFlag BOOLEAN:=false;
v_psFlag BOOLEAN:=false;
BEGIN

DELETE FROM tlmdba.item WHERE corr_acc_no = v_setid AND stmt_date =v_stmtdate;
SELECT Nvl(Max(gin),1) INTO v_gin FROM tlmdba.item WHERE corr_acc_no = v_setid ;

INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+1,    180,       1,v_stockid + 0  ,v_stmtdate,v_string_15,  'IBM1',' ' ,v_stmtdate-3,v_stmtdate-4,  123.456,     'USD',   102.36,      'USD',    203.25,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+2,    190,       1,v_stockid + 0  ,v_stmtdate,v_string_15,  'IBM1','ext IBM desc1' ,v_stmtdate-2,v_stmtdate-1,  456.123,     'USD',   36.102,      'USD',    -25.203,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+3,    180,       2,v_stockid + 1  ,v_stmtdate,v_string_15,  'APL',' ' ,v_stmtdate-3,v_stmtdate-4,  223.456,     'USD',   502.36,      'USD',    -203.25,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+4,    190,       2,v_stockid + 1  ,v_stmtdate,v_string_15,    ' ','ext IBM desc1' ,v_stmtdate-2,v_stmtdate-1,  256.123,     'USD',   46.102,      'USD',  125.203,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+5,    180,       3,v_stockid + 2  ,v_stmtdate,v_string_15,  'DELL',' ' ,v_stmtdate-3,v_stmtdate-4,  323.456,     'USD',   502.36,      'USD',  0,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+6,    190,       3,v_stockid + 2  ,v_stmtdate,v_string_15,  'DELL','extIBM desc1' ,v_stmtdate-2,v_stmtdate-1,  356.123,     'USD',   26.102,      'USD',  -225.203,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+7,    180,       6,v_stockid + 5  ,v_stmtdate,v_string_15,  'APL',' ' ,v_stmtdate-3,v_stmtdate-4,  223.456,     'USD',   502.36,      'USD',    0,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+8,    190,       6,v_stockid + 5  ,v_stmtdate,v_string_15,    ' ','extIBM desc1' ,v_stmtdate-2,v_stmtdate-1,  256.123,     'USD',   -46.102,      'USD',  125.203,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+9,    180,       7,v_stockid + 6  ,v_stmtdate,v_string_15,  'DELL','int IBM desc1' ,v_stmtdate-3,v_stmtdate-4,  323.456,     'USD',   502.36,      'USD',  0,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+10,    190,       7,v_stockid + 6  ,v_stmtdate,v_string_15,  'DELL','ext IBM desc1' ,v_stmtdate-2,v_stmtdate-1,  356.123,     'USD',   -26.102,      'USD',  0,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+11,    180,       8,v_stockid + 7  ,v_stmtdate,v_string_15,  'DELL','int IBM desc1' ,v_stmtdate-3,v_stmtdate-4,  323.456,     'USD',   -502.36,      'USD',  0,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+12,    190,       8,v_stockid + 7  ,v_stmtdate,v_string_15,  'DELL','ext IBM desc1' ,v_stmtdate-2,v_stmtdate-1,  356.123,     'USD',   26.102,      'USD',  0,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+13,    180,       9,v_stockid + 8  ,v_stmtdate,v_string_15,  'DELL','int IBM desc1' ,v_stmtdate-3,v_stmtdate-4,  323.456,     'USD',   0,      'USD',  0,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+14,    190,       9,v_stockid + 8  ,v_stmtdate,v_string_15,  'DELL','ext IBM desc1' ,v_stmtdate-2,v_stmtdate-1,  356.123,     'USD',   26.102,      'USD',  0,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+15,    180,       10,v_stockid + 9  ,v_stmtdate,v_string_15,  'DELL','int IBM desc1' ,v_stmtdate-3,v_stmtdate-4,  323.456,     'USD',   0,      'USD',  0,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+16,    190,       10,v_stockid + 9  ,v_stmtdate,v_string_15,  'DELL','ext IBM desc1' ,v_stmtdate-2,v_stmtdate-1,  356.123,     'USD',   -26.102,      'USD',  0,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+17,    180,       11,v_stockid + 10  ,v_stmtdate,v_string_15,  'DELL','int IBM desc1' ,v_stmtdate-3,v_stmtdate-4,  323.456,     'USD',   0,      'USD',  0,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+18,    190,       11,v_stockid + 10  ,v_stmtdate,v_string_15,  'DELL','ext IBM desc1' ,v_stmtdate-2,v_stmtdate-1,  356.123,     'USD',   0,      'USD',  0,'Tran type', 1 );

--Not breaks
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+19,    180,       4,v_stockid + 3  ,v_stmtdate,v_string_15,     ' ','int IBM desc1' ,v_stmtdate-3,v_stmtdate-4,  423.456,     'USD',   102.36,      'EUR',  -3203.25,'Tran type', 0 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+20,    190,       4,v_stockid + 3  ,v_stmtdate,v_string_15,    'AB','ext IBM desc1' ,v_stmtdate-2,v_stmtdate-1,  456.123,     'USD',   16.102,      'EUR',  -425.203,'Tran type', 0 );

--Singletons
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+21,    180,       5,v_stockid + 4  ,v_stmtdate,v_string_15,    'AB','int IBM sing1' ,v_stmtdate-2,v_stmtdate-1,  -456.23,     'USD',   16.102,      'EUR',  -425.203,'Tran type', 1 );
INSERT INTO tlmdba.item(corr_acc_no,   gin,ls_type,match_no,       stock_id,stmt_date,       string_15,sfield_8,    sfield_9,     date_14,     date_15,   amount,currency_6, amount_6,currency_16, amount_16,  string_16,flag_16)
            VALUES     (v_setid,   v_gin+22,   190,       0,v_stockid + 4  ,v_stmtdate,v_string_15,    'AB','ext IBM sing2' ,v_stmtdate-2,v_stmtdate-1,  1456.13,     'USD',   16.102,      'EUR',  -425.203,'Tran type', 1 );
			
			
-- Cash
IF v_cashFlag = true  THEN
update tlmdba.item set flag_2=0,ls_type=1 where corr_acc_no = v_setid and ls_type=180;
update tlmdba.item set flag_2=0,ls_type=2 where corr_acc_no = v_setid and ls_type=180;
END IF; 

--DA

--FX

--PS
END;


/
 
