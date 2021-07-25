package com.sekwah.reskin.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Arrays;
import java.util.Collection;

public class URLArgument implements ArgumentType<String> {

   private static final String HTTPS_START = "https://";

   private static final SimpleCommandExceptionType HTTPS_EXCEPTION = new SimpleCommandExceptionType(new TranslatableComponent("argument.nohttps"));

   private static final Collection<String> EXAMPLES = Arrays.asList(HTTPS_START, "https://i.imgur.com/mORJxcm.png");

   public static String getURL(CommandContext<CommandSourceStack> ctx, String arg) {
      return ctx.getArgument(arg, String.class);
   }

   @Override
   public String parse(StringReader reader) throws CommandSyntaxException {
      StringBuilder url = new StringBuilder();
      while(reader.canRead() && reader.peek() != ' ') {
         url.append(reader.read());
         String compareString = url.toString();
         if(!compareString.startsWith(compareString.length() < HTTPS_START.length() ? HTTPS_START.substring(0,compareString.length()) : HTTPS_START)) {
            throw HTTPS_EXCEPTION.createWithContext(reader);
         }
      }
      return url.toString();
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public static URLArgument urlArg() {
      return new URLArgument();
   }
}
