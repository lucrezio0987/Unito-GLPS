# lezione 24 11

install.packages("MASS")
library(MASS)
library(UsingR)

data("Animals")
str(Animals)

plot(Animals$body, Animals$brain)
cor(Animals$body, Animals$brain, method = "spearman")

#descrittiva bivariata quantitativa - qualitativa
data("grades")
str(grades)

table(grades)
mosaicplot(table(grades))

# argomento in più, per l'esame basterà la relazione di pierson
grades_num <- list() # creazione di un nuovo dataset
grades_num$prev <- as.numeric(grades$prev)
grades_num$grade <- as.numeric(grades$grade)
cor(grades_num$prev, grades_num$grade, method = "kendal")
