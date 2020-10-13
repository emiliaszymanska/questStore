const container = document.querySelector(".profile");
const spinner = document.querySelector("#spinner");
const editForm = document.querySelector("#data-form");

(() => {
    getProfile();
    console.log("ccccc");
    editForm.addEventListener('submit', function(event) {
        console.log("x");
        event.preventDefault();

        const data = `firstName=${this.firstName.value}&lastName=${this.lastName.value}
        &email=${this.email.value}&phoneNumber=${this.phoneNumber.value}`;

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
    container.innerHTML = "";
    let node = `<form id="data-form">

                    <p class="fieldDescription">Name:</p>
                    <input class="data" value="${student.firstName}"></input>
       

                    <p class="fieldDescription">Surname:</p>
                    <input class="data" value="${student.lastName}"></input>


                    <p class="fieldDescription">E-mail:</p>
                    <input class="data" value="${student.email}"></input>
     

                    <p class="fieldDescription">Phone number:</p>
                    <input class="data" value="${student.phoneNumber}"></input>

                <button class="button" id="close-button">Submit Changes</button>
                </form>`;
    container.innerHTML += node;
}

function getCookie(cname) {
    //TODO refactor
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
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