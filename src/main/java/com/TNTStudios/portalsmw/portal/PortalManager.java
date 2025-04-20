package com.TNTStudios.portalsmw.portal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.util.math.BlockPos;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PortalManager {

    private static final Map<String, PortalData> portals = new HashMap<>();
    private static final Map<BlockPos, String> positionMap = new HashMap<>();
    private static final Gson GSON = new Gson();
    private static final File CONFIG_FILE = new File("config/portalsmw/portals.json");

    public static void loadPortals() {
        if (!CONFIG_FILE.exists()) return;
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            Type type = new TypeToken<Map<String, PortalData>>(){}.getType();
            Map<String, PortalData> loaded = GSON.fromJson(reader, type);
            portals.clear();
            positionMap.clear();
            portals.putAll(loaded);
            for (Map.Entry<String, PortalData> entry : portals.entrySet()) {
                positionMap.put(entry.getValue().getPosition(), entry.getKey());
            }
        } catch (Exception e) {
            System.err.println("[PortalsMw] Error al cargar portales: " + e.getMessage());
        }
    }

    public static void savePortals() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(portals, writer);
            }
        } catch (Exception e) {
            System.err.println("[PortalsMw] Error al guardar portales: " + e.getMessage());
        }
    }

    public static void createPortal(String name, String command, BlockPos pos) {
        PortalData data = new PortalData(name, command, pos, false);
        portals.put(name, data);
        positionMap.put(pos, name);
        savePortals();
    }

    public static void activatePortal(String name) {
        PortalData data = portals.get(name);
        if (data != null) {
            data.setActive(true);
            savePortals();
        }
    }

    public static void deactivatePortal(String name) {
        PortalData data = portals.get(name);
        if (data != null) {
            data.setActive(false);
            savePortals();
        }
    }

    public static boolean isPortalActive(String name) {
        PortalData data = portals.get(name);
        return data != null && data.isActive();
    }

    public static String getCommandForPortal(String name) {
        PortalData data = portals.get(name);
        return data != null ? data.getCommand() : null;
    }

    public static String getKeyFromPos(BlockPos pos) {
        return positionMap.get(pos);
    }

    public static Map<String, PortalData> getAllPortals() {
        return portals;
    }
}
