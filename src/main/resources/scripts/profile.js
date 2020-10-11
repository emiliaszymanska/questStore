const container = document.querySelector(".profile");

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

function getProfile() {
    const sessionId = getCookie("sessionId");

    fetch(`http://localhost:8001/student/profile/${sessionId.slice(1, -1)}`)
        .then(function(response) {
            return response.json();
        })
        .then(function(student) {
            displayProfile(student);
        });
}

function displayProfile(student) {
    let node = `<div class="fieldPlusDescription">
                    <p class="fieldDescription">Name:</p>
                    <p class="data">${student.firstName}</p>
                </div>
                <div class="fieldPlusDescription">
                    <p class="fieldDescription">Surname:</p>
                    <p class="data">${student.lastName}</p>
                </div>
                <div class="fieldPlusDescription">
                    <p class="fieldDescription">E-mail:</p>
                    <p class="data">${student.email}</p>
                </div>
                <div class="fieldPlusDescription">
                    <p class="fieldDescription">Phone number:</p>
                    <p class="data">${student.phoneNumber}</p>
                </div>`;
    container.innerHTML += node;
    console.log(student);
}
getProfile();