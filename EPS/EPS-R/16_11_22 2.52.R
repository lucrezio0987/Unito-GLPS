#eps-a 16/11/22 2.52
library("UsingR")

data("babyboom")
str(babyboom)

interarrival <- diff(babyboom$running.time)

hist(interarrival)
hist(interarrival, breaks = 15 )
sd(interarrival)/mean(interarrival)

