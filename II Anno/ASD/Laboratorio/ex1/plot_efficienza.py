import matplotlib.pyplot as plt
import numpy as np

x_val = []
y_val = []

with open('k_medi.csv', 'r') as file:
    for line in file.readlines():
        x, y = map(int, line.strip().split())
        x_val.append(x)
        y_val.append(y)

plt.plot(x_val, y_val)

plt.xlabel('Numero Eseguzione')
plt.ylabel('Valore K trovato')
plt.legend()

plt.hist2d(x_val, y_val, bins=(50, 50), cmap=plt.cm.jet)
plt.colorbar()

plt.show()
