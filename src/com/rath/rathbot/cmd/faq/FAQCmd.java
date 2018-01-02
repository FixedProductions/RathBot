
package com.rath.rathbot.cmd.faq;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import com.rath.rathbot.RathBot;
import com.rath.rathbot.cmd.RBCommand;
import com.rath.rathbot.exceptions.FAQNotFoundException;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 * This class handles the 'faq' command, which provides a way to store and recall any text.
 * 
 * @author Tim Backus tbackus127@gmail.com
 */
public class FAQCmd extends RBCommand {
  
  /** The path to the saved FAQ Strings. */
  private static final String FAQ_DATA_PATH = "dat/faq.dat";
  
  /** A map from FAQ name to its contents. */
  private static TreeMap<String, String> faqMap = initFAQ();
  
  /** The command String for this command. */
  private static final String FAQ_CMD = "faq";
  
  /** The command description. */
  private static final String FAQ_DESCR = "Allows the storing and recalling of text blocks.";
  
  /**
   * Initializes the FAQ map.
   * 
   * @return a TreeMap mapping FAQ ID to its contents.
   */
  private static final TreeMap<String, String> initFAQ() {
    
    System.out.println("Init.");
    
    final File fdat = new File(FAQ_DATA_PATH);
    
    // If the file doesn't exist, create it
    if (!fdat.exists()) {
      try {
        fdat.createNewFile();
        return new TreeMap<String, String>();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      
      // If the file is empty, create a new TreeMap for it
      if (fdat.length() <= 0) {
        return new TreeMap<String, String>();
      }
    }
    
    return loadFAQMap(fdat);
    
  }
  
  /**
   * Loads the FAQ map from file.
   * 
   * @return the previously-serialized HashMap.
   */
  @SuppressWarnings("unchecked")
  private static final TreeMap<String, String> loadFAQMap(final File file) {
    
    System.out.println("Loading FAQ map from file.");
    
    // Initialize data streams
    FileInputStream fis = null;
    ObjectInputStream oin = null;
    TreeMap<String, String> result = null;
    Object obj = null;
    try {
      
      // Build an input stream for deserialization
      fis = new FileInputStream(FAQ_DATA_PATH);
      if (fis.available() > 0) {
        oin = new ObjectInputStream(fis);
        
        // Read the object in and close the streams
        obj = oin.readObject();
        oin.close();
        fis.close();
        
      } else {
        System.err.println("FAQ map data is empty.");
      }
      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    
    // If something went wrong, return null
    if (fis == null || oin == null) {
      System.err.println("FAQ map load error.");
      return null;
    }
    
    // Cast the read object to a TreeMap
    if (obj instanceof TreeMap) {
      result = (TreeMap<String, String>) obj;
      System.out.println("Read successfully.");
    } else {
      System.err.println("Read object not instance of TreeMap.");
      return null;
    }
    
    return result;
  }
  
  /**
   * Saves the FAQ map to a file.
   */
  private static final void saveFAQMap() {
    
    FileOutputStream fos = null;
    ObjectOutputStream oos = null;
    
    try {
      
      // Build an output stream for serialization
      fos = new FileOutputStream(FAQ_DATA_PATH);
      oos = new ObjectOutputStream(fos);
      
      // Write the map and close streams
      oos.writeObject(faqMap);
      oos.close();
      fos.close();
      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Posts the list of FAQs that are currently set.
   * 
   * @param channel the channel to send the list to. Returns null if the map is empty.
   */
  public static final String getFaqList() {
    
    // Check that the map has entries in it
    if (faqMap.size() <= 0) {
      return null;
    }
    
    // Construct the FAQ list message
    String message = "FAQ List:\n";
    for (final String s : faqMap.keySet()) {
      message += ("  " + s + "\n");
    }
    return message;
  }
  
  /**
   * Tests if the FAQ map has a specific ID registered.
   * 
   * @param faq the FAQ ID.
   * @return true if the map's key set contains the ID; false if not.
   */
  public static final boolean hasFaq(final String faq) {
    
    return faqMap.containsKey(faq);
  }
  
  /**
   * Sends a FAQ's contents to the specific channel.
   * 
   * @param faq the FAQ ID to fetch the message of.
   */
  public static final String getFaq(final String faq) {
    
    if (faqMap.containsKey(faq)) {
      return faqMap.get(faq);
    } else {
      return null;
    }
  }
  
  /**
   * Adds or replaces a FAQ entry.
   * 
   * @param faqName the FAQ ID.
   * @param message the FAQ's contents.
   */
  public static final void addFaq(final String faqName, final String message) {
    
    if (hasFaq(faqName)) {
      System.out.println("Editing FAQ: \"" + faqName + "\" to \"" + message + "\".");
    } else {
      System.out.println("Adding FAQ: \"" + faqName + "\" -> \"" + message + "\".");
    }
    
    faqMap.put(faqName, message);
    saveFAQMap();
  }
  
  /**
   * Removes a FAQ entry.
   * 
   * @param faqName the FAQ ID.
   * @throws FAQNotFoundException if the FAQ entry does not exist.
   */
  public static final void removeFaq(final String faqName) throws FAQNotFoundException {
    
    // If the map isn't created yet for some reason, do it.
    if (faqMap == null) {
      initFAQ();
    }
    
    System.out.println("Removing FAQ: \"" + faqName + "\".");
    
    // Remove the mapping and save
    if (faqMap.containsKey(faqName)) {
      faqMap.remove(faqName);
      saveFAQMap();
    } else {
      throw new FAQNotFoundException(faqName);
    }
    
  }
  
  /**
   * Removes all mappings from the FAQ map.
   */
  public static final void clearFAQMap() {
    
    faqMap.clear();
    saveFAQMap();
  }
  
  @Override
  public Set<RBCommand> getSubcommands() {
    
    final Set<RBCommand> result = new HashSet<RBCommand>();
    
    // All sub-commands of the 'faq' command are registered here.
    result.add(new FAQListCmd());
    result.add(new FAQEditCmd());
    result.add(new FAQRemoveCmd());
    
    return result;
  }
  
  @Override
  public boolean executeCommand(final RathBot rb, final IUser author, final IChannel channel, final String[] tokens,
      final int tokenDepth) {
    
    // If there are no subcommands for this command
    if (!super.executeCommand(rb, author, channel, tokens, tokenDepth)) {
      
      // TODO: Do normal "rb! faq" stuff (post help message)
      System.out.println("Executing faq");
      
    }
    
    return true;
    
  }
  
  @Override
  public String getCommandName() {
    
    return FAQ_CMD;
  }
  
  @Override
  public String getCommandDescription() {
    
    return FAQ_DESCR;
  }
  
  @Override
  public boolean requiresModStatus() {
    
    return false;
  }
  
}
