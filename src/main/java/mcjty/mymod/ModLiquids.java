package mcjty.mymod;

import mcjty.mymod.fload.LiquidFload;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ModLiquids {

    public static final Fluid fload = new LiquidFload();

    public static void init() {
        // @todo 1.13
//        FluidRegistry.registerFluid(fload);
//        FluidRegistry.addBucketForFluid(fload);
    }

    public static boolean isValidFloadStack(FluidStack stack){
        return getFluidFromStack(stack) == fload;
    }

    public static Fluid getFluidFromStack(FluidStack stack){
        return stack == null ? null : stack.getFluid();
    }

    public static String getFluidName(FluidStack stack){
        Fluid fluid = getFluidFromStack(stack);
        return getFluidName(fluid);
    }

    public static String getFluidName(Fluid fluid){
        return fluid == null ? "null" : fluid.getName();
    }

    public static int getAmount(FluidStack stack){
        return stack == null ? 0 : stack.amount;
    }
}
