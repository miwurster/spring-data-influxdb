package org.springframework.data.influxdb;

import com.google.common.collect.Lists;
import java.util.concurrent.TimeUnit;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner
{
  private static Logger logger = LoggerFactory.getLogger(Application.class);

  @Autowired
  private InfluxDBTemplate<Point> influxDBTemplate;

  @Override
  public void run(final String... args) throws Exception
  {
    // Create database...
    influxDBTemplate.createDatabase();

    // Create some data...
    final Point p1 = Point.measurement("cpu")
      .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
      .tag("tenant", "default")
      .addField("idle", 90L)
      .addField("user", 9L)
      .addField("system", 1L)
      .build();
    final Point p2 = Point.measurement("disk")
      .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
      .tag("tenant", "default")
      .addField("used", 80L)
      .addField("free", 1L)
      .build();
    influxDBTemplate.write(Lists.newArrayList(p1, p2));

    // ... and query the latest data
    final String q = "SELECT last(*) FROM cpu GROUP BY tenant";
    final QueryResult queryResult = influxDBTemplate.getConnection().query(new Query(q, influxDBTemplate.getDatabase()));
    logger.info(queryResult.toString());
  }

  public static void main(String[] args)
  {
    SpringApplication.run(Application.class, args);
  }
}
