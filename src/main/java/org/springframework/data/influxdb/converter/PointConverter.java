package org.springframework.data.influxdb.converter;

import com.google.common.collect.Lists;
import java.util.List;
import org.influxdb.dto.Point;

public class PointConverter implements PointCollectionConverter<Point>
{
  @Override
  public List<Point> convert(final Point source)
  {
    return Lists.newArrayList(source);
  }
}
