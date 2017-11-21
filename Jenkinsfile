#!groovy

node {
    stage('Checkout sources') {
        git url: 'https://github.com/bb4/bb4-common.git', branch: 'master'
    }

    stage ('build/test') {
        gradleCmd("clean build --refresh-dependencies")
    }

    stage ('documentation') {
        gradleCmd("javadoc")
    }
}

def gradleCmd(cmd) {
    if (isUnix()) {
        sh "./gradlew ${cmd}"
    } else {
        bat "./gradlew.bat ${cmd}"
    }
}