--Mostrare l'username e il numero di follower degli spettatori della live in diretta effettuata da 'artandmusicstreaming'
select Registrato.Username, Canale.Numero_di_follow 
from Registrato join Canale on Registrato.URL_canale = Canale.URL 
join Spettatore on Registrato.Username = Spettatore.Registrato
join Live_In_Diretta on Spettatore.Live_in_diretta = Live_In_Diretta.URL
where Live_in_diretta.Streamer = 'artandmusicstreaming'; 


--Update di una chiave primaria(Modifica il valore dell'attributo URL_Account della tabella Social associata al Registrato 'esportsgamingchannel')
update Social 
	set URL_Account = 'www.facebook.com/esportsgamingchannel_official'
	where Nome = 'facebook' and Canale in (
	select Canale.URL from Canale join Registrato on Canale.URL = Registrato. URL_Canale
	where Registrato.Username = 'esportsgamingchannel');


--Modificare la categoria(chiave esterna) di una live programmata
	update Live_programmate
	set Categoria = 'Just_Chat'
	where Categoria = 'Fortnite';


--Contare il numero di live di un determinato registrato
select Registrato.Username, count(*)
from Registrato join Canale on Registrato.URL_Canale = Canale.URL
join Multimedia on Canale.URL = Multimedia.Canale join Live_Passate on Multimedia.URL = Live_Passate.URL
where Registrato.Username = 'artandmusicstreaming'
group by Registrato.Username;
--    |
--RIDONDANZA
--    |
select Numero_di_live 
from Canale join Registrato on URL = URL_Canale
where Username = 'artandmusicstreaming';


--Mostrare la classifica dei registrati più seguiti
select Registrato.Username, Canale.Numero_di_Follow
from Registrato join Canale on Registrato.URL_Canale = Canale.URL
where Canale.Numero_di_Follow > 0
order by Canale.Numero_di_follow desc;
--    |
--RIDONDANZA
--    |
select Registrato.Username, count(*) as NumeroFollower
from Follow join Canale on Follow.Canale = Canale.URL join Registrato on Canale.URL = Registrato.URL_Canale 
group by Registrato.Username
order by NumeroFollower desc;


--Eliminare un registrato affiliate per verificare la cancellazione a cascata: 
--	Registrato -> Affiliate, 
--	Registrato -> Canale -> Multimedia -> Live_In_Diretta -> Spettatore
delete from Registrato where Username = 'artandmusicstreaming';

