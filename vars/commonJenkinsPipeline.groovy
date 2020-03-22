/**
 * Shared Jenkinsfile pipeline for all bb4 projects.
 * See https://jenkins.io/doc/book/pipeline/shared-libraries/
 * @param pipelineParams the are:
 *   gitUrl - the git repository url from github.
 *   language - either java or scala.
 *   deploymentTask - either publishArtifacts (default) or deploy.
 *   upstreamProjects - list of projects that should trigger us to build when they are built successfully.
 *   docPath - location of generated java or scala doc files. default is "build/docs"
 * @author Barry Becker
 */
def call(Map pipelineParams) {

    def defaultParams = [
            language: "java",
            deploymentTask: "publishArtifacts",
            upstreamProjects: "",
            docPath: "build/docs"
    ]
    def params = defaultParams << pipelineParams

    pipeline {
        agent any
        options {
            buildDiscarder(logRotator(numToKeepStr: '7')) // keep only recent builds
        }
        triggers {
            //pollSCM('H/15 * * * *')
//            upstream(
//                    upstreamProjects: params.upstreamProjects == '' ? '-none-' : params.upstreamProjects,
//                    threshold: hudson.model.Result.SUCCESS
//            )
            upstream(upstreamProjects: params.upstreamProjects, threshold: hudson.model.Result.SUCCESS)
        }

        stages {
            stage('Checkout source') {
                steps {
                    checkout scm
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
                    //gradleCmd("tasks")
                    gradleCmd(params.deploymentTask + " --info")
                }
            }
        }
        post {
            always {
                junit "**/TEST-*.xml"  // 'build/test-results/test/*.xml'
                step([$class: 'JavadocArchiver',
                      javadocDir: params.docPath + '/' + params.language + 'doc',
                      keepAll: true])
            }
            success {
                echo 'This build was SUCCESSFUL!'
                //if (currentBuild.previousBuild() != null && currentBuild.previousBuild().result != 'SUCCESS') {
                //    mail to: 'barrybecker4@gmail.com',
                //            subject: "Successful Pipeline: ${currentBuild.fullDisplayName}",
                //            body: "Build is back to normal (success): ${env.BUILD_URL}"
                //}
            }
            failure {
                mail to: 'barrybecker4@gmail.com',
                        subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
                        body: """Something is wrong with ${env.BUILD_URL}.
                          |It is failing in ${env.FAILURE_STAGE} stage.
                          |\\u2639 ${env.JOB_NAME} (${env.BUILD_NUMBER}) has failed.
                          """
            }
            unstable {
                echo 'This build is unstable.'
            }
        }
    }
}

def cmd(cmd) {
    if (isUnix()) {
        sh "${cmd}"
    } else {
        bat "${cmd}"
    }
}

def gradleCmd(cmd) {
    if (isUnix()) {
        sh "./gradlew ${cmd}"
    } else {
        bat "./gradlew.bat ${cmd}"
    }
}