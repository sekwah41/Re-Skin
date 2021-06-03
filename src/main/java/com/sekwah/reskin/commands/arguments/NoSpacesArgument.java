package com.sekwah.reskin.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;

import java.util.Arrays;
import java.util.Collection;

public class NoSpacesArgument implements ArgumentType<String> {

   private static final Collection<String> EXAMPLES = Arrays.asList("sometexthere", "just_no_spaces");

   public static String getText(CommandContext<CommandSource> ctx, String arg) {
      return ctx.getArgument(arg, String.class);
   }

   @Override
   public String parse(StringReader reader) {
      StringBuilder url = new StringBuilder();
      while(reader.canRead() && reader.peek() != ' ') {
         url.append(reader.read());
      }
      return url.toString();
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
