package chat.kata

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ChatController)
class ChatControllerTests {

	void setUp() {
		mockForConstraintsTests(ChatMessage)
	}
	
    void testListAll() {
       controller.list()
	   assert response.text == '{"messages":[{"nick":"user1","message":"hello"},{"nick":"user2","message":"hola"}],"last_seq":1}'
    }
}
