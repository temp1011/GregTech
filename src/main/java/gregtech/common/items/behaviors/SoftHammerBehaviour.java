package gregtech.common.items.behaviors;

import gregtech.api.capability.IWorkable;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class SoftHammerBehaviour implements IItemBehaviour {

    private final int cost;

    public SoftHammerBehaviour(int cost) {
        this.cost = cost;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (world.isRemote || world.isAirBlock(pos)) {
            return EnumActionResult.PASS;
        }
        ItemStack stack = player.getHeldItem(hand);

        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity != null) {
            IWorkable workable = tileEntity.getCapability(IWorkable.CAPABILITY_WORKABLE, null);
            if (workable != null) {
                if (workable.isWorkingEnabled()) {
                    workable.setWorkingEnabled(false);
                } else {
                    workable.setWorkingEnabled(true);
                }
                GTUtility.doDamageItem(stack, cost, false);
            }
        }

        return EnumActionResult.PASS;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.format("behaviour.softhammer"));
    }
}
