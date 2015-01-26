package com.fooorg.fooproj.core;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import lombok.Value;

@Value
public class FooDependencyB {
   double threshold;

   @Inject
   public FooDependencyB(@Named("threshold") double threshold) {
      this.threshold = threshold;
   }
}
