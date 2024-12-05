package com.vaadin.herberts.views;

import com.vaadin.flow.component.dashboard.Dashboard;
import com.vaadin.flow.component.dashboard.DashboardSection;
import com.vaadin.flow.component.dashboard.DashboardWidget;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.commons.lang3.ObjectUtils;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@AnonymousAllowed
@Route(value = "dashboard")
@Menu(order = 1, icon = LineAwesomeIconUrl.TABLE_SOLID)
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        add(new H1("Dashboard"));

        var dashboard = new Dashboard();
        dashboard.setEditable(true);
        dashboard.setMaximumColumnCount(4);
        dashboard.addItemMovedListener(event -> {
            var sectionName = event.getSection().map(DashboardSection::getTitle).orElse("_");
            var item = (DashboardWidget) event.getItem();
            var items = event.getItems();
            Notification.show("%s in section %s was moved. %d items given".formatted(item.getTitle(), sectionName, items.size()));
        });

        var miniSection = dashboard.addSection("Mini section");
        miniSection.add(
                createMiniDashboardWidget("Points", "1"),
                createMiniDashboardWidget("Steals", "5"),
                createMiniDashboardWidget("Rebounds", "4"),
                createMiniDashboardWidget("Assists", "1")
                );

        var bigSection = dashboard.addSection("Big section");
        bigSection.add(
                createBigDashboardWidget("Trend 1", "55"),
                createBigDashboardWidget("Trend 2", "12"),
                createBigDashboardWidget("Trend 3", "112"),
                createBigDashboardWidget("Trend 4", "0.4")
        );

        add(dashboard);
    }

    private DashboardWidget createMiniDashboardWidget(String name, String value) {
        var widget = new DashboardWidget(name);
        widget.addClassName("mini-widget");
        widget.setContent(new Div(value));
        return widget;
    }

    private DashboardWidget createBigDashboardWidget(String name, String value) {
        var widget = new DashboardWidget(name);
        widget.setColspan(2);
        widget.addClassName("big-widget");

        var verticalLayout = new VerticalLayout(new Paragraph("This is a big widget with more content"), new Div(value));
        widget.setContent(verticalLayout);
        verticalLayout.setHeight("400px");
        verticalLayout.setWidthFull();
        verticalLayout.setAlignItems(Alignment.CENTER);
        return widget;
    }
}
