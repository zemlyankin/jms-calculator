## Overview

This is an example project that provides simple calculator functionality via JMS.

## Preparing environment
Before running application, ActiveMQ broker should be started.

The simplest you can do is to run ActiveMQ in Docker.
```
docker pull rmohr/activemq
docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
```

The ActiveMQ console can be used to play with calculator.

## Messages format

The messages are in JSON format.

### Request
```json
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
```json
{
    "result": Integer
}
```
or
```json
{
    "error": "String"
}
```

## Queues

Request queue: `org.example.jms-calculator.request`

Response queue: `org.example.jms-calculator.response`
