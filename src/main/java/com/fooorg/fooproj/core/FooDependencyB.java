package com.fooorg.fooproj.core;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import lombok.Data;

@Data
public class FooDependencyB {
   private final double threshold;

   @Inject
   public FooDependencyB(@Named("threshold") double threshold) {
      this.threshold = threshold;
   }

   public void invoke() {
     // empty
   }
}
