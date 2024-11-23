package com.contact.operation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.db.Contact;
import com.contact.db.Users;
import com.contact.dto.ContactData;
import com.contact.dto.RegisterData;
import com.contact.repository.ContactRepository;
import com.contact.repository.UserRepository;

import jakarta.transaction.Transactional;

//TODO:DB OPERATION

@Transactional
@Service
public class DBOperations {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ContactRepository contactRepository;

    public void testaddData() {
        Users user = new Users("Sourav", "sourav", "sourv@gmail.com", "I am a new user",
                "http://hujkfdufd.adsfdasfadsf.dsaf.dfs.png", true, "user", null);
        List<Contact> contacts = new ArrayList<>();

        Contact contact1 = new Contact("Sourav Das", "9800426955", "Sourav@gmail.com", "he is my friend",
                "http://hujkfdufd.adsfdasfadsf.dsaf.dfs.png", user);
        Contact contact2 = new Contact("Sourav Das", "9800426956", "Sourav@gmail.com", "he is my friend",
                "http://hujkfdufd.adsfdasfadsf.dsaf.dfs.png", user);
        Contact contact3 = new Contact("Sourav Das", "9800426957", "Sourav@gmail.com", "he is my friend",
                "http://hujkfdufd.adsfdasfadsf.dsaf.dfs.png", user);

        contacts.add(contact3);
        contacts.add(contact2);
        contacts.add(contact1);

        user.setContact(contacts);
        userRepository.save(user);

    }

    /**
     * Registers a new user with the provided registration data.
     *
     * @param registerData The registration data for the new user.
     * @return "email" if the email address is already registered, "success" if the
     *         registration was successful, or the exception message if an error
     *         occurred.
     */
    public String register(RegisterData registerData) {
        if (isExistWithEmail(registerData.getEmail())) {
            return "email";
        }
        try {
            Users user = new Users(registerData.getName(),
                    registerData.getPassword(),
                    registerData.getEmail(), null, null, true, "user", null);
            userRepository.save(user);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    /**
     * Adds a new contact to the specified user's contact list.
     *
     * @param contactData The contact data to be added.
     * @param id          The ID of the user to add the contact to.
     */
    public void addContact(ContactData contactData, int id) {
        long ids = id;

        Users user = userRepository.findById(ids).orElse(null);
        Contact contact = new Contact(contactData.getName(), contactData.getNumber(), contactData.getEmail(),
                contactData.getAbout(), null, user);

        user.getContact().add(contact);
        userRepository.save(user);
    }

    /**
     * Checks if a user with the given email address already exists in the system.
     *
     * @param email The email address to check for.
     * @return True if a user with the given email address exists, false otherwise.
     */
    public boolean isExistWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Retrieves the username of a user by their email address.
     *
     * @param email The email address of the user to retrieve the username for.
     * @return The username of the user with the specified email address, or null if
     *         no user is found.
     */
    public String getUsername(String email) {
        Users users = userRepository.findByEmail(email);
        if (users != null) {
            return users.getName();
        }
        return null;
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return The user object with the specified email address, or null if no user
     *         is found.
     */
    public Users getUsers(String email) {
        Users users = userRepository.findByEmail(email);
        return users;
    }

    /**
     * Updates a user in the system.
     *
     * @param users The user object to be updated.
     * @return True if the user was successfully updated, false otherwise.
     */
    public boolean updateUser(Users users) {
        Users user = userRepository.findByEmail(users.getEmail());
        // if (user != null) {
            user.setName(users.getName());
            user.setEmail(users.getEmail());
            user.setPassword(users.getPassword());
            user.setAbout(users.getAbout());
        // }
        // System.out.println(
        // "\n\n#############\nEnter into DBOperation\nname: " + users.getName() +
        // "\nemail: " + users.getEmail()
        // + "\npassword: " + users.getPassword() + "\nabout: " + users.getAbout() +
        // "\n\n" + "\nID: "
        // + users.getUser_id() + "\n\n");

        userRepository.save(user);
        return false;
    }
}
