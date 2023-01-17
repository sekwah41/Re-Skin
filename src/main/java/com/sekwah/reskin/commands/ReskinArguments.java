package com.sekwah.reskin.commands;

import com.sekwah.reskin.core.arguments.URLArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.server.command.ModIdArgument;

import static com.sekwah.reskin.ReSkin.MOD_ID;

public class ReskinArguments {

    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, MOD_ID);


    public static final RegistryObject<SingletonArgumentInfo<URLArgument>> SKIN_URL = ARGUMENT_TYPES.register("skin_url", () ->
            ArgumentTypeInfos.registerByClass(URLArgument.class, SingletonArgumentInfo.contextFree(URLArgument::urlArg)));
    // ArgumentTypes.register("reskin:url_argument", URLArgument.class, new EmptyArgumentSerializer<>(URLArgument::urlArg));

    public static void register(IEventBus eventBus) {
        ARGUMENT_TYPES.register(eventBus);
    }
}
