-- TRIGGERS PROJET ABD --

-- A revoir
-- suppression des images sans photo --
-- on compte les images qui n'ont pas de photo, on les supprime si il y en a --
--Create or replace trigger trigger_suppr_photo
--after 


-- Verifier qu'un album n'est que d'un seul type
-- on compte les idAlbum qui sont au moins en double dans au moins deux tables.
-- sur LIVRE
Create or replace trigger trigger_type_livre
after update or insert on Livre
Declare 
nb1 integer;
nb2 integer;
Begin
select count(idAlbum) into nb1
from Livre join Calendrier using (idAlbum);
select count(idAlbum) into nb2
from Livre join Agenda using (idAlbum);
if (nb1+nb2) > 0
then raise_application_error(-20001, 'Ce livre est deja un calendrier ou un agenda');
end if;
end;
/
show error;

-- sur CALENDRIER
Create or replace trigger trigger_type_calendrier
after update or insert on Calendrier
Declare 
nb1 integer;
nb2 integer;
Begin
select count(idAlbum) into nb1
from Livre join Calendrier using (idAlbum);
select count(idAlbum) into nb2
from Livre join Agenda using (idAlbum);
if (nb1+nb2) > 0
then raise_application_error(-20002, 'Ce calendrier est deja un agenda ou un livre');
end if;
end;
/
show error;

-- sur AGENDA
Create or replace trigger trigger_type_agenda
after update or insert on Agenda
Declare 
nb1 integer;
nb2 integer;
Begin
select count(idAlbum) into nb1
from Livre join Calendrier using (idAlbum);
select count(idAlbum) into nb2
from Livre join Agenda using (idAlbum);
if (nb1+nb2) > 0
then raise_application_error(-20003, 'Cet agenda est deja un calendrier ou un livre');
end if;
end;
/
show error;

-- Verifier que c'est bien le proprietaire d'un album qui passe sa commande
-- on compte les commandes qui sont destinees a une personne differente du proprietaire de l'album commande

--drop trigger trigger_commande_proprietaire;
Create or replace trigger trigger_commande_proprietaire
after update or insert on Commande
Declare
nb1 integer;
Begin
Select count(idCom) into nb1
From Commande natural join (
select idCom, count(idLot) as nbLots
from Commande C natural left outer join Lot L 
group by idCom )
Where mailClient not in (
select mailClient
from Lot natural join Commande join Album using (mailClient)) and nbLots > 0 ;
if nb1 > 0
then raise_application_error(-20004, 'Cet album ne vous appartient pas !');
end if;
end;
/
show error;


-- on ne peut pas mettre une image non partagee dans un album d'une personne qui n'est pas son proprietaire
	-- on compte les photos d'images non partagees qui sont dans des album dont
	-- le proprietaire est different de celui qui possede la photo

drop trigger trigger_use_image_partagee;
Create or replace trigger trigger_use_image_partagee
after update or insert on Photo
Declare
nb1 integer;
Begin
Select count(idPhoto) into nb1
from (	
Select idPhoto 
From Photo natural join Image
minus
Select idPhoto 
From Photo natural join Image join Album using (idAlbum)
where Image.mailClient=Album.mailClient
);
if nb1 > 0
then raise_application_error(-20005, 'cette image ne vous appartient pas !');
end if;
end;
/
show error;

/*
insert into Photo values (30,'xxx',1,8,1,'C est tres chaud');
insert into Photo values (31,'xxx',1,8,1,'C est tres chaud');
insert into Photo values (32,'xxx',1,8,1,'C est tres chaud');
insert into Photo values (33,'xxx',1,8,1,'C est tres chaud');
insert into Photo values (34,'xxx',1,8,1,'C est tres chaud');
insert into Photo values (35,'xxx',1,8,1,'C est tres chaud');
insert into Photo values (36,'xxx',1,8,1,'C est tres chaud');
insert into Photo values (37,'xxx',1,8,1,'C est tres chaud');
insert into Photo values (38,'xxx',1,8,1,'C est tres chaud');
insert into Photo values (39,'xxx',1,8,1,'C est tres chaud');
delete from photo where idPhoto=37;
delete from photo where idPhoto=38;
delete from photo where idPhoto=39;
select * from photo where idAlbum=8;
*/

-- Les Calendriers ont 12 pages
-- on compte le nombre de page pour chaque album et on vérifie que ce nombre est inférieur à 12
	-- mode ligne before update
	-- on recupere la valeur de l'album -> new:idAlbum 
	-- on compte le nombre de page de cet album et on recupère le type de l'album

drop trigger trigger_calendrier_nbPage;
Create or replace trigger trigger_calendrier_nbPage
Before insert or update on photo
For each row
Declare
nbPage integer;
Begin
dbms_output.put_line(:new.idAlbum);
Select count(idPhoto) into nbPage
From Photo join Album using (idAlbum)
where idAlbum=:new.idAlbum and idAlbum in (
Select idAlbum 
From Calendrier
);
if nbPage >= 12
then raise_application_error(-20006, ' !');
end if;
end;
/
show error;

-- Les Agenda ont 52 ou 365 pages

Create or replace trigger trigger_agenda_nbPage
Before insert or update on photo
For each row
Declare
nbPage integer;
Begin
Select count(idPhoto) into nbPage
From Photo join Album using (idAlbum)
where idAlbum=:new.idAlbum and idAlbum in (
Select idAlbum 
From Agenda
);
if nbPage >= 365
then raise_application_error(-20006, ' !');
end if;
end;
/
show error;

