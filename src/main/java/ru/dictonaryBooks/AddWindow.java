package ru.dictonaryBooks;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Window;

/**
 * Created by nasak on 05.03.14.
 */
public class AddWindow extends Window {
    FormLayout addLayout = new FormLayout();
    public AddWindow(String tittle) {
        setCaption(tittle);
        setModal(true);
        setResizeLazy(true);
        setResizable(false);
        setClosable(false);
        center();

        addLayout.setMargin(true);
        addLayout.setSpacing(true);
        addLayout.setSizeUndefined();
        setContent(addLayout);
    }

    public FormLayout getAddLayout() {
        return addLayout;
    }
    public void addComponent(Component component) {
        addLayout.addComponent(component);
    }
}
