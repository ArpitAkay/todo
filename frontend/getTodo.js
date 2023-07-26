window.addEventListener("load", async () => {
    let response = await webServiceInvokerRest("http://127.0.0.1:8080/api/v1/todo/get/all", "GET");

    if (response.status === 200) {
        response = await response.json();
        console.log(response);

        let todoContent = document.getElementsByClassName("todoContent")[0];
        console.log(todoContent);
        let ihtml = "";

        for (let index in response) {

            ihtml += `
                    <div class="todo">
                        <input type="checkbox" name="todoCheckBox" class="todoCheckbox" onclick="completeTodo(${response[index].todoId})" ${response[index].completed ? "checked" : ""}>
                        <input type="text" name="todoText" class="todoText" value="${response[index].content}" style="text-decoration: ${response[index].completed ? 'line-through;' : 'none;'}" readonly>
                        <input type="button" value="Edit" class="editButton" onclick="editTodo(${response[index].todoId})" id="${response[index].todoId}">
                        <input type="button" value="Delete" class="deleteButton" onclick="deleteTodo(${response[index].todoId})">
                    </div>
                    `;
        }

        todoContent.innerHTML = ihtml;
    }
    else {
        swal.fire(response.message);
    }
});

const completeTodo = async (todoId) => {
    let inputElem = document.getElementById(todoId).previousSibling.previousSibling;
    let checkBoxElem = document.getElementById(todoId).previousSibling.previousSibling.previousSibling.previousSibling;
    
    let completed = checkBoxElem.checked; 

    if(completed) {
        inputElem.style.textDecoration = "line-through";
    }
    else {
        inputElem.style.textDecoration = "none";
    }

    let todo = {
        "completed": completed
    }

    let response = await callEditAPI(todo, todoId);

    if(response.status === 200) {
        response = await response.json();
        console.log(response);
        if(response.completed) {
            swal.fire("Todo completed");
        }
        else {
            swal.fire("Todo incomplete");
        }
    }
    else {
        swal.fire("Something went wrong");
    }
}

const editTodo = async (todoId) => {
    let editElem = document.getElementById(todoId);
    let inputElem = editElem.previousSibling.previousSibling;

    let editElemValue = editElem.value;
    if (editElemValue === "Edit") {
        inputElem.removeAttribute("readonly");
        editElem.value = "Save";
        inputElem.style.backgroundImage = "linear-gradient( to right, rgb(133, 133, 246), rgb(240, 209, 214))";
    }
    else {
        if (inputElem.value.trim() === "") {
            await swal.fire("Please enter some value to update in todo");
            return;
        }

        Swal.fire({
            title: 'Do you want to save the changes?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Save',
            denyButtonText: `Don't save`,
        }).then(async (result) => {
            if (result.isConfirmed) {
                let todo = {
                    "content": inputElem.value,
                };
                let response = await callEditAPI(todo, todoId);
                if (response.status === 200) {
                    Swal.fire('Saved!', '', 'success')
                }
                else {
                    await Swal.fire('Something went wrong!');
                    window.location.reload();
                }
            } else if (result.isDenied) {
                await Swal.fire('Changes are not saved', '', 'info')
                window.location.reload();
            }
        })

        inputElem.setAttribute("readonly", true);
        editElem.value = "Edit";
        inputElem.style.backgroundImage = "linear-gradient( to right, rgb(200, 200, 239), rgb(239, 134, 151))";
    }
}

const callEditAPI = async (todo, todoId) => {

    const params = new URLSearchParams();
    params.append("todoId", todoId);

    let response = webServiceInvokerRest(`http://localhost:8080/api/v1/todo/update?${params}`, "PATCH", todo);

    return response;
}

const deleteTodo = (todoId) => {
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then(async (result) => {
        if (result.isConfirmed) {

            const params = new URLSearchParams();
            params.append("todoId", todoId);

            response = await webServiceInvokerRest(`http://localhost:8080/api/v1/todo/delete?${params}`, "DELETE");

            if (response.status === 200) {
                response = await response.json();
                Swal.fire(response);
                window.location.reload();
            }
            else {
                Swal.fire("Something went wrong");
            }
        }
    })
}