DISCLAIMER: I do not intend to offer any support, or even sane versioning, unless I happen to feel like it. Always feel free to open an issue, but don't expect a quick response.

LICENSE: See `LICENSE` file. MIT License, 2017, Alex Walterbos.

## Jarvis
Jarvis is a custom home automation server, and currently serves as a host project for my ~~tinkering around~~ side projects.
The goal is to create a central control point for the connected equipment via various ways of communication.

### Hub
Hub is Jarvis' central component. It is built with [Dropwizard](http://www.dropwizard.io/), and provides a RESTful(ish) api to communicate with. So far, it handles authentication/authorization and turning off lights in my house.

### Antenna
Antenna provides an interface to a GPIO header using [Pi4J](http://pi4j.com/). Via the GPIO header, it controlls a 433MHz transmitter. 

### Clifton
A 'Command Line Interface For Turning lights ON'. If you know their codewords, that is.
