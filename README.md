# Customer Manager (ICT261 Lecture 3 lab)

Data is kept in memory, so closing the app loses the customers.

Run (from the folder that contains build.gradle):

    gradle run

Before you submit: open CustomerManagerApp.java and replace YOUR_STUDENT_NUMBER.

Lab steps and where they are in the code (CustomerManagerApp.java):
1. Form with name field and province list - "Step 1"
2. Customer class and ObservableList - Customer.java and the `customers` list
3. TableView with two columns - "Step 3"
4. Validate, then add - "Step 4"
5. Confirm deletion - "Step 5"
6. Test invalid input and keyboard use (see below)

Test it yourself:
- Press Save with everything empty: "Enter the customer name." and the cursor goes to the name box
- Type a name only: "Choose a province." and the name stays
- Use Tab to move between boxes and Enter to save
- Select a row, press Delete selected, then try Cancel and Delete
