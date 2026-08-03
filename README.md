# bb4-common
This library project contains common scala code for all bb4 projects.
The bb4-common jar file along with the corresponding source and scaladoc will be published to Sonatype so other projects depend on them.

**Build conventions:** this repo uses [bb4-gradle](https://github.com/barrybecker4/bb4-gradle) **2.1-SNAPSHOT** via the Gradle `plugins { }` DSL. `settings.gradle` adds **`https://central.sonatype.com/repository/maven-snapshots/`** to `pluginManagement` so Gradle can resolve that SNAPSHOT (OSSRH is EOL). Optionally, for local plugin work without publishing, use `mavenLocal()` there and run `./gradlew publishToMavenLocal` in bb4-gradle.

To use, include this dependency in your gradle file

`compile 'com.barrybecker4:bb4-common:<version>`

### [How to Build](https://github.com/barrybecker4/bb4-common/wiki/Building-bb4-Projects)
If you have not already done so, first install [Git](http://git-scm.com/) and [Intellij](http://www.jetbrains.com/idea/).
You also need to have [java SDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed and set the JAVA_HOME environment variable to point to the location where it is installed.

Type 'gradlew build' at the root (prepend with ./ if running on Cygwin or Linux). This will build everything, but since it is a library project there won't be much to see. 

When there is a new release, versioned artifacts will be published to [Maven Central](https://central.sonatype.com/) (via the Central Publisher Portal).

### License
All source (unless otherwise specified in individual file) is provided under the [MIT License](http://www.opensource.org/licenses/MIT)
