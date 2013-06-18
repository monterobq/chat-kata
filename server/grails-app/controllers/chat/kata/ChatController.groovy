package chat.kata


class ChatController {

	ChatService chatService

	def list(Integer seq) {
		if(hasErrors()){
			render(status: 400, contentType: "text/json") { 
				error = "Invalid seq parameter" 
			}
			return
		}
		final List _messages = []
		final int _lastSeq = chatService.collectChatMessages(_messages, seq)
		render(contentType: "text/json") {
			messages = []
			for(m in _messages){
				messages.add([nick:m.nick, message:m.message])
			}
			last_seq = _lastSeq
		}
	}

	def send(){
		if(!request.JSON){
			render(status: 400, contentType: "text/json") { 
				error = "Invalid body" 
			}
			return
		}
		def message = new ChatMessage(request.JSON)
		if(message.validate()){
			chatService.putChatMessage(message)
			render(status:201)
		}else{
			render(status: 400, contentType: "text/json"){ 
				if (message.errors.hasFieldErrors("nick")) {
					error = "Missing nick parameter"
				}
				if (message.errors.hasFieldErrors("message")) {
					error = "Missing message parameter"
				}
			}
		}
	}
}
