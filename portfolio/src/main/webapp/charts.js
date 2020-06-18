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
      
google.charts.load("current", {packages:["corechart"]});
google.charts.setOnLoadCallback(drawChart);

/** Creates a chart and adds it to the page. */
function drawChart() {
  fetch('/comments-authors-data')
    .then(response => response.json())
    .then((json) => {
      const data = new google.visualization.arrayToDataTable(json);

      const options = {
        title: 'Comments by Which Authors',
        pieHole: 0.4,
        width: 500,
        height: 400
      };

      const chart = new google.visualization.PieChart(document.getElementById('pie-chart'));
      chart.draw(data, options);
     })
     .catch((err) => {
      document.getElementById('pie-chart').textContent = "Chart data missing.";
     });
}
