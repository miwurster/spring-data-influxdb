package org.springframework.data.influxdb;

import com.google.common.collect.Lists;
import java.util.concurrent.TimeUnit;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner
{
  @Autowired
  private InfluxDBTemplate<Point> influxDBTemplate;

  @Override
  public void run(final String... args) throws Exception
  {
    influxDBTemplate.createDatabase();
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
  }

  public static void main(String[] args)
  {
    SpringApplication.run(Application.class, args);
  }
}
