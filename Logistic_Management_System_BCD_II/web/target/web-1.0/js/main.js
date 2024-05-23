function login() {
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    let data = {
        "email": email,
        "password": password
    }
    fetch("login", {
        method: "POST",
        body: JSON.stringify(data)
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.text();
    }).then(data => {

    }).catch(error => {
        console.error("error", error);
    });
}



