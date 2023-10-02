//Validace na (všech!) elementech ve formuláři, při zadávání dat
const nickname = document.querySelector('#nickname');
const email = document.querySelector('#email');
const password = document.querySelector('#password');
const password2 = document.querySelector('#password2');

nickname.addEventListener('input', dynValidateNickname);
email.addEventListener('input', dynValidateEmail);
password.addEventListener('input', dynValidatePassword);
password2.addEventListener('input', dynValidatePassword2);

function dynValidateNickname() {
    if ((nickname.value.length < 3) && nickname.value.length !== 0) {
        let error_nickname = document.getElementById("error_nickname")
        error_nickname.innerHTML = "Min. length of nickname is 3 characters"
    } else if (nickname.value.length >= 3) {
        let error_nickname = document.getElementById("error_nickname")
        error_nickname.innerHTML = "Nickname was written right"
    } else {
        let error_nickname = document.getElementById("error_nickname")
        error_nickname.innerHTML = " "
    }
}

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

function dynValidatePassword2() {
    if ((password.value !== password2.value) && password2.value.length !== 0) {
        let error_password2 = document.getElementById("error_password2")
        error_password2.innerHTML = "Passwords do not match"
    } else if (password.value === password2.value) {
        let error_password2 = document.getElementById("error_password2")
        error_password2.innerHTML = "Password was written right"
    } else {
        let error_password2 = document.getElementById("error_password2")
        error_password2.innerHTML = " "
    }
}
