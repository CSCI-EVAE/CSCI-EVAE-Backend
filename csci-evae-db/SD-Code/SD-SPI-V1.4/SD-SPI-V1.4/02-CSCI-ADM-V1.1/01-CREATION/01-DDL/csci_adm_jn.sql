-- C:\Temp\csci_adm.tab
--
-- Generated for Oracle 10.2 on Tue Jan 20  09:51:30 2015 by Server Generator 10.1.2.11.12
 


PROMPT Creating Table 'ENSEIGNANT_JN'
CREATE TABLE ENSEIGNANT_JN
 (JN_OPERATION CHAR(3) NOT NULL
 ,JN_ORACLE_USER VARCHAR2(30) NOT NULL
 ,JN_DATETIME DATE NOT NULL
 ,JN_NOTES VARCHAR2(240)
 ,JN_APPLN VARCHAR2(35)
 ,JN_SESSION NUMBER(38)
 ,NO_ENSEIGNANT NUMBER(5) NOT NULL
 ,TYPE VARCHAR2(5)
 ,SEXE VARCHAR2(1)
 ,NOM VARCHAR2(50)
 ,PRENOM VARCHAR2(50)
 ,ADRESSE VARCHAR2(255)
 ,CODE_POSTAL VARCHAR2(10)
 ,VILLE VARCHAR2(255)
 ,PAYS VARCHAR2(5)
 ,MOBILE VARCHAR2(20)
 ,TELEPHONE VARCHAR2(20)
 ,EMAIL_UBO VARCHAR2(255)
 ,EMAIL_PERSO VARCHAR2(255)
 )
 PCTUSED 5
 PCTFREE 60
/

