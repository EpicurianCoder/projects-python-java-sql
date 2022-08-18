import math

# Asks for input "invesmenet" or "Bond"

# "investment" - prompts them for 3 integers
# asks 1 more question: 'simple or compound'
# then prints formula result

# "bond" - prompts them for 3 integers and print a formula result

user_input = input("""Choose either 'investment' or 'bond' from the menu below to proceed:

investment        - to calculate the amount of interest you'll earn on interest
bond              - to calculate the amount you'll have to pay for a home loan

Input choice here: """).lower()

# Gets input, and returns error message and repeats question is not in desired range

# Removes " ", from large number input to avoid errors using .replace
# Changes case of inputs containing words using .lower()
# Rounds our answer to rwo decimal places using round()

retry = """
Error. Invalid Input.                              Retry: """
while not (user_input == "investment" or user_input == "bond"):
    user_input = input(retry).lower()

# Investment Branch:
if user_input == "investment":
    
    deposit = input("""
How much would you like to deposit?       Deposit Amount: R """).replace(" ","")
    while not deposit.isdigit():
        deposit = input(retry).replace(" ","")
        
    interest_rate = input("""
Please enter the interest rate                   (1-100): """)
    while not (interest_rate.isdigit() and int(interest_rate)>0 and int(interest_rate)<100):
        interest_rate = input(retry)
    interest_rate = float(interest_rate)/100
    
    years = input("""
Length of investment (in years)                     (0+): """)
    while not (years.isdigit() and int(years)>0):
        years = input(retry)
        
    interest = input("""
Which Interest type would you like?     simple/compound): """).lower()
    while not (interest == "compound" or interest == "simple"):
        interest = input(retry).lower()
        
    print(f"""\n
Amount                   - R {deposit}.00
Investment Period        - {years} years
interest rate            - {interest_rate*100}%
interest type            - {interest} interest

The total amount recevied after investment period  """, end="")
# prints total amounts for the interest type chosen using formulas from variables below
    comp = float(deposit)*math.pow((1+interest_rate), float(years))
    simp = float(deposit)*(1+interest_rate*float(years))
    if interest == "compound":
        print(f"R {round(comp,2)}")
        print(f"Interest Earned = R {round(comp-float(deposit),2)}")
    elif interest == "simple":
        print(f"R {round(simp,2)}")
        print(f"Interest Earned = R {round(simp-float(deposit),2)}")
        
elif user_input.lower() == "bond":
# Bond Branch:

    house_value = input("""
What is the value of the house?                         : R """).replace(" ","")
    while not house_value.isdigit():
            house_value = input(retry).replace(" ","")
            
    interest_rate = input("""
What is the intereste rate?                      (0-100): """)
    while not interest_rate.isdigit():
            interest_rate = input(retry)
    interest_rate = ((float(interest_rate)/100)/12)
    
    num_months = input("""
How many months do you want to pay loan back over?      : """)
    while not num_months.isdigit():
        num_months = inpout(retry)

    instlmnt = (interest_rate*float(house_value))/(1-math.pow((1+interest_rate),(-float(num_months))))
    print(f"""
Value of the house       - R {house_value}.00
months of repayment      - {num_months} months
Interest Rate            - {interest_rate*100*12}%

The monthly repayment on your bond will be:
= R{round(instlmnt,2)}""")

