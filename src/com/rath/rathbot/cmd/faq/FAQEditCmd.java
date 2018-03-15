
package com.rath.rathbot.cmd.faq;

import java.util.Set;

import com.rath.rathbot.RathBot;
import com.rath.rathbot.cmd.RBCommand;
import com.rath.rathbot.msg.MessageHelper;

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
  public boolean executeCommand(final RathBot rb, final IUser author, final IChannel channel, final String[] tokens,
      final int tokenDepth) {
    
    if (!super.executeCommand(rb, author, channel, tokens, tokenDepth)) {
      System.out.println("Executing faq.edit");
      
      final String message = MessageHelper.concatenateTokens(tokens, 4);
      
      FAQCmd.addFaq(tokens[3], message);
      rb.sendMessage(channel, "FAQ " + tokens[3] + " updated.");
    }
    
    return true;
    
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
  public String getCommandUsage() {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public int permissionLevelRequired() {
    return RBCommand.PERM_MODERATOR;
  }
  
  @Override
  public void setupCommand() {
    return;
  }
  
}
