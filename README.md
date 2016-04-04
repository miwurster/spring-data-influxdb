
Spring Data InfluxDB
--------------------

The primary goal of the [Spring Data](http://projects.spring.io/spring-data/) project is to make it easier to build Spring-powered applications that use new data access technologies such as non-relational databases, map-reduce frameworks, and cloud based data services.

This modules provides integration with the [InfluxDB](https://influxdata.com/) database and wraps the capabilities of the official [influxdb-java](https://github.com/influxdata/influxdb-java) library.

## Artifacts

### Maven

```xml
<dependency>
  <groupId>com.github.miwurster</groupId>
  <artifactId>spring-data-influxdb</artifactId>
  <version>${version}</version>
</dependency> 
```

## Usage (Spring Boot)

* Following properties can be used in your `application.yml`:

    ```yml
    spring:
      influxdb:
        https: false
        hostname: localhost
        port: 8086
        username: root
        password: root
        database: default
        retention-policy: default
    ```

* Configure the InfluxDB connection factory:

    ```java
    @Configuration
    @EnableConfigurationProperties(InfluxDBProperties.class)
    public class InfluxDBConfiguration
    {
      @Bean
      public ConversionService conversionService()
      {
        final DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(/* ... */);
        return conversionService;
      }

      @Bean
      public InfluxDBConnectionFactory connectionFactory(final InfluxDBProperties properties)
      {
        return new InfluxDBConnectionFactory(properties);
      }

      @Bean
      public InfluxDBTemplate<Measurements> influxDBTemplate(final InfluxDBConnectionFactory connectionFactory)
      {
        return new InfluxDBTemplate<>(connectionFactory, conversionService());
      }
    }
    ```

* Use `InfluxDBTemplate` to interact with the InfluxDB database:

    ```java
    @Autowired
    private InfluxDBTemplate<String> influxDBTemplate;
    
    influxDBTemplate.createDatabase();
    influxDBTemplate.write("cpu_load_short=0.64");
    ```

## Building

Spring Data InfluxDB uses Maven as its build system. 

```bash
mvn clean install
```
