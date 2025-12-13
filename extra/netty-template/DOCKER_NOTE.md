# Pull
docker pull almalinux:9.7-minimal

# Run
docker run -it --name my_container almalinux:9.7-minimal /bin/bash

# Install apps
microdnf install -y java-11-openjdk-devel unzip vi && microdnf clean all

# Copy file/folder from host to container
docker cp ./container_assets/apache-maven-3.9.11-bin.zip my_container:/home/apache-maven-3.9.11-bin.zip

# Unzip
unzip /home/apache-maven-3.9.11-bin.zip -d /home/maven/

# Set PATH
vi /etc/profile
At the end of profile, insert: export PATH=/home/maven/apache-maven-3.9.11/bin:$PATH
source /etc/profile
echo $PATH

# Container to boilerplate image
docker commit my_container my_image:v1

# Run boilerplate image
docker run -it --name my_container_final my_image:v1 /bin/bash

# Enter running boilerplate container
docker exec -it my_container_final bash -l

# Build Dockerfile
docker build -t demo1:tag1 .

# Run app image
docker run --name demo1_container -p 8080:8080 -p 8443:8443 -d demo1:tag1 java -jar /home/app/target/netty-server-standalone.jar

# Keep alive run to check if container is stopped after running
docker run --name demo1_container -d demo1:tag1 bash -c "while true; do sleep 60; done"

# Enter running app container
docker exec -it demo1_container bash -l

