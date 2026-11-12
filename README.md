# bb4-common

📊 [Build status for all bb4 projects](https://github.com/bb4/.github)

This library project contains common scala code for all bb4 projects.
The bb4-common jar file along with the corresponding source and scaladoc will be published to Sonatype so other projects depend on them.

**Build conventions:**
this repo uses [bb4-gradle](https://github.com/barrybecker4/bb4-gradle) **3.0-SNAPSHOT** via `com.barrybecker4.bb4.settings` 
and the `bb4Libs` version catalog (Java **25**, Gradle **9.1+**, wrapper **9.8.2**). 
A sibling `../bb4-gradle` checkout is preferred for local plugin work; otherwise the plugin resolves from Maven Central snapshots.

To use, include this dependency in your build.gradle file (catalog consumers can write `implementation bb4Libs.common`):

`implementation 'com.barrybecker4:bb4-common:<version>'`

## Internationalization

User-visible library strings live in `com.barrybecker4.common.i18n.message` (`MessageContext.COMMON_BUNDLE`). Applications should still call `AppContext.initialize(locale, List(MessageContext.COMMON_BUNDLE, …), logger)` so the active locale and app-specific bundles are set. Library helpers that call `AppContext.getLabel` also work without initialization by falling back to the English common bundle shipped in the JAR.

## Running bb4 applications

This repo is a **library** published to Sonatype; consumers add the dependency above. It is not an application you run on its own.

To build, test, and run bb4 **applications** (games, puzzles, simulations, and similar), see the [Building bb4 Projects](https://github.com/bb4/bb4-common/wiki/Building-bb4-Projects) wiki. Installers and zip bundles for end users come from each application’s GitHub Releases (native `.dmg` / `.msi` / `.deb` where available, or a zip with `bin/` run scripts). Many apps accept `-locale "<locale>"` on the command line (`ENGLISH`, `GERMAN`, `VIETNAMESE`, `JAPANESE`).

Shared HTML used in legacy zip-based distribution trees (for example `bb4-common.css` and run instructions linked from application index pages) lives in [bb4-website](https://github.com/bb4/bb4-website/tree/master/source/html/barrybecker4/bb4-projects/bb4-common) and on [barrybecker4.org](https://barrybecker4.org/bb4-projects/bb4-common/run-instructions.html).

## Building from source

Requires **JDK 25+** for the Gradle daemon (bb4-gradle 3.0).
See the [Building bb4 Projects wiki](https://github.com/bb4/bb4-common/wiki/Building-bb4-Projects).
