
package com.rath.rathbot.cmd.faq;

import java.util.Set;

import com.rath.rathbot.RathBot;
import com.rath.rathbot.cmd.RBCommand;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 * Subcommand of 'faq' that allows editing of FAQs.
 * 
 * @author Tim Backus tbackus127@gmail.com
 */
public class FAQEditCmd extends RBCommand {

  /** The subcommand's name that the bot checks for. */
  private static final String CMD_NAME = "edit";

  /** The description of this subcommand. */
  private static final String CMD_DESCR = "Edits the contents of FAQ with name=<NAME> to have contents=<CONTENTS>"
      + " if the FAQ entry exists. Otherwise, created a new FAQ with name=<NAME> and with contents=<CONTENTS>.";

  @Override
  public Set<RBCommand> getSubcommands() {

    return null;
  }

  @Override
  public void executeCommand(final RathBot rb, final IUser author, final IChannel channel, final String[] tokens,
      final int tokenDepth) {

    // Check this command's subcommands for a match, and return the matched command
    final RBCommand cmd = super.checkSubcommands(getSubcommands(), tokens, tokenDepth);

    // If a subcommand is not found
    if (cmd == null) {

      // TODO do edit FAQ stuff here

    } else {
      cmd.executeCommand(rb, author, channel, tokens, tokenDepth + 1);
    }

  }

  @Override
  public String getCommandName() {

    return CMD_NAME;
  }

  @Override
  public String getCommandDescription() {

    return CMD_DESCR;
  }

  @Override
  public boolean requiresModStatus() {

    return true;
  }

}