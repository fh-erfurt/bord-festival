package de.bord.festival.eventManagement;

import de.bord.festival.band.Band;
import de.bord.festival.stageManagement.TimeSlot;

import java.util.HashMap;
import java.util.Map;

public class LineUp {
   private Map<TimeSlot, Band> lineUp;
   public LineUp(){
       lineUp=new HashMap<>();//Map is not a collection, it is an interface
   }

}
