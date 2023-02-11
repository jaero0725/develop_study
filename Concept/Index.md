# Oracle Index의 장점과 단점 
## Advantages of Oracle Database Index:

1) Improves Query Performance: Indexes can significantly improve the performance of SELECT queries by allowing the database to retrieve the required data much faster.

2) Reduces I/O Operations: Indexes reduce the number of I/O operations required to retrieve data, which leads to faster query execution.

3) Enhances Data Security: Indexes can be used to enforce unique constraints, which helps ensure data integrity and consistency.

4) Improves Join Performance: Indexes can be used to optimize join operations, especially for large tables, by reducing the number of rows that need to be scanned.

5) Increases Scalability: Indexes can improve the scalability of a database by allowing it to handle more concurrent users and larger amounts of data.

##  Disadvantages of Oracle Database Index:

1) Increases Disk Space Requirements: Indexes require additional disk space to store their structure and data, which can consume a significant portion of the available storage.

2) Slows Down Insert and Update Operations: Indexes can slow down the performance of INSERT and UPDATE operations, as the database needs to update the index structure whenever data is modified.

3) Complexity: The use of multiple indexes on a single table can lead to complexity and maintenance issues, as the database needs to keep track of the relationships between the indexes.

## When to Use Oracle Database Index:

1) When Queries are Slow: If a query is taking too long to execute, an index may be able to improve its performance.

2) When Tables are Large: Indexes can be especially useful for large tables, as they allow the database to retrieve data faster by reducing the number of rows that need to be scanned.

3) When Joining Tables: Indexes can be used to optimize join operations, especially for large tables, by reducing the number of rows that need to be scanned.

4) When Enforcing Unique Constraints: Indexes can be used to enforce unique constraints, which helps ensure data integrity and consistency.

<hr>

In general, it's a good idea to use indexes on columns that are frequently used in WHERE clauses, JOIN conditions, and ORDER BY clauses. However, you should also consider the disadvantages of indexes and weigh the benefits against the costs before implementing them.
