package chat.kata

class ChatController {

	def list(Integer seq) {
		if(hasErrors()){
			log.error("Invalid seq: ${errors.getFieldError('seq').rejectedValue}")
			//TODO: send error about invalid seq
		}
		render(contentType: "text/json") {
			messages = [
				{
					nick = "user1"
					message = "hello"
				},
				{
					nick = "user2"
					message = "hola"
				}
			]
			last_seq = 1
		}
	}

	def send(){
		//TODO: implement me
	}
}
