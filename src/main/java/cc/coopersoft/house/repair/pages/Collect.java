package cc.coopersoft.house.repair.pages;

import org.apache.deltaspike.core.api.config.view.ViewConfig;

public interface Collect extends ViewConfig {

    class Payment implements Collect {}


    class Notice implements Collect {}

    class House implements Collect {}
}
