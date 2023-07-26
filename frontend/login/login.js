let loginForm = document.getElementsByClassName("login-form")[0];

loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    let email = document.getElementById("emailInput").value.trim();
    let password = document.getElementById("passwordInput").value.trim();

    let body = {
        email: email,
        password: password
    }
    
    let response = await webServiceInvokerRest("http://localhost:8080/api/v1/login", "POST", body);
    if (response.status == 200) {
        response = await response.json();
        localStorage.setItem("accessToken", response.accessToken);
        localStorage.setItem("refreshToken", response.refreshToken);    
        window.location.replace("http://localhost:5500/index.html");
    }
    else {
        response = await response.json();
        swal.fire(response.message);
    }
});

const webServiceInvokerRest = async (url, method, body) => {
    let options = {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    }
    
    let response = await fetch(url, options);

    return response;
}
