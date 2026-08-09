/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.invoke.LambdaMetafactory;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import shit.Client;
import shit.command.Command;
import shit.command.CommandManager;
import shit.command.ToggleCommand;
import shit.render.LineRenderer2;

@Environment(value=EnvType.CLIENT)
public class KitCommand
extends Command {
    private static final MinecraftClient client2 = MinecraftClient.getInstance();

    public KitCommand() {
        super("kit", "Manages SmartRegear kits.", "kit <list|create|delete> [name]");
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public String[] getSuggestions(int var1_1, Object var2_2) {
        String[] parts = (String[])var2_2;
        if (var1_1 == 1) {
            String prefix = parts.length > 1 ? parts[1].toLowerCase() : "";
            return (String[])Arrays.stream(new String[]{"list", "create", "delete"}).filter(string -> string.startsWith(prefix)).toArray(String[]::new);
        }
        if (var1_1 == 2) {
            String sub = parts.length > 1 ? parts[1].toLowerCase() : "";
            if (!sub.equals("delete") && !sub.equals("del") && !sub.equals("remove")) {
                return new String[0];
            }
            File dir = new File(client2.runDirectory, "kissoo/kits");
            File[] files = dir.listFiles((file, name) -> name.endsWith(".kit"));
            if (files == null) {
                return new String[0];
            }
            String prefix = parts.length > 2 ? parts[2].toLowerCase() : "";
            return (String[])Arrays.stream(files).map(file -> file.getName().replace(".kit", "")).filter(string -> string.toLowerCase().startsWith(prefix)).toArray(String[]::new);
        }
        return new String[0];
    }

    @Override
    public void run(Object object) {
        String[] args = (String[])object;
        if (args.length < 1) {
            CommandManager.sendFeedback("Usage: " + Client.commandManager.getPrefix() + this.getText31());
            return;
        }
        String sub = args[0].toLowerCase();
        File dir = new File(client2.runDirectory, "kissoo/kits");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (sub.equals("list")) {
            File[] files = dir.listFiles((file, name) -> name.endsWith(".kit"));
            if (files == null || files.length == 0) {
                CommandManager.sendFeedback("No kits found. Create one with 'kit create <name>'.");
                return;
            }
            ArrayList<String> names = new ArrayList<String>();
            for (File file : files) {
                String name = file.getName();
                names.add(name.substring(0, name.length() - 4));
            }
            CommandManager.sendFeedback("Saved Kits: §a" + String.join("§7, §a", names));
            return;
        }
        if (args.length < 2) {
            CommandManager.sendFeedback("Missing kit name.");
            return;
        }
        String name = args[1];
        File file = new File(dir, name + ".kit");
        if (sub.equals("create") || sub.equals("add")) {
            if (client2.player == null) {
                CommandManager.sendFeedback("You must be in-game to create a kit.");
                return;
            }
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));){
                for (int i = 0; i < 40; ++i) {
                    String id = Registries.ITEM.getId(client2.player.getInventory().getStack(i).getItem()).toString();
                    writer.println(i + ":" + id);
                }
                CommandManager.sendFeedback("Kit §a" + name + "§7 created successfully.");
            }
            catch (Exception exception) {
                CommandManager.sendFeedback("§cFailed to create kit " + name + ".");
                exception.printStackTrace();
            }
            return;
        }
        if (sub.equals("delete") || sub.equals("del") || sub.equals("remove")) {
            if (file.exists() && file.delete()) {
                CommandManager.sendFeedback("Kit §c" + name + "§7 removed.");
            } else {
                CommandManager.sendFeedback("Kit §c" + name + "§7 not found.");
            }
            return;
        }
        CommandManager.sendFeedback("Usage: " + Client.commandManager.getPrefix() + this.getText31());
    }

    private static /* synthetic */ String[] cfrlam$getSuggestions$5(int n) {
        return new String[n];
    }

    private static /* synthetic */ boolean cfrlam$getSuggestions$4(String string, String string2) {
        return string2.toLowerCase().startsWith(string);
    }

    private static /* synthetic */ String cfrlam$getSuggestions$3(File file) {
        return file.getName().replace(".kit", "");
    }

    private static /* synthetic */ boolean cfrlam$getSuggestions$2(File file, String string) {
        return string.endsWith(".kit");
    }

    private static /* synthetic */ String[] cfrlam$getSuggestions$1(int n) {
        return new String[n];
    }

    private static /* synthetic */ boolean cfrlam$getSuggestions$0(String string, String string2) {
        return string2.startsWith(string);
    }

    /*
     * Unable to fully structure code
     */
    static {}
}

