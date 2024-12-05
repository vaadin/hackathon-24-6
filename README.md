# hackathon-24-6 by Herberts

### What I did:
I played around with the new Dashboard. Tried to implement it in a way my previous customer would want / designed. 
I found several issues, and thought of some missing features that I'd like to see. 

My feedback:
* The dashboard looks good out of the box, all the resizing and moving of items is very smooth.
* In edit mode, I'd like to be able to disable resizing. Sometimes I'd like to prevent resize horizontally or vertically. This could be controlled on widget / section / dashboard level.
* Feels like sections should have their own column count restrictions, so that I could have 3 columns in section 1, 4 columns in section 2. I could potentially use different dashboards for this, but then I can't move the sections around.
* Would be nice to have control of how many columns there should be. So ability to provide responsive steps I guess. For example, I don't want the dashboard to have 3 columns.
* In addition to the responsive steps of the previous point, I'd like to have a listener for when change between responsive steps occurs, so that I could add/remove widgets automatically and control how much content is shown.
* Not sure why DashboardItemMovedEvent event.getItems() returns root level items? It's not clear by the name of the method that it returns the root level items of the dashboard. I would expect that method would return all involved / moved items. Is there a use case for having root level items in the moved event?
* In DashboardItemMovedEvent there is no way to determine the new item index. Could use dashboard.getWidgets() or section.getWidgets().. but doesn't seem like there's equivalent for sections (no dashboard.getSection()).
* Dashboard or section should have a way to determine given item index, or the item itself should be aware of its index.
* Seems like there's no straight forward way to move a widget programmatically. I'm guessing I can remove(..) and addItemToIndex(..) to achieve this, but would be nice to have something like moveToIndex(..)  and / or moveLeft(..) , moveRight().
* The ability to reorder / resize by keyboard is really cool. A11y support is appriciated. 