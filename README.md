## Overview

This is an example project that provides simple calculator functionality via JMS.

## Preparing environment
Before running application, ActiveMQ broker should be started.

The simplest you can do is to run ActiveMQ in Docker.
```
docker pull rmohr/activemq
docker run -it -p 61616:61616 -p 8161:8161 rmohr/activemq
```

The ActiveMQ console http://localhost:8161 can be used to play with calculator. Credentials are `admin:admin` by default.

## Messages format

The messages are in JSON format.

### Request

Requester is responsible to set JMSCorrelationID for further association with response.

Message body:
```
{
    "operator": "String",
    "operands": [ Integer, Integer, ... ]
}
```
Supported operands: `"add"`, `"sub"`, `"mul"`, `"div"`

Example:
```json
{"operator":"add","operands":[1,2,3]}
```

### Response

JMSCorrelationID is used to associate response with request.

Message body: 
```
{
    "result": Integer
}
```
or
```
{
    "error": "String"
}
```

## Queues

Request queue: `org.example.jms-calculator.request`

Response queue: `org.example.jms-calculator.response`
