# Thank you, I see when updating the users/passwords, I printed them
# to the document, but I forgot to update the dictionary, so when asking
# via dictionary, the user didnt exist.

# import library to pull current date
# textwrap for when task description risks overflowing.
from datetime import date
import textwrap

# Create variables 
user_list = []
user_dict = {}
private = ""

# opens user.txt, strips each line into list
# and adds to dictionary
with open('user.txt', 'r') as f:
    for i in f:
        user_list.append((i.strip('\n')).split(', '))
user_dict.update(dict(user_list))

# Create loop, request input and check against
# the key:items created in user_dict
while True:
    print("Please Login Below")
    usr_name = input(f"{'─' * 50}\n{'USERNAME: ':>30}").lower()
    usr_password = input(f"{'PASSWORD: ':>30}").lower()
    if (usr_name in user_dict and user_dict[usr_name] == usr_password):
        print("\nlogin successful!\n")
        break
    elif usr_name in user_dict and user_dict[usr_name] != usr_password:
        print("Incorrect password!")
    else:
        print("Incorrect Username!")
# If user is admin, add additional options in the menu
if usr_name == "admin":
    private = '''s - Statistics
r - Registering a user'''
# Presents menu options, some only visible to admin,
# and converts the input to lover case
while True:
    menu = input(f'''\nSelect one of the following Options below:
{private}
a - Adding a task
va - View all tasks
vm - view my task
e - Exit
: ''').lower()

# must be admin, requests username and continues to request
# password until both entires match
    if menu == "r" and usr_name == "admin":
        r_user = input("username: ")
        while True:
            r_pass = input("password: ")
            r_pass_2 = input("confirm password: ")
            if r_pass == r_pass_2:
# Write the username and password combo to new line .txt file
# And append the dictionary file for user/pass
                user_dict[r_user] = r_pass
                with open('user.txt', 'a') as f:
                    f.write('\n'+r_user + ', ' + r_pass)
                    break
            else:
                print("Passwords don't match")

# For creating a new task.
    elif menu == "a":
# Creates an empty list called task and builds a list using questions
        task = []
        while True:
# If the username is a key in user_dict, accepts the entry
            name = input("Assign task to (username): ")
            if name in user_dict.keys():
                task.append(name)
                break
            else:
                print("Username not on record")
        task.append(input("Task Title: "))
        task.append(input("Task Description: "))
        task.append(str(date.today().strftime("%d %B %Y")))
        task.append(input("Task Due Date (format - 01 Jan 2020): "))
        task.append("No")
# complies list into sentence and writes to a new line in .txt file
        with open("tasks.txt", "a") as f:
            f.write("\n"+", ".join(task)) 
# For viewing all tasks.
    elif menu == "va":
# opens .txt file for task and prints each line neatly arranged
        with open("tasks.txt", "r") as f:
            for line in f:
                line = line.strip('\n')
                print(f"""
{'─' * 50}
Task:{line.split(', ')[1]:>45}
Assigned to:{line.split(', ')[0]:>38}
Date assigned:{line.split(', ')[3]:>36}
Due Date:{line.split(', ')[4]:>41}
Completed?:{line.split(', ')[5]:>39}
Task Description:
{(textwrap.fill(line.split(', ')[2],50))}
{'─' * 50}
""")

# opens .txt file file for tasks and print tasks that match usr_name
    elif menu == 'vm':
        with open("tasks.txt", "r") as f:
            for line in f:
# checks the first word of the string against the usr_name
                if line.split(", ")[0] == usr_name:
                    line = line.strip('\n')
                    print(f"""
{'─' * 50}
Task:{line.split(', ')[1]:>45}
Assigned to:{line.split(', ')[0]:>38}
Date assigned:{line.split(', ')[3]:>36}
Due Date:{line.split(', ')[4]:>41}
Completed?:{line.split(', ')[5]:>39}
Task Description:
{(textwrap.fill(line.split(', ')[2],50))}
{'─' * 50}
""")

# if the user is admin he can see the 's' option
# it saves .txt lines as a list, counts the length
# does this for task count and user count
    elif menu == 's' and usr_name == "admin":
        with open('user.txt', 'r') as f:
            print(f"\nNumber of Users: {len(f.readlines())}")
        with open('tasks.txt', 'r') as g:
            print(f"Number of Tasks: {len(g.readlines())}")

# Continual loop runs until 'e' is pushed and returns exit()
    elif menu == 'e':
        print("Goodbye!!!")
        exit()
# None of the above conditions have been satisfied
    else:
        print("You have made a wrong choice, Please Try again")
