#!groovy

node {

    def gradle(tasks) {
        if (isUnix()) {
            sh './gradlew ${tasks}'
        } else {
            bat './gradlew.bat ${tasks}'
        }
    }

    stage('Checkout sources') {
        git url: 'https://github.com/bb4/bb4-common.git'
    }

    stage 'build'
    gradle("clean build --refresh-dependencies")

    stage 'test'
    gradle("test")
}

