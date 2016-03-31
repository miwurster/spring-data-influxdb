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

package org.springframework.data.influxdb.detail;

import com.google.common.base.Preconditions;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Consumer;

import static com.google.common.base.Strings.isNullOrEmpty;

public final class URLBuilder
{
  private String protocol;
  private String host;
  private Integer port = -1;
  private String path = "";

  public static URLBuilder create()
  {
    return new URLBuilder();
  }

  private URLBuilder()
  {
  }

  public URLBuilder http()
  {
    protocol = "http";
    return this;
  }

  public URLBuilder https()
  {
    protocol = "https";
    return this;
  }

  public URLBuilder protocol(final String protocol)
  {
    this.protocol = protocol;
    return this;
  }

  public URLBuilder host(final String host)
  {
    this.host = host;
    return this;
  }

  public URLBuilder port(final Integer port)
  {
    this.port = port;
    return this;
  }

  public URLBuilder path(final String path)
  {
    this.path = path;
    return this;
  }

  public Actions and()
  {
    return this.new Actions();
  }

  public class Actions
  {
    public URL buildIt()
    {
      Preconditions.checkState(!isNullOrEmpty(protocol), "Parameter 'protocol' must not be empty");
      Preconditions.checkState(!isNullOrEmpty(host), "Parameter 'host' must not be empty");
      try
      {
        return new URL(protocol, host, port, path);
      }
      catch (MalformedURLException e)
      {
        throw new IllegalStateException(e.getMessage(), e);
      }
    }

    public URI buildURI()
    {
      try
      {
        return buildIt().toURI();
      }
      catch (URISyntaxException e)
      {
        throw new IllegalStateException(e.getMessage(), e);
      }
    }

    public String buildString()
    {
      return buildIt().toString();
    }

    public void consumeIt(Consumer<URL> consumer)
    {
      consumer.accept(buildIt());
    }
  }
}
