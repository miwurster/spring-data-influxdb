
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
<dependency>
  <groupId>org.influxdb</groupId>
  <artifactId>influxdb-java</artifactId>
  <version>2.5</version>
</dependency>
```

## Usage (Spring Boot)

* Following properties can be used in your `application.yml`:

    ```yml
    spring:
      influxdb:
        url: http://localhost:8086
        username: user
        password: ~
        database: test
        retention-policy: autogen
    ```

* Create `InfluxDBConnectionFactory` and `InfluxDBTemplate` beans:

    ```java
    @Configuration
    @EnableConfigurationProperties(InfluxDBProperties.class)
    public class InfluxDBConfiguration
    {
      @Bean
      public InfluxDBConnectionFactory connectionFactory(final InfluxDBProperties properties)
      {
        return new InfluxDBConnectionFactory(properties);
      }

      @Bean
      public InfluxDBTemplate<Point> influxDBTemplate(final InfluxDBConnectionFactory connectionFactory)
      {
        /*
         * You can use your own 'PointCollectionConverter' implementation, e.g. in case
         * you want to use your own custom measurement object.
         */
        return new InfluxDBTemplate<>(connectionFactory, new PointConverter());
      }
      
      @Bean
      public DefaultInfluxDBTemplate defaultTemplate(final InfluxDBConnectionFactory connectionFactory)
      {
        /*
         * If you are just dealing with Point objects from 'influxdb-java' you could
         * also use an instance of class DefaultInfluxDBTemplate.
         */
        return new DefaultInfluxDBTemplate(connectionFactory);
      }
    }
    ```

* Use `InfluxDBTemplate` to interact with the InfluxDB database:

    ```java
    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    influxDBTemplate.createDatabase();
    final Point p = Point.measurement("disk")
      .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
      .tag("tenant", "default")
      .addField("used", 80L)
      .addField("free", 1L)
      .build();
    influxDBTemplate.write(p);
    ```

## Building

Spring Data InfluxDB uses Maven as its build system. 

```bash
mvn clean install
```
