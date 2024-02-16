--
-- Création des contraintes de vérification de l'article de configuration CSCI_ADM
--
 
PROMPT Creating Check Constraint on 'ETUDIANT'
ALTER TABLE ETUDIANT
 ADD (CONSTRAINT CK_GROUPE_TP CHECK (GROUPE_TP BETWEEN 1 AND 2))
/

PROMPT Creating Check Constraint on 'ETUDIANT'
ALTER TABLE ETUDIANT
 ADD (CONSTRAINT CK_GROUPE_ANGLAIS CHECK (GROUPE_ANGLAIS BETWEEN 1 AND 2))
/
       
PROMPT Creating Check Constraint on 'FORMATION'
ALTER TABLE FORMATION
 ADD (CONSTRAINT CK_NO_ANNEE CHECK (N0_ANNEE BETWEEN 1 AND 3))
/


