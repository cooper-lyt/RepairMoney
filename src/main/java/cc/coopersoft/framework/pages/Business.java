package cc.coopersoft.framework.pages;

import org.apache.deltaspike.core.api.config.view.ViewConfig;

public interface Business extends ViewConfig {

    class Editor implements Business {}

    class PrepareComplete implements Business {}

    class PrepareCreate implements Business {}

    class Completed implements Business {}
}
