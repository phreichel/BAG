# Notwendige Bibliotheken importieren
import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats

# Annahmen für die Vermögensverteilung in Deutschland (Log-Normal-Verteilung)
# Durchschnittliches Vermögen und angenommene Standardabweichung
mu_wealth = np.log(214000)  # Durchschnittsvermögen in Deutschland (logarithmiert)
sigma_wealth = 1.0  # Standardabweichung (angenommene Verteilung)

# Erstellen einer Log-Normal-Verteilung für Vermögen
wealth_distribution = stats.lognorm(sigma_wealth, scale=np.exp(mu_wealth))

# Generiere Vermögenswerte auf der X-Achse
wealth_values = np.linspace(1000, 3000000, 500)  # Bis 3.000.000 EUR

# Berechne die Dichte und kumulative Verteilung
wealth_density = wealth_distribution.pdf(wealth_values)
wealth_cumulative = wealth_distribution.cdf(wealth_values)

# Visualisierung der Vermögensverteilung mit 50.000er-Schritten
fig, ax = plt.subplots(2, 1, figsize=(10, 8))

# Plot der Dichte
ax[0].plot(wealth_values, wealth_density, label='Vermögensdichte', color='blue')
ax[0].set_title('Vermögensverteilung in Deutschland (angenommene Log-Normal-Verteilung)')
ax[0].set_xlabel('Vermögen (EUR)')
ax[0].set_ylabel('Dichte')
ax[0].set_xticks(np.arange(0, 3100000, 50000))  # 50.000er-Schritte
ax[0].set_xlim(0, 3000000)  # Limit bei 3.000.000
ax[0].legend()

# Plot der kumulativen Verteilung (Lorenzkurve)
ax[1].plot(wealth_values, wealth_cumulative, label='Kumulative Vermögensverteilung', color='green')
ax[1].set_title('Kumulative Vermögensverteilung (Lorenzkurve)')
ax[1].set_xlabel('Vermögen (EUR)')
ax[1].set_ylabel('Kumulierte Bevölkerung (%)')
ax[1].set_xticks(np.arange(0, 3100000, 50000))  # 50.000er-Schritte
ax[1].set_xlim(0, 3000000)  # Limit bei 3.000.000
ax[1].legend()

plt.tight_layout()
plt.show()

