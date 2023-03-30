#EPS A 16 nov 22

library(UsingR)
data(normtemp)
str(normtemp)

normtemp$gender_f <- as.factor(normtemp$gender)
str(normtemp)
levels(normtemp$gender_f)
levels(normtemp$gender_f) <- c("male", "female")

male <- normtemp$gender_f == "male"
female <- normtemp$gender_f == "female"
normtemp$temperature[male] -> temperature_male
normtemp$temperature[female] -> temperature_female

mean(temperature_male)
mean(temperature_female)
hist(temperature_male)
hist(temperature_female)
par(mfrow = c(1,2))   #"1" una riga, "2" due colonne nell'istogramma

density(temperature_male) -> dm
density(temperature_female) -> df
plot(df$x,df$y, type = "l", col = "red")  #type è lo spessore della linea
plot(dm$x,dm$y, type = "l", col = "blue")
par(mfrow = c(1,1))
plot(df$x,df$y, type = "l", col = "red")
lines(dm$x, dm$y, col = "blue")

#per grafici alta qualità ggolot2