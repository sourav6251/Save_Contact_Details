// Ensure this runs after the page is fully loaded
window.addEventListener("load", () => {
  // Get the span containing the image URL
  const imageUrl = document.querySelector("#imageURL").textContent;

  // Get the profile photo div element
  const profilePhoto = document.getElementById("profile-photo");

  // Update the background-image style of the div
  if (imageUrl) {
    profilePhoto.style.backgroundImage = `url(${imageUrl})`;
  }
});
