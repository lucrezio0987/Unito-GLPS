import matplotlib.pyplot as plt

x_val = []
y_val = []

with open('tempi.csv', 'r') as file:
    for line in file.readlines():
        x, y = map(int, line.strip().split())
        x_val.append(x)
        y_val.append(y)

plt.plot(x_val, y_val)

plt.xlabel('Numero Eseguzione')
plt.ylabel('Valore K trovato')
plt.legend()

plt.show()
