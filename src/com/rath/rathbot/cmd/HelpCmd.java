
package com.rath.rathbot.cmd;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.rath.rathbot.RathBot;
import com.rath.rathbot.msg.MessageHelper;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 * This command provides a manual for the other commands.
 * 
 * @author Tim Backus tbackus127@gmail.com
 */
public class HelpCmd extends RBCommand {
  
  /** The name of this command. */
  private static final String CMD_NAME = "help";
  
  /** The description of this command. */
  private static final String CMD_DESCR = "Provides information on how to use various commands.";
  
  /** The set of commands such that help is available. */
  private static final Map<String, RBCommand> helpCmdMap = new TreeMap<String, RBCommand>();
  
  /**
   * Adds a command to the help command set.
   * 
   * @param cmd the command to add.
   */
  public void addCommandEntry(final String name, final RBCommand cmd) {
    
    helpCmdMap.put(name, cmd);
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
  public Set<RBCommand> getSubcommands() {
    
    return null;
  }
  
  @Override
  public boolean executeCommand(final RathBot rb, final IUser author, final IChannel channel, final String[] tokens,
      final int tokenDepth) {
    
    // If the bot only receives "rb! help", post the commands list
    if (tokens.length <= 2) {
      rb.sendMessage(channel, MessageHelper.buildCmdDescrMsg("Available commands:", helpCmdMap));
    } else {
      
      // If the command sent for the 3rd token is a valid command, post its syntax
      if (helpCmdMap.containsKey(tokens[2])) {
        final String cmdDescr = helpCmdMap.get(tokens[2]).getCommandDescription();
        rb.sendMessage(channel, tokens[2] + " - " + cmdDescr);
      } else {
        // TODO: Help entry not found
      }
      
    }
    
    return true;
  }
  
  @Override
  public int permissionLevelRequired() {
    return RBCommand.PERM_BASE;
  }
  
  @Override
  public void setupCommand() {
    return;
  }
  
}
