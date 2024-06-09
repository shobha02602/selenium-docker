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
            steps{
                bat "docker push shobha02602/selenium"
            }
        }
    }
}