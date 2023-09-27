library(UsingR)

data("nym.2002")
str(nym.2002)

#1. percentuale di gente che ha corso sotto le 3 ore

hist(nym.2002$time)
nym.2002_1 <- nym.2002$time[nym.2002$time < 180]
str(nym.2002_1)
length(nym.2002_1) / length(nym.2002$time)

#2. qual è il tempo da fare per rimanere nella top 10%?

quantile(nym.2002$time, 0.1)
quantile(nym.2002$time, 0.25)

#3. qual è il tempo da fare per piazzarsi al fondo dalla cassfica (10%)
options(digits = 10)
quantile(nym.2002$time, 0.9)