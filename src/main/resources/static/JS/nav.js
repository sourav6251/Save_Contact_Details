window.addEventListener("load", () => {
  const loginButton = document.querySelector("#login");
  const logoutButton = document.querySelector("#logout");
  const log = document.querySelector("#log").textContent;

  if (log == "login") {
    loginButton.classList.add("hidden");
    loginButton.style.display = "none";
    logoutButton.classList.remove("hidden");
    logoutButton.style.display = "block";
    console.log(`login: ${log}`);
  }
  if (log == "logout" || log == '') {
    logoutButton.classList.add("hidden");
    logoutButton.style.display = "none";
    loginButton.classList.remove("hidden");
    loginButton.style.display = "block";
    console.log(`logout: ${log}`);
  }
  console.log(log);
});

// function details() {
//   const display = document.querySelector("#ProfileDetailsEdits");
//   if (display) {
//     display.classList.toggle("hidden");
//   } else {
//     console.error("ProfileDetailsEdit element not found.");
//   }
// }
