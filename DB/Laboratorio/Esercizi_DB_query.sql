/* Es 1 */
select S.Snum, S.Sname 
from S
where Sname like '%h%';

/* es 2 */
select S.Snum, S.Sname 
from S
where Sname like '%s%' or Sname like '%S%';

/* Es 3 */
select S.Snum, S.Sname 
from S
where Sname like '%a%s';

/* Es 4 */
select S.Snum, S.Sname 
from S
where Sname like '%a%' and Sname like '%e%';

/* Es 5 */
select S.Snum, S.Sname 
from S
where Sname like '%a%' and Sname like '%e%' and Sname like '%i%' and Sname like '%o%' and Sname like '%u%';

/* Es 6 */
select S.SNum, S.Sname
from S, P
where Sname = Pname;

/* Es 7 */
select S.SNum, S.Sname
from S
where SName like '____%';

/* Es 2.1 */
select PName, PNum
from P
where Color = 'Red' and Weight >= 13 and Weight <= 17;

/* Es 2.2 */
select SName
from S
where City = 'Athens' and Status < 20;

/* Es 2.3 */
select P.PName, P.Color, P.Weight, S.SName
from S, P
where S.City = P.City;

/* Es 2.4 */
select distinct S.SName
from S, P, SP
where S.SNum = SP.SNum and P.PNum = SP.PNum
and ((P.PName = 'Bolt' and SP.QTY >= 300) or (P.PName = 'Nut' and SP.QTY >= 300))
order by S.SName;

/* Es 2.5 */
select distinct S.SName, S.Status 
from S, P, SP
where S.SNum = SP.SNum and P.PNum = SP.PNum
and (P.Weight < 14 or P.Weight > 17)
order by S.SName desc;
