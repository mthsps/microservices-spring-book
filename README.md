# Learn Microservices with Spring: Boot A Practical Approach to RESTful Services Using an Event-Driven Architecture, Cloud-Native Patterns, and Containerization

<img width="" align="right" src="https://user-images.githubusercontent.com/78484194/179214146-9764dba5-8d8b-4618-be0a-ce88648d7a3d.png" />

As the name says, it is an introduction to various aspects of software development involved in building microservices with Java and Spring, by Moisés Macero García. During the book, we built a multiplication challenge application that evolves from a small monolith to four microservices and a frontend built with React. 

# Some notes

The book is very good because it has a very balanced approach between theory and practice. The main focus is to build the multiplication application and during this process *understand* the pros and cons of using microservices. In a way, the main focus of the book is on the *why* rather than on *how*.  People often consider microservices as the best solution, but Macero shows that they also added a lot of work, mainly to maintain some non-functional requirements like: scalability, availability, extensibility, resilience, and fault tolerance.

It was my first time working with messaging and event-driven development, in particular in the book, we worked with RabbitMQ. I saw how the *publisher-subscriber* pattern helps to increase the system decoupling, making microservices unaware of the logic of the service they depend on. The message broker fulfills this central role of managing the *events* produced by services and making these events available for other services that need them.  Also, with the queueing system, RabbitMQ can keep a certain number of events for a while, so if any service fails, it sends them back when that service is restored.

### Reading progress: 
Complete

# Reference

Moisés Macero García, *Learn Microservices with Spring: Boot A Practical Approach to RESTful Services Using an Event-Driven Architecture, Cloud-Native Patterns, and Containerization*. Apress, 2020.

