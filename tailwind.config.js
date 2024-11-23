/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./src/main/resources/HTML/**/*.html", // Updated pattern
        // "./src/main/resources/templates/**/*.{html}",
        "./src/main/resources/static/**/*.{js,css}",
    ],
    theme: {
        extend: {},
    },
    plugins: [],
};
