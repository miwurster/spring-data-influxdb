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
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.Assert;

public class InfluxDBTemplate<T> extends InfluxDBAccessor implements InfluxDBOperations<T>
{
  private ConversionService conversionService;

  private final TypeDescriptor targetTypeDescriptor = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(Point.class));

  public InfluxDBTemplate()
  {

  }

  public InfluxDBTemplate(final InfluxDBConnectionFactory connectionFactory, final ConversionService conversionService)
  {
    setConnectionFactory(connectionFactory);
    setConversionService(conversionService);
  }

  @Override
  public void afterPropertiesSet()
  {
    super.afterPropertiesSet();
    Assert.notNull(getConversionService(), "ConversionService is required");
  }

  public ConversionService getConversionService()
  {
    return conversionService;
  }

  public void setConversionService(final ConversionService conversionService)
  {
    this.conversionService = conversionService;
  }

  @Override
  public void createDatabase()
  {
    final String database = getConnectionFactory().getProperties().getDatabase();
    getConnectionFactory().getConnection().createDatabase(database);
  }

  @Override
  public void write(final T payload)
  {
    Preconditions.checkArgument(payload != null, "Parameter 'object' must not be null");
    final String database = getConnectionFactory().getProperties().getDatabase();
    final String retentionPolicy = getConnectionFactory().getProperties().getRetentionPolicy();
    final BatchPoints ops = BatchPoints.database(database)
      .retentionPolicy(retentionPolicy)
      .consistency(InfluxDB.ConsistencyLevel.ALL)
      .build();
    convert(payload).stream().forEach(ops::point);
    getConnectionFactory().getConnection().write(ops);
  }

  @Override
  public void write(final List<T> payload)
  {
    Preconditions.checkArgument(payload != null, "Parameter 'object' must not be null");
    final String database = getConnectionFactory().getProperties().getDatabase();
    final String retentionPolicy = getConnectionFactory().getProperties().getRetentionPolicy();
    final BatchPoints ops = BatchPoints.database(database)
      .retentionPolicy(retentionPolicy)
      .consistency(InfluxDB.ConsistencyLevel.ALL)
      .build();
    payload.stream().forEach(t -> convert(t).stream().forEach(ops::point));
    getConnectionFactory().getConnection().write(ops);
  }

  @Override
  public QueryResult query(final Query query)
  {
    return getConnectionFactory().getConnection().query(query);
  }

  @Override
  public QueryResult query(final Query query, final TimeUnit timeUnit)
  {
    return getConnectionFactory().getConnection().query(query, timeUnit);
  }

  @Override
  public Pong ping()
  {
    return getConnectionFactory().getConnection().ping();
  }

  @Override
  public String version()
  {
    return getConnectionFactory().getConnection().version();
  }

  @SuppressWarnings("unchecked")
  private List<Point> convert(T payload)
  {
    return (List<Point>) conversionService.convert(payload, TypeDescriptor.forObject(payload), targetTypeDescriptor);
  }
}
