Feature: network communication with livelink controller
to establish a network communication with livelink controller

Scenario outline: Resolve the livelink mdns service instance object for communication purposes

Given <service-instance-name> is the service-instance-name of livelink that is found(added) on Network

When resolve <service-instance-name> as service instance name of type _http._tcp and domain .local on Network

Then <hostname> is the hostname value that should be parsed on Network
Then <port> is the port value that should be parsed on Network
Then <client> should be able to establish a network communication with livelink controller on Network

Examples:
|service-instance-name|hostname|port|client
|livelinkapp[12345]   |livelink|8443|Android iOS
