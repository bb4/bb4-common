# bb4-common
Contains common java code for all bb4 projects.
It is a library project that produces a bb4-common-1.x jar file.
This jar file along with the source and javadoc jars will be published to Sonatype so
other projects can easily depend on it.

### How to Build
If you have not already done so, first install [Git](http://git-scm.com/), [Gradle](http://www.gradle.org/), and [Intellij](http://www.jetbrains.com/idea/).

Type 'gradle build' at the root. This will build everything, but since its a library project there won't be much to see.
If you want to open the source in Intellij, then first run 'gradle idea'.

When there is a new release, versioned artifacts will be published by Barry Becker to [Sonatype](https://oss.sonatype.org).

### License
All source (unless otherwise specified in individual file) is provided under the [MIT License](http://www.opensource.org/licenses/MIT)



