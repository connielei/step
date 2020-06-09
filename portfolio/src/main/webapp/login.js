let authEl;

function displayAddComment() {
    fetch('/login')
     .then(res => res.json())
     .then(json => {
        document.getElementById("comments-form").hidden = !json.loggedIn;
        document.getElementById("nickname-form").hidden = !json.loggedIn;
        authEl = document.getElementById("auth-el");
        if (json.loggedIn) {
            updateAuth("Log out!", json.url);
            document.getElementById("nicknameEl").value = json.nickname
        } else {
            updateAuth("Log in!", json.url);
        }
    });
}

function updateAuth(text, href){
    authEl.textContent = text;
    authEl.href = href;
}

displayAddComment();