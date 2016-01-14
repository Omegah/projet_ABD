--Drop tables
drop table DispositifFormat;
drop table format;
drop table dispositifs
drop table Societe;
drop table client;

-- Creation des tables
--Client
create table client (
	mailClient varchar2(50) ,
	nom varchar2(50),
	prenom varchar2(50),
	adressePostale varchar2(50),
	MDP varchar2(50),
	constraint client_pk primary key(mailClient)
	
);

-- Societe (inclus PhotoNum)
create table Societe(
	idS integer,
	nomSociete varchar2(50),
	adresse varchar2(50),
	preference integer,
	delai integer,
	constraint societe_pk primary key (idS),
	constraint societe_check1 check (preference>=0 and preference <= 20),
	constraint societe_check2 check (delai >= 0 and delai<=72)
);


--Dispositifs
create table dispositif(
	idD integer,
	idS integer,
	constraint dispositif_pk primary key(idD),
	constraint dispositif_fk foreign key (idS) references Societe(idS)
);

--Format
create table format(
	idF integer,
	taille varchar2(50),
	nbPixel integer,
	libelle varchar2(50),
	constraint format_pk primary key(idF)
);

--DispositifFormat
create table DispositifFormat(
	idD integer,
	idF integer,
	constraint dispositifFormat_pk primary key(idD,idF),
	constraint dispositifFormat_fk1 foreign key(idD) references dispositif(idD),
	constraint dispositifFormat_fk2 foreign key(idF) references Format(idF)
);

--FormatSociete
create table FormatSociete(
	idS integer,
	idF integer,
	stock integer,
	prixUnitaire number(3,2),
	tirageParJour integer,
	constraint FormatSociete_pk primary key(idS,idf),
	constraint FormatSociete_fk1 foreign key(idS) references Societe(idS),
	constraint FormatSociete_fk2 foreign key(idF) references Format(idF),
	constraint FormatSociete_check1 check (prixUnitaire>0 and prixUnitaire <= 500000),
	constraint FormatSociete_check2 check (tirageParJour>0 and tirageParJour<=10000)
	
);

--Image
create table Image(
	idI integer,
	partage boolean,
	URL varchar2(50),
	mailClient varchar2(50),
	informationImage varchar2(50),
	resolution integer,
	constraint Image_pk primary key(idI),
	constraint Image_fk foreign key(mailClient) references Client(mailClient),
	constraint Image_check check (resolution > 0)
);


--Album
create table Album(
	idAlbum integer,
	mailClient varchar2(50),
	constraint Album_pk primary key(idAlbum),
	constraint Album_fk foreign key (mailClient) references Client(mailClient)
);


-- Photo
create table Photo(
	idPhoto integer,
	titrePhoto varchar2(50),
	numPage integer,
	idAlbum integer,
	idI integer,
	commentaire varchar2(50),
	constraint photo_pk primary key(idPhoto),
	constraint photo_fk1 foreign key(idAlbum) references Album(idAlbum),
	costraint photo_fk2 foreign key(idI) references Image(idI),
	constraint photo_check1 check (numPage>=0)

);


--Livre
create table Livre(
	idAlbum integer,
	preface varchar2(50),
	postface varchar2(50),
	photoCouverture integer,
	titreLivre varchar2(50),
	constraint Livre_pk primary key (idAlbum),
	constraint Livre_fk1 foreign key (idAlbum) references Album (idAlbum),
	constraint Livre_fk2 foreign key (photoCouverture) references Photo(idPhoto)
	
);

