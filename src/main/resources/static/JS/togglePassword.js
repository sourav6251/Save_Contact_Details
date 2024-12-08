document.addEventListener("DOMContentLoaded", () => {
  // Select all toggle buttons
  const toggleButtons = document.querySelectorAll(".togglePassword");

  toggleButtons.forEach((button) => {
    button.addEventListener("click", () => {
      // Find the associated input element (previous sibling)
      const passwordInput = button.previousElementSibling;

      // Toggle password visibility
      if (passwordInput.type === "password") {
        passwordInput.type = "text";
        button.src = "../icon/eye-off.svg"; // Change to "eye-off" icon
      } else {
        passwordInput.type = "password";
        button.src = "../icon/eye-open.svg"; // Change back to "eye-open" icon
      }
    });
  });
});
