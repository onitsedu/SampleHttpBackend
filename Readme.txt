Usage of the application:

java -jar KingMiniGameBackend-1.0.jar [port(optional)]

if port is not provided, server will be initialized in the default port (8080)

The entry point as specified in the test are:

	GET http://localhost:[port]/<userid>/login
	POST http://localhost:[port]/<levelId>/score?sessionkey=<sessionId> (body) <score> 
	GET http://localhost:[port]/<levelId>/highscorelist
	
Design considerations

Session
	
	
For Session storage I decided to use a ConcurrentHashMap that 
For session key generation I decide to use the UUID.randomUUID()

User Score

Game Level

In order to all Business stuff of user scores and sessions can be reused I decided to develop Managers Singleton in order to make accessible a unique instance of each manager.