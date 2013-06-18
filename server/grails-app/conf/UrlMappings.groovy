class UrlMappings {

	static mappings = {
		"/api/chat/"(controller: "chat"){
			action = [GET: "list", POST : "send"]
		}
		"/"(view:"/index")
		"500"(view:'/error')
	}
}
