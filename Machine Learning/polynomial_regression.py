import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import PolynomialFeatures

complete_dataset = []
# number to exclude from generated list
verboden = [6,8,12,15,19]

x_train = [] # No. of Oscars won
for i in range(26):
    if not (i+1) in verboden:
        x_train.append(i+1)

# Year Oscar was won
year_data = [
            1979,1980,1982,1983,1984,1986,1988,
            1989,1991,1996,1999,2001,2003,2007,
            2009,2010,2012,2014,2015,2017,2018 
            ]

# No. Golden Globe Nominations
y_train = [
            2,2,4,5,5,6,7,
            8,9,13,16,17,19,21,
            24,25,27,29,30,31,32
            ]

# Builds a complete dataset for future use or exporting
for i in range(len(x_train)):
    complete_dataset.append([year_data[i], x_train[i], y_train[i]])
print(complete_dataset)

# Unique Datapoints exclusively for testing
x_test = [6,8,12,15,19]
y_test = [5,7,11,16,22]

# Instantiate and train our linear regression algorithm
regressor = LinearRegression()
regressor.fit([[i] for i in x_train], y_train)
xx = np.linspace(0,30,40)

# Predict best fit using learned data
yy = regressor.predict(xx.reshape(xx.shape[0],1))

# Generates polynomial features for quadratic equation
quadratic_featurizer = PolynomialFeatures(degree = 2)

# Train quadratic_featurizer and regressor_quadratic to learn size of our
# matrixes and then transform accordingly
x_train_quadratic = quadratic_featurizer.fit_transform([[i] for i in x_train])
x_test_quadratic = quadratic_featurizer.transform([[i] for i in x_test])

regressor_quadratic = LinearRegression()
regressor_quadratic.fit(x_train_quadratic, y_train)

xx_quadratic = quadratic_featurizer.transform(xx.reshape(xx.shape[0], 1))

# Display our Title, graphs, annotations and a legend
plt.figure(figsize=(6,6))
plt.plot(xx,yy, c='g', linestyle='dashed', linewidth=0.5)
plt.title('Meryl Streep Oscar wins vs Golden Globe Nominations')

for i, label in enumerate(year_data):
    plt.annotate(label, (x_train[i]-0.5, y_train[i]+3), fontsize=4)

plt.plot(xx, regressor_quadratic.predict(xx_quadratic), c='r')
plt.grid(True, c='y', linestyle='dashed', linewidth=0.5)
plt.xlabel('Academy Award Wins')
plt.ylabel('Golden Globe Nominations')
plt.scatter(x_train, y_train, c='b')
plt.scatter(x_test, y_test, c='y')
plt.legend([
        'Line of best fit',
        'Polynomial regression curve',
        'Training data',
        'Test Data'
        ])
plt.show()

# ---- SOURCES USED ----

# Some additional scatterplot styling
# square?, How. "How Do I Make A Matplotlib Scatter Plot Square?". Stack Overflow, 2015, https://stackoverflow.com/questions/28122013/how-do-i-make-a-matplotlib-scatter-plot-square. Accessed 12 Oct 2022.

# Annotations and font help
# matplotlib, Different, and Bertil Ipsen. "Different Font Sizes In The Same Annotation Of Matplotlib". Stack Overflow, 2013, https://stackoverflow.com/questions/14643891/different-font-sizes-in-the-same-annotation-of-matplotlib. Accessed 12 Oct 2022.

# Understanding of best fit line
# Kumar, Bijay, and Bijay Kumar. "Matplotlib Best Fit Line - Python Guides". Python Guides, 2021, https://pythonguides.com/matplotlib-best-fit-line/. Accessed 12 Oct 2022.

# SOURCE OF DATA USED
# "List Of Awards And Nominations Received By Meryl Streep - Wikipedia". En.Wikipedia.Org, 2022, https://en.wikipedia.org/wiki/List_of_awards_and_nominations_received_by_Meryl_Streep#Golden_Globe_Awards. Accessed 12 Oct 2022.

# HyperionDev edcuation materials, Lesson 3, Task 23
