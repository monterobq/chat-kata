PART I - Make a simple REST service
------------------------------------

In this part we build a mock of the GET method for the Chat API which returns a valid JSON response.  

1. Read about grails unit testing [here][1] and check the provided *ChatControllerTest* unit test (in _test/unit/chat/kata_).

2. Run the unit test within your IDE. *It will should fail since we have not implemented this behaviour yet.*

	> You can also run it from the command line:  `grails test-app`

3. Now let's go and implement the list method in the ChatController (grails-app/controller/chat/kata) to make the test past, use this with the body which returns an statically defined json

	```groovy
	   if(hasErrors()){
	        log.error("Invalid seq: ${errors.getFieldError('seq').rejectedValue}")
	        //TODO: send error about invalid seq
	    }
	    render(contentType: "text/json") {
	        messages = [{
	        	nick = "user1"
	        	message = "hello"
	    	},{
	        	nick = "user2"
	        	message = "hola"
	    	}]
	    	last_seq = 1
	    }
	```

	> Note how we use a JSON builder with the render function . Read this [documentation][2]  

4. Open the provided *URLMappingTests* unit test (in /test/unit) and complete the method *testCharListUrlMapping* with the following content

	```groovy
	    assertRestForwardUrlMapping("GET", "/api/chat", controller:"chat", action:"list")
	```

5. Test that the method fails since we have not created this mapping yet

6. Add the following content to *UrlMappings* (in /grails-app/config) to create the expected mapping and make the test past

	```groovy
	    "/api/chat/"(controller: "chat"){
		     action = [GET: "list"]
	    }
	```

7. Run the unit test again and check that now it passes

8. Run the server locally ``grails run-app``

9. When grails finish bootstrapping the test server, you should see this message:

	> Server running. Browse to http://localhost:8080/chat-kata

10. Now open the URL [http://localhost:8080/chat-kata/api/chat](http://localhost:8080/chat-kata/api/chat) in your browser to test the service. You should get a JSON response


PART II - Add a service layer to your REST service
--------------------------------------------------

In this part we will add a service layer with the business logic for the storage and retrieval of chat messages.

1. Open the already created unit test *ChatServiceTest*. Carfully inspect the methods of this test case. You should be able to understand what the *ChatService* is expected to do by reading this unit test. Run the test, it should fail since we haven't implemented the logic yet.

2. Open the *ChatService* class (in _/grails-app/services/chat/kata_). Implement the two methods of this class: *collectChatMessages* and *putChatMessage*. You should use an ArrayList as the backend storage for chat messages. However, consider that this service will be used as a Singleton and therefore you need to deal with concurrent access to it. Since ArrayList is not a thread-save class you need to guard all accesses to it such that conccurent reads can occur, but writes can not occurr simultanously with any read or write operation. Use a [*ReentrantReadWriteLock*][3] for this.

3. Run the unit test again until your implementation satisfies the tests.


PART III - Wire up the service layer
-------------------------------------

In this part we hook the new created *ChatService* to your *ChatController* and implement the HTTP Post method for the chat controller.

1. Open the *ChatController* class and add the following class field

```groovy
	ChatService chatService
```

> Grails is a framework build around conventions. In this case, because you named the field *chatService* Grails will automatically inject an instance of the *ChatService* when the controller instance is created. Read about how this dependency injection works [here][4]

2. Read about mocking collaborators in [Grails docs][5], [Groovy docs][6] and [Tom's blog][7]. Then open the *ChatControllerTest* unit test and change its content by the following:

```groovy
	void testListAll() {
		// create a mock definition with a stub for the collectChatMessages method
		def mockService = mockFor(ChatService)
		mockService.demand.collectChatMessages(1){ List collector, Integer seq ->
			collector.addAll([
				new ChatMessage([nick:"user3",message:"hello"]),
				new ChatMessage([nick:"user4",message:"hola"])
			])
			return 1
		}
		//inject the mock
		controller.chatService = mockService.createMock()
		// execute the controller
		controller.list()
		// validate the response
		assert response.text == '{"messages":[{"nick":"user3","message":"hello"},{"nick":"user4","message":"hola"}],"last_seq":1}'
	}

	void testListFromLastSequence() {
		// create a mock definition with a stub for the collectChatMessages method
		def mockService = mockFor(ChatService)
		mockService.demand.collectChatMessages(1) { List collector, Integer seq ->
			collector.add(new ChatMessage([nick:"user3",message:"bye"]))
			return seq + 1
		}
		//inject the mock
		controller.chatService = mockService.createMock()
		// execute the controller
		controller.list(1)
		// validate the response
		assert response.text == '{"messages":[{"nick":"user3","message":"bye"}],"last_seq":2}'
	}
```

> Study this code and make sure you understand how the mocking is working

3. Run the new unit test. Of course, it should fail.

4. Now let's change the implementation of the controller to make use of the service as the test expects. Reimplement the *list* method to use the *chatService.collectChatMessages*.

5. Run the unit test util your implementation pass it.

6. Now with need to implement the functionality for send message API. As good TDD developers let's start with the unit test. Add this method to the *ChatControllerTest* unit test:

```groovy
	void testSend(){
		def sentMessage = null
		
		// create a mock definition with a stub for the putChatMessage method
		def mockService = mockFor(ChatService)
		mockService.demand.putChatMessage(1){ message ->
			sentMessage = message
		}
		
		controller.chatService = mockService.createMock()
		// execute the controller
		def data = [nick:"user3",message:"aloha"]
		request.JSON = data
		def message = new ChatMessage(data)
		controller.send()
		//validate that message was sent to controller
		assert sentMessage == message
		
	}
```

7. Now provide an implementation for the *ChatController.send* method which satisfies the test

> *Tip:* you can create an instance of ChatMessage using `new ChatMessage(request.JSON)`

8. As a last step with need to map the *send* method to the POST http method of the chat resource as specified by our API documentation. First at this method to the *UrlMappingingsTest* class

```groovy
	public void testChatSendURLMapping() {
		assertRestForwardUrlMapping("POST", "/api/chat", controller:"chat", action:"send")
	}
```

9. Now add the POST action to the *"/api/chat/"* mapping in the *UrlMappings* class

10. Run the unit test *UrlMappingingsTest* to verify your implementation

11. Run the server locally ``grails run-app``

12. Test your REST service with *curl* or any other REST client (e.g. [Postman](https://chrome.google.com/webstore/detail/postman-rest-client/fdmmgilgnpjigdojojpjoooidkmcomcm?hl=en))

```
$ curl http://localhost:8080/chat-kata/api/chat --data '{"nick":"alex","message":"hello"}' --header "Content-Type: text/json; charset=UTF-8" -X POST

$ curl http://localhost:8080/chat-kata/api/chat
{"messages":[{"nick":"alex","message":"hello"}],"last_seq":0}

$ curl http://localhost:8080/chat-kata/api/chat --data '{"nick":"alex","message":"ciao"}' --header "Content-Type: text/json; charset=UTF-8" -X POST

$ curl http://localhost:8080/chat-kata/api/chat
{"messages":[{"nick":"alex","message":"hello"},{"nick":"alex","message":"ciao"}],"last_seq":1}

$ curl http://localhost:8080/chat-kata/api/chat?seq=0
{"messages":[{"nick":"alex","message":"ciao"}],"last_seq":1}
```

PART IV - Handle API errors
----------------------------

1. Add the following test method to the *ChatControllerTest* unit test

```groovy
	void testInvalidSeq(){
		params.seq = 'invalid'
		controller.list()
		assert response.status == 400
		assert response.text == '{"error":"Invalid seq parameter"}'
	}
```

2. Run the test. It should fail if you have not added any error handling yet to your controller.

3. Read about Grails automatic validation [here][8] and implement the error handling logic in *ChatController.list* to handle invalid seq parameter (i.e. not a number)

> *Tip:* in the controller you have the method `hasErrors()` and the field `errors` that capture the data binding errors at the controller level.

4. Run the unit test until it pass it

5. Add the following test method to the *ChatControllerTest* unit test

```groovy
	void testSendWithInvalidJson(){
		request.content = "not a json"
		controller.send()
		assert response.status == 400
		assert response.text == '{"error":"Invalid body"}'
	}
```

6. Implement the logic in the *ChatController.send* method to hadle the invalid JSON error. Use the unit test to validate your implementation.

> *Tip:* You can check if `request.JSON` exists

7. Add the following test methods to the *ChatControllerTest* unit test

```groovy
	void testSendWithMissingNick(){
		request.content = '{"message":"hi"}'
		controller.send()
		assert response.status == 400
		assert response.text == '{"error":"Missing nick parameter"}'
	}
	
	
	void testSendWithMissingMessage(){
		request.content = '{"nick":"bob"}'
		controller.send()
		assert response.status == 400
		assert response.text == '{"error":"Missing message parameter"}'
	}
```

8. Using contraints (refer to this [doc][8]), implement the logic to validate the *ChatMessage* object created on the *send* method of the controller

> *Tip:* You want to call the `validate()` method on the *ChatMessage* instance

9. Run the server locally ``grails run-app``

10. Test the error handling by calling the API

```
$ curl http://localhost:8080/chat-kata/api/chat?seq=invalid
{"error":"Invalid seq parameter"}%

$ curl http://localhost:8080/chat-kata/api/chat --data '{"nick":"alex"}' --header "Content-Type: text/json; charset=UTF-8" -X POST
{"error":"Missing message parameter"}

$ curl http://localhost:8080/chat-kata/api/chat --data '{"message":"jsut message"}' --header "Content-Type: text/json; charset=UTF-8" -X POST
{"error":"Missing nick parameter"}

$ curl http://localhost:8080/chat-kata/api/chat --data 'blah blah blah' --header "Content-Type: text/json; charset=UTF-8" -X POST
{"error":"Invalid body"}
```

PARTE V - Deploy to an application server
------------------------------------------

1. Install Tomcat 7 in your local machine following the instrunctions from [here][9]

2. Generate a WAR file of the application ``grails run-app``

> the WAR file will be created on the *target* directory

3. Rename the WAR file to *chat-kata.war*

4. Copy the WAR file to the *webapp* directory located on the directory of your tomcat installation (*CATALINA_HOME*)

5. Start tomcat ``CATALINA_HOME/bin/startup.sh``

6. You should have your application running on [http://localhost:8080/chat-kata]


<a rel="license" href="http://creativecommons.org/licenses/by/3.0/deed.en_US"><img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by/3.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/deed.en_US">Creative Commons Attribution 3.0 Unported License</a>.

[1]: http://grails.org/doc/2.2.1/guide/testing.html "Grails Unit Testing"
[2]: http://grails.org/doc/2.2.1/ref/Controllers/render.html "Grails reder user guide"
[3]: http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html "ReentrantReadWriteLock"
[4]: http://grails.org/doc/2.2.1/guide/services.html#dependencyInjectionServices "Grails services dependency injection"
[5]: http://grails.org/doc/2.2.1/guide/testing.html#mockingCollaborators "Grails mocking collaborations"
[6]: http://groovy.codehaus.org/Using+MockFor+and+StubFor "Using MockFor and StubFor"
[7]: http://www.kelvin-williams.dk/?p=53 "Using Mock Annotation and mockFor in Grails Unit Tests"
[8]: http://grails.org/doc/latest/guide/validation.html "Grails validation"
[9]: http://tomcat.apache.org/ "Apache Tomcat"