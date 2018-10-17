package com.fleventyfive.CRUDDemo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import org.springframework.beans.factory.annotation.Autowired;

public class PlayerForm extends FormLayout {
    private TextField username = new TextField("Username");
    private TextField level = new TextField("Level");

    private MainView mainView;

    private final PlayerRepository repo;

    private Player player;

    private Binder<Player> binder = new Binder<>(Player.class);

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    @Autowired
    public PlayerForm(MainView mainView, PlayerRepository repo) {
        this.mainView = mainView;
        this.repo = repo;

        add(username, level);

        binder.forField(username).asRequired("Username is required").bind(Player::getUsername, Player::setUsername);
        binder.forField(level)
                .withConverter(Integer::valueOf,
                        String::valueOf,
                        "Please enter a number")
                .bind(Player::getLevel, Player::setLevel);

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());

        HorizontalLayout buttons = new HorizontalLayout(save, delete);

        add(username, level, buttons);

        save.getElement().setAttribute("theme", "primary");

        setPlayer(null);
    }

    public void setPlayer(Player player) {
        this.player = player;

        binder.setBean(player);

        boolean enabled = player != null;

        save.setEnabled(enabled);
        delete.setEnabled(enabled);
        username.setEnabled(enabled);
        level.setEnabled(enabled);

        if(enabled) {
            username.focus();
        }
    }

    private void save() {
        repo.save(player);
        mainView.updateList();
        setPlayer(null);
    }

    private void delete() {
        repo.delete(player);
        mainView.updateList();
        setPlayer(null);
    }

}
