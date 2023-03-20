### Intent Service
This is a demo app on how to implement IntentService.

### Intent service key points.

* IntentService is an extension of the Service component class that handles asynchronous requests (expressed as Intents) on demand. Clients send requests through Context.startService(Intent) calls; the service is started as needed, handles each Intent in turn using a worker thread, and stops itself when it runs out of work.
* All requests are handled on a single worker thread -- they may take as long as necessary (and will not block the application's main loop), but only one request will be processed at a time.
* Intent service runs on worker thread. So, it will not block the main thread.

### Start & Stop service
```
startService(Intent(this,MyIntentService::class.java))
```
* Intent service stops itself when it runs out of work.
* IntentService automatically stops itself when onHandleIntent() ends, if no more commands had been sent to it while onHandleIntent() was running. Hence, you do not manually stop an IntentService yourself.
* Calling stopService() does not stop the service immediately. When stopService() is called, the service is marked for termination and its onDestroy() method is called. However, the system may not immediately destroy the service, as there may still be ongoing work being done in the service.

### Communication b/w activity & service.

* We can use broadcast receiver to communicate/pass the data between them.
* We can also use iBinder, Livedata, RoomDb.


