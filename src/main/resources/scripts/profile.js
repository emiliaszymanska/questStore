const spinner = document.querySelector("#spinner");
const editForm = document.querySelector("#profile-form");

(() => {
    getProfile();
    editForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const data = `firstName=${this.firstName.value}&lastName=${this.lastName.value}`
            + `&email=${this.email.value}&phoneNumber=${this.phoneNumber.value}`;
        console.log(data);

        update(data);
    })
})();


function getProfile() {
    const sessionId = getCookie("sessionId");
    spinner.removeAttribute('hidden');
    fetch(`http://localhost:8001/student/profile/${sessionId.slice(1, -1)}`)
        .then(function(response) {
            return response.json();
        })
        .then(function(student) {
            spinner.setAttribute('hidden', '');
            displayProfile(student);
        });
}

function displayProfile(student) {
    editForm.innerHTML = "";
    let node = `
                <img id="profileImage" src="../../images/users/natalie-portman.jpg" alt="profile-image">
                <div id="form-data">
                    <div id="firstName-group">
                        <label for="input-firstName">First name:</label>
                        <input type="text" id="input-firstName" name="firstName" value="${student.firstName}">
                    </div>
                    <div id="lastName-group">
                        <label for="input-lastName">Last name:</label>
                        <input type="text" id="input-lastName" name="lastName" value="${student.lastName}">
                    </div>
                    <div id="email-group">
                        <label for="input-email">Email:</label>
                        <input type="text" id="input-email" name="email" value="${student.email}">
                    </div>
                    <div id="phoneNumber-group">
                        <label for="input-phoneNumber">Phone number:</label>
                        <input type="text" id="input-phoneNumber" name="phoneNumber" value="${student.phoneNumber}">
                    </div>
                    <button id="profile-submit-button">Submit Changes</button>
                </div>
                `;
    editForm.innerHTML += node;
}

function getCookie(cname) {
    //TODO refactor
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function update(data) {
    const sessionId = getCookie("sessionId");
    fetch(`http://localhost:8001/student/update/${sessionId.slice(1, -1)}`, {
        method: "POST",
        body: data
    })
        .then(function(response) {
            return response.json();
        })
        .then(function(student) {
            alert("updated");
            displayProfile(student);
        });
}
