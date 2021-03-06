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

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.GetNickname;
import java.io.IOException;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for donut chart's data */
@WebServlet("/comments-authors-data")
public class CommentsAuthorsServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    Map<String, Integer> table = new Hashtable<>();
    for (Entity entity : results.asIterable()) {
      String id = (String) entity.getProperty("id");
      table.put(id, table.getOrDefault(id, 0) + 1);
    }

    StringBuilder sb = new StringBuilder("[");

    table.forEach((id, numComments) -> {
      sb.append("[\"");
      sb.append(GetNickname.getUserNickname(id));
      sb.append("\", ");
      sb.append(numComments);
      sb.append("],");
    });

    // Remove trailing comma if needed.
    if (sb.length() > 1) {
      sb.deleteCharAt(sb.length() - 1);
    }

    sb.append("]");

    response.setContentType("application/json;");
    response.getWriter().println(sb.toString());
  }
}
