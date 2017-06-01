/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.influxdb;

import com.google.common.base.Preconditions;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.data.influxdb.converter.PointCollectionConverter;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class InfluxDBTemplate<T> extends InfluxDBAccessor implements InfluxDBOperations<T>
{
  private PointCollectionConverter<T> converter;

  public InfluxDBTemplate()
  {

  }

  public InfluxDBTemplate(final InfluxDBConnectionFactory connectionFactory, final PointCollectionConverter<T> converter)
  {
    setConnectionFactory(connectionFactory);
    setConverter(converter);
  }


  public void setConverter(final PointCollectionConverter<T> converter)
  {
    this.converter = converter;
  }

  @Override
  public void afterPropertiesSet()
  {
    super.afterPropertiesSet();
    Assert.notNull(converter, "PointCollectionConverter is required");
  }

  @Override
  public void createDatabase()
  {
    final String database = getDatabase();
    // Since Influx 1.0.0 this does only work if the database does not exist, otherwise "java.lang.RuntimeException: {"error":"error parsing query: found NOT, expected ; at line 1, char 20"}" is thrown
    // See https://github.com/jmxtrans/jmxtrans/issues/489 for example
    if (getCreateDatabase()) {
      getConnection().createDatabase(database);
    }
  }

  @Override
  public void write(final T payload)
  {
    Preconditions.checkArgument(payload != null, "Parameter 'payload' must not be null");
    final String database = getDatabase();
    final String retentionPolicy = getRetentionPolicy();
    final BatchPoints ops = BatchPoints.database(database)
      .retentionPolicy(retentionPolicy)
      .consistency(InfluxDB.ConsistencyLevel.ALL)
      .build();
    converter.convert(payload).forEach(ops::point);
    getConnection().write(ops);
  }

  @Override
  public void write(final List<T> payload)
  {
    Preconditions.checkArgument(payload != null, "Parameter 'payload' must not be null");
    final String database = getDatabase();
    final String retentionPolicy = getConnectionFactory().getProperties().getRetentionPolicy();
    final BatchPoints ops = BatchPoints.database(database)
      .retentionPolicy(retentionPolicy)
      .consistency(InfluxDB.ConsistencyLevel.ALL)
      .build();
    payload.forEach(t -> converter.convert(t).forEach(ops::point));
    getConnection().write(ops);
  }

  @Override
  public QueryResult query(final Query query)
  {
    return getConnection().query(query);
  }

  @Override
  public QueryResult query(final Query query, final TimeUnit timeUnit)
  {
    return getConnection().query(query, timeUnit);
  }

  @Override
  public Pong ping()
  {
    return getConnection().ping();
  }

  @Override
  public String version()
  {
    return getConnection().version();
  }
}
