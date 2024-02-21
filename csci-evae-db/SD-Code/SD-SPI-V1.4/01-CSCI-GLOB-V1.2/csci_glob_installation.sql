-- 
-- Script d'installation de l'article de configuration CSCI-GLOB
--	

-- Cr�ation des �l�ments DDL :  tables, vues, index, ...
@@01-CREATION/01-DDL/csci_glob_tab
@@01-CREATION/01-DDL/csci_glob_pk
@@01-CREATION/01-DDL/csci_glob_seq
@@01-CREATION/01-DDL/csci_glob_ind

-- Cr�ation du package CG$ERROS n�cessaire aux API de tabme
@@01-CREATION/03-PLSQL/CDSAPER.PKS
@@01-CREATION/03-PLSQL/CDSAPER.PKB

-- Cr�ation de l'API de la table CG_REF_CODES
@@01-CREATION/02-API/CGRC.PKS
@@01-CREATION/02-API/CGRC.PKB
@@01-CREATION/02-API/CGRC.TRG


-- Cr�ation des domaines dynamiques
@@01-CREATION/01-DDL/csci_glob_avt
