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


PART II - Add a service layer to your service
---------------------------------------------

In this part we will add a service layer with the business logic for the storage and retrieval of chat messages.

1. Open the already created unit test *ChatServiceTest*. Carfully inspect the methods of this test case. You should be able to understand what the *ChatService* is expected to do by reading this unit test. Run the test, it should fail since we haven't implemented the logic yet.

2. Open the *ChatService* class (in _/grails-app/services/chat/kata_). Implement the two methods of this class: *collectChatMessages* and *putChatMessage*. You should use an ArrayList as the backend storage for chat messages. However, consider that this service will be used as a Singleton and therefore you need to deal with concurrent access to it. Since ArrayList is not a thread-save class you need to guard all accesses to it such that conccurent reads can occur, but writes can not occurr simultanously with any read or write operation. Use a [*ReentrantReadWriteLock*][3] for this.




[1]: http://grails.org/doc/2.2.1/guide/testing.html "Grails Unit Testing"
[2]: http://grails.org/doc/2.2.1/ref/Controllers/render.html "Grails reder user guide"
[3]: http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html "ReentrantReadWriteLock"