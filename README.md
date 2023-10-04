sbt -v docker:publishLocal
cd target/docker/stage
docker build -t docker-minimal .
docker ps
docker images
docker run -dp 127.0.0.1:3000:3000 -v /home/michael/docker/log:/opt/docker/log docker-minimal
cd -
