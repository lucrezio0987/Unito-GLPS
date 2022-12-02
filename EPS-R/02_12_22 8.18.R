library(UsingR)
data("normtemp")
str(normtemp)

tempcelsius <- (normtemp$temperature -32) / 1.8
hist(tempcelsius) # è plausibile che i dati siano estratti da una popolazione normale
                  # perchè l'istogramma è circa una campana

t.test(tempcelsius, conf.level = 0.9)
