## Create a broadcast receiver to send and receive data from Android system and different apps

## 2 main type of receiver: STATIC & DYNAMIC

+ Static broadcast receiver:  

Declared in the manifest file with an intent filter to specifies the type of broadcast it should listen for.

This receiver runs in the background even if the app is not running (STATIC) -> Useful for system events that do not depend on the app's state, such as network state changes or device reboots.


+ Dynamic broadcast receiver:

Registered and unregistered programmatically at app's runtime (Eg: in MainActivity class).

This receiver runs in the foreground when the app is running (DYNAMIC) and is used to handle events that depend on the app's state, such as button clicks or user input.

## NOTE:

- dynamic_br folder is a project, in which a dynamic broadcast receiver is created to listen to change in Wifi connection
- custom_br folder is a project, in which a broadcast receiver is created to send data to local br and receive back
- explicit_br folder is a project with a explicit broadcast receiver using an intent to send the data directly to the br, action key is not required. Explicit br can also be used to send data among multiple projects as shown in the code

-> download the folder, change to src and replace your src folder to run
