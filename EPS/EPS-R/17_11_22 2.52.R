#es. 2.52 17/11/22
library("usingR")
library("moments")

data("babybum")
str(babybum)
skewness(babybum$nt)
hist(skewness)
