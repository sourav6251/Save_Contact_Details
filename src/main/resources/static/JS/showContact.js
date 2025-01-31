document.addEventListener("DOMContentLoaded", () => {
  const detailsLinks = document.querySelectorAll(".details-link");
  const contactModal = document.getElementById("contactModal");
  const modalContent = document.getElementById("modalContent");
  const closeModalButton = document.getElementById("closeModal");

  // Handle click on details link
  detailsLinks.forEach((link) => {
    link.addEventListener("click", async (e) => {
      e.preventDefault();
      const contactId = link.getAttribute("data-id");

      // Fetch contact details from the server
      const response = await fetch(`/user/contactDetails/${contactId}`);
      const html = await response.text();

      // Populate the modal with the fetched content
      modalContent.innerHTML = html;

      // Update the edit link with the correct contact ID
      const editContactLink = modalContent.querySelector("#editContactLink");
      if (editContactLink) {
        editContactLink.href = `/user/editContact=${contactId}`;
      }

      //Update the delete link with the correct contact ID
      const deleteContactLink = modalContent.querySelector("#deleteContact");
      if (deleteContactLink) {
        deleteContactLink.href = `/user/deleteContact=${contactId}`;
      }
      // Show the modal
      contactModal.classList.remove("hidden");
      contactModal.classList.add("flex");
    });
  });

  // Close the modal when the close button is clicked
  closeModalButton.addEventListener("click", () => {
    contactModal.classList.remove("flex");
    contactModal.classList.add("hidden");
  });
});

let count = "details";
// Modify the details() function
async function details() {
  if (count == "details") {
    try {
      // Fetch the content from profileDetailsEdit
      const response = await fetch("/user/profileDetailsEdit");
      const html = await response.text();

      // Update the profile details section
      const profileDetailsDiv = document.getElementById("profileDetails");
      profileDetailsDiv.innerHTML = html;
    } catch (error) {
      console.error("Error fetching profile details:", error);
    }
    count = "edit";
  } else if (count == "edit") {
    try {
      // Initial load with regular profile details
      const response = await fetch("/user/profileDetails");
      await fetch("../JS/profilePhoto.js");
      const html = await response.text();
      add(html);
    } catch (error) {
      console.error("Error loading initial profile:", error);
    }
    count = "details";
  }
}

// Modify the initial DOMContentLoaded event listener
document.addEventListener("DOMContentLoaded", async () => {
  try {
    // Initial load with regular profile details
    const response = await fetch("/user/profileDetails");
    const html = await response.text();
    add(html);
  } catch (error) {
    console.error("Error loading initial profile:", error);
  }
});

function add(html) {
  const profileDetailsDiv = document.getElementById("profileDetails");
  profileDetailsDiv.innerHTML = html;
}
