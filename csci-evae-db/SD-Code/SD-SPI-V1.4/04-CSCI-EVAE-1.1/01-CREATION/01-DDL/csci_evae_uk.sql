-- C:\tmp\dosi00\CSCI-EVAE\csci_evae.con
--
-- Generated for Oracle 10g on Fri Jan 17  16:31:04 2014 by Server Generator 10.1.2.11.12
  


PROMPT Creating Unique Key on 'EVALUATION'
ALTER TABLE EVALUATION
 ADD (CONSTRAINT EVE_EVE_UK UNIQUE 
  (ANNEE_UNIVERSITAIRE
  ,NO_ENSEIGNANT
  ,NO_EVALUATION
  ,CODE_FORMATION
  ,CODE_UE))
/

PROMPT Creating Unique Key on 'DROIT'
ALTER TABLE DROIT
 ADD (CONSTRAINT DRT_DRT_UK UNIQUE 
  (NO_ENSEIGNANT
  ,ID_EVALUATION))
/

  