-- TRIGGERS PROJET ABD --   	Palmier - Berthier - AOUN - AMIN

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


-- on ne peut pas mettre une image dans un album d'une autre personne
    -- on compte les photos d'images non partagees qui sont dans des album dont
    -- le proprietaire est different de celui qui possede la photo

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

-- Les Calendriers ont 12 pages
-- on compte le nombre de page pour chaque album et on vérifie que ce nombre est inférieur à 12
    -- mode ligne before update
    -- on recupere la valeur de l'album -> new:idAlbum
    -- on compte le nombre de page de cet album et on recupère le type de l'album

--  ? devrait déclencher un mutating table : on fait un select sur la table que l'on surveille ?

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
then raise_application_error(-20006, ' Vous ne pouvez pas ajouter plus de 12 page dans un calendrier !');
end if;
end;
/
show error;

-- Les Agendas ont 52 ou 365 pages

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
then raise_application_error(-20007, ' vous ne pouvez pas ajouter plus de 365 pages dans cet agenda !');
end if;
end;
/
show error;

-- on ne peut pas modifier les albums qui sont dans des commandes pas 'en creation'
-- A chaque fois que l'on ajoute ou modifie une photo d'un album il faut vérifier si l'album n'est pas dans une commande validee

Create or replace trigger trigger_commande_enCours
Before insert or update or delete on photo
For each row
Declare
nbAlbum integer;
Begin
Select count(idAlbum) into nbAlbum
From Lot join Commande using (idCom)
where idAlbum=:new.idAlbum and statutCommande<>'en creation';
if nbAlbum >= 1
then raise_application_error(-20008, ' vous ne pouvez pas ajouter de phoo dans un album en cours de commande !');
end if;
end;
/
show error;

-- generation code promo
-- A chaque creation de commande, on verifie si le montant est superieur a 100, si c'est le cas on
-- genere un code promo de valeur egale a  5 pourcent

Create or replace trigger trigger_code_promo
After insert or update on commande
For each row
Declare
idCodeP integer;
valeurCode integer;
Begin
if :new.prixTotal > 100
then
select max(idP) into idCodeP
from CodePromo;
idCodeP := idCodeP+1;
valeurCode := :new.prixTotal/20;
insert into CodePromo values (idCodeP,:new.mailClient,    :new.idCom, null, valeurCode);
end if;
end;
/
show error;

-- quand on met a jour les livraison "en cours" --> "en livraison" des qu'une livraison passe en livre faire passer la commande liee à cette livraison en l'etat suivant
--
-- il faut compter au prealable si il s'agit de la dernière livraison a livrer pour cette commande
Create or replace trigger trigger_maj_commande
After update on livraison
For each row
Declare
nbLivraisonRestante integer;
idCo integer;
statutCo varchar(20);
Begin
if :new.statutLivraison='envoye' then
select idCom into idCo
from Commande join Lot using (idCom)
where idLot=:new.idLot;
select statutCommande into statutCo
from Commande join Lot using (idCom)
where idLot=:new.idLot;
select count(idLivraison) into nbLivraisonRestante
from Livraison join lot using (idLot)
where idCom=idCo and statutLivraison='en cours';
if statutCo='en cours' and nbLivraisonRestante = 0 then
update Commande set statutCommande='envoi complet' where idCom=idCo;
elsif statutCo='en cours' and nbLivraisonRestante > 0 then
update Commande set statutCommande='envoi partiel' where idCom=idCo;
end if;
end if;
end;
/
show error;


