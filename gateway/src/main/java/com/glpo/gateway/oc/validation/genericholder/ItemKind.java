package com.glpo.gateway.oc.validation.genericholder;

public enum ItemKind {
    REQUIRED("required"), OPTIONAL("optional"), PROHIBITED("prohibited");

    private String text;

    private ItemKind(String text) {
        this.text = text;
    }

    public String value() {
        return text;
    }

    public static ItemKind getItemKind(String tag) {
        for (ItemKind itemKind : ItemKind.values()) {
            if (itemKind.value().equalsIgnoreCase(tag)) {
                return itemKind;
            }
        }
        throw new IllegalArgumentException("Specified tag \"" + tag + "\" does not exist in configuration");
    }
}
