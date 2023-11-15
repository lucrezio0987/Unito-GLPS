#! pip install matplotlib

import matplotlib.pyplot as plt

# Leggi i dati dal file tempi.csv
with open('tempi.csv', 'r') as file:
    lines = file.readlines()

# Estrai le coppie di valori
x_values = []
y_values = []
for line in lines:
    x, y = map(int, line.strip().split())
    x_values.append(x)
    y_values.append(y)

# Crea il grafico
plt.plot(x_values, y_values, marker='o', linestyle='-')
plt.title('Grafico delle Coppie di Valori')
plt.xlabel('Valore X')
plt.ylabel('Valore Y')

# Mostra il grafico
plt.show()
