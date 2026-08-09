/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import java.lang.invoke.LambdaMetafactory;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.Client;
import shit.command.Command;
import shit.command.CommandManager;
import shit.command.ToggleCommand;
import shit.module.Module;
import shit.setting.ColorSetting2;

@Environment(value=EnvType.CLIENT)
public class BindCommand
extends Command {
    public BindCommand() {
        super("bind", "Binds a module to a key.", "bind <module> <key|none>");
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public String[] getSuggestions(int var1_1, Object var2_2) {
        String[] stringArray = (String[])var2_2;
        if (var1_1 == 1) {
            String string = stringArray.length > 1 ? stringArray[1].toLowerCase() : "";
            return (String[])Client.moduleManager.getModules().stream().map(Module::getName).filter(string2 -> string2.toLowerCase().startsWith(string)).toArray(String[]::new);
        }
        return var1_1 == 2 ? new String[]{"none", "y", "r", "right_shift", "mouse4"} : new String[0];
    }

    @Override
    public void run(Object object) {
        block17: {
            Module module;
            Module module2;
            block15: {
                block16: {
                    Integer n;
                    boolean bl;
                    block14: {
                        Integer n2;
                        String[] stringArray;
                        block13: {
                            block12: {
                                block11: {
                                    block10: {
                                        stringArray = (String[])object;
                                        bl = ToggleCommand.isSet106();
                                        if (!bl) break block10;
                                        if (stringArray.length >= 2) break block11;
                                        String string = this.getText31();
                                        String string2 = Client.commandManager.getPrefix();
                                        CommandManager.sendFeedback("Usage: " + string2 + string);
                                    }
                                    return;
                                }
                                module2 = Client.moduleManager.getModule(stringArray[0]);
                                if (!bl) break block12;
                                if (module2 != null) break block13;
                                CommandManager.sendFeedback("Module not found: " + stringArray[0]);
                            }
                            return;
                        }
                        n = n2 = ColorSetting2.parseKey(stringArray[1]);
                        if (!bl) break block14;
                        if (n == null) {
                            CommandManager.sendFeedback("Use a key name, mouse button, GLFW key code, or none.");
                            return;
                        }
                        module = module2;
                        if (!bl) break block15;
                        module.getKeyBindSetting().setValueInternal(n2);
                        n = n2;
                    }
                    if (n != -1) break block16;
                    CommandManager.sendFeedback(module2.getName() + " unbound.");
                    if (bl) break block17;
                }
                module = module2;
            }
            CommandManager.sendFeedback(module.getName() + " bound to " + module2.getKeyBindSetting().getDisplayName() + ".");
        }
    }

    private static /* synthetic */ String[] cfrlam$getSuggestions$1(int n) {
        return new String[n];
    }

    private static /* synthetic */ boolean cfrlam$getSuggestions$0(String string, String string2) {
        return string2.toLowerCase().startsWith(string);
    }
}

