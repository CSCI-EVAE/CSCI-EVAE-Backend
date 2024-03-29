-- C:\tmp\dosi00\CSCI-EVAE\csci_evae.vw
--
-- Generated for Oracle 10g on Fri Jan 17  13:45:10 2014 by Server Generator 10.1.2.11.12
 

PROMPT Creating View 'V_POSITIONNEMENT'
CREATE OR REPLACE FORCE VIEW V_POSITIONNEMENT
 (CODE
 ,ABREVIATION
 ,SIGNIFICATION)
 AS SELECT CGRC.RV_LOW_VALUE CODE
          ,CGRC.RV_ABBREVIATION ABREVIATION
          ,CGRC.RV_MEANING SIGNIFICATION
FROM CG_REF_CODES CGRC
  WHERE RV_DOMAIN='POSITIONNEMENT'
/

PROMPT Creating View 'V_ETAT_EVALUATION'
CREATE OR REPLACE FORCE VIEW V_ETAT_EVALUATION
 (CODE
 ,ABREVIATION
 ,SIGNIFICATION)
 AS SELECT CGRC.RV_LOW_VALUE CODE
          ,CGRC.RV_ABBREVIATION ABREVIATION
          ,CGRC.RV_MEANING SIGNIFICATION
FROM CG_REF_CODES CGRC
  WHERE RV_DOMAIN='ETAT_EVALUATION'
/
