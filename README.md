Blockdaemon SDK for JAVA
======

Java SDK for service [Blockdaemon](https://blockdaemon.com/).
Implementation of [contract](https://ubiquity.docs.blockdaemon.com/swagger-ui/)

## Integration to your project
### Make commands in new project workspace:
```shell script
git clone https://github.com/prpaha/blockdaemon-sdk-java.git
cd blockdaemon-sdk-java
gradle clean
gradle build
gradle publishToMavenLocal
```
Add dependency to your project, for example in gradle: 
```yaml
implementation "ru.prpaha.blockdaemon:blockdaemon-sdk-java:0.0.1"
```

### Library Settings:
Add properties to your project
```yaml
blockdaemon:
  network: 'mainnet'
  apiKey: '${BLOCKDAEMON_API_KEY}'
  debugging: true
```

### Invoke methods:
For autoconfiguration import BlockdaemonSDKConfiguration to your Spring:
```java
@Import(BlockdaemonSDKConfiguration.class)
```
Autowired bean ru.prpaha.blockdaemon.TransactionsService to your code. For example:

```java
@Component
class SomeClass {
    @Autowired
    private BlockdaemonService blockdaemonService;
    
    public void someMethod() {
        blockdaemonService.callSomeMethod(...);
    }
}
```

<br/>
<br/>
#blockdaemon-sdk-java
#spring-boot
#gradle
#blockdaemon
#peernetwork
