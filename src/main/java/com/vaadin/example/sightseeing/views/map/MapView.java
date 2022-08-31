package com.vaadin.example.sightseeing.views.map;

import com.vaadin.example.sightseeing.data.generator.DataGenerator;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.View;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import javax.annotation.security.PermitAll;

@PageTitle("Map")
@Route(value = "map")
@RouteAlias(value = "")
@PermitAll
public class MapView extends VerticalLayout {

    private Map map = new Map();

    public MapView() {
        setSizeFull();
        setPadding(false);
        map.getElement().setAttribute("theme", "borderless");
        View view = map.getView();
        view.setCenter(DataGenerator.CENTER);
        view.setZoom(12);
        addAndExpand(map);
    }
}
