How to Run This App ?

**Step 1**: Install Docker

**Step 2**: Create Network on for both the container to communicate in Docker.

`**docker network create docker-network**`

**Step 3**: Install MySql Docker Image using below command and run using the above created network.

**`docker container run --name mysqldb --network docker-network -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bank -d mysql`**

**Step 4**: mvn clean package on the project directory to create jar for the docker image of this repo.

**`mvn clean package`**

**Step 5**: Build Docker image using below command in repository root directory.

**`docker image build -t bank .`**

**Step 6**: Run the Docker Image using below command,

**`docker run --network docker-network -p 8080:8080 bank`**
