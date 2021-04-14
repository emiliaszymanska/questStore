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
            mode: 'cors',
            credentials: 'include',
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
            switch (user.typeId) {
                case 1:
                    window.location.href = "../html/admin/admin.html";
                    break;
                case 2:
                    window.location.href = "../html/mentor/mentor.html";
                    break;
                case 3:
                    window.location.href = "../html/student/student.html";
                    break
                default:
                    alert("Can't log in. Wrong email or password");
                    window.location.href = "login.html";
                    break;
            }

        })
        .catch(function(error) {
            alert(error)
        });
}