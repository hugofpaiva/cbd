# [Cassandra](https://www.tutorialspoint.com/cassandra/index.htm)

Cassandra is a distributed database from Apache that is highly scalable and designed to manage very large amounts of structured data. It provides high availability with no single point of failure.

Apache Cassandra is a highly scalable, high-performance distributed database designed to handle large amounts of data across many commodity servers, providing high availability with no single point of failure. It is a type of NoSQL database. 

## How to Start Cassandra

1. First follow this [guide](https://medium.com/@manishyadavv/how-to-install-cassandra-on-mac-os-d9338fcfcba4) to install Cassandra on Mac

2. Start the service on background

```
cassandra -f
```

3. Run the command line shell for interacting with Cassandra


```
cqlsh
```

## What is Apache Cassandra?

Apache Cassandra is an open source, distributed and decentralized/distributed storage system (database), for managing very large amounts of structured data spread out across the world. It provides highly available service with no single point of failure.

Listed below are some of the notable points of Apache Cassandra −

- It is scalable, fault-tolerant, and consistent.
- It is a column-oriented database.
- Its distribution design is based on Amazon’s Dynamo and its data model on Google’s Bigtable.
- Created at Facebook, it differs sharply from relational database management systems.
- Cassandra implements a Dynamo-style replication model with no single point of failure, but adds a more powerful “column family” data model.
- Cassandra is being used by some of the biggest companies such as Facebook, Twitter, Cisco, Rackspace, ebay, Twitter, Netflix, and more.

## Cassandra Query Language

Users can access Cassandra through its nodes using Cassandra Query Language (CQL). CQL treats the database **(Keyspace)** as a container of tables. Programmers use **cqlsh:** a prompt to work with CQL or separate application language drivers.

Clients approach any of the nodes for their read-write operations. That node (coordinator) plays a proxy between the client and the nodes holding the data.

## Creating a Keyspace using Cqlsh

A keyspace in Cassandra is a namespace that defines data replication on nodes. A cluster contains one keyspace per node. Given below is the syntax for creating a keyspace using the statement **CREATE KEYSPACE**.

### Syntax

```
CREATE KEYSPACE <identifier> WITH <properties>
```

## Replication

The replication option is to specify the **Replica Placement strategy** and the number of replicas wanted. The following table lists all the replica placement strategies.

[Untitled](https://www.notion.so/b76ce0b23e4e4ad2bcacc4bbfeae8a3a)

### Example

Given below is an example of creating a KeySpace.

- Here we are creating a KeySpace named **cbd**.
- We are using the first replica placement strategy, i.e.., **Simple Strategy**.
- And we are choosing the replication factor to **1 replica**.

```
cqlsh.> CREATE KEYSPACE cbd
WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};
```

### Verification

You can verify whether the table is created or not using the command **Describe**. If you use this command over keyspaces, it will display all the keyspaces created as shown below.

```
cqlsh> DESCRIBE keyspaces;

cbd  system_schema  system_auth  system  system_distributed  system_traces

```

## Durable_writes

By default, the durable_writes properties of a table is set to **true,** however it can be set to false. You cannot set this property to **simplex strategy.**

### Example

Given below is the example demonstrating the usage of durable writes property.

```
cqlsh> CREATE KEYSPACE test
... WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3 }
... AND DURABLE_WRITES = false;
```

### Verification

You can verify whether the durable_writes property of test KeySpace was set to false by querying the System Keyspace. This query gives you all the KeySpaces along with their properties.

```
cqlsh> SELECT * FROM system_schema.keyspaces;

 keyspace_name      | durable_writes | replication
--------------------+----------------+-------------------------------------------------------------------------------------
        system_auth |           True | {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '1'}
      system_schema |           True |                             {'class': 'org.apache.cassandra.locator.LocalStrategy'}
 system_distributed |           True | {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '3'}
             system |           True |                             {'class': 'org.apache.cassandra.locator.LocalStrategy'}
                cbd |           True | {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '3'}
      system_traces |           True | {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '2'}

(6 rows)
```

## Using a Keyspace

You can use a created KeySpace using the keyword **USE**. Its syntax is as follows −

```
Syntax:USE <identifier>

```

### Example

```bash
cqlsh> use cbd;
cqlsh:cbd>
```

## Altering a KeySpace

ALTER KEYSPACE can be used to alter properties such as the number of replicas and the durable_writes of a KeySpace. Given below is the syntax of this command.

### Syntax

```
ALTER KEYSPACE <identifier> WITH <properties>
```

### Example (altering replication factor)

```bash
cqlsh:cbd> ALTER KEYSPACE cbd WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 2};
cqlsh:cbd> SELECT * FROM system_schema.keyspaces;

 keyspace_name      | durable_writes | replication
--------------------+----------------+-------------------------------------------------------------------------------------
        system_auth |           True | {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '1'}
      system_schema |           True |                             {'class': 'org.apache.cassandra.locator.LocalStrategy'}
 system_distributed |           True | {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '3'}
             system |           True |                             {'class': 'org.apache.cassandra.locator.LocalStrategy'}
                cbd |           True | {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '2'}
      system_traces |           True | {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '2'}

(6 rows)
```

## Dropping a Keyspace

You can drop a KeySpace using the command **DROP KEYSPACE**. Given below is the syntax for dropping a KeySpace.

### Syntax

```
DROP KEYSPACE <identifier>
```

### Example

```bash
cqlsh:cbd> DROP KEYSPACE cbd;
cqlsh:cbd> SELECT * FROM system_schema.keyspaces;

 keyspace_name      | durable_writes | replication
--------------------+----------------+-------------------------------------------------------------------------------------
        system_auth |           True | {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '1'}
      system_schema |           True |                             {'class': 'org.apache.cassandra.locator.LocalStrategy'}
 system_distributed |           True | {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '3'}
             system |           True |                             {'class': 'org.apache.cassandra.locator.LocalStrategy'}
      system_traces |           True | {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '2'}

(5 rows)
```

## Creating a Table

You can create a table using the command **CREATE TABLE**. Given below is the syntax for creating a table.

### Syntax

```
CREATE (TABLE | COLUMNFAMILY) <tablename>
('<column-definition>' , '<column-definition>')
(WITH <option> AND <option>)

```

## Defining a Column

You can define a column as shown below.

```
column name1 data type,
column name2 data type,
```

## Primary Key

The primary key is a column that is used to uniquely identify a row. Therefore,defining a primary key is mandatory while creating a table. A primary key is made of one or more columns of a table. You can define a primary key of a table as shown below.

```
CREATE TABLE tablename(
   column1 name datatype PRIMARYKEY,
   column2 name data type,
   column3 name data type.
   )

```

**or**

```
CREATE TABLE tablename(
   column1 name datatype PRIMARYKEY,
   column2 name data type,
   column3 name data type,
   PRIMARY KEY (column1)
   )

```

### Example

```bash
cqlsh:cbd> CREATE TABLE emp(
   emp_id int PRIMARY KEY,
   emp_name text,
   emp_city text,
   emp_sal varint,
   emp_phone varint
   );
cqlsh:cbd> select * from emp;

 emp_id | emp_city | emp_name | emp_phone | emp_sal
--------+----------+----------+-----------+---------

(0 rows)
```

## Altering a Table

You can alter a table using the command **ALTER TABLE**. Given below is the syntax for creating a table.

### Syntax

```
ALTER (TABLE | COLUMNFAMILY) <tablename> <instruction>

```

Using ALTER command, you can perform the following operations −

- Add a column
- Drop a column

## Adding a Column

Using ALTER command, you can add a column to a table. While adding columns, you have to take care that the column name is not conflicting with the existing column names and that the table is not defined with compact storage option. Given below is the syntax to add a column to a table.

```
ALTER TABLE table name
ADD  new column datatype;

```

**Example**

```bash
cqlsh:cbd> ALTER TABLE emp ADD emp_email text;
cqlsh:cbd> select * from emp;

 emp_id | emp_city | emp_email | emp_name | emp_phone | emp_sal
--------+----------+-----------+----------+-----------+---------

(0 rows)
```

## Dropping a Column

Using ALTER command, you can delete a column from a table. Before dropping a column from a table, check that the table is not defined with compact storage option. Given below is the syntax to delete a column from a table using ALTER command.

```
ALTER table name
DROP column name;

```

**Example**

```bash
cqlsh:cbd> ALTER TABLE emp DROP emp_email;
cqlsh:cbd> select * from emp;

 emp_id | emp_city | emp_name | emp_phone | emp_sal
--------+----------+----------+-----------+---------

(0 rows)
```

## Dropping a Table

You can drop a table using the command **Drop Table**. Its syntax is as follows −

### Syntax

```
DROP TABLE <tablename>

```

### Example and Verification

```bash
cqlsh:cbd> DROP TABLE emp;
cqlsh:cbd> DESCRIBE COLUMNFAMILIES;

<empty>
```

## Truncating a Table

You can truncate a table using the TRUNCATE command. When you truncate a table, all the rows of the table are deleted permanently. Given below is the syntax of this command.

### Syntax

```
TRUNCATE <tablename>

```

## Creating an Index using Cqlsh

You can create an index in Cassandra using the command **CREATE INDEX.** Its syntax is as follows −

```
CREATE INDEX <identifier> ON <tablename>
```

### Example

```bash
cqlsh:cbd> CREATE TABLE emp(
       ...    emp_id int PRIMARY KEY,
       ...    emp_name text,
       ...    emp_city text,
       ...    emp_sal varint,
       ...    emp_phone varint
       ...    );
cqlsh:cbd> select * from emp;

 emp_id | emp_city | emp_name | emp_phone | emp_sal
--------+----------+----------+-----------+---------

(0 rows)
cqlsh:cbd> CREATE INDEX name ON emp (emp_name);
```

## Dropping an Index

You can drop an index using the command **DROP INDEX**. Its syntax is as follows −

```
DROP INDEX <identifier>
```

### Example

```bash
cqlsh:cbd> CREATE INDEX name ON emp (emp_name);
cqlsh:cbd> DROP INDEX name;
```

## Using Batch Statements

Using **BATCH,** you can execute multiple modification statements (insert, update, delete) simultaneiously. Its syntax is as follows −

```
BEGIN BATCH
<insert-stmt>/ <update-stmt>/ <delete-stmt>
APPLY BATCH
```

### Example

```bash
cqlsh:cbd> BEGIN BATCH
       ... INSERT INTO emp (emp_id, emp_city, emp_name, emp_phone, emp_sal) values(  4,'Pune','rajeev',9848022331, 30000);
       ... UPDATE emp SET emp_sal = 50000 WHERE emp_id =3;
       ... DELETE emp_city FROM emp WHERE emp_id = 2;
       ... APPLY BATCH;
cqlsh:cbd>  select * from emp;

 emp_id | emp_city | emp_name | emp_phone  | emp_sal
--------+----------+----------+------------+---------
      4 |     Pune |   rajeev | 9848022331 |   30000
      3 |     null |     null |       null |   50000

(2 rows)
```

## Creating Data in a Table

You can insert data into the columns of a row in a table using the command **INSERT**. Given below is the syntax for creating data in a table.

```
INSERT INTO <tablename>
(<column1 name>, <column2 name>....)
VALUES (<value1>, <value2>....)
USING <option>
```

### Example

```bash
cqlsh:cbd> INSERT INTO emp (emp_id, emp_name, emp_city,
       ...    emp_phone, emp_sal) VALUES(1,'ram', 'Hyderabad', 9848022338, 50000);
cqlsh:cbd> INSERT INTO emp (emp_id, emp_name, emp_city,
       ...    emp_phone, emp_sal) VALUES(2,'robin', 'Hyderabad', 9848022339, 40000);
cqlsh:cbd> INSERT INTO emp (emp_id, emp_name, emp_city,
       ...    emp_phone, emp_sal) VALUES(3,'rahman', 'Chennai', 9848022330, 45000);
cqlsh:cbd> SELECT * FROM emp;

 emp_id | emp_city  | emp_name | emp_phone  | emp_sal
--------+-----------+----------+------------+---------
      1 | Hyderabad |      ram | 9848022338 |   50000
      2 | Hyderabad |    robin | 9848022339 |   40000
      4 |      Pune |   rajeev | 9848022331 |   30000
      3 |   Chennai |   rahman | 9848022330 |   45000

(4 rows)
```

## Updating Data in a Table

**UPDATE** is the command used to update data in a table. The following keywords are used while updating data in a table −

- **Where** − This clause is used to select the row to be updated.
- **Set** − Set the value using this keyword.
- **Must** − Includes all the columns composing the primary key.

While updating rows, if a given row is unavailable, then UPDATE creates a fresh row. Given below is the syntax of UPDATE command −

```
UPDATE <tablename>
SET <column name> = <new value>
<column name> = <value>....
WHERE <condition>
```

### Example

```bash
cqlsh:cbd> UPDATE emp SET emp_city='Delhi',emp_sal=50000
       ...    WHERE emp_id=2;
cqlsh:cbd> select * from emp;

 emp_id | emp_city  | emp_name | emp_phone  | emp_sal
--------+-----------+----------+------------+---------
      1 | Hyderabad |      ram | 9848022338 |   50000
      2 |     Delhi |    robin | 9848022339 |   50000
      4 |      Pune |   rajeev | 9848022331 |   30000
      3 |   Chennai |   rahman | 9848022330 |   45000

(4 rows)
```

## Reading Data using Select Clause

SELECT clause is used to read data from a table in Cassandra. Using this clause, you can read a whole table, a single column, or a particular cell. Given below is the syntax of SELECT clause.

```
SELECT FROM <tablename>
```

### Example

```bash
cqlsh:cbd> select * from emp;

 emp_id | emp_city  | emp_name | emp_phone  | emp_sal
--------+-----------+----------+------------+---------
      1 | Hyderabad |      ram | 9848022338 |   50000
      2 |     Delhi |    robin | 9848022339 |   50000
      4 |      Pune |   rajeev | 9848022331 |   30000
      3 |   Chennai |   rahman | 9848022330 |   45000

(4 rows)
```

## Where Clause

Using WHERE clause, you can put a constraint on the required columns. Its syntax is as follows −

```
SELECT FROM <table name> WHERE <condition>;

```

**Note** − A WHERE clause can be used only on the columns that are a part of primary key or have a secondary index on them.

```bash
cqlsh:cbd> CREATE INDEX ON emp(emp_sal);
cqlsh:cbd> SELECT * FROM emp WHERE emp_sal=50000;

 emp_id | emp_city  | emp_name | emp_phone  | emp_sal
--------+-----------+----------+------------+---------
      1 | Hyderabad |      ram | 9848022338 |   50000
      2 |     Delhi |    robin | 9848022339 |   50000

(2 rows)
```

## Deleting Datafrom a Table

You can delete data from a table using the command **DELETE**. Its syntax is as follows −

```
DELETE FROM <identifier> WHERE <condition>;

```

### Example

```bash
cqlsh:cbd> DELETE emp_sal FROM emp WHERE emp_id=3;
cqlsh:cbd> select * from emp;

 emp_id | emp_city  | emp_name | emp_phone  | emp_sal
--------+-----------+----------+------------+---------
      1 | Hyderabad |      ram | 9848022338 |   50000
      2 |     Delhi |    robin | 9848022339 |   50000
      4 |      Pune |   rajeev | 9848022331 |   30000
      3 |   Chennai |   rahman | 9848022330 |    null

(4 rows)
```

## Deleting an Entire Row

The following command deletes an entire row from a table.

```bash
cqlsh:cbd> DELETE FROM emp WHERE emp_id=3;
cqlsh:cbd> select * from emp;

 emp_id | emp_city  | emp_name | emp_phone  | emp_sal
--------+-----------+----------+------------+---------
      1 | Hyderabad |      ram | 9848022338 |   50000
      2 |     Delhi |    robin | 9848022339 |   50000
      4 |      Pune |   rajeev | 9848022331 |   30000

(3 rows)
```

# Cassandra - CQL Datatypes

---

CQL provides a rich set of built-in data types, including collection types. Along with these data types, users can also create their own custom data types. The following table provides a list of built-in data types available in CQL.

[Untitled](https://www.notion.so/c5475bb7a5c445fabd9509893cba03fe)

## Collection Types

Cassandra Query Language also provides a collection data types. The following table provides a list of Collections available in CQL.

[Untitled](https://www.notion.so/4705e5e8f0154ff6a2a884366836e96c)

## User-defined datatypes

Cqlsh provides users a facility of creating their own data types. Given below are the commands used while dealing with user defined datatypes.

- **CREATE TYPE** − Creates a user-defined datatype.
- **ALTER TYPE** − Modifies a user-defined datatype.
- **DROP TYPE** − Drops a user-defined datatype.
- **DESCRIBE TYPE** − Describes a user-defined datatype.
- **DESCRIBE TYPES** − Describes user-defined datatypes.

# Cassandra - CQL Collections

---

CQL provides the facility of using Collection data types. Using these Collection types, you can store multiple values in a single variable. This chapter explains how to use Collections in Cassandra.

## List

List is used in the cases where

- the order of the elements is to be maintained, and
- a value is to be stored multiple times.

You can get the values of a list data type using the index of the elements in the list.

### Creating a Table with List and more

```bash
cqlsh:cbd> CREATE TABLE data(name text PRIMARY KEY, email list<text>);
cqlsh:cbd> INSERT INTO data(name, email) VALUES ('ramu',
       ... ['abc@gmail.com','cba@yahoo.com'])
       ... ;
cqlsh:cbd> UPDATE data
       ... SET email = email +['xyz@tutorialspoint.com']
       ... where name = 'ramu';
cqlsh:cbd> SELECT * FROM data;

 name | email
------+--------------------------------------------------------------
 ramu | ['abc@gmail.com', 'cba@yahoo.com', 'xyz@tutorialspoint.com']

(1 rows)
```

## SET

Set is a data type that is used to store a group of elements. The elements of a set will be returned in a sorted order.

### Creating a Table with Set and more

```bash
cqlsh:cbd> CREATE TABLE data2 (name text PRIMARY KEY, phone set<varint>);
cqlsh:cbd> INSERT INTO data2(name, phone)VALUES ('rahman',    {9848022338,9848022339});
cqlsh:cbd> UPDATE data2
       ... SET phone = phone + {9848022330}
       ... where name = 'rahman';
cqlsh:cbd> SELECT * FROM data2;

 name   | phone
--------+--------------------------------------
 rahman | {9848022330, 9848022338, 9848022339}

(1 rows)
```

## MAP

Map is a data type that is used to store a key-value pair of elements.

### Creating a Table with Map and more

```bash
cqlsh:cbd> CREATE TABLE data3 (name text PRIMARY KEY, address
       ... map<text, text>);
cqlsh:cbd> INSERT INTO data3 (name, address)
       ...    VALUES ('robin', {'home' : 'hyderabad' , 'office' : 'Delhi' } );
cqlsh:cbd> UPDATE data3
       ... SET address = address+{'office':'mumbai'}
       ... WHERE name = 'robin';
cqlsh:cbd> select * from data3;

 name  | address
-------+-------------------------------------------
 robin | {'home': 'hyderabad', 'office': 'mumbai'}

(1 rows)
```

# Cassandra - CQL User Defined Datatypes

---

CQL provides the facility of creating and using user-defined data types. You can create a data type to handle multiple fields. This chapter explains how to create, alter, and delete a user-defined data type.

## Creating a User-defined Data Type

The command **CREATE TYPE** is used to create a user-defined data type. Its syntax is as follows −

```
CREATE TYPE <keyspace name>. <data typename>
( variable1, variable2).

```

### Example

```bash
cqlsh:cbd> CREATE TYPE card_details (
       ... num int,
       ... pin int,
       ... name text,
       ... cvv int,
       ... phone set<int>
       ...  );

cqlsh:cbd> DESCRIBE TYPE card_details

CREATE TYPE cbd.card_details (
    num int,
    pin int,
    name text,
    cvv int,
    phone set<int>
);
```

## Altering a User-defined Data Type

**ALTER TYPE** − command is used to alter an existing data type. Using ALTER, you can add a new field or rename an existing field.

### Adding a Field to a Type

Use the following syntax to add a new field to an existing user-defined data type.

```
ALTER TYPE typename
ADD field_name field_type; 
```

### Example

```bash
cqlsh:cbd> ALTER TYPE card_details ADD email text;
cqlsh:cbd> describe type card_details;

CREATE TYPE cbd.card_details (
    num int,
    pin int,
    name text,
    cvv int,
    phone set<int>,
    email text
);
```

### Renaming a Field in a Type

Use the following syntax to rename an existing user-defined data type.

```
ALTER TYPE typename
RENAME existing_name TO new_name;
```

### Example

```bash
cqlsh:cbd> ALTER TYPE card_details RENAME email TO mail;
cqlsh:cbd> describe type card_details;

CREATE TYPE cbd.card_details (
    num int,
    pin int,
    name text,
    cvv int,
    phone set<int>,
    mail text
);
```

## Deleting a User-defined Data Type

**DROP TYPE** is the command used to delete a user-defined data type. Given below is an example to delete a user-defined data type.

### Example

```bash
cqlsh:cbd> DESCRIBE TYPES;

card_details

cqlsh:cbd> drop type card_details;
cqlsh:cbd> describe types;

<empty>
```

# KEYS

The primary key is a general concept to indicate one or more columns used to retrieve data from a Table.

The primary key may be ***SIMPLE*** and even declared inline:

```
 create table stackoverflow_simple (
      key text PRIMARY KEY,
      data text      
  );

```

That means that it is made by a single column.

But the primary key can also be ***COMPOSITE*** (aka ***COMPOUND***), generated from more columns.

```
 create table stackoverflow_composite (
      key_part_one text,
      key_part_two int,
      data text,
      PRIMARY KEY(key_part_one, key_part_two)      
  );

```

In a situation of ***COMPOSITE*** primary key, the "first part" of the key is called ***PARTITION KEY*** (in this example **key_part_one** is the partition key) and the second part of the key is the ***CLUSTERING KEY*** (in this example **key_part_two**)

**Please note that the both partition and clustering key can be made by more columns**, here's how:

```
 create table stackoverflow_multiple (
      k_part_one text,
      k_part_two int,
      k_clust_one text,
      k_clust_two int,
      k_clust_three uuid,
      data text,
      PRIMARY KEY((k_part_one, k_part_two), k_clust_one, k_clust_two, k_clust_three)      
  );

```

Behind these names ...

- The **Partition Key** is responsible for data distribution across your nodes.
- The **Clustering Key** is responsible for data sorting within the partition.
- The **Primary Key** is equivalent to the **Partition Key** in a single-field-key table (i.e. **Simple**).
- The **Composite/Compound Key** is just any multiple-column key

Further usage information: [DATASTAX DOCUMENTATION](http://www.datastax.com/documentation/cql/3.0/cql/cql_reference/create_table_r.html)

---

Small usage and content examples

***SIMPLE***

KEY:

```
insert into stackoverflow_simple (key, data) VALUES ('han', 'solo');
select * from stackoverflow_simple where key='han';

```

**table content**

```
key | data
----+------
han | solo

```

***COMPOSITE/COMPOUND KEY*** can retrieve "wide rows" (i.e. you can query by just the partition key, even if you have clustering keys defined)

```
insert into stackoverflow_composite (key_part_one, key_part_two, data) VALUES ('ronaldo', 9, 'football player');
insert into stackoverflow_composite (key_part_one, key_part_two, data) VALUES ('ronaldo', 10, 'ex-football player');
select * from stackoverflow_composite where key_part_one = 'ronaldo';

```

**table content**

```
 key_part_one | key_part_two | data
--------------+--------------+--------------------
      ronaldo |            9 |    football player
      ronaldo |           10 | ex-football player

```

But you can query with all key (both partition and clustering) ...

```
select * from stackoverflow_composite 
   where key_part_one = 'ronaldo' and key_part_two  = 10;

```

**query output**

```
 key_part_one | key_part_two | data
--------------+--------------+--------------------
      ronaldo |           10 | ex-football player

```