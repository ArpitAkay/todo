let todoForm = document.getElementsByClassName("todoForm")[0];

todoForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    let todoValue = document.getElementById("text").value.trim();

    if(todoValue.length === 0) {
        swal.fire("Please enter some value in todo");
        return;
    }

    let todo = {
        "content": todoValue
    }

    let response = await webServiceInvokerRest("http://localhost:8080/api/v1/todo/save", "POST", todo);

    if(response.status === 201) {
        todoForm.reset();
        await swal.fire("Todo added successfully");
        window.location.reload();
    }
    else {
        swal.fire("Something went wrong");
    }
});