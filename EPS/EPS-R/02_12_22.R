library(UsingR)

binom.test(5, 100, conf.level = 0.95)
binom.test(c(5,95), conf.level = 0.95)


# ****************************************

samplep <- 4/5
n <- 5
binom.test(samplep * n, n, conf.level = 0.9)

# ****************************************

library(HistData)
data("GaltonFamilies")
str(GaltonFamilies)

boxplot(GaltonFamilies$childHeight ~ GaltonFamilies$gender)

alt.m <-GaltonFamilies$childHeight[GaltonFamilies$gender == "male"]
alt.f <-GaltonFamilies$childHeight[GaltonFamilies$gender == "female"]

t.test(alt.m, alt.f, paired = FALSE, conf.level = 0.95)

# *****************************************

data("babies")
str(babies)

plot(density(babies$age)$x, density(babies$age)$y, type = "l")
lines(density(babies$dage)$x, density(babies$dage)$y, , type = "l", color = "blue")

t.test(babies$age, babies$dage, paired = TRUE, conf.level = 0.95)