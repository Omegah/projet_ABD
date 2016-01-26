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
then raise_application_error(-20003, 'Cet album ne vous appartient pas !');
end if;
end;
/
show error;


-- on ne peut pas mettre une image non partagee dans un album 
