package gregtech.common;

import gregtech.api.unification.OreDictUnifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandlers {

	@SubscribeEvent
	public static void onEndermanTeleportEvent(EnderTeleportEvent event) {
		if (event.getEntity() instanceof EntityEnderman && event.getEntityLiving()
				.getActivePotionEffect(MobEffects.WEAKNESS) != null) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onEntitySpawningEvent(EntityJoinWorldEvent event) {
		if (event.getEntity() != null && !event.getEntity().getEntityWorld().isRemote) {
			if (event.getEntity() instanceof EntityItem) {
				((EntityItem) event.getEntity()).setItem(OreDictUnifier.getUnificated(((EntityItem) event.getEntity()).getItem()));
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerInteraction(PlayerInteractEvent.RightClickBlock event) {
		ItemStack stack = event.getItemStack();
		if (!stack.isEmpty() && stack.getItem() == Items.FLINT_AND_STEEL) {
			if (!event.getWorld().isRemote
                    && !event.getEntityPlayer().capabilities.isCreativeMode
                    && event.getWorld().rand.nextInt(100) >= 50) { // TODO CONFIG FlintChance
				stack.damageItem(1, event.getEntityPlayer());
				if (stack.getItemDamage() >= stack.getMaxDamage()) {
                    stack.shrink(1);
                }
				event.setCanceled(true);
			}
		}
	}
}
