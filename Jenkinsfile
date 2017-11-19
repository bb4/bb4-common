def gradle(command) {
    sh "./gradlew ${command}"
}

node {
    echo "Hello from jenkins file with Gradle"
    gradle 'tasks'
}