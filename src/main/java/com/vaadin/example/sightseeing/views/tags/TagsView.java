package com.vaadin.example.sightseeing.views.tags;

import com.vaadin.example.sightseeing.data.entity.Tag;
import com.vaadin.example.sightseeing.data.service.TagService;
import com.vaadin.example.sightseeing.ui.AdminNav;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Tags")
@Route(value = "tags/:tagID?/:action?(edit)")
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class TagsView extends Div implements BeforeEnterObserver {

    private final String TAG_ID = "tagID";
    private final String TAG_EDIT_ROUTE_TEMPLATE = "tags/%s/edit";

    private Grid<Tag> grid = new Grid<>(Tag.class, false);

    private TextField placeName;
    private TextField name;
    private TextField val;
    private Checkbox enabled;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Tag> binder;

    private Tag tag;

    private final TagService tagService;

    @Autowired
    public TagsView(TagService tagService) {
        this.tagService = tagService;
        addClassNames("tags-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn(i -> i.getPlace().getName()).setHeader("place").setAutoWidth(true);
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("val").setAutoWidth(true);
        LitRenderer<Tag> enabledRenderer = LitRenderer.<Tag>of(
                "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
                .withProperty("icon", enabled -> enabled.isEnabled() ? "check" : "minus").withProperty("color",
                        enabled -> enabled.isEnabled()
                                ? "var(--lumo-primary-text-color)"
                                : "var(--lumo-disabled-text-color)");

        grid.addColumn(enabledRenderer).setHeader("Enabled").setAutoWidth(true);

        grid.setItems(query -> tagService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(TAG_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(TagsView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Tag.class);

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.tag == null) {
                    this.tag = new Tag();
                }
                binder.writeBean(this.tag);
                tagService.update(this.tag);
                clearForm();
                refreshGrid();
                Notification.show("Tag details stored.");
                UI.getCurrent().navigate(TagsView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the tag details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> tagId = event.getRouteParameters().get(TAG_ID).map(UUID::fromString);
        if (tagId.isPresent()) {
            Optional<Tag> tagFromBackend = tagService.get(tagId.get());
            if (tagFromBackend.isPresent()) {
                populateForm(tagFromBackend.get());
            } else {
                Notification.show(String.format("The requested tag was not found, ID = %s", tagId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(TagsView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        placeName = new TextField("Place");
        name = new TextField("Name");
        val = new TextField("Val");
        enabled = new Checkbox("Enabled");
        Component[] fields = new Component[] { placeName, name, val, enabled };

        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);
        editorLayoutDiv.add(new AdminNav("tags"));

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Tag value) {
        this.tag = value;
        binder.readBean(this.tag);
    }
}
