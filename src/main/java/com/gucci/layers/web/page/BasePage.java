package com.gucci.layers.web.page;

import com.gucci.helper.AlertHelper;
import com.gucci.layers.web.manager.ElementManager;

public abstract class BasePage <T extends BasePage> {
    public abstract T waitForPageLoaded();
    protected final ElementManager elementManager = new ElementManager();
    protected final AlertHelper alertHelper = new AlertHelper();
}
