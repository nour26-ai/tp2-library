

(a) The Loan constructor is package-private to ensure that loans are created through Member.borrow(), which checks the borrowing rules.

(b) The Book constructor accepts an author, and addAuthor() allows adding more authors. However, it should reject null to guarantee at least one author.

(c) Unmodifiable collections prevent external modifications and protect the consistency of object relationships.

(d) The isLate() method receives a date parameter to make the result predictable and easier to test.

(e) An object's class cannot be changed at runtime. We can create a new object or use composition to change its role.

(f) Bidirectional associations require updating both sides. They improve navigation but increase implementation complexity.