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

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['The steps you take don’t need to be big. They just need to take you in the right direction.', 
       'I always pictured you as Watson.', 
       'I like to think about the first law of thermodynamics, that no energy in the universe is created and... none is destroyed. \
        That means that every bit of energy inside us, every particle will go on to be a part of something else. Maybe live as a\
        dragonfish, a microbe, maybe burn in a supernova ten billion years from now. And every part of us now was once a part of \
        some other thing - a moon, a storm cloud, a mammoth.', 
       'Usually one person doesn\'t solve the solution, but 100 people with 1% of the solution? That will get it done. I think that\'s beautiful, pieces solving a puzzle.'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
