# esercizio 8.14

library(UsingR)
data(stud.recs)
str(stud.recs)

t.test(stud.recs$sat.m, conf.level = 0.9)
t.test(stud.recs$sat.m, conf.level = 0.9) -> ic

str(ic)
ic$conf.int

options(digits = 15) # per visualizzare 15 cifre 

