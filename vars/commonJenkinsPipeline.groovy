/**
 * Shared Jenkinsfile pipline for all bb4 projects.
 * @param pipelineParams the are:
 *   gitUrl - the git repository url from github
 *   language - either java or scala
 *   deploymentTask - either publishArtifacts (default) for deploy
 * @author Barry Becker
 */
def call(Map pipelineParams) {

    def defaultParams = [language: "java", deploymentTask:"publishArtifacts"]
    def params = defaultParams << pipelineParams

    pipeline {
        agent any
        stages {
            stage('Checkout source') {
                steps {
                    git url: params.gitUrl, branch: 'master'
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
                    gradleCmd("${params.language}doc")
                }
            }

            stage('deploy') {
                steps {
                    gradleCmd(params.deploymentTask + " --info --refresh-dependencies")
                }
            }
        }
        post {
            always {
                junit 'build/test-results/test/*.xml'
                step([$class: 'JavadocArchiver', javadocDir: 'build/docs/' + params.language + 'doc', keepAll: true])
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