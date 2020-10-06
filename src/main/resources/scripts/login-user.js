const form = document.querySelector("#login-form");

form.addEventListener('submit', function(event) {
    event.preventDefault();

    const data = `email=${this.email.value}&password=${this.password.value}`;

    console.log(data);
    login(data);
})

function login(data) {
    fetch("http://localhost:8001/login",
        {
            credentials: 'same-origin',
            method: "POST",
            body: data
        })
        .then(function (response) {
            if(!response.status) {
                throw Error("User not found");
            }
            return response.json();
        })
        .then(function(user) {
            console.log(user);
            switch (user.typeId){
                case 1:
                    window.location.href = "admin.html";
                    break;
                case 2:
                    window.location.href = "mentor.html";
                    break;
                case 3:
                    window.location.href = "student.html";
                    break
                default:
                    alert("Can't log in. Wrong email or password");
                    window.location.href = "login.html";
                    break;
            }

            document.cookie = `user=${JSON.stringify(user)}`;

            let div = document.createElement("div")

            div.innerHTML = `<div class="text">${user.firstName}</div>`;

        })
        .catch(function(error) {
            alert(error)
        });
}
