# Technology Platforms - Laboratories | Gdańsk University of Technology 2024

This repository contains laboratory assignments for the "Technology Platforms" course, completed during the fourth semester. Laboratories 1-6 focused on Java, while laboratories 7 and 8 were implemented in C#.

## Laboratory 1: Collections

Lab focused on basic operations with data collections, using `HashSet` for unsorted data and `TreeSet` for sorted data structures. Implemented sorting with natural ordering and custom comparators, and generated descendant statistics via `HashMap` and `TreeMap`.

## Laboratory 2: Multithreading

Lab focused on basic mechanisms for starting and synchronizing threads in Java. Implemented a multi-threaded application for parallel execution of computational tasks, using `wait-notify` for task queue synchronization and critical sections for result storage. Threads continuously processed tasks and stored results until application shutdown.

## Laboratory 3: Network Sockets

Lab focused on basic I/O operations and network communication using sockets. Implemented a client-server application with a binary communication protocol, enabling multi-threaded handling of multiple client connections on the server side. Logged communication steps and utilized `ObjectOutputStream` and `ObjectInputStream` for data exchange.

## Laboratory 4: Java Persistence API (JPA)

Lab focused on object-relational mapping with Java Persistence API (JPA) using Hibernate and an in-memory H2 database. Implemented a text-based application to manage two related entity classes in a one-to-many relationship, including adding, deleting, and retrieving entries from the database. Configured automatic table creation and executed custom queries on entities.

## Laboratory 5: Unit Testing

Lab focused on unit testing with `JUnit` and `Mockito`. Implemented in-memory entity management with repository and controller layers, handling CRUD operations and exception cases. Tested repository logic and controller responses with mocks.

## Laboratory 6: Parallel Stream Processing

Lab focused on parallelizing operations using Java Stream API. Implemented a pipeline to process large image collections, involving parallel streams and transformations such as pixel-level color modifications. The application utilized a custom thread pool and measured execution time based on thread pool size.

## Laboratory 7: File System Operations in C#

Lab focused on working with file system operations in C#. Implemented a console application to display and recursively traverse directory contents, extending classes with methods for finding the oldest file and showing DOS attributes. Added features for sorting directory entries by name and size, and performed binary serialization and deserialization of collections.

## Laboratory 8: File Management in WPF

Lab focused on creating a WPF application for browsing and managing file structures. Implemented features included a menu for opening folders and exiting the application, the ability to open and display file contents, and a context menu for deleting elements.
