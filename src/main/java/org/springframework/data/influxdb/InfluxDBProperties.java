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
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.influxdb")
public class InfluxDBProperties
{
  @NotEmpty
  private String url;

  @NotEmpty
  private String username;

  private String password;

  @NotEmpty
  private String database;

  @NotEmpty
  private String retentionPolicy;

  public String getUrl()
  {
    return url;
  }

  public void setUrl(final String url)
  {
    this.url = url;
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

  @Override
  public String toString()
  {
    return MoreObjects.toStringHelper(this)
      .add("url", url)
      .add("username", username)
      .add("password", password)
      .add("database", database)
      .add("retentionPolicy", retentionPolicy)
      .toString();
  }
}
