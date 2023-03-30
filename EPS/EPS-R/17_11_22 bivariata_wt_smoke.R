library(moments)
data(babies)
str(babies)

babies$smoke_f <- as.factor(babies$smoke)
levels(babies$smoke_f) <- c("mai", "sempre", "pregravidanza",
                            "molto tempo fa", "non registrato")

table(babies$smoke_f)
table(babies$smoke_f)/nrow(babies)

barplot(table(babies$smoke_f), horwitz)
datachart(table(babies$smoke_f))

pie(table(babies$smoke_f))

#bivariata wt e smoke

#1. mettiamo aposto 999= NA
#2. trasformiamo in grammi

babies$wt(babies$wt == 999) <- NA
sum(babies$wt == 999)
hist(babies$wt)

babies$wt_grammi <- babies$wt * 28.3495
hist(babies$wt_grammi)

boxplot(babies$wt_grammi  ~ babies(smoke_f)
summary(babies$wt_grammi  ~ babies(smoke_f)