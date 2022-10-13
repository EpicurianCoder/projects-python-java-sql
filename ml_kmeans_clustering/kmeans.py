""" PSEUDO CODE for the kmeans method and the other smaller methods used below.

Choose value for K
Enter CSV source adress

Input K and CSV values into 'kmeans' method: (METHOD)
	Create empty list cluster_lists for clusters
	FOR as many times as value of K:
		Append empty list to cluster_lists

	Import data from .csv file: (METHOD)
		For each row in the CSV other than the header
			Extract data and add to list

	Shuffle the list of data collected from the .csv
	FOR the number of items in the list:
		Use modulo distrubute list items 'equally' between the 
		  lists in cluster_lists
	Initiate two counters
	Start an infinite loop
	Increment counter for iteration number

	Run our "distrubtion" by "nearest centroid" method: (METHOD)
		For each cluster in cluster_lists:

			Populate local centroid list using two-dimensional mean: (METHOD)
				Returns two-dimensional mean
			For each datapoint inside cluster:
				Create empty dictionary
				For each centroid inside list of centroids:
					Add distance to centroid to dictionary as "key"
					Add index for cluster_list it represents as "value"
					   (using current centroid iteration as index)
				Perfrom dictionary call for lowest "key" value, which
				 list index of nearest centroid's list
				If datapoint is not already in correct list:
					Append datapoint to list with nearst centroid
					Remove instance from previous list

	If the length of our first cluster_lists list is the same as the previous
	 iteration:
		Increment counter to mark "same list length as previous iteration"
	Else Reset the counter to zero)
	If 10 x iterations of the clustering process have taken place 
	sequentially without change in length of list size:
		Display statistics for iterations
		For each cluster inside the cluster_lists:
			Display number of items in cluster
			For each datapoint in cluster:
				Append indivual index values to axis_values lists
				Display individual datapoint details
				Append details variables for calculating cluster mean
			Calculate and display group statistics
			Create scatterplot using axis_values lists datapoints
				(assign colour based on list index)
		Display graph of combined scatterplots

"""

import math
import matplotlib.pyplot as plt
import numpy as np
from random import sample
import random
import csv

def kmeans(cluster_count, data_array):
	"""Loop runs until final kmeans grouping, stats and displays scatterplots

    Parameters
    ----------
    cluster_count : int
        The file location of the spreadsheet
    data_array : Array
    	Array containing arrays with datapoints
    """
	cluster_list = []
	for i in range(cluster_count):
		cluster_list.append([])
	# Shuffles our current data and appends them to our clusters
	# (This alternative to a random centroid worked better for my code)
	random.shuffle(data_array)
	for i in range(len(data_array)):
		cluster_list[i % cluster_count].append(data_array[i])

	count_a = 0
	count_b = 0
	# Starts and infinite loop until the list sizes cease to change
	x = True
	while(x):
		count_a += 1
		converge_test = len(cluster_list[0])
		cluster_iteration(cluster_list)
		# Tests to see is ist size is changing
		if(converge_test == len(cluster_list[0])):
			count_b += 1
		else:
			count_b = 0
		# Loops runs if 10 iterations pass consecutively without a change
		# to our list size
		if(count_b > 10):
			print(f"""
				{count_a} iterations total
				{count_b} consequetive iterations with no change before exit
				""")
			group_count = 1
			colour_dict = {1:'r', 2:'g',3:'b',4:'y',5:'c',6:'m',7:'k'}
			fig = plt.figure()
			for group in cluster_list:
				print(f"-- {len(group)} Countries in group {group_count} --")
				birth = 0.0
				life = 0.0
				xaxis = []
				yaxis = []
				for country in group:
					# Creates co-ordinate points for each individual group
					xaxis.append(country[1])
					yaxis.append(country[2])
					print(f"*** Country:\t{country[0]}")
					birth += country[1]
					life += country[2]
				print(f"""
					Life Expectancy: cluster {group_count} = {life/len(group)}
					Birth Rate: cluster {group_count} = {birth/len(group)}
					""")
				# Adds a layer to our scatterplot
				plt.scatter(xaxis, yaxis, c=(colour_dict[group_count]))
				group_count += 1
			plt.show()
			x = False

def csv_to_array(csv_file):
	"""Creates an array dataset using our CSV files

    Parameters
    ----------
    cluster_count : int
        The file location of the spreadsheet
    data_array : List
    	Array containing arrays with datapoints

    Returns
    -------
    list
    	a populated array of datapoints
    """
	file = open(csv_file)
	reader = csv.reader(file)
	data_array = []
	count = 1
	for row in reader:
		if count < 1:
			data_array.append([row[0], float(row[1]) , float(row[2]) ])
		count -= 1
	return data_array

def find_distance(a_point,b_point):
	"""Returns the distance between two data points

    Parameters
    ----------
    a_point : List
        a list of float datapoints
    b_point : List
    	a list of float datapoints
    Returns
    -------
    int
    	the distance betwen two datapoints
    """
	return math.sqrt((b_point[1] - a_point[0])**2 + (b_point[2] - a_point[1])**2)

def two_dim_mean(datapoint_array):
	"""Finds the two-dimensional mean of an array

    Parameters
    ----------
    datapoint_array : List
        a list of float datapoints
    Returns
    -------
    List
    	datapoints representing the two-dimensional mean
    """
	count = 0
	sumx = 0
	sumy = 0
	for item in datapoint_array:
		sumx += item[1]
		sumy += item[2]
		count +=1
	if count > 0:
		return [sumx/count, sumy/count]

def cluster_iteration(cluster_list):
	"""Redistributes elements to groups based on their distance to centroids

    Parameters
    ----------
    cluster_list : List
        a list of lists with float datapoints
    """
	centroids = [two_dim_mean(cluster) for cluster in cluster_list]
	for cluster in cluster_list:
		for datapoint in cluster:
			# creates a dictionary to story the distances to each centroid
			centroid_dict = {}
			count = 0
			for centroid in centroids:
					centroid_dict[find_distance(centroid, datapoint)] = count
					count += 1
			# Checks that it isnt already in the same cluster
			if not cluster_list[centroid_dict[min(
				centroid_dict.keys())]] == cluster:
				cluster_list[centroid_dict[min(
				centroid_dict.keys())]].append(datapoint)
				cluster.remove(datapoint)

csv_file = 'dataBoth.csv'
cluster_count = 4
kmeans(cluster_count, csv_to_array(csv_file))

## SOURCES USED ##

# - Syntax for using file and reading CSV data -
# file?, How et al. "How To Convert String Values To Integer Values While Reading A CSV File?". Stack Overflow, 2015, https://stackoverflow.com/questions/33547790/how-to-convert-string-values-to-integer-values-while-reading-a-csv-file. Accessed 12 Oct 2022.
# Bonthu, Harika. "Python Tutorial: Working With CSV File For Data Science". Analytics Vidhya, 2021, https://www.analyticsvidhya.com/blog/2021/08/python-tutorial-working-with-csv-file-for-data-science/. Accessed 12 Oct 2022.

# - Information on handling empty cluster erros when i ran into empty clusters -
# cluster, k-means, and Andres Felipe. "K-Means Empty Cluster". Stack Overflow, 2012, https://stackoverflow.com/questions/11075272/k-means-empty-cluster. Accessed 12 Oct 2022.

# - Needed a refresher on dictionaries regarding returning minimum values
# "Python | Minimum Value Keys In Dictionary - Geeksforgeeks". Geeksforgeeks, 2019, https://www.geeksforgeeks.org/python-minimum-value-keys-in-dictionary/. Accessed 12 Oct 2022.

# - Learned how to use random.sample syntax -
# "Python | Random.Sample() Function - Geeksforgeeks". Geeksforgeeks, 2018, https://www.geeksforgeeks.org/python-random-sample-function/. Accessed 12 Oct 2022.

# - Assistance with 2D array comprehension - 
# Pandey, Mehul. "2D Array In Python | Python Two Dimensional Array - Scaler Topics". Scaler Topics, 2022, https://www.scaler.com/topics/2d-array-in-python/. Accessed 12 Oct 2022.



