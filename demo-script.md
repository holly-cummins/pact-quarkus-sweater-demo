# Sweater Shop Demo notes 

## Pre-demo prep

- Print these instructions!
- Run `./prep-demo` script, which will delete the contract tests and initialise the podman container environment. It also clears the database if the architecture recorder is running.

### Machine tidy
- Pause any backup software
- Turn on Mac Focus
- Quit email and other messaging tools

### Display
- Make terminal and IDE fonts huge
- Make browser font huge
- Reduce screen resolution to 1920 x 1080

### Environment setup

- Sort out web conference green screen if necessary
- Get an iPad with a timer running

### IDE setup 

Open three IDEs, one for cold-person, knitter, and farmer. 

Open a terminal within each IDE (or two OS terminals). 

### Services setup 

```shell
cd architecture-recorder
quarkus dev
```

It may be useful to clear all architecture information, or just the historical interactions. 

```shell
 curl -i -X POST http://localhost:8088/recorder/clearall
```

```shell
 curl -i -X POST http://localhost:8088/recorder/clearinteractions
```

## The demo 
1. Start the cold-person service with `quarkus dev`. Visit http://localhost:8000. The app has a React front end and a Quarkus back end, stitched together and bridged by Quinoa.
2. Try and do an order. Nothing will happen; there are no other services.
3. Start the knitter service. It should appear on the front end.
4. Try and do an order. Nothing will happen; we need wool.
5. Start the farmer service. It should appear on the front end.
6. Do an order for a white sweater. It should succeed, and an set of orders should appear.
7. We've had to do quite a lot of starting of services, just to see if our app works... and this is a trivial application. Microservices are hard!
8. Show the tests. Because of course there are tests, we're responsible developers. 
   1. Run the back-end tests in IntelliJ (they're also running continuously in Quarkus)
9. Refactor the `Skein` in the farmer app. Colour is a British spelling. To refactor, `shift-f6` on `colour` variable, change it to ‘color’. Getters and setters will update too.  . 
10. Sense check. Run the Java tests (all working), all working in all services. 
11. Visit the web page again. It's all going to work, right, because the tests all worked?
12. How can we fix this? The app is broken, but the tests are all green. 
13. Use the git history to recreate the contract tests. Rollback `ResidentTable.contract.test.js` from the git history. Show the `frontend/src/ResidentTable.test.js` and then compare the two tests. Explain the differences are because Pact acts both as a mock and a validator of all possible values.
14. Restart the tests. A json contract has appeared in `knitter/target/pacts`
15. The test should pass, we're the consumer, we made assumptions about how the provider should behave. But are those assumptions correct? Now is when we find out! 
16. Copy the pact from the knitter to share it to the farmer: `publish-contracts.shb`. Normally this would be done by automatically checking it into source control or by using a pact broker. 
17. Restore the Java test from history. 
18. Run the Java tests, show the failure, explain how it could be fixed by negotiating a contract change or changing the source code. 

## A really long demo


