package net.ano;

import com.google.gson.JsonObject;
import net.ano.mixin.BossHealthAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventListener {

    private static final Pattern pattern = Pattern.compile("§3\\[WAR§3] The battle has begun!");
    private static final Pattern towerPattern = Pattern.compile("§3\\[([a-zA-Z0-9]{3,4})] §b([a-zA-Z0-9 ]{3,})§7 - §4❤ ([0-9]*)§7 \\(§6(\\d+\\.?\\d*%)§7\\) - §c☠ ([0-9]*-[0-9]*)§7 \\(§b(\\d+\\.?\\d*)x§7\\)");

    public static void processChat(Component component) {
        if (Objects.equals(ChatFormatting.stripFormatting(ComponentUtils.getCoded(component)), "[WAR] 5 seconds..."))
            CharacterManager.openClassMenu();
        Matcher matcher = pattern.matcher(ComponentUtils.getCoded(component));
        if (!matcher.matches()) return;
        CharacterManager.openClassMenu();
        BossHealthAccessor overlay = (BossHealthAccessor) anode.getMc().gui.getBossOverlay();
        for (Map.Entry<UUID, LerpingBossEvent> entry : overlay.getBossBars().entrySet()) {
            LerpingBossEvent event = entry.getValue();
            String name = ComponentUtils.getCoded(event.getName());
            Matcher tower = towerPattern.matcher(name);
            if (!tower.matches()) continue;
            String towerString = String.format("{\"owner\": \"%s\", \"territory\": \"%s\", \"health\": %d, \"defense\": %f, \"damage\": \"%s\", \"attackSpeed\": %f}",
                    tower.group(1), tower.group(2), Integer.parseInt(tower.group(3)), Float.parseFloat(tower.group(4)), tower.group(5), Float.parseFloat(tower.group(6)));
            String jsonString = String.format("{\"class_\": \"%s\", \"name\": \"%s\",  \"uuid\": \"%s\", \"tower\": %s}",
                    anode.classname, anode.getPlayer().getName().getString(), anode.getPlayer().getUUID(), towerString
            );
            String response;
            try {
                response = WebUtils.postAPI(new URL("http://38.242.159.42:6969/postwar"), jsonString).get().toString();
            } catch (IOException | ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            anode.logger.info(String.format("POST WAR, FROM SERVER: {%s}", response));
            anode.getPlayer().sendSystemMessage(Component.literal("[Anode] Tracked attempt"));
            anode.getMc().getHotbarManager();
        }
    }

    public static void processContainerOpened(Component component) {
        if (!Objects.equals(ChatFormatting.stripFormatting(ComponentUtils.getCoded(component)), "Titans Valor: Territories"))
            return;
        AbstractContainerMenu menu = anode.getPlayer().containerMenu;
        List<ItemStack> items = menu.getItems();
        WebUtils.readJsonFromUrl("http://38.242.159.42:6969/conn.json").thenAccept((el) -> {
            WebUtils.readJsonFromUrl("https://api.wynncraft.com/public_api.php?action=territoryList").thenAcceptAsync((json) -> {

            });

        });
        for (ItemStack stack : items) {
            String name = ChatFormatting.stripFormatting(ComponentUtils.getCoded(stack.getDisplayName()));
            assert name != null;
            if (name.equals("Back")) {
                //process
            }

        }
    }

}