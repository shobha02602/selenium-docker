pipeline{

    agent any

    stages{

        stage('Build Jar'){
            steps{
                bat "mvn clean package -DskipTests"
            }

        }
        stage('Build Image'){
            steps{
                bat "docker build -t=shobha02602/selenium ."
            } 

        }
        stage('Push Image'){
            environment{
                DOCKER_HUB = credentials('dockerhub-creds')
            }
            steps{
                //using single quotes below because here we want to send literal string
                bat 'docker login -u ${DOCKER_HUB_USR} -p ${DOCKER_HUB_PSW}'
                //bat 'echo %DOCKER_HUB_PSW% | docker login -u %DOCKER_HUB_USR% --password-stdin'
                bat "docker push shobha02602/selenium:latest"
                bat "docker tag shobha02602/selenium:latest shobha02602/selenium:latest:%env.BUILD_NUMBER%"
                bat "docker push shobha02602/selenium:latest:%env.BUILD_NUMBER%"
            }
        }
    }

    post {
        always {
            bat "docker logout"
        }
    }
}