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

let loginEl, addCommentsEl, nicknameContainerEl;

/** 
 * Initializes all the variables needed for updateCommentFormDisplay(), 
 * then calls the function to display the correct content based on
 * whether the user is logged in or not.
 */
function toggleDisplay() {
  loginEl = document.getElementById("login");
  addCommentsEl = document.getElementById("comments-form-container");
  nicknameContainerEl = document.getElementById("nickname-container");
  updateCommentFormDisplay();
}

/**
 * Fetches login status from '/login' endpoint, then if user is logged in:
 *  - displays form allowing user to add a comment
 *  - display user's nickname and button allowing user to update their nickname
 *  - updates a url to display a log out link
 * and if user is not logged in:
 *  - hides form allowing user to comment and button allowing user to update nickname
 *  - updates a url to display a log in link
 */
function updateCommentFormDisplay() {
  fetch('/login')
  .then(res => res.json())
  .then(json => {
    addCommentsEl.hidden = !json.loggedIn;
    nicknameContainerEl.hidden = !json.loggedIn;
    updateLogin(json.displayText, json.url);
    if (json.loggedIn) {
     document.getElementById("nickname").value = json.nickname
    }
  });
}

/** 
 * Updates the login element with the given text and href.
 * @param {string} text
 * @param {href} text
 */
function updateLogin(text, href){
  loginEl.textContent = text;
  loginEl.href = href;
}
