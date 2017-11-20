sh('''#!C:/Program Files/Git/bin/bash.exe

def gradle(command) {
    sh "./gradlew ${command}"
}

stage 'build'
node {
    echo "Hello from jenkins file with Gradle"
    sh "./gradlew build"
    echo "done"
    //gradle 'tasks'
}

''')

