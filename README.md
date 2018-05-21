## Oyster Card Problem

 - [Application](#application)
    - [Running](#running) 
    - [Tech Stack](#techstack)
 - [Approach](#approach)
    - [Testability](#testability)
    - [Simplicity](#simplicity)
 - [Design](#design)
    - [Command & Query Object Pattern](#copattern)
    - [Fare design](#fare)
  - [Testing](#testing)

-----
#### Application <a name="application" />

##### Running <a name="running" />

Unzip the folder and build the application (if gradle is not installed, it will be downloaded):

Unix/OSX

`./gradlew build`

Windows

`gradlew.bat build`

This will run the acceptance test scenarios (*AcceptanceTests.feature*) written in gherkin. There is no I/O implemented in the solution, it is relying on the tests. 

##### Tech Stack <a name="techstack" />

|Technology			| Objective				|
|-------------------|-----------------------|
|Java 8				| Programming language	|
|Gradle				| Build tool 			|
|Lombok				| Library to reduce boiler plate code |
|JUnit				| Testing library |
|Mockito			| Mocking library |
|Hamcrest			| Assertion library|
|Cucumber			| Testing library for BDD |

---------

#### Approach <a name="approach" />

I have based the entire solution on two main goals: *testability* and *simplicity*.

##### Testability <a name="testability" />

All classes (except the in memory repositories) are covered by unit testing. Also, there is a feature test covering the end-to-end scenarios that are proposed by the problem statement. Finally, relying on tests in the development is the main reason why I have not added any kind of I/O interaction in the solution.

##### Simplicity <a name="simplicity" />

In order to keep the solution as simple as possible, I have chosen to:

1. Keep the model as simple as possible. For example, I don't have an *User* model or an *ID* field inside the *Card* model.
2. Even though I am applying the *IoC* design principle, I am not using any framework to provide me a container or manage my internal dependencies.
3. Another reason to do not waste time with I/O interactions like reading user input and printing the results in the screen. 
4. I am very keen on the functional paradigm, so I use it to write smarter and less code.

---------

#### Design <a name="design" />

##### Command & Query Object Pattern <a name="copattern" />

The very first thing to notice is that I am not using the traditional Service-Repository pattern. In my 14 years of experience, I have seen (and wrongly designed) applications with this pattern that resulted in:

1. Repositories with business logic.
2. Manually written repositories working as wrappers for an ORM framework.
3. Services without business logic, just with wrappers methods for repositories.
4. Services with hundred (if not thousand) of lines of code.
5. Business logic duplication between services.
6. Poor testability and complex dependency graph.

Therefore, I rather introduce the concept of commands and queries in my applications:

- Command: an action that will change the state of my application or perform any kind of calculation based on several inputs and give a result.
- Query: an action that will query the state of my application.

Every command or query is a really small piece of business logic that can be individually tested, read and understood.

**Q:** Why have you still implemented repositories?

**A:** In a real world application those repositories would be automatically provided by an ORM implementation. If not, I would remove them and implement then as commands/queries with database access.

##### Fare design <a name="fare" />

The design of how the fares will work is the open challenge in the problem. Given the context provided, there are two major group of ways to do that:

###### Hard coded fares

If you hard code the fares in your application, you can, as expected, write a cleaner code in order to calculate what is the best fare. You could declare an abstract class *Fare* and, using polymorphism, implement each fare in a child class with it's criteria based on the parent fields (zones crossed, zone one crossed, transport type).

The obvious disadvantage of this approach is the flexibility that you lose. Changing the value fo a single fare may need to change the code and go through all the development process to deploy the change.

###### Dynamic fares

I have chosen to implement the fares as an external datasource and dynamic fetch them all to choose which one is the best one for the user. The main reason, given the context, is the code complexity that it is still low. The flexibility to manage the fares outside the code is worth in my opinion.

---------

#### Testing <a name="testing" />

As expected in any software, the classes are covered by unit tests including the exceptional paths. Additionally, I have written scenarios in a feature test using cucumber in order to show that the solution meets the requirements.

My testing naming convention may see weird at the first moment, but my pattern is *[Behaviour]_[Expected Result]*. Some developers defend the use o what you are testing (like the method name) at the beginning.

**Q:** Why do you have methods inside your stub classes in place of constants?

**A:** If you look at the stub classes (ex: *CardStubs*), I don't use constants to declare the stubs that are used by my tests. I use factory methods because of two reasons: the tests as more readable and reliability. By reliability, I mean that all my tests assertions must be by value, otherwise, if they are by reference, they will fail. 

I truly understand that there is a really, really, really small memory and performance penalty. But, since it is inside the test suite and not main code, it is worth it.

**Q:** Why do you have the *OysterCardSystem* class inside your test source set?

**A:** In summary, to make my step definitions clean. I am not using any *IoC* container, so I must create all my dependency graph. And also, because of the nature of the feature tests, I must save some state saved between the steps.

**Q:** Cool, You have feature tests, are you using BDD?

**A:** From my perspective, creating feature tests in a business language is not enough to say that you are using this development process. It may be a very interesting step, but this is a culture change that should be shared with all the organization. We cannot measure that in a code assignment. 

**Q:** Cool, you don't have I/O, just tests, are you doing TDD?

**A:** TDD has many different interpretations. I like to say that I have a test-driven development mindset because I use tests to: verify that my solution meet the requirements, improve my code/design quality and **boost my productivity** (manually testing a feature that I have developed is my last painful resource). However, if you ask me if I write failing tests for empty classes that I just created; definitely not...













