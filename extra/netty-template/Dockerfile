FROM my_image:v1

# Use login shell for all RUN commands
SHELL ["/bin/bash", "-l", "-c"]

WORKDIR /home/app

# Copy project files
COPY . .

# Build the project
RUN mvn clean package -DskipTests

EXPOSE 8080 8443

# Run with login shell to load environment variables from .bash_profile, .profile, etc.
CMD ["bash", "-l", "-c", "java -jar /home/app/target/netty-server-standalone.jar]
