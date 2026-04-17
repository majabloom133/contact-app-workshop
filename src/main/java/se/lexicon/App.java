package se.lexicon;

import se.lexicon.controller.ContactController;
import se.lexicon.data.ContactDAO;
import se.lexicon.data.FileContactDAOImpl;
import se.lexicon.view.ContactView;

public class App {
    public static void main(String[] args) {

        ContactDAO dao = new FileContactDAOImpl("contacts.txt");
        ContactView view = new ContactView();
        ContactController controller = new ContactController(dao, view);

        controller.run();
    }
}