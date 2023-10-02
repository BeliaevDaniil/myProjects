//Validace na (všech!) elementech ve formuláři, při zadávání dat
const email = document.querySelector('#email_login');
const password = document.querySelector('#password_login');

email.addEventListener('input', dynValidateEmail);
password.addEventListener('input', dynValidatePassword);


function dynValidateEmail() {
    let pattern="[^\s]+[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}";
    if ((!email.value.match(pattern)) && email.value.length !== 0) {
        let error_email = document.getElementById("error_email")
        error_email.innerHTML = "Email should match format xxx@xxx.xxx"
    } else if (email.value.includes("@")) {
        let error_email = document.getElementById("error_email")
        error_email.innerHTML = "E-mail was written right"
    } else {
        let error_email = document.getElementById("error_email")
        error_email.innerHTML = " "
    }
}

function dynValidatePassword() {
    if ((password.value.length < 6) && password.value.length !== 0) {
        let error_password = document.getElementById("error_password")
        error_password.innerHTML = "Min. length of password is 6 characters"
    } else if (password.value.length >= 6) {
        let error_password = document.getElementById("error_password")
        error_password.innerHTML = "Password was written right"
    } else {
        let error_password = document.getElementById("error_password")
        error_password.innerHTML = " "
    }
}
