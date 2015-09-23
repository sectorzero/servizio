package com.impinj.itemsense.utils;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class MetricUtils {

  public static Optional<Meter> registerAndGetMeter(MetricRegistry metricRegistry, Optional<String> key) {
    if(!key.isPresent()) {
      return Optional.empty();
    }
    return Optional.of(metricRegistry.meter(key.get()));
  }

  public static Optional<Timer> registerAndGetTimer(MetricRegistry metricRegistry, Optional<String> key) {
    if(!key.isPresent()) {
      return Optional.empty();
    }
    return Optional.of(metricRegistry.timer(key.get()));
  }

  public static Optional<Histogram> registerAndGetHistogram(MetricRegistry metricRegistry, Optional<String> key) {
    if(!key.isPresent()) {
      return Optional.empty();
    }
    return Optional.of(metricRegistry.histogram(key.get()));
  }

  public static <T> Optional<Gauge<T>> registerAndGetGauge(
      MetricRegistry metricRegistry, Optional<String> key, Supplier<T> f) {
    if(!key.isPresent()) {
      return Optional.empty();
    }
    Optional<Gauge<T>> g = Optional.of(f::get);
    metricRegistry.register(key.get(), g.get());
    return g;
  }
}
