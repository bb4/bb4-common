# bb4-common
This library project contains common java code for all bb4 projects.
The bb4-common jar file along with the corresponding source and javadoc will be published to Sonatype so other projects can easily depend on it.

### [How to Build](https://github.com/barrybecker4/bb4-common/wiki/Building-bb4-Projects)
If you have not already done so, first install [Git](http://git-scm.com/) and [Intellij](http://www.jetbrains.com/idea/). You also need to have [java SDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed and set the JAVA_HOME environment variable to point to the location where it is installed.

Type 'gradlew build' at the root (prepend with ./ if running on Cygwin or Linux). This will build everything, but since it is a library project there won't be much to see. If you want to open the source in Intellij, then first run 'gradlew idea'.

When there is a new release, versioned artifacts will be published by Barry Becker to [Sonatype](https://oss.sonatype.org).

### License
All source (unless otherwise specified in individual file) is provided under the [MIT License](http://www.opensource.org/licenses/MIT)



