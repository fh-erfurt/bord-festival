package de.bord.festival;

import java.util.HashMap;
import java.util.Map;

public class LineUp {
   private Map<TimeSlot, Band> lineUp;
   public LineUp(){
       lineUp=new HashMap<>();//Map is not a collection, it is an interface
   }

}
