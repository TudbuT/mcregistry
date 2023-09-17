package de.tudbut.registry;

import de.tudbut.io.StreamReader;
import de.tudbut.tools.Tools;
import tudbut.parsing.TCN;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class Registry {

    private static TCN dataStore;
    private static final Set<String> givenOut = new HashSet<>();
    private static boolean initialized = false;

    public static void init() {
        if(initialized) {
            throw new IllegalStateException("Already initialized");
        }
        initialized = true;

        try {
            FileInputStream reader = new FileInputStream("registry.tcnm");
            String s = new StreamReader(reader).readAllAsString();
            dataStore = TCN.readMap(Tools.stringToMap(s));
        } catch (FileNotFoundException e) {
            dataStore = new TCN();
        } catch (IOException e) {
            if(JOptionPane.showConfirmDialog(null, "Unable to load registry! Do you want to delete it?", "Registry", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                dataStore = new TCN();
            }
            else {
                throw new RuntimeException(e);
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                FileOutputStream writer = new FileOutputStream("registry.tcnm");
                writer.write(Tools.mapToString(dataStore.toMap()).getBytes(StandardCharsets.UTF_8));
                writer.close();
            } catch (IOException e) {
                System.out.println(Tools.mapToString(dataStore.toMap()));
                throw new RuntimeException("Unable to save registry! Dumped it to stdout instead.", e);
            }
        }, "Registry shutdown hook"));
    }

    public static TCN register(String keyName) throws IllegalAccessException {
        if(givenOut.contains(keyName) && !keyName.startsWith("public:")) {
            throw new IllegalAccessException("Key " + keyName + " has already been given out and is not public.");
        }
        givenOut.add(keyName);
        TCN key = dataStore.getSub(keyName);
        if(key == null) {
            dataStore.set(keyName, key = new TCN());
        }
        return key;
    }

    public static void unregister(String keyName, TCN key) throws IllegalAccessException {
        if(dataStore.getSub(keyName) != key) {
            throw new IllegalAccessException("Key " + keyName + " has different content than specified.");
        }
        givenOut.remove(keyName);
    }

}
