/**
 *  Program to test the basic functionality of the Library class
 *
 *  @author Dr. Cordova
 */

public class LibraryTest
{
   public static void main(String[] args)
   {
      LibStacks theLibrary = new LibStacks("ulmlibrary.txt");
      System.out.println(theLibrary.getBooks("fiction"));
      System.out.println(theLibrary.getBooks("nonfiction"));
      System.out.println(theLibrary.getBooks("textbooks"));
      System.out.println(theLibrary.checkOutBook("fiction", "2001 : A Space Odyssey"));
      System.out.println(theLibrary.checkOutBook("nonfiction", "Things that matter"));
      theLibrary.returnBook("fiction", "2001 : A Space Odyssey");
      String book = "Tales of the grotesque and arabesque";
      System.out.println(book + " : " + theLibrary.findBook(book));
      book = "Human ANATOMY";
      System.out.println(book + " : " + theLibrary.findBook(book));
      
   }
}
