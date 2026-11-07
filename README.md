# bb4-common

📊 [Build status for all bb4 projects](https://github.com/bb4/.github)

This library project contains common scala code for all bb4 projects.
The bb4-common jar file along with the corresponding source and scaladoc will be published to Sonatype so other projects depend on them.

**Build conventions:** 
this repo uses [bb4-gradle](https://github.com/barrybecker4/bb4-gradle) **2.1-SNAPSHOT** via the Gradle `plugins { }` DSL (Java **25**, Gradle **9.8.2**). `settings.gradle` adds **`https://central.sonatype.com/repository/maven-snapshots/`** to `pluginManagement` so Gradle can resolve that SNAPSHOT (OSSRH is EOL). Optionally, for local plugin work without publishing, use `mavenLocal()` there and run `./gradlew publishToMavenLocal` in bb4-gradle.

To use, include this dependency in your build.gradle file

`implementation 'com.barrybecker4:bb4-common:<version>'`

## Building from source

Requires **JDK 25+** for the Gradle daemon (bb4-gradle 2.1). 
See the [Building bb4 Projects wiki](https://github.com/bb4/bb4-common/wiki/Building-bb4-Projects).
