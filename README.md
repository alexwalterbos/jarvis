# Jarvis
Jarvis is a custom home automation server, and currently serves as a host project for my improbable side projects of a completely feasible scale.
The goal is to create a central control point for the connected equipment via various ways of communication.

## Hub
Hub is Jarvis' component responsible for processing requests by a client, for example to send a codeword.
It consists of a [Dropwizard](http://www.dropwizard.io/) setup that provides a RESTful API to communicate with.

## Antenna
Antenna provides an interface to a GPIO header using [Pi4J](http://pi4j.com/). 
Via the GPIO header, it controlls a 433MHz transmitter (receiver functionality will be added later).
It allows to send 'codewords' through the antenna, aiming to reach 433MHz-enabled equipment throughout the environment, like remote-controllable lights.

Antenna is designed to run on a Raspberry Pi, to which a transmitter is connected via the GPIO header.
