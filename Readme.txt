Usage of the application:

java -jar MiniGameBackend-1.0.jar [port(optional)]

if port is not provided, server will be initialized in the default port (8080)

The entry point as specified in the test are:

	GET http://localhost:[port]/<userid>/login
	POST http://localhost:[port]/<levelId>/score?sessionkey=<sessionId> (body) <score> 
	GET http://localhost:[port]/<levelId>/highscorelist

Dependecies

junit v.4.11 (included if maven is used)
JDK v8
	
Design considerations


*Business 

In order to all Business stuff of user scores and sessions can be reused I decided to develop Managers Singleton in order to make accessible a unique instance of each manager.

*Data

>Session
	
For Session storage I decided to use a ConcurrentHashMap that provides a thread safe implementation of a hash map in order to easily retrieve the session having the session Key.The ConcurrentHashMap is slow when there are a lot of updates/deltes but works well with a lot of reads. The performance of the usage of this hash map could be better if one user can't have various sessions at the same time, because it supposed less updates/deletes.For session key generation I decide to use the UUID.randomUUID(), that is a thread safe implementation of a generation of pseudo-random UID's. To accomplish to the specifications of the test, the special characters of the generated string will be deleted.
Every time a user tries to post a score with a particular session Key, and it session is expire, the session object will be removed from the map, also a task is scheduled that cleans the expired sessions of the map in order not to increase to infinite the size of this structure.

>User Score and Game Level

In order to maintain a sorted list of the best scores in the game level, I decided to use a ConcurrentSkipListSet that provides a thread safe implementation of a sorted set defined by the UserScore class that implements Comparator. To decide the order of two equal scores of different users, the user with the greater id will be positioned after. 


To maintain all the data structures accessible for all the processes, I also implemented another singleton as "datasource"


*Server

An HttpFilter was implemented, that extracts the body, the query string and the path parameters from the request, this implementation of the filter is generic and can be reused to extract this kind of data of any request.

Also an a serial of processors was implemented that look for the specific parameters of each expected request and calls the business managers to do the action requested and also writes the response.
	
If the request goes well, an 200 OK code is returned, otherwise an error code is returned depending on the kind of error occurred, 404 if the request is not correct, 401 if the sessionKey is not correct or the session is expired and so on.

