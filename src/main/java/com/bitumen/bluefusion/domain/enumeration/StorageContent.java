package com.bitumen.bluefusion.domain.enumeration;

import lombok.Getter;

@Getter
public enum StorageContent {
    PETROL("051-0001"),
    DIESEL("050-0001"),
    NONE(""),
    OTHER("");

    public final String code;

    StorageContent(String code) {
        this.code = code;
    }

    public static StorageContent findStorageContentByCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Fuel code cannot be null");
        }

        for (StorageContent storageContent : values()) {
            if (storageContent.code.equals(code)) {
                return storageContent;
            }
        }
        return null;
    }

    public static String findStorageContentCode(StorageContent storageContent) {
        return storageContent != null ? storageContent.code : null;
    }
}
