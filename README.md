
# BlockchainIG Task

This project is an automated test for the [interview site](https://arielkiell.wixsite.com/interview), developed using Java, Selenium WebDriver, Maven. The test follows the **Page Object Model** design pattern, ensuring modularity and reusability. Additionally, the test generates an Allure report for better visual insights into test execution.

## Project Structure

- **src/main/java**: Contains the Page Object Model classes, encapsulating page actions and elements.
- **src/test/java**: Contains the test cases that use the page objects.

## Prerequisites

- Java JDK 8 or higher
- Maven 3.x
- Junit
- Allure for generating test reports

## Running the Tests

You can run the tests via Maven commands or using the `runner.bat` file. 

### 1. Maven Commands:

To run all tests, execute:

```bash
mvn clean test
```


### 2. Running Allure Report:

Once the tests finish execution, generate and serve the Allure report with:

```bash
allure serve
```

Alternatively, you can use the `runner.bat` to directly run the tests and generate the report.

## Test Scenario

The test performs the following steps:
- Opens the site
- Navigates to the shop
- Selects the best seller product
- Chooses any color
- Adds 3 items to the cart using the quantity box's up arrow
- Views the cart and proceeds to checkout
- Verifies that the total price sums up to 54 CAD
- Takes screenshots at each step

## Allure Reporting

After running the tests, Allure reports will be automatically generated and served. You can view a detailed breakdown of the test steps, assertions, and errors, if any.

## Future Improvements (What's Next?)

This framework is designed to be extended further. Future enhancements could include:

- **Dynamic Quantity Input via JSON**: A JSON file can be used to input the quantity of products dynamically. This will allow the framework to read the number of items to add and adjust the test accordingly.
  
  - A method could be added to accept an integer, loop through clicks until the quantity is reached, and then calculate the total price based on the product's price. The framework would then assert that the calculated total matches the displayed total.


## Output Folder

The framework is designed to automatically create an `output` folder that includes logs and screenshots for each test step. This folder is cleared at the beginning of each test run to ensure fresh outputs.

- If, for any reason, the folder is not created automatically, please manually create it at `output/screenshots`.

