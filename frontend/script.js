let name = localStorage.getItem("name");

let liElem = document.getElementsByClassName("name")[0];
liElem.innerHTML = liElem.innerHTML + " " + name;

const webServiceInvokerRest = async (url, method, body) => {
    let options = {
        method: method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("accessToken")
        },
        body: body !== undefined ? JSON.stringify(body) : null
    };

    return await fetch(url, options);
}

let svg = document.getElementsByTagName("svg")[0];
svg.addEventListener("click", () => {
    window.location.href = "/login/login.html";
});