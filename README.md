# bb4-common

📊 [Build status for all bb4 projects](https://github.com/bb4/.github)

This library project contains common scala code for all bb4 projects.
The bb4-common jar file along with the corresponding source and scaladoc will be published to Sonatype so other projects depend on them.

**Build conventions:** 
this repo uses [bb4-gradle](https://github.com/barrybecker4/bb4-gradle) **2.1-SNAPSHOT** via the Gradle `plugins { }` DSL (Java **25**, Gradle **9.8.2**). `settings.gradle` adds **`https://central.sonatype.com/repository/maven-snapshots/`** to `pluginManagement` so Gradle can resolve that SNAPSHOT (OSSRH is EOL). Optionally, for local plugin work without publishing, use `mavenLocal()` there and run `./gradlew publishToMavenLocal` in bb4-gradle.

To use, include this dependency in your build.gradle file

`implementation 'com.barrybecker4:bb4-common:<version>'`

## Running bb4 applications

This repo is a **library** published to Sonatype; consumers add the dependency above. It is not an application you run on its own.

To build, test, and run bb4 **applications** (games, puzzles, simulations, and similar), see the [Building bb4 Projects](https://github.com/bb4/bb4-common/wiki/Building-bb4-Projects) wiki. Installers and zip bundles for end users come from each application’s GitHub Releases (native `.dmg` / `.msi` / `.deb` where available, or a zip with `bin/` run scripts). Many apps accept `-locale "<locale>"` on the command line (`ENGLISH`, `GERMAN`, `VIETNAMESE`, `JAPANESE`).

Shared HTML used in legacy zip-based distribution trees (for example `bb4-common.css` and run instructions linked from application index pages) lives in [bb4-website](https://github.com/bb4/bb4-website/tree/master/source/html/barrybecker4/bb4-projects/bb4-common) and on [barrybecker4.org](https://barrybecker4.org/bb4-projects/bb4-common/run-instructions.html).

## Building from source

Requires **JDK 25+** for the Gradle daemon (bb4-gradle 2.1). 
See the [Building bb4 Projects wiki](https://github.com/bb4/bb4-common/wiki/Building-bb4-Projects).
