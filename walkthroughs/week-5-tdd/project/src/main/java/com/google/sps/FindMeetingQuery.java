// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.util.Iterator;

public final class FindMeetingQuery {

  /** Returns time ranges that satisfy the request and take into account, the events */
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    long duration = request.getDuration();
    // meeting duration is greater than a day means no viable ranges
    if (duration > TimeRange.WHOLE_DAY.duration()) return Arrays.asList();
    
    // no events means range should be the entire day
    if (events.isEmpty()) return Arrays.asList(TimeRange.WHOLE_DAY);

    Collection<String> attendees = request.getAttendees();
    int start = TimeRange.START_OF_DAY;
    int end = TimeRange.END_OF_DAY;
    Collection<TimeRange> ranges = new ArrayList();

    // sort events with respect to ascending start time, needs to be converted to a List
    // to use the sort function and lambda
    List<Event> sorted_events = new ArrayList<>(events);
    sorted_events.sort((e1, e2) -> e1.getWhen().start() - e2.getWhen().start());

    for(Event event: sorted_events) {
      // event does not contain the request's attendees, so there's no need to update the
      // start time for a possible time range
      if (!containsAttendees(event.getAttendees(), attendees)) continue;

      TimeRange event_time_range = event.getWhen();
      int event_start = event_time_range.start();
      int event_end = event_time_range.end();

      if (start < event_start && duration <= event_start - start)
        // create and add range if duration condition is statisfied 
        ranges.add(TimeRange.fromStartEnd(start, event_start, false));

      // deals with nested events case: if the event's end time is later than the current
      // range's start time, then update the start time with the event's end time
      if (start < event_end) start = event_end; 
    }
    if (duration <= end - start) ranges.add(TimeRange.fromStartEnd(start, end, true));

    return ranges;
  }

  /**
   * Returns whether any elements in the second set, {@code people} are in the first set,
   * {@code attendees}, ie. the two collections overlap/share elements
   */
  private boolean containsAttendees(Collection<String> attendees, Collection<String> people) {
    Iterator itr = people.iterator();
    while(itr.hasNext()) {
      String person = (String) itr.next();
      if (attendees.contains(person)) return true;
    }
    return false;
  }
}
