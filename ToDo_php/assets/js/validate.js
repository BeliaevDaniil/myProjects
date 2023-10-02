//Validation procedure
function init() {
	const formElement = document.querySelector(".form");
	formElement.addEventListener('submit', validate);
}
function validate(event) {
	let nickname = document.querySelector("#nickname");
	let email = document.querySelector("#email");
	let password = document.querySelector("#password");
	let password2 = document.querySelector("#password2");
	//Nickname validation
	if (nickname.value.length < 3) {
		alert("Nickname is too short, min. length is 3 characters");
		event.preventDefault();
		return;
	}
	//E-mail validation
	if (!email.value.includes("@")) {
		alert("Email was written wrong");
		event.preventDefault();
		return;
	}
	//Passwords validation
	if (password.value.length < 6) {
		alert("Password is too short, min. length is 6 characters");
		event.preventDefault();
		return;
	}
	if (password.value !== password2.value) {
		alert("Passwords do not match");
		event.preventDefault();
		return;
	}
}