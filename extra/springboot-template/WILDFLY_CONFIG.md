# Change limit of upload size
1. Open \standalone\configuration\standalone.xml
1. Change max-post-size value in http-listener: 
```
<http-listener name="default" socket-binding="http" max-post-size="2147483648" redirect-socket="https" enable-http2="true"/>
```

# Change heap size
1. Open \bin\standalone.conf.bat (if Windows)
1. Change to 4G, 8G...
```
set "JBOSS_JAVA_SIZING=-Xms4G -Xmx8G -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m"
```