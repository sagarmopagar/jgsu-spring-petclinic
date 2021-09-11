pipeline {
    agent any

    triggers { pollSCM('* * * * *')}

    stages {
        stage('Checkout') {
            steps {

                git url: 'https://github.com/sagarmopagar/jgsu-spring-petclinic.git',
                branch: 'main'                
            }
        }
        stage('Build') {
            steps {
                bat 'mvnw.cmd clean package'
            }

            post {
                always {
                   junit '**/target/surefire-reports/TEST-*.xml'                    
                // }
                // changed{
                    emailext body: 'Please go to ${BUILD_URL} and verify the build', subject: 'Job \'${JOB_NAME}\' (${BUILD_NUMBER}) is waiting for input', to: 'sagarmopagar@gmail.com'
                }
            }
        }
    }
}
