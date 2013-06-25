
PART II - Make a simple Android client
---------------------------------------

1. Open activities LoginActivity and ChatActivity

 >* The first one is the main activity and the second one is the chat room. 

 >* You're going to implement these activities. 

2. Open NetRequests and NetResponseHandler.

 >* These classes are the base of aplication's networking. The first has methods for api requests. 
	
 >* The second is a callback to handle asynchronous requests.
	
 >* You must implement these classes according the API documentation [here][1]
	

3. Open classes in model package.

 >* These classes model the API information model.
	
 >* They're empty so you must implement them according the API documentation [here][1]
      

4. Add to LoginActivity a field to enter user nick and a button to login into the chat room.

 >TIPS:
	
 >* You may use the GUI designer provided by ADT 
	
 >* Login button must store nick and navigate to ChatActivity on click.
	
 >* Use SharedPreferences to store user information
	
5. Add to ChatActivity a scrollable area in which you'll write chat messages. 

6. Add to ChatActivity a field to enter messages and a button to send them.

7. Implement NetRequests.chatPOST and invoke it from Send Button's onclick

 >TIPS: 
	
 >* You may use HttpClient bundled with Android
	
 >* Write message to screen on success
	
 >* Remember to modify UI on a UI thread
	
8. Implement NetRequests.chatGET and invoke it from ChatActivity's onStart method

 >TIPS:
	
 >* Use [JSONObject](http://developer.android.com/reference/org/json/JSONObject.html) or other method to serialize/deserialize JSON
	
9. Invoke NetRequests.chatGET periodically.

 >TIPS:
	
 >* Use Android's [Handler.postDelayed](http://developer.android.com/reference/android/os/Handler.html)
	
 >* Schedule next invocation on success
	
 >* Handle Activity Lifecycle to stop scheduling
	 

Additional
-----------


Read about JUnit 3 testing [here][2] (Android Framework only supports JUnit 3) and about Android unit testing [here][3]

Unfortunately, current documentation refers to JUnit 4. You can find some doc. about JUnit 3 [here][4] and [here][5]


<a rel="license" href="http://creativecommons.org/licenses/by/3.0/deed.en_US"><img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by/3.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/deed.en_US">Creative Commons Attribution 3.0 Unported License</a>.


[1]: https://github.com/bqreaders/chat-kata/ "KATA API"
[2]: http://junit.sourceforge.net/junit3.8.1/ "JUnit 3"
[3]: http://developer.android.com/tools/testing/testing_android.html "Android Testing Fundamentals"
[4]: http://junit.sourceforge.net/doc/cookstour/cookstour.htm
[5]: http://pub.admc.com/howtos/junit3x/junit3x.html 
