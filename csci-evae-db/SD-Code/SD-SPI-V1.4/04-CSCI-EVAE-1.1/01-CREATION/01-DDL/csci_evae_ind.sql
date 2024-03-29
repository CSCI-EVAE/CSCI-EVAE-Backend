-- C:\Temp\05-SPI-Sprint-1\SD-Code\SD-SPI-AvecAPI-V1.0\04-CSCI-EVAE-1.0\01-CREATION\01-DDL\csci_evae.ind
--
-- Generated for Oracle 10g on Fri Feb 27  14:32:46 2015 by Server Generator 10.1.2.11.12
  

PROMPT Creating Index 'EVE_ENS_FK_I'
CREATE INDEX EVE_ENS_FK_I ON EVALUATION
 (NO_ENSEIGNANT)
/

PROMPT Creating Index 'EVE_PRO_FK_I'
CREATE INDEX EVE_PRO_FK_I ON EVALUATION
 (ANNEE_UNIVERSITAIRE)
/

PROMPT Creating Index 'EVE_EC_FK_I'
CREATE INDEX EVE_EC_FK_I ON EVALUATION
 (CODE_EC)
/

PROMPT Creating Index 'EVE_UE_FK_I'
CREATE INDEX EVE_UE_FK_I ON EVALUATION
 (CODE_FORMATION
 ,CODE_UE)
/

PROMPT Creating Index 'QEV_QUA_FK_I'
CREATE INDEX QEV_QUA_FK_I ON QUESTION_EVALUATION
 (ID_QUALIFICATIF)
/

PROMPT Creating Index 'QEV_QUE_FK_I'
CREATE INDEX QEV_QUE_FK_I ON QUESTION_EVALUATION
 (ID_QUESTION)
/

PROMPT Creating Index 'QEV_REV_FK_I'
CREATE INDEX QEV_REV_FK_I ON QUESTION_EVALUATION
 (ID_RUBRIQUE_EVALUATION)
/

PROMPT Creating Index 'RBQ_QUE_FK_I'
CREATE INDEX RBQ_QUE_FK_I ON RUBRIQUE_QUESTION
 (ID_QUESTION)
/

PROMPT Creating Index 'RBQ_RUB_FK_I'
CREATE INDEX RBQ_RUB_FK_I ON RUBRIQUE_QUESTION
 (ID_RUBRIQUE)
/

PROMPT Creating Index 'RUB_ENS_FK_I'
CREATE INDEX RUB_ENS_FK_I ON RUBRIQUE
 (NO_ENSEIGNANT)
/

PROMPT Creating Index 'REV_EVE_FK_I'
CREATE INDEX REV_EVE_FK_I ON RUBRIQUE_EVALUATION
 (ID_EVALUATION)
/

PROMPT Creating Index 'REV_RUB_FK_I'
CREATE INDEX REV_RUB_FK_I ON RUBRIQUE_EVALUATION
 (ID_RUBRIQUE)
/

PROMPT Creating Index 'DRT_EVE_FK_I'
CREATE INDEX DRT_EVE_FK_I ON DROIT
 (ID_EVALUATION)
/

PROMPT Creating Index 'DRT_ENS_FK_I'
CREATE INDEX DRT_ENS_FK_I ON DROIT
 (NO_ENSEIGNANT)
/

PROMPT Creating Index 'QUE_ENS_FK_I'
CREATE INDEX QUE_ENS_FK_I ON QUESTION
 (NO_ENSEIGNANT)
/

PROMPT Creating Index 'QUE_QUA_FK_I'
CREATE INDEX QUE_QUA_FK_I ON QUESTION
 (ID_QUALIFICATIF)
/

PROMPT Creating Index 'RPQ_QEV_FK_I'
CREATE INDEX RPQ_QEV_FK_I ON REPONSE_QUESTION
 (ID_QUESTION_EVALUATION)
/

PROMPT Creating Index 'RPQ_RPE_FK_I'
CREATE INDEX RPQ_RPE_FK_I ON REPONSE_QUESTION
 (ID_REPONSE_EVALUATION)
/

PROMPT Creating Index 'RPE_EVE_FK_I'
CREATE INDEX RPE_EVE_FK_I ON REPONSE_EVALUATION
 (ID_EVALUATION)
/

PROMPT Creating Index 'RPE_ETU_FK_I'
CREATE INDEX RPE_ETU_FK_I ON REPONSE_EVALUATION
 (NO_ETUDIANT)
/


