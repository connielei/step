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
     .then(res => displayComments());
}

/**
 * Fetches a list of comments and displays them on the page.
 * If there are no comments, the page displays a no comments yet message.
 */
async function displayComments() {
    const response = await fetch(`/list-comments?num=${document.getElementById('num').value}`);
    const json = await response.json();

    const ulElement = document.getElementById('comments-list');

    for (comment of json) {
        ulElement.appendChild(createListElement(comment));
    }

    if (json.length === 0) {
        ulElement.textContent = 'No comments yet.';
    }
}
