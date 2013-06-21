package chat.kata

class ChatController {

	ChatService chatService

	def list(Integer seq) {
		if(hasErrors()){
			log.error("Invalid seq: ${errors.getFieldError('seq').rejectedValue}")
			//TODO: send error about invalid seq
		}
		final List chatMessages = []
		final int nextMessage = chatService.collectChatMessages(chatMessages, seq)
		render(contentType: "text/json") {
			messages = []
			for(currentMessage in chatMessages){
				messages.add([nick:currentMessage.nick, message:currentMessage.message])
			}
			last_seq = nextMessage
		}
	}

	def send(){
		def message = new ChatMessage(request.JSON)
		chatService.putChatMessage(message)
		render(status:201)
	}
}
