package chat.kata;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@TestFor(ChatService)
class ChatServiceTest {

	void testCollectAllMessages() {
		// Add a couple of messages to the chat
		def message1 = new ChatMessage([nick:"user1", message: "hello"])
		def message2 = new ChatMessage([nick:"user2", message: "hi there"])
		service.putChatMessage(message1)
		service.putChatMessage(message2)
		
		//Retrieve all messages from the chat
		List<ChatMessage> messages = new ArrayList()
		def lastSeq = service.collectChatMessages(messages)
		
		//Assert correct response
		assert messages.size() == 2
		assert messages[0] == message1
		assert messages[1] == message2
		assert lastSeq == 1
		
	}
	
	void testCollectMessagesFromSequence() {
		// Add some intial messages
		def message1 = new ChatMessage([nick:"user1", message: "hello"])
		def message2 = new ChatMessage([nick:"user2", message: "hi there"])
		service.putChatMessage(message1)
		service.putChatMessage(message2)
		
		// Collect those messages
		def lastSeq = service.collectChatMessages([])
		
		//Now the last seq should be 1
		assert lastSeq == 1
		
		// Let's put more messages
		def message3 = new ChatMessage([nick:"user1", message: "bye"])
		def message4 = new ChatMessage([nick:"user2", message: "ciao"])
		service.putChatMessage(message3)
		service.putChatMessage(message4)
		
		List<ChatMessage> messages = new ArrayList()
		lastSeq = service.collectChatMessages(messages, lastSeq)
		
		//Assert correct response
		assert messages.size() == 2
		assert messages[0] == message3
		assert messages[1] == message4
		assert lastSeq == 3
	}
	
	
	void testConcurrency() {
		final errors = []
		ExecutorService executor = Executors.newFixedThreadPool(2) 
		final CountDownLatch latch = new CountDownLatch(1)
		final service = this.service
		
		//make a writer thread
		executor.submit(new Runnable(){
			void run() {
				latch.await()
				for(i in 0..299){
					try{
						service.putChatMessage(new ChatMessage([nick:"writer", message: i]))
					}catch(Throwable e){
						errors.add(e)
					}
				}
			};
		})
		
		//make a reader thread
		executor.submit(new Runnable(){
			void run() {
				latch.await()
				for(i in 0..299){
					try{
						def m = []
						service.collectChatMessages(m)
						Thread.currentThread().sleep(5)
					}catch(Throwable e){
						errors.add(e)
					}
				}
			};
		})
		
		//launch both threads at the same time and wait for termination
		latch.countDown()
		executor.shutdown()
		executor.awaitTermination(1, TimeUnit.MINUTES)
		if(errors){
			for(def error : errors){
				error.printStackTrace()
			}
			fail "Concurrency errors"
		}
	}

}
