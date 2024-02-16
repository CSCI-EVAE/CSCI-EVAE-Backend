-- 
-- Script d'installation de l'article de configuration CSCI-ADM
--	

-- Cr�ation des �l�ments DDL :  tables, vues, index, ...
@@01-CREATION/01-DDL/csci_adm_tab
@@01-CREATION/01-DDL/csci_adm_jn
@@01-CREATION/01-DDL/csci_adm_vw
@@01-CREATION/01-DDL/csci_adm_pk
@@01-CREATION/01-DDL/csci_adm_fk
@@01-CREATION/01-DDL/csci_adm_ck
@@01-CREATION/01-DDL/csci_adm_ind
@@01-CREATION/01-DDL/csci_adm_avt
@@01-CREATION/01-DDL/csci_adm_seq
@@01-CREATION/01-DDL/csci_adm_glob_fk

-- Cr�ation de l'API de la table AUTHENTIFICATION
@@01-CREATION/02-API/AUT.PKS
@@01-CREATION/02-API/AUT.PKB
@@01-CREATION/02-API/AUT.TRG

-- Cr�ation du jeu d'essai
@@02-JEU-ESSAI/FORMATION
@@02-JEU-ESSAI/ENSEIGNANT
@@02-JEU-ESSAI/UNITE_ENSEIGNEMENT
@@02-JEU-ESSAI/ELEMENT_CONSTITUTIF
@@02-JEU-ESSAI/PROMOTION
@@02-JEU-ESSAI/CANDIDAT
@@02-JEU-ESSAI/ETUDIANT
@@02-JEU-ESSAI/AUTHENTIFICATION

commit;





