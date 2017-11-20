#!groovy

node {

    stage('Checkout sources') {
        git url: 'https://github.com/bb4/bb4-common.git'
    }

    stage ('build')
    if (isUnix()) {
        sh './gradlew clean build --refresh-dependencies'
    } else {
        bat './gradlew.bat clean build --refresh-dependencies'
    }

    stage ('test')
    if (isUnix()) {
        sh './gradlew test'
    } else {
        bat './gradlew.bat test'
    }

}

