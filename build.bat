@ECHO OFF
IF "%2"=="quick" (gradlew -b build.gradle -x processResources -x test --parallel) ELSE (gradlew -b build.gradle -x test --parallel)