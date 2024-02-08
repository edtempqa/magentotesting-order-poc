# Order Placement on Magento Website Test Automation Framework PoC

## Getting started:

*Prerequisites*

- Java 17
- JAVA_HOME added to your PATH
- Git
- Apache Maven 3.9.6

## How to run:

*1.Using Maven from CLI*

```shell
mvn clean test
```

Optionally, one can specify the number scenarios to run in parallel. By default, they run in sequence.
For example, in order to have at most 5 scenarios running in parallel:

```shell
mvn clean test "-Dthreads.number=5"
```