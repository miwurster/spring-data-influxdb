
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

## Usage

* Configure the InfluxDB connection factory:

    ```java
    @Configuration
    @EnableConfigurationProperties
    public class InfluxDBConfiguration
    {
      @Bean(name = "org.springframework.data.influxdb.InfluxDBProperties")
      public InfluxDBProperties influxDBProperties()
      {
        return new InfluxDBProperties();
      }

      @Bean
      public ConversionServiceFactoryBean conversionService()
      {
        final ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        factoryBean.setConverters(/* ... */);
        return factoryBean;
      }

      @Configuration
      protected static class InfluxDBAutoConfiguration
      {
        @Autowired
        protected InfluxDBProperties properties;

        @Bean
        public InfluxDBConnectionFactory connectionFactory()
        {
          return new InfluxDBConnectionFactory(properties);
        }

        @Bean
        public InfluxDBTemplate<String> influxDBTemplate(final ConversionService conversionService)
        {
          return new InfluxDBTemplate<>(connectionFactory(), conversionService);
        }
      }
    }
    ```

* Use `InfluxDBTemplate` to interact with the InfluxDB database:

    ```java
    @Autowired
    private InfluxDBTemplate<String> influxDBTemplate;
    
    influxDBTemplate.createDatabase();
    influxDBTemplate.write("cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000");
    ```

## Building

Spring Data InfluxDB uses Maven as its build system. 

```bash
mvn clean install
```
