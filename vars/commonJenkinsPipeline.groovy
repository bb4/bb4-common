def call(Map pipelineParams) {

    pipeline {
        agent any
        stages {
            stage('Checkout source') {
                steps {
                    git url: pipelineParams.gitUrl, branch: 'master'
                }
            }

            stage('build') {
                steps {
                    gradleCmd("clean build -x test --refresh-dependencies")
                }
            }

            stage('test') {
                steps {
                    gradleCmd("test")
                }
            }

            stage('documentation') {
                steps {
                    gradleCmd("${pipelineParams.language}doc")
                }
            }

            stage('publish') {
                steps {
                    gradleCmd("publishArtifacts --info --refresh-dependencies")
                }
            }
        }
        post {
            always {
                junit 'build/test-results/test/*.xml'
                def dir = 'build/docs/' + pipelineParams.language + 'doc'
                step([$class: 'JavadocArchiver', javadocDir: dir, keepAll: true])
            }
            success {
                mail to: 'barrybecker4@gmail.com',
                        subject: "Successful Pipeline: ${currentBuild.fullDisplayName}",
                        body: "This build succeeded: ${env.BUILD_URL}"
            }
            failure {
                mail to: 'barrybecker4@gmail.com',
                        subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
                        body: "Something is wrong with ${env.BUILD_URL}"
            }
            unstable {
                echo 'This build is unstable.'
            }
        }
    }
}

def gradleCmd(cmd) {
    if (isUnix()) {
        sh "./gradlew ${cmd}"
    } else {
        bat "./gradlew.bat ${cmd}"
    }
}