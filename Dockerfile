FROM bellsoft/liberica-openjdk-alpine:17.0.11

# install curl and jq : apk add curl jq
RUN apk add curl jq

# create a workspace
WORKDIR /home/selenium-docker

#Add required files to run the tests
ADD target/docker-resources ./
#Adding runner.sh to check when the hub is ready
ADD runner.sh   runner.sh

#Environment variables
#BROWSER
#HUB_HOST
#TEST_SUITE
#THREAD_COUNT



#Run the tests
# as we have already copied so libs will be current directory so /* will work
#ENTRYPOINT java -cp 'libs/*' \
 #          -Dselenium.grid.enabled=true \
  #         -Dselenium.grid.hubHost=${HUB_HOST} \
   #        -Dbrowser=${BROWSER} \
    #       org.testng.TestNG \
     #      -threadcount ${THREAD_COUNT} \
      #     test-suites/${TEST_SUITE}

      #After addig runner.sh it will go like below
#Start the runner.sh
ENTRYPOINT sh runner.sh