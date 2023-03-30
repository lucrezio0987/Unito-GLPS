library(UsingR)


alpha <- 0.01
 
# z_alpha / 2
qnorm(alpha / 2, 0, 1)

qnorm(1 - alpha / 2, 0, 1)


# PDF della t di Student
x <- seq(-10, 10, 0.01)
plot(x, dt(x, 10), type = "l")
lines(x, dt(x, 1), col = "red")
lines(x, dnorm(x, 0, 1), col = "blue")

alpha <- 0.05

qt(alpha/2, 1)
qt(1-alpha/2, 1)

# intervalli di confidenza per la media
data(babies)
str(babies)

t.test(babies$age, conf.level = 0.95)
