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

1. If this is the first time you are running this, build/install the [observer extension](observer-extension):
    ```shell
    cd observer-extension
    ./mvnw install
    ```
2. Start the [architecture recorder](architecture-recorder):
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
1. Start the cold-person service with `quarkus dev`. Visit http://localhost:8080. The app has a React front end and a Quarkus back end, stitched together and bridged by Quinoa.
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
12. Shouldn't the unit tests have caught this? Look at `WoolResourceTest`. Because we're using the object model, our IDE automatically refactored `getColour` to `getColor`. We could have done more hard-coding in the tests, but that's kind of icky, and the IDE might have refactored the hard-coded strings, too. If we were to lock the hard-coded strings and say they can't be changed ... well, that's basically a contract. But it's only on one side, with no linkage to the other side, so it's pretty manual and error prone. 
13. 

## The first contract test 
### Consumer
1. How can we fix this? The app is broken, but the tests are all green. This is where contract tests give that extra validation to allow us to confirm assumptions. 
2. Pact is consumer-driven contract testing, so we start with the consumer. It's a bit like a TDD principle, start with the expectations. Open the `knitter` project.
3. In a terminal, run `quarkus extension add quarkus-pact-consumer`
4. Add the tests
   1. If you're in a hurry, use the git history to recreate the contract tests. Rollback any pom changes and `SweaterResourceContractTest` from the git history.
   2. Otherwise, copy the `SweaterResourceTest` and use it as the starting point. The test method stays exactly the same, because we're trying to validate the behaviour of *our* knitter code.
   3. The mocking logic is a bit different. Delete the mock injection and the `BeforeEach` *pact-tab* for the following live template:
   ```java
         @Pact(provider = "farmer", consumer = "knitter")
   public V4Pact createPact(PactDslWithProvider builder) {
   Map<String, String> headers = new HashMap<>();
   headers.put("Content-Type", "application/json");

        // Here we define our mock, which is also our expectations for the provider

        // This defines what the body of the request could look like; we are generic and say it can be anything that meets the schema
        DslPart woolOrderBody = new PactDslJsonBody()
                .stringType("colour")
                .numberType("orderNumber");

        String woolBody = "{\"colour\":\"white\"}\n";

        return builder
                .uponReceiving("post request")
                .path("/wool/order")
                .headers(headers)
                .method(HttpMethod.POST)
                .body(woolOrderBody)
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(woolBody)
                .toPact(V4Pact.class);
   }
   ```
5. Finally, we need to add some extra annotations. *extend-tab* on the class declaration to add

  ```java
   @ExtendWith(PactConsumerTestExt.class)
   @PactTestFor(providerName = "farmer", port = "8096")
   @PactDirectory("target/pacts")
   
```

6. Show the `SweaterResourceTest` and then compare the two tests. Explain the differences are because Pact acts both as a mock and a validator of all possible values.
7. Restart the tests. A json contract has appeared in `knitter/target/pacts`
8. The test should pass, we're the consumer, we made assumptions about how the provider should behave. But are those assumptions correct? Now is when we find out! 
9. Copy the pact from the knitter to share it to the farmer: `publish-contracts.shb`. Normally this would be done by automatically checking it into source control or by using a pact broker. 
10. 

### Provider 

9. Restore the Java test from history. 
10. Run the Java tests, show the failure, explain how it could be fixed by negotiating a contract change or changing the source code. 

## Sweater colour 

1. We were too vague in our contract. We actually said in the contract any colour would give a white sweater. 
Change `stringType` to 
```java
                .stringValue("colour", "pink")
```
The contract tests should still pass.
(Normally we would build up the tests, but to keep it simple, we will just change the test.)
2. Now publish the tests, and we have the failure. 
3. If you do want to add both tests, you can copy the existing pact and test methods, and change the colours in the copy. You will also need to add
```java
    @PactTestFor(pactMethod = "buyingAPinkSweater")
```
onto the test method. (By default, Pact will only stand up the first `@Pact` for the right provider.)

## Fallback and error handling

1. So we have a failing test, but what's the right fix? Fallback to white isn't right, there should be some kind of error. 
2. We think we should have a `418`, not a white sweater. `418` is `I'm a teapot`, maybe not the right code, but it's my code so I can return what I want. Also, it keeps behaviour of the different services distinct. 
3. Look at the exception mapper to turn `NotFoundException`s into 418s. 
4. Update the tests to expect a `418`. 
```java
    @Test
    public void testSweaterEndpointForWhiteSweater() {
        SweaterOrder order = new SweaterOrder("pink", 100);
        given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/sweater/order")
                .then()
                .statusCode(418);
        //     .extract().as(Sweater.class);

        //  assertEquals("pink", sweater.getColour());
    }
```
5. Update the implementation in `SweaterResource` to wrap the invocation in a `try` and 
```java
     } catch (Exception e) {
            throw new NotFoundException(order.getColour());
        }
```
5. The tests should pass. All good, except ... 
6. ... publish the tests, and they fail on the other side. 
7. Update the code to return null instead of white as the fallback, and ... they should still fail.
8. In this case the right thing to do is to update the contract to return `204`, and update implementation to instead to a null check. The implementation would be to add a 
```java
            if (skein == null) {
```
check in `SweaterResource` and then throw an exception from the null check body.


