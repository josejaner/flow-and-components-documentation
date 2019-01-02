package com.vaadin.flow.tutorial.components;

import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcut;
import com.vaadin.flow.component.ShortcutNotifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.tutorial.annotations.CodeFor;

@CodeFor("components/tutorial-flow-shortcut.asciidoc")
public class Shortcuts {

    public void focusableShortcut() {
        TextField username = new TextField();
        username.addShortcutListener(Shortcut.of(Key.ENTER), this::login);
    }

    public void clickShortcut() {
        TextField username = new TextField();
        TextField password = new TextField();
        Button login = new Button();
        login.addClickListener(event -> login(),
                Shortcut.of(Key.ENTER).withSources(username, password));
    }

    /**
     * Global shortcut example (third)
     */
    public class SomeView extends Div {
        private Registration shortcutRegistration;

        public SomeView() {
            shortcutRegistration = UI.getCurrent().addShortcutListener(
                    Shortcut.of(Key.BACKSPACE),
                    event -> UI.getCurrent().navigate(MainView.class));
        }

        /**
         * Removes the registered global shortcut
         */
        private void removeShortcut() {
            if (shortcutRegistration != null) shortcutRegistration.remove();
        }
    }

    /**
     * ShortcutNotifier example (fourth)
     */
    public class Login1 {
        public class LoginScreen extends FlexLayout implements ShortcutNotifier {

            public LoginScreen() {
                TextField username = new TextField();
                TextField password = new TextField();
                Button login = new Button();

                add(username);
                add(password);
                add(login);

                this.addShortcutListener(Shortcut.of(Key.ENTER), this::login);
            }

            private void login() {
                // Login goes here.
            }
        }
    }

    /**
     * Alternative approach for ShortcutNotifier (fifth)
     */

    public class Login2 {
        public class LoginScreen extends FlexLayout {

            public LoginScreen() {
                TextField username = new TextField();
                TextField password = new TextField();
                Button login = new Button();

                add(username);
                add(password);
                add(login);

                // notice the layout as a source for the shortcut. The shortcut will only
                // get triggered when used from a focused field inside the layout
                login.addClickListener(event -> login(),
                        Shortcut.of(Key.ENTER).withSources(this));
            }

            private void login() {
                // Login goes here.
            }
        }
    }

    /**
     * Focusable + tabindex trick (sixth)
     */
    public class Login3 {
        public class LoginScreen extends FlexLayout implements Focusable {

            public LoginScreen() {
                // ...

                // Make the component selectable by the users' clicks
                setTabIndex(-1);

                // This removes the focus style when user clicks it.
                this.getElement().getStyle().set("outline", "none");

                // ...
            }
        }
    }

    /**
     * Helpers
     */

    private void login() {}

    public class MainView extends Div {

    }
}
