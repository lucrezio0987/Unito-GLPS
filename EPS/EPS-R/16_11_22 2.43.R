#EPS A 16/11/22

data("nym.2002")
str(nym.2002)

hist(nym.2002$time)
summary(nym.2002$time)
abline(v= 180, col= "red" )

nym.2002$time[nym.2002$time <= 180] -> veloci
str(veloci)
length(veloci)
length(veloci)/length(nym.2002$time)

length(nym.2002)
nrow(nym.2002)    #restituisce il numero di caselle dell'oggetto che gli passi

sum(is.na(nym.2002$time))
quantile(nym.2002$time, 0.1)
options(digits = 10)
quantile(nym.2002$time, 0.25)
quantile(nym.2002$time, 0.90)
