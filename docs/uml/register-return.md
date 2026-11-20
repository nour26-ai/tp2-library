
# UC-07 – Register a return

## Use Case Name
Register a return

## ID
UC-07

## Primary Actor
Librarian

## Supporting Actors
Mail server

## Stakeholders and Interests
- Member: Wants to return the borrowed copy successfully.
- Librarian: Wants to register the return correctly.
- Library: Wants to keep track of loans and reserved copies.

## Preconditions
- The member has borrowed a copy.
- The copy is registered in the library system.
- The librarian is authenticated.

## Postconditions
- The loan is closed.
- If the return is late, the member is suspended.
- If the copy is reserved, it is kept aside.
- The member who reserved the copy is notified.

## Main Success Scenario
1. The member brings back the borrowed copy.
2. The librarian selects the return operation.
3. The librarian scans the returned copy.
4. The system finds the corresponding loan.
5. The system closes the loan.
6. The system checks whether the copy is reserved.
7. The system keeps the copy aside if it is reserved.
8. The system notifies the member who reserved the copy.
9. The system prints the return receipt.
10. The system confirms that the return has been registered.

## Extensions

- 1a. The barcode cannot be read:
  1. The librarian manually enters the inventory number.
  2. The system retrieves the copy information.

- 2a. The copy is not on loan:
  1. The system displays an error message.
  2. The system cancels the return operation.
  3. The use case ends.

- 5a. The return is late:
  1. The system detects that the return is late.
  2. The extension point `late return` is activated.
  3. The use case `Suspend the member` is executed.

- 6a. The copy is reserved:
  1. The system keeps the returned copy aside.
  2. The system notifies the member who reserved it through the mail server.

## Special Requirements
- The system must correctly calculate late returns.
- The system must notify the member when a reserved copy becomes available.

## Frequency of Occurrence
This use case occurs whenever a member returns a borrowed copy.

