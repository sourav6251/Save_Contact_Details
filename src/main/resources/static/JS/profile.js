document.addEventListener("DOMContentLoaded", () => {
  // Select the navlogo element
  const profileLink = document.getElementById("navLogo");

  const contactModal = document.getElementById("contactModal");
  const closeModalButton = document.getElementById("closeModal");

  profileLink.addEventListener("click", async (e) => {
    e.preventDefault();
    console.log("Toggle Navlogo");
    try {
      const response = await fetch("/user/profileDetailsEdit");

      // Check if the response is unauthorized
      if (response.status === 401) {
        console.error("User is unauthorized");
        contactModal.classList.remove("flex");
        contactModal.classList.add("hidden");
        return;
      }

      const html = await response.text();
      const profileDetailsDiv = document.getElementById("modalContent");
      profileDetailsDiv.innerHTML = html;

      contactModal.classList.remove("hidden");
      contactModal.classList.add("flex");
    } catch (error) {
      console.error("Error fetching profile details:", error);
      contactModal.classList.remove("flex");
      contactModal.classList.add("hidden");
    }
  });

  closeModalButton.addEventListener("click", () => {
    contactModal.classList.remove("flex");
    contactModal.classList.add("hidden");
  });

  // Optionally close the modal if the user clicks outside
  document.addEventListener("click", (event) => {
    if (
      !contactModal.contains(event.target) &&
      !profileLink.contains(event.target)
    ) {
      contactModal.classList.remove("flex");
      contactModal.classList.add("hidden");
    }
  });
});
