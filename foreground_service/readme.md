Create a foreground service to play music.

- Feature:
+ Play/Pause music from the notification
+ Clear the music (stop the service) from the notification

- When start the service, a notification pop up
- The notification keep on running even when the app is terminated
- Only when we call StopService or the device run out of memory can the service be stop & the notification disappear
