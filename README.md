
./gradlew clean build
./gradlew generateAvroJava
./gradlew bootRun --args='--spring.profiles.active=dev'
