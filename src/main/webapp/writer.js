document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('userForm');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(form);
        const user = {};
        formData.forEach(function (value, key) {
            user[key] = value;
        });

        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8080/lab4_war_exploded/user', true);
        xhr.setRequestHeader('Content-Type', 'application/json');

        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log('Данные успешно отправлены');
            } else {
                console.error('Произошла ошибка при обработке запроса:', xhr.statusText);
            }
        };

        xhr.send(JSON.stringify(user));
        this.reset();
    });
});