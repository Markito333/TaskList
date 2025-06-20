document.addEventListener("DOMContentLoaded", function () {
    const initialStateDiv = document.querySelector(".initial-state");
    const taskEditor = document.querySelector(".task-editor");
    const toggleAddBtn = document.querySelector(".toggle-add-btn");
    const taskInput = document.getElementById("task-input");
    const addBtn = document.getElementById("add-btn");
    const cancelBtn = document.getElementById("cancel-btn");
    const taskList = document.querySelector(".task-list");
    const options = document.querySelectorAll(".option");
    const avatar = document.querySelector(".avatar");

    const API_URL = "/api/tasks";

    let editingTaskId = null;

    loadTasks();

    toggleAddBtn.addEventListener("click", function () {
        resetEditor();
        taskEditor.style.display = "block";
        initialStateDiv.style.display = "none";
        taskInput.focus();
    });

    function setEndOfContenteditable(contentEditableElement) {
        let range, selection;
        if (document.createRange) {
            range = document.createRange();
            range.selectNodeContents(contentEditableElement);
            range.collapse(false);
            selection = window.getSelection();
            selection.removeAllRanges();
            selection.addRange(range);
        }
    }

    function updateEditorState() {
        const text = taskInput.textContent.trim();
        
        if (text !== "") {
            addBtn.disabled = false;
            options.forEach(opt => opt.classList.add("active"));
            avatar.classList.add("active");
        } else {
            addBtn.disabled = true;
            options.forEach(opt => opt.classList.remove("active"));
            avatar.classList.remove("active");
        }
        feather.replace();
    }

    function updateEditor() {
        let text = taskInput.textContent;
        let processedHTML = parseTextToBadgesPreview(text);
        taskInput.innerHTML = processedHTML;
        setEndOfContenteditable(taskInput);
        updateEditorState();
    }

    taskInput.addEventListener("input", updateEditor);

    function parseTextToBadgesPreview(text) {
        const tokens = text.split(" ");
        return tokens
            .map((token) => {
                if (token.match(/@gmail\.com/i)) {
                    return `<span style="color: orange;">${token}</span>`;
                } else if (token.startsWith("@")) {
                    return `<span style="color: green;">${token}</span>`;
                } else if (token.startsWith("#")) {
                    return `<span style="color: purple;">${token}</span>`;
                } else if (
                    token.includes("www") ||
                    token.includes(".com") ||
                    token.includes("http://") ||
                    token.includes("https://")
                ) {
                    return `<span style="color: blue;">${token}</span>`;
                } else {
                    return token;
                }
            })
            .join(" ");
    }

    function parseTextToFinalTask(text) {
        let cleanText = text;
        if (text.startsWith('"') && text.endsWith('"')) {
            cleanText = text.slice(1, -1);
        }
        cleanText = cleanText.replace(/^\/\//, "").replace(/\/\/$/, "");

        const tokens = cleanText.split(" ");
        let emailCount = 0;
        let linkCount = 0;
        const outputTokens = [];
        let emailBadgeInserted = false;
        let linkBadgeInserted = false;

        tokens.forEach((token) => {
            if (token.match(/@gmail\.com/i)) {
                emailCount++;
            } else if (
                (token.includes("www") ||
                token.includes(".com") ||
                token.includes("http://") ||
                token.includes("https://")
            )) {
                linkCount++;
            }
        });

        tokens.forEach((token) => {
            if (token.match(/@gmail\.com/i)) {
                if (!emailBadgeInserted) {
                    emailBadgeInserted = true;
                    const displayText = emailCount > 1 ? `Mail ${emailCount}` : "Mail";
                    outputTokens.push(
                        `<span class="badge badge-email-final"><i data-feather="mail"></i> ${displayText}</span>`
                    );
                }
            } else if (
                (token.includes("www") ||
                token.includes(".com") ||
                token.includes("http://") ||
                token.includes("https://")
            )) {
                if (!linkBadgeInserted) {
                    linkBadgeInserted = true;
                    const displayText = linkCount > 1 ? `Link ${linkCount}` : "Link";
                    outputTokens.push(
                        `<span class="badge badge-link-final"><i data-feather="link"></i> ${displayText}</span>`
                    );
                }
            } else if (token.startsWith("@")) {
                outputTokens.push(`<span class="badge badge-at">${token}</span>`);
            } else if (token.startsWith("#")) {
                outputTokens.push(`<span class="badge badge-hashtag">${token}</span>`);
            } else {
                outputTokens.push(token);
            }
        });
        return outputTokens.join(" ");
    }

    cancelBtn.addEventListener("click", function () {
        resetEditor();
    });

    function resetEditor() {
        taskInput.innerHTML = "";
        taskEditor.style.display = "none";
        initialStateDiv.style.display = "flex";
        updateEditorState();
        editingTaskId = null;
        addBtn.querySelector(".action-text").textContent = "Add";
    }

    addBtn.addEventListener("click", async function () {
        let text = taskInput.textContent.trim();
        if (text === "") {
            resetEditor();
            return;
        }
        
        try {
            let response;
            
            if (editingTaskId) {
                response = await fetch(`${API_URL}/${editingTaskId}`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ description: text })
                });
            } else {
                response = await fetch(API_URL, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ description: text })
                });
            }
            
            if (!response.ok) {
                throw new Error(editingTaskId ? "Error al actualizar tarea" : "Error al agregar tarea");
            }
            
            await loadTasks();
            resetEditor();
        } catch (error) {
            console.error("Error:", error);
            showErrorAlert(error.message);
        }
    });

    async function loadTasks() {
        try {
            const response = await fetch(API_URL);
            if (!response.ok) {
                throw new Error("Error al cargar tareas");
            }
            const tasks = await response.json();
            renderTasks(tasks);
        } catch (error) {
            console.error("Error al cargar tareas:", error);
            showErrorAlert("Error al cargar tareas. Por favor recarga la página.");
        }
    }

    function renderTasks(tasks) {
        taskList.innerHTML = "";
        
        tasks.forEach(task => {
            const li = document.createElement("li");
            li.classList.add("task-item");
            li.dataset.taskId = task.id;
            
            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.classList.add("task-checkbox");
            checkbox.checked = task.completed;
            
            checkbox.addEventListener("change", async () => {
                try {
                    const response = await fetch(`${API_URL}/${task.id}/toggle`, {
                        method: "PATCH"
                    });
                    
                    if (!response.ok) {
                        throw new Error("Error al actualizar tarea");
                    }
                    
                    await loadTasks();
                } catch (error) {
                    console.error("Error al actualizar tarea:", error);
                    showErrorAlert("Error al actualizar tarea. Por favor intenta de nuevo.");
                }
            });
            
            const taskText = document.createElement("span");
            taskText.classList.add("task-text");
            taskText.innerHTML = parseTextToFinalTask(task.description);
            
            const actionsDiv = document.createElement("div");
            actionsDiv.classList.add("task-actions");
            
            const editBtn = document.createElement("button");
            editBtn.classList.add("action-btn", "edit-btn");
            editBtn.innerHTML = '<i data-feather="edit-2"></i>';
            editBtn.addEventListener("click", () => openEditModal(task));
            
            const deleteBtn = document.createElement("button");
            deleteBtn.classList.add("action-btn", "delete-btn");
            deleteBtn.innerHTML = '<i data-feather="trash-2"></i>';
            deleteBtn.addEventListener("click", () => openDeleteModal(task.id));
            
            actionsDiv.appendChild(editBtn);
            actionsDiv.appendChild(deleteBtn);
            
            li.appendChild(checkbox);
            li.appendChild(taskText);
            li.appendChild(actionsDiv);
            
            taskList.appendChild(li);
        });
        
        feather.replace();
    }

    function openEditModal(task) {
        editingTaskId = task.id;
        
        taskEditor.style.display = "block";
        initialStateDiv.style.display = "none";
        taskInput.textContent = task.description;
        updateEditor();
        addBtn.disabled = false;
        options.forEach(opt => opt.classList.add("active"));
        avatar.classList.add("active");
        
        addBtn.querySelector(".action-text").textContent = "Update";
        feather.replace();
    }

    function openDeleteModal(taskId) {
        const modal = document.getElementById("deleteModal");
        modal.style.display = "flex";
        
        const cancelBtn = modal.querySelector(".modal-cancel");
        const confirmBtn = modal.querySelector(".modal-confirm");
        
        const closeModal = () => {
            modal.style.display = "none";
            cancelBtn.onclick = null;
            confirmBtn.onclick = null;
        };
        
        cancelBtn.onclick = closeModal;
        
        confirmBtn.onclick = async () => {
            try {
                const response = await fetch(`${API_URL}/${taskId}`, {
                    method: "DELETE"
                });
                
                if (!response.ok) throw new Error("Error al eliminar tarea");
                
                await loadTasks();
                closeModal();
            } catch (error) {
                console.error("Error al eliminar tarea:", error);
                showErrorAlert("Error al eliminar tarea. Por favor intenta de nuevo.");
            }
        };
    }

    function showErrorAlert(message) {
        const alertDiv = document.createElement("div");
        alertDiv.className = "error-alert";
        alertDiv.textContent = message;
        
        alertDiv.style.position = "fixed";
        alertDiv.style.bottom = "20px";
        alertDiv.style.right = "20px";
        alertDiv.style.backgroundColor = "#f44336";
        alertDiv.style.color = "white";
        alertDiv.style.padding = "15px";
        alertDiv.style.borderRadius = "4px";
        alertDiv.style.zIndex = "1000";
        alertDiv.style.boxShadow = "0 2px 5px rgba(0,0,0,0.2)";
        alertDiv.style.animation = "fadeIn 0.3s";
        
        document.body.appendChild(alertDiv);
        
        setTimeout(() => {
            alertDiv.style.animation = "fadeOut 0.5s";
            setTimeout(() => alertDiv.remove(), 500);
        }, 3000);
    }

    feather.replace();

    function handleResize() {
        feather.replace();
    }
    window.addEventListener('resize', handleResize);
});