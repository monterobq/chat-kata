package chat.kata

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ChatController)
class ChatControllerTests {

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
}
