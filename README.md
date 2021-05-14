Word Counter

# Usage - CLI

    ./mvnw compile exec:java -Dexec.args=/path/to/text/file  #mvnw[.cmd] 'Maven Wrapper 'is a distributable version of Maven

for example:

`./mvnw compile exec:java -Dexec.args=./src/test/resources/hello_world.txt`

this distribution includes it's own Maven (mvn) CLI distribution, for convenience, `mvnw`, you may of course supply you own.

# Usage - API Library

```
import com.rjoseph.WordCounter

//Then use either
StringwordCountReport = new wordCounter().createReportFromFile(filePathString)

//OR

StringwordCountReport = new wordCounter().createReportFromText(myTextString)

```

# Testing

Run the tests with `mvnw test` - see

# Todos
- [ ] Add/fix logging
- [ ] Configure 'Fat' executable Jar packaging (Maven/pom.xml)
- [ ] More testing scenarios

# Appendix
- https://maven.apache.org/archetypes/maven-archetype-quickstart/ => Maven Java project archetype bootstrap
-  https://github.com/takari/maven-wrapper => 'Maven Wrapper' redistributable Maven executable
- http://gitignore.io => Online gitignore constructor tool
