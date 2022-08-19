## This belongs to the file **inventory.py**

# This is part of a series of python programs written as excercises.

This program simulates a functional maenu for a 
shoe storage facility and the stock they have availalbe.

This program uses classes to create a shoe object with the following properties:
* country
* code
* product
* cost
* quantity
  
 * The menu then prompts and allows you to perfrom the following actions:
   * Find the Lowest Stock item
     * Prompts if more stock is desired
   * Display all data for a particular object
   * Search for product data by code
   * Calculate value on item
   * Highest quantity of item
   * Add item (object) to list


* All functions contained within the "void main()":
  * def read_shoes_data()
  * def capture_shoes()
  * def update_file()
  * def view_all()
  * def re_stock()
  * def search_shoe(code)
  * def value_per_item(obj)
  * def highest_qty()