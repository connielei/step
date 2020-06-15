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

/**
 * Creates and returns a list element
 * @param {string} text
 * @return {!Element}
 */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.textContent = text;
  return liElement;
}

/**
 * Sends a request to a servlet that deletes all the comments, then refetches the comments 
 * to delete them from the page
 */
function deleteComments() {
    fetch('/delete-comments')
    .then(displayComments());
}

/**
 * Fetches a list of comments and displays them on the page.
 * If there are no comments, the page displays a no comments yet message.
 */
function displayComments() {
    fetch(`/list-comments?num=${document.getElementById('num').value}`)
    .then(response => response.json())
    .then(json => {
        const ulElement = document.getElementById('comments-list');
        ulElement.textContent = '';
    
        if (json.length === 0) {
          ulElement.textContent = 'No comments yet.';
          return;
        }
      
        for (comment of json) {
          ulElement.appendChild(createListElement(comment));
        }
    });
}
