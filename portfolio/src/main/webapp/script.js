// Copyright 2019 Google LLC
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

function addRandomProject() {
  const projects =
      [
          { name:"Tak", 
            text:"This is terminal version of the KingKiller Chronicle's two player game with the same name!", 
            link:"https://github.com/connielei-stuy/apcs1-final-tak", 
            image:"/images/tak.png"
          }, 
          { name:"TriWizard Maze", 
            text:"This is virtual reality experience of the TriWizard Maze!", 
            link:"https://github.com/connielei-kwk/kwk17-FINAL-PROJ-maze", 
            image:"/images/tri.png"
          },
          { name:"Graphics Engine", 
            text:"This is simple graphics engine that can create basic shapes.", 
            link:"https://github.com/connielei-stuy/graphics-anim", 
            image:"/images/graphics.gif"
          } 
      ];

  const project = projects[Math.floor(Math.random() * projects.length)];

  const projectName = document.getElementById('project-name');
  projectName.innerText = project["name"];

  const projectTextContainer = document.getElementById('project-text');
  projectTextContainer.innerText = project["text"];

  const projectLink = document.getElementById('project-link');
  projectLink.innerText = "Click here for the repo!";
  projectLink.href = project["link"];

  const projectImg = document.getElementById('project-img');
  projectImg.src = project["image"];
}

async function getMessages() {
    const response = await fetch('/data');
    const json = await response.json();

    const ulElement = document.getElementById('messages-list');
    ulElement.innerHTML = '';
    for (message of json) {
        ulElement.appendChild(createListElement(message));
    }
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}