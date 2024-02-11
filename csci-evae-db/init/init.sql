create table if not exists enseignant
(
    no_enseignant   int          not null
    primary key,
    adresse         varchar(255) not null,
    cp              varchar(10)  not null,
    enc_perso_email varchar(255) null,
    enc_perso_tel   varchar(20)  null,
    enc_ubo_email   varchar(255) null,
    enc_ubo_tel     varchar(20)  null,
    int_fonction    varchar(50)  null,
    int_no_insee    varchar(50)  null,
    int_prof_email  varchar(255) null,
    int_prof_tel    varchar(20)  null,
    int_soc_adresse varchar(255) null,
    int_soc_cp      varchar(10)  null,
    int_soc_nom     varchar(50)  null,
    int_soc_pays    varchar(255) null,
    int_soc_ville   varchar(255) null,
    nom             varchar(50)  not null,
    pays            varchar(255) not null,
    prenom          varchar(50)  not null,
    sexe            varchar(1)   not null,
    tel_port        varchar(20)  null,
    type            varchar(10)  not null,
    ville           varchar(255) not null
    );

create table if not exists formation
(
    code_formation     varchar(8)  not null
    primary key,
    debut_habilitation datetime(6) null,
    diplome            varchar(3)  not null,
    double_diplome     char        not null,
    fin_habilitation   datetime(6) null,
    n0_annee           tinyint     not null,
    nom_formation      varchar(64) not null
    );

create table if not exists qualificatif
(
    id_qualificatif bigint      not null
    primary key,
    maximal         varchar(16) not null,
    minimal         varchar(16) not null
    );

create table if not exists question
(
    id_question     bigint      not null
    primary key,
    intitulֹ        varchar(64) not null,
    type            varchar(10) not null,
    id_qualificatif bigint      not null,
    no_enseignant   int         null,
    constraint FKenuqmo8ato75nwtmnhvqn8of
    foreign key (no_enseignant) references enseignant (no_enseignant),
    constraint FKl4ler5t8k2w31e88uwr9hiam3
    foreign key (id_qualificatif) references qualificatif (id_qualificatif)
    );

create table if not exists roles
(
    id   int      not null
    primary key,
    name tinytext null
);

create table if not exists roles_seq
(
    next_val bigint null
);

create table if not exists rubrique
(
    id_rubrique   bigint      not null
    primary key,
    designation   varchar(32) not null,
    ordre         double      null,
    type          varchar(10) not null,
    no_enseignant int         null,
    constraint FKq2uic5c6upre2u11c867e5pli
    foreign key (no_enseignant) references enseignant (no_enseignant)
    );

create table if not exists rubrique_question
(
    ordre       decimal(38) not null,
    ID_QUESTION bigint      not null,
    ID_RUBRIQUE bigint      not null,
    primary key (ID_QUESTION, ID_RUBRIQUE),
    constraint FK3hu52bbx0ayvbcsdmqt7k2uh5
    foreign key (ID_QUESTION) references question (id_question),
    constraint FKbi4l32ekwlji8t9juvdqvae20
    foreign key (ID_RUBRIQUE) references rubrique (id_rubrique)
    );

create table if not exists structure_evaluation
(
    no_evaluation int         not null
    primary key,
    date_creation datetime(6) not null,
    etat          varchar(3)  not null
    );

create table if not exists promotion
(
    annee_pro         varchar(10)  not null
    primary key,
    commentaire       varchar(255) null,
    date_rentree      datetime(6)  null,
    date_reponse_lalp datetime(6)  null,
    date_reponse_lp   datetime(6)  null,
    etat_preselection varchar(3)   not null,
    lieu_rentree      varchar(255) null,
    nb_etu_souhaite   smallint     not null,
    processus_stage   varchar(5)   null,
    sigle_pro         varchar(5)   not null,
    code_formation    varchar(8)   null,
    no_enseignant     int          null,
    no_evaluation     int          null,
    constraint FKbem6u73c3epx5s1fdt97uf13g
    foreign key (code_formation) references formation (code_formation),
    constraint FKcryp2cgc4is7ky41koxgr9uc0
    foreign key (no_evaluation) references structure_evaluation (no_evaluation),
    constraint FKlcylu9f2sqrl7hrvvnko8la0t
    foreign key (no_enseignant) references enseignant (no_enseignant)
    );

create table if not exists etudiant
(
    no_etudiant_nat varchar(50)  not null
    primary key,
    abandon_date    datetime(6)  null,
    abandon_motif   varchar(255) null,
    actu_adresse    varchar(255) null,
    actu_cp         varchar(10)  null,
    actu_pays       varchar(255) null,
    actu_ville      varchar(255) null,
    code_com        varchar(10)  null,
    compte_cri      varchar(10)  not null,
    date_naissance  datetime(6)  not null,
    dernier_diplome varchar(255) not null,
    email           varchar(255) null,
    est_diplome     char         null,
    grpe_anglais    int          null,
    lieu_naissance  varchar(255) not null,
    nationalite     varchar(50)  not null,
    no_etudiant_ubo varchar(20)  null,
    nom             varchar(50)  not null,
    perm_adresse    varchar(255) not null,
    perm_cp         varchar(10)  not null,
    perm_pays       varchar(255) not null,
    perm_ville      varchar(255) not null,
    prenom          varchar(50)  not null,
    sexe            varchar(1)   not null,
    sigle_etu       varchar(3)   not null,
    situation       varchar(3)   not null,
    tel_fixe        varchar(20)  null,
    tel_port        varchar(20)  null,
    ubo_email       varchar(255) null,
    universite      varchar(255) not null,
    annee_pro       varchar(10)  not null,
    constraint FKru5hakekuhbjw90gml6lqq9jb
    foreign key (annee_pro) references promotion (annee_pro)
    );

create table if not exists structure_evaluation_seq
(
    next_val bigint null
);

create table if not exists unite_enseignement
(
    code_ue        varchar(8)   not null
    primary key,
    description    varchar(256) null,
    designation    varchar(64)  not null,
    nbh_cm         decimal(38)  null,
    nbh_td         tinyint      null,
    nbh_tp         tinyint      null,
    semestre       varchar(3)   not null,
    code_formation varchar(8)   not null,
    no_enseignant  int          not null,
    constraint FK144kbyvq4xmg673abyy35ee50
    foreign key (no_enseignant) references enseignant (no_enseignant),
    constraint FKtncs5cp8xgnt0p078xa1oijlp
    foreign key (code_formation) references formation (code_formation)
    );

create table if not exists element_constitutif
(
    code_ec                    varchar(8)   not null,
    code_formation             varchar(8)   not null,
    code_ue                    varchar(8)   not null,
    description                varchar(240) not null,
    designation                varchar(64)  not null,
    nbh_cm                     tinyint      null,
    nbh_td                     tinyint      null,
    nbh_tp                     tinyint      null,
    no_enseignant              int          not null,
    unite_enseignement_code_ue varchar(8)   not null,
    primary key (code_ec, code_formation, code_ue),
    constraint FK6v81wmehjl6qsa8vvo8ml7eq2
    foreign key (no_enseignant) references enseignant (no_enseignant),
    constraint FK7ko6kndoseuvunnqvyl5gqt0i
    foreign key (unite_enseignement_code_ue) references unite_enseignement (code_ue)
    );

create table if not exists evaluation
(
    id_evaluation  bigint      not null
    primary key,
    debut_reponse  datetime(6) not null,
    etat           varchar(3)  not null,
    fin_reponse    datetime(6) not null,
    no_evaluation  tinyint     not null,
    periode        varchar(64) null,
    annee_pro      varchar(10) not null,
    code_ec        varchar(8)  not null,
    code_formation varchar(8)  not null,
    code_ue        varchar(8)  not null,
    no_enseignant  int         not null,
    constraint FKemryn3bkip9kone6srol8wwlk
    foreign key (no_enseignant) references enseignant (no_enseignant),
    constraint FKqkyyu36otfgith83oaf6ahchl
    foreign key (annee_pro) references promotion (annee_pro),
    constraint FKqnc7bmp3jrano3238wdyo51d7
    foreign key (code_ec, code_formation, code_ue) references element_constitutif (code_ec, code_formation, code_ue)
    );

create table if not exists droit
(
    consultation  char   not null,
    duplication   char   not null,
    ID_EVALUATION bigint not null,
    NO_ENSEIGNANT int    not null,
    primary key (ID_EVALUATION, NO_ENSEIGNANT),
    constraint FKcterw8cqj8v15xj36qb3ivyx4
    foreign key (NO_ENSEIGNANT) references enseignant (no_enseignant),
    constraint FKldyrfcjwtks3wi6cc4v0rdas3
    foreign key (ID_EVALUATION) references evaluation (id_evaluation)
    );

create table if not exists reponse_evaluation
(
    id_reponse_evaluation bigint       not null
    primary key,
    commentaire           varchar(512) null,
    nom                   varchar(32)  null,
    prenom                varchar(32)  null,
    id_evaluation         bigint       not null,
    no_etudiant_nat       varchar(50)  null,
    constraint FKlk5perpahqbwartij4fmiwl05
    foreign key (no_etudiant_nat) references etudiant (no_etudiant_nat),
    constraint FKlrbksy6fsgb03iak3dk0w42oo
    foreign key (id_evaluation) references evaluation (id_evaluation)
    );

create table if not exists rubrique_evaluation
(
    id_rubrique_evaluation bigint      not null
    primary key,
    designation            varchar(64) null,
    ordre                  tinyint     not null,
    id_evaluation          bigint      not null,
    id_rubrique            bigint      null,
    constraint FK20jvujg1dvmy0s0tg6cd0u0j9
    foreign key (id_rubrique) references rubrique (id_rubrique),
    constraint FKlu5sx4yfycfa11noogcn0kacm
    foreign key (id_evaluation) references evaluation (id_evaluation)
    );

create table if not exists question_evaluation
(
    id_question_evaluation bigint      not null
    primary key,
    intitule               varchar(64) null,
    ordre                  tinyint     not null,
    id_qualificatif        bigint      null,
    id_question            bigint      null,
    id_rubrique_evaluation bigint      not null,
    constraint FK7bvyyl9mow6stqpk9un8qnw2n
    foreign key (id_qualificatif) references qualificatif (id_qualificatif),
    constraint FK95saw6ruhtpgwidv4n48qtr8l
    foreign key (id_rubrique_evaluation) references rubrique_evaluation (id_rubrique_evaluation),
    constraint FKh584khu4508arn65wv9khiikv
    foreign key (id_question) references question (id_question)
    );

create table if not exists reponse_question
(
    positionnement         decimal(38) null,
    ID_QUESTION_EVALUATION bigint      not null,
    ID_REPONSE_QUESTION    bigint      not null,
    primary key (ID_QUESTION_EVALUATION, ID_REPONSE_QUESTION),
    constraint FK9h3ucdpex2fiy0jmnjnii64y8
    foreign key (ID_QUESTION_EVALUATION) references question_evaluation (id_question_evaluation),
    constraint FKeaf555xyssr4derke8cbchp3o
    foreign key (ID_REPONSE_QUESTION) references reponse_evaluation (id_reponse_evaluation)
    );

create table if not exists users
(
    id            bigint       not null
    primary key,
    created_at    datetime(6)  not null,
    created_by    varchar(255) null,
    email_address varchar(255) not null,
    email         varchar(255) null,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    password      varchar(255) null,
    updated_at    datetime(6)  not null,
    updated_by    varchar(255) null,
    username      varchar(255) null
    );

create table if not exists user_roles
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id),
    constraint FKh8ciramu9cc9q3qcqiv4ue8a6
    foreign key (role_id) references roles (id),
    constraint FKhfh9dx7w3ubf1co1vdev94g3f
    foreign key (user_id) references users (id)
    );

create table if not exists users_seq
(
    next_val bigint null
);