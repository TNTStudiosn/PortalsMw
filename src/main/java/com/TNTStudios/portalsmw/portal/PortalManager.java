package com.TNTStudios.portalsmw.portal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PortalManager {

    private static final File CONFIG_FILE = new File("config/portalsmw/portals.json");
    // Ahora usamos GsonBuilder con pretty printing
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Map<String, String> portals = new HashMap<>();
    private static String activePortal = null;
    private static boolean isActive = true;

    public static void createPortal(String name, String command) {
        portals.put(name, command);
        savePortals();
    }

    public static void activatePortal(String name) {
        if (portals.containsKey(name)) {
            activePortal = name;
            isActive = true;
            savePortals();
        }
    }

    public static void deactivatePortal() {
        isActive = false;
        savePortals();
    }

    public static String getCommandIfActive() {
        if (isActive && activePortal != null) {
            return portals.get(activePortal);
        }
        return null;
    }

    public static boolean isPortalActive() {
        return isActive;
    }

    public static void loadPortals() {
        if (!CONFIG_FILE.exists()) return;
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            Type type = new TypeToken<PortalConfig>() {}.getType();
            PortalConfig config = GSON.fromJson(reader, type);
            portals.clear();
            portals.putAll(config.portals);
            activePortal = config.activePortal;
            isActive = config.isActive;
        } catch (Exception e) {
            System.err.println("[PortalsMw] Error al cargar portales: " + e.getMessage());
        }
    }

    public static void savePortals() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                PortalConfig config = new PortalConfig();
                config.portals = portals;
                config.activePortal = activePortal;
                config.isActive = isActive;
                // Esto ahora escribe el JSON con sangrías y saltos de línea
                GSON.toJson(config, writer);
            }
        } catch (Exception e) {
            System.err.println("[PortalsMw] Error al guardar portales: " + e.getMessage());
        }
    }

    private static class PortalConfig {
        public Map<String, String> portals = new HashMap<>();
        public String activePortal = null;
        public boolean isActive = true;
    }
}
