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
let init = false, projects, projectNameEl, projectTextEl, projectLinkEl, projectImgEl;

/** Initializes all the variables needed for displayRandomProject */
function populate() {
  projects = [
      { name:"Tak", 
        text:"This is terminal version of the KingKiller Chronicle's \
              two player game with the same name!", 
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
  
  projectNameEl = document.createElement("h2");
  projectTextEl = document.createElement("p");
  projectLinkEl = document.createElement("a");
  projectLinkEl.textContent = "Click here for the repo!";
  projectImgEl = document.createElement("img");
  projectImgEl.id = "project-img";
  appendProjectElements();
  init = true;
}

/** Appends necessary project elements to the project container element. */
function appendProjectElements() {
    const projectContainer = document.getElementById("project-container");
    projectContainer.appendChild(projectNameEl);
    projectContainer.appendChild(projectLinkEl);
    projectContainer.appendChild(projectTextEl);
    projectContainer.appendChild(projectImgEl);
}

/** 
 * Updates the project name element with the given name. 
 * @param {string} name 
 */
function updateProjectName(name) {
  projectNameEl.innerText = name;
}

/** 
 * Updates the project text element with the given text. 
 * @param {string} text 
 */
function updateProjectText(text) {
  projectTextEl.innerText = text;
}

/** 
 * Updates the project link element with the given link. 
 * @param {string} link 
 */
function updateProjectLink(link) {
  projectLinkEl.href = link;
}

/** 
 * Updates the image element with the given image. 
 * @param {string} image 
 */
function updateProjectImage(image) {
  projectImgEl.src = image;
}

/** 
 * Returns a random project
 * @return {!Object}
 */
function getRandomProject() {
  return projects[Math.floor(Math.random() * projects.length)];
}

/**
 * Initializes all necessary variables if needed, then
 * chooses a random project and updates the following: 
 *  - img element with the project's img URl 
 *  - heading element with the project's name
 *  - paragraph element with the project's description
 *  - link element with the project's Github link
 */
function displayRandomProject() {
  if (!init) populate();
  const project = getRandomProject();  

  updateProjectName(project.name);
  updateProjectText(project.text);
  updateProjectLink(project.link);
  updateProjectImage(project.image);
}
