function previewFile() {
  const fileInput = document.getElementById("file");
  const preview = document.getElementById("profile-photo-preview");

  const file = fileInput.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      preview.style.backgroundImage = `url(${e.target.result})`;
    };
    reader.readAsDataURL(file);
  }
}



let contain = "add";

async function details() {
  if (contain == "add") {
    try {
      const response = await fetch("/user/profileDetailsEdit");
      const html = await response.text();

      const profileDetailsDiv = document.getElementById("profileSetting");
      profileDetailsDiv.innerHTML = html;
    } catch (error) {
      console.error("Error fetching profile details:", error);
    }
    contain = "remove";
  } else  {
    try {
      const profileDetailsDiv = document.getElementById("profileSetting");
      profileDetailsDiv.innerHTML = ''; 
    } catch (error) {
      console.error("Error removing profile details:", error);
    }
    contain = "add";
  }
}

