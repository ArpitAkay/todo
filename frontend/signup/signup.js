let formElement = document.getElementsByClassName("signup-form")[0];

formElement.addEventListener("submit", async (e) => {
    e.preventDefault();
    let firstName = document.getElementById("firstName").value.trim();
    if(firstName.length < 2) {
        swal.fire("First Name should contain atleast 3 characters");
        return;
    }

    let lastName = document.getElementById("lastName").value.trim();
    if(lastName.length < 2) {
        swal.fire("Last Name should contain atleast 3 characters");
        return;
    }

    let phoneNumber = document.getElementById("phoneNumber").value.trim();
    
    let email = document.getElementById("email").value.trim();

    let password = document.getElementById("password").value.trim();
    if(password.length < 8) {
        swal.fire("Password should be of atleast 8 characters");
        return;
    }

    console.log(firstName, lastName, phoneNumber, email, password);

    let signup = {
        "firstName": firstName,
        "lastName": lastName,
        "phoneNumber": phoneNumber,
        "email": email,
        "password": password
    }

    let response = await webServiceInvokerRest("http://localhost:8080/api/v1/signup", "POST", signup);

    if(response.status === 201) {
        response = await response.json();
        swal.fire("Signup successful");
        localStorage.setItem("name", response.firstName + " " + response.lastName);
        formElement.reset();
    }
    else {
        swal.fire("Something went wrong");
    }
})

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