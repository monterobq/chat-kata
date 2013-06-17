chat-kata
=========

A Code Kata for building and REST client-server chat app for Android

PART I - Make a simple REST service
------------------------------------

1. Read about grails unit testing [here][1] and check the provided ChatControllerTest unit test (in test/unit/chat/kata).
2. Run the unit test within your IDE, it should fail.
     
   - You can also run it from the command line:  `grails test-app`

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
```

> Note how we use a JSON builder with the render function . Read this [documentation][2]  

4. Open the provided URLMappingTests unit test (in /test/unit) and complete the method 'testCharListUrlMapping" with the following content

```groovy
    assertRestForwardUrlMapping("GET", "/api/chat", controller:"chat", action:"list")
```

5. test that the method fails since we have not created this mapping yet

6. Add the following content to UrlMappings (in /grails-app/config) to create the expected mapping and make the test past

```groovy
    "/api/chat/"(controller: "chat"){
	     action = [GET: "list"]
    }
```

7. Run the unit test again and check that now it passes

7. Run the server locally ``grails run-app``

8. When grails finish bootstrapping the test server, you should see this message:

> | Server running. Browse to http://localhost:8080/chat-kata

Now open the URL http://localhost:8080/chat-kata/api/chat in your browser to test the response




[1]: http://grails.org/doc/2.2.1/guide/testing.html "Grails Unit Testing"
[2]: http://grails.org/doc/2.2.1/ref/Controllers/render.html "Grails reder user guide"
