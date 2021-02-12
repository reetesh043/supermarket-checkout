# The Supermarket Checkout

Implement a Supermarket checkout that calculates the total price of a number of items. Some rule discounts may apply.
Some items have multiple prices based on price discounts such as:
- *Rule1*: buy any 3 equal priced items and pay for 2
- *Rule2*: buy 2 equal priced items for a special price
- *Rule3*: buy 3 (in a group of items), and the cheapest is free
- *Rule4*: for each `N` items `X`, you get `K` items `Y` for free

Write a program which takes input from a CSV file with the checkout items. The file contains the `item-id`, the `group-id`, the quantity and the unit price.
A second input file should contain the configuration of the discounts and to which items they apply. For example, *Rule2* needs to know to which `item-id` it applies to and what is the final price to charge. Similarly, *Rule4* needs `N`, `X`, `K` and `Y` to be defined. Rule1 and Rule3 should work out of the box without needing extra configuration.
Each item can have at most only one rule applied. If more than one rule applies, pick the best rule for the user.
The output required is the receipt with the actual price of every item and the grand total.

### Prerequisites
Java 8
Maven
IDE(Optional)

### Input file format

items.csv (provided for testing in project folder)
rules.csv (provided for testing in project folder)

### Running the application

We can run this application 3 ways:

Application is standalone java application.
#### Main class is: CheckoutApp
#### Main method: main(String args) -- entry point
#### It has 2 arguments: (i) path of items csv and (ii) rules csv files. 

#### 1. Running the app using mvn command line
As shown below pass 2 arguments: first - Path for items file amd second - path rules file in above format.

```
 mvn exec:java -Dexec.mainClass="com.reet.CheckoutApp" -Dexec.args="items.csv rules.csv"
```

#### 2. Running the app using mvn clean test
pass arg[0] -- items file and arg[1]-- rules as shown below in pom.xml
```
    <configuration>
        <mainClass>com.reet.CheckoutApp</mainClass>
        <arguments>
      ####      <argument>arg[0]</argument>
      ####      <argument>arg[1]</argument>
        </arguments>
    </configuration>
```
Then run the command. This will execute app and the tests both

```
   mvn clean test
```

#### 3. Running the app using IDE

As I have explained above as it is java app, import the mvn project in IDE and pass the arguments 
to main method of CheckoutApp class and run the app.

