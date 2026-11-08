# Get maven jars lib
```
mvn dependency:copy-dependencies -DoutputDirectory=lib

mvn dependency:copy-dependencies -DincludeScope=runtime

mvn clean dependency:copy-dependencies
```