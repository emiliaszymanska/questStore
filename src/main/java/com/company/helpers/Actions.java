
package com.company.helpers;

import java.util.Optional;
import java.util.UUID;

public class Actions {

    private String[] URLComponents;

    public Actions(String[] URLComponents) {
        this.URLComponents = URLComponents;
    }

    public Optional<UUID> getUUID() {
        if (this.URLComponents.length != 4)
            return Optional.empty();
        return Optional.of(UUID.fromString(URLComponents[3]));
    }

    public String getEntity() {
        return URLComponents[2];
    }

    public String getOperation() {
        return URLComponents[2];
    }

    public int getActionsSize() {
        return URLComponents.length;
    }

    public int getThirdComponent() {
        return Integer.parseInt(URLComponents[2]);
    }
}