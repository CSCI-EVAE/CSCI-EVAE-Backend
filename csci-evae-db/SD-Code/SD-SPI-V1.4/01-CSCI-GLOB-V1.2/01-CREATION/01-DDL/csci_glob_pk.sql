-- C:\Temp\05-SPI-Sprint-1\SD-Code\SD-SPI-V1.1\01-CSCI-GLOB-V1.0\01-CREATION\01-DDL\csci_auth.con
--
-- Generated for Oracle 10g on Fri Feb 27  16:21:07 2015 by Server Generator 10.1.2.11.12
  
  
PROMPT Creating Primary Key on 'CG_REF_CODES'
ALTER TABLE CG_REF_CODES
 ADD (CONSTRAINT CGRC_PK PRIMARY KEY 
  (ID_CGRC))
/

PROMPT Creating Primary Key on 'AUTHENTIFICATION'
ALTER TABLE AUTHENTIFICATION
 ADD (CONSTRAINT AUT_PK PRIMARY KEY 
  (ID_CONNECTION))
/


  


