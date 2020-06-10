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

let authEl, addCommentsEl, nicknameContainerEl;

function toggleDisplay() {
  authEl = document.getElementById("auth-el");
  addCommentsEl = document.getElementById("comments-form-container");
  nicknameContainerEl = document.getElementById("nickname-container");
  updateCommentFormDisplay();
}

function updateCommentFormDisplay() {
  fetch('/login')
  .then(res => res.json())
  .then(json => {
    addCommentsEl.hidden = !json.loggedIn;
    nicknameContainerEl.hidden = !json.loggedIn;
    updateAuth(json.displayText, json.url);
    if (json.loggedIn) {
     document.getElementById("nickname").value = json.nickname
    }
  });
}

function updateAuth(text, href){
  authEl.textContent = text;
  authEl.href = href;
}