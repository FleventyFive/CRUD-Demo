package com.fleventyfive.CRUDDemo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {
    private final PlayerRepository repo;

    private Grid<Player> grid = new Grid<>();

    private TextField filterText = new TextField();

    private PlayerForm form;

    public MainView(PlayerRepository repo) {
        this.repo = repo;
        this.form = new PlayerForm(this, repo);

        filterText.setPlaceholder("Filter by username...");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> filterText.clear());
        HorizontalLayout filtering = new HorizontalLayout(filterText, clearFilterTextBtn);

        Button addPlayerBtn = new Button("Add new player");
        addPlayerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setPlayer(new Player());
        });
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addPlayerBtn);


        grid.setSizeFull();

        grid.addColumn(Player::getId).setHeader("Id").setWidth("50px").setFlexGrow(0);
        grid.addColumn(Player::getUsername).setHeader("Username");
        grid.addColumn(Player::getLevel).setHeader("Level");

        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();

        add(toolbar, main);
        setHeight("100vh");
        updateList();

        grid.asSingleSelect().addValueChangeListener(event -> {
            form.setPlayer(event.getValue());
        });
    }

    public void updateList() {
        grid.setItems(repo.findByUsernameStartsWithIgnoreCase(filterText.getValue()));
    }
}
