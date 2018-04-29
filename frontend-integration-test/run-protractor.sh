#!/bin/bash

# build image
docker build -t wiwit/protractor:latest .

# remove <none> tag docker images
docker rmi $(docker images --filter "dangling=true" -q --no-trunc)

# run test
docker run -it --privileged --rm --net=host --shm-size 2g wiwit/protractor