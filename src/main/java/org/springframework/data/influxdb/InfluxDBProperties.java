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

import com.google.common.base.MoreObjects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.influxdb.detail.URLBuilder;

@ConfigurationProperties("spring.influxdb")
public class InfluxDBProperties
{
  private boolean https = false;

  @NotEmpty
  private String hostname;

  @Min(1)
  @Max(65535)
  private Integer port;

  @NotEmpty
  private String username;

  @NotEmpty
  private String password;

  @NotEmpty
  private String database;

  @NotEmpty
  private String retentionPolicy;

  public boolean isHttps()
  {
    return https;
  }

  public void setHttps(final boolean https)
  {
    this.https = https;
  }

  public String getHostname()
  {
    return hostname;
  }

  public void setHostname(final String hostname)
  {
    this.hostname = hostname;
  }

  public Integer getPort()
  {
    return port;
  }

  public void setPort(final Integer port)
  {
    this.port = port;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(final String username)
  {
    this.username = username;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(final String password)
  {
    this.password = password;
  }

  public String getDatabase()
  {
    return database;
  }

  public void setDatabase(final String database)
  {
    this.database = database;
  }

  public String getRetentionPolicy()
  {
    return retentionPolicy;
  }

  public void setRetentionPolicy(final String retentionPolicy)
  {
    this.retentionPolicy = retentionPolicy;
  }

  public String getUrl()
  {
    return URLBuilder.create()
      .protocol(isHttps() ? "https" : "http")
      .host(getHostname()).port(getPort())
      .and().buildIt().toString();
  }

  @Override
  public String toString()
  {
    return MoreObjects.toStringHelper(this)
      .omitNullValues()
      .add("url", getUrl())
      .add("username", username)
      .add("database", database)
      .add("retentionPolicy", retentionPolicy)
      .toString();
  }
}
