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
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;

public final class FindMeetingQuery {

  /** Returns time ranges that satisfy the request and take into account, the events. */
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    long duration = request.getDuration();

    // When the requested meeting duration is greater than a day, there are no viable ranges.
    // There is no way to create a meeting longer than the day because information about the next 
    // day's events is needed and given events only contain those for the requested day.
    if (duration > TimeRange.WHOLE_DAY.duration()) {
      return Arrays.asList();
    }

    // No events means range should be the entire day.
    if (events.isEmpty()) {
      return Arrays.asList(TimeRange.WHOLE_DAY);
    }

    // Sort events with respect to ascending start time (needs to be converted to a List
    // to use the sort function and lambda).
    List<Event> sortedEvents = new ArrayList<>(events);
    sortedEvents.sort((e1, e2) -> e1.getWhen().start() - e2.getWhen().start());

    Collection<String> attendees = request.getAttendees();
    Collection<String> allAttendees = new HashSet<>(attendees);
    allAttendees.addAll(request.getOptionalAttendees());

    // Return the time ranges that include optional attendees if there are only optional attendees
    // or when there are non optional attendees and a set of non empty time ranges.
    Collection<TimeRange> allAttendeesRange = queryHelper(duration, sortedEvents, allAttendees);
    if (!allAttendeesRange.isEmpty() || attendees.isEmpty()) {
      return allAttendeesRange;
    }

    return queryHelper(duration, sortedEvents, attendees);
  }

  /**
   * Returns a collection of time ranges that statisfy the duration, attendees and the events given.
   */
  private Collection<TimeRange> queryHelper(long duration, List<Event> sortedEvents, Collection<String> attendees) {
    int start = TimeRange.START_OF_DAY;
    int end = TimeRange.END_OF_DAY;
    Collection<TimeRange> ranges = new ArrayList();

    for(Event event: sortedEvents) {
      // Event does not contain the request's attendees, so there's no need to update the
      // start time for a possible time range.
      if (!containsAttendees(event.getAttendees(), attendees)) {
        continue;
      }

      TimeRange eventTimeRange = event.getWhen();
      int eventStart = eventTimeRange.start();
      int eventEnd = eventTimeRange.end();

      if (eventStart > start && eventStart - start >= duration) {
        // Create and add range if duration condition is statisfied.
        ranges.add(TimeRange.fromStartEnd(start, eventStart, false));
      }

      // Deals with nested events case: if the event's end time is later than the current
      // range's start time, then update the start time with the event's end time.
      if (eventEnd > start) {
        start = eventEnd; 
      }
    }
    
    if (duration <= end - start) {
      ranges.add(TimeRange.fromStartEnd(start, end, true));
    }
    
    return ranges;
  }

  /**
   * Returns whether any elements in the second set, {@code people} are in the first set,
   * {@code attendees}, ie. the two collections overlap/share elements
   */
  private boolean containsAttendees(Collection<String> attendees, Collection<String> people) {
    Iterator<String> itr = people.iterator();
    while(itr.hasNext()) {
      String person = itr.next();
      if (attendees.contains(person)) {
        return true;
      }
    }
    return false;
  }
}
