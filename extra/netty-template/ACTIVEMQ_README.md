# ActiveMQ Classic Integration

## Overview
This project includes ActiveMQ Classic 5.18.3 integration with sample producer and consumer implementations.

## Connection Details
- **Broker URL**: `tcp://localhost:61616`
- **Username**: `admin`
- **Password**: `admin`
- **Default Queue**: `sample.queue`

## Files Created
- `ActiveMQConfig.java` - Connection configuration and management
- `ActiveMQProducer.java` - Message producer with queue/topic support
- `ActiveMQConsumer.java` - Message consumer with sync/async modes
- `ActiveMQSample.java` - Sample application demonstrating usage

## Prerequisites
1. Install and start ActiveMQ Classic 5.19:
   ```cmd
   REM Download from: https://activemq.apache.org/components/classic/download/
   REM Extract and run:
   cd apache-activemq-5.19.0\bin
   activemq start
   ```

2. Verify ActiveMQ is running:
   - Admin Console: http://localhost:8161/admin
   - Login with admin/admin

## Build Project
```cmd
mvn clean package
```

## Running Examples

### Option 1: Run Both Producer and Consumer
```cmd
java -cp target\netty-server-standalone.jar com.example.netty.activemq.ActiveMQSample both
```

### Option 2: Run Producer Only
```cmd
java -cp target\netty-server-standalone.jar com.example.netty.activemq.ActiveMQSample producer
```

### Option 3: Run Consumer Only (in separate terminal)
```cmd
java -cp target\netty-server-standalone.jar com.example.netty.activemq.ActiveMQSample consumer
```

### Option 4: Run Individual Classes
Producer:
```cmd
java -cp target\netty-server-standalone.jar com.example.netty.activemq.ActiveMQProducer
```

Consumer:
```cmd
java -cp target\netty-server-standalone.jar com.example.netty.activemq.ActiveMQConsumer
```

## Usage in Your Code

### Producer Example
```java
ActiveMQProducer producer = new ActiveMQProducer();
producer.sendMessage("Hello ActiveMQ!");
producer.sendMessage("Priority message", "priority", "high");
producer.close();
```

### Consumer Example (Synchronous)
```java
ActiveMQConsumer consumer = new ActiveMQConsumer();
String message = consumer.receiveMessage(5000); // 5 second timeout
System.out.println("Received: " + message);
consumer.close();
```

### Consumer Example (Asynchronous)
```java
ActiveMQConsumer consumer = new ActiveMQConsumer();
consumer.startListening(); // Uses default message handler
// Keep application running...
```

### Custom Topics
```java
// Producer for topic
ActiveMQProducer topicProducer = new ActiveMQProducer("my.topic", true);
topicProducer.sendMessage("Topic message");

// Consumer for topic
ActiveMQConsumer topicConsumer = new ActiveMQConsumer("my.topic", true);
topicConsumer.startListening();
```

## Testing
1. Start ActiveMQ broker
2. Run consumer in one terminal
3. Run producer in another terminal
4. Check messages in ActiveMQ admin console

## Troubleshooting
- **Connection refused**: Make sure ActiveMQ is running on localhost:61616
- **Authentication failed**: Verify username/password in ActiveMQConfig.java
- **ClassNotFoundException**: Make sure to use the standalone JAR with all dependencies
