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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    long duration = request.getDuration();  
    if (duration > TimeRange.WHOLE_DAY.duration()) return Arrays.asList();
    
    if (events.isEmpty()) return Arrays.asList(TimeRange.WHOLE_DAY);

    Collection<String> attendees = request.getAttendees();
    int start = TimeRange.START_OF_DAY;
    int end = TimeRange.END_OF_DAY;

    List<Event> sorted_events = new ArrayList<>(events);
    sorted_events.sort((e1, e2) -> e1.getWhen().start() - e2.getWhen().start());

    Collection<TimeRange> ranges = new ArrayList();
    for(Event event: sorted_events) {
        if (!containsAttendees(event.getAttendees(), attendees)) continue;

        TimeRange event_time_range = event.getWhen();
        int event_start = event_time_range.start();
        int event_end = event_time_range.end();

        if (start < event_start && event_start < end) {
            end = event_start;
            if (duration <= end - start) {
                ranges.add(TimeRange.fromStartEnd(start, end, false));
            }
            end = TimeRange.END_OF_DAY;
        }

        if (event_end > start) start = event_end;
    }
    if (duration <= end - start) ranges.add(TimeRange.fromStartEnd(start, end, true));

    return ranges;
  }

  private boolean containsAttendees(Collection<String> attendees1, Collection<String> attendees2) {
    Iterator itr = attendees2.iterator();
    while(itr.hasNext()) {
      String attendee = (String) itr.next();
      if (attendees1.contains(attendee)) return true;
    }
    return false;
  }
}
