import java.util.*;
import java.io.*;

public class LibStacks
{

   private StackInt<String> fiction;
   private StackInt<String> nonfiction;
   private StackInt<String> textbooks;
   private StackInt<String> buffer = new LinkedStack<>();


   /**
      library stack constructor, creates three linked stacks (fiction, nonfiction, textbooks) from a file
      @param fileName file containing the desired library books 
      
   **/
   public LibStacks(String filename)
   {
      Scanner scan = null;
      int section = 1;
      
      fiction =    new LinkedStack<String>(); // create the stacks
      nonfiction = new LinkedStack<String>();
      textbooks =  new LinkedStack<String>();
   
      try //to open the file
      {
         
         scan = new Scanner(new File(filename));
         boolean endFile = false;
      
      
      
         while (!endFile) 
         {
            try // to keep reading another line
            {
               Scanner read = new Scanner(scan.nextLine()); //scan in the line
               boolean endLine = false;
               while (!endLine)
               {
                  try // to keep perusing the line
                  {
                     String book = read.next().replaceAll("_", " "); //replace the underscores with spaces
                     switch(section) //push the book onto the proper section stack
                     {
                        case 1: fiction.push(book);
                           break;
                     
                        case 2: nonfiction.push(book);
                           break;
                     
                        case 3: textbooks.push(book);
                           break;
                     }
                  } 
                  
                  catch(NoSuchElementException endOfLine) 
                  {
                     endLine = true; 
                  }
               }             
               read.close(); // close scanner
               section++;    // new section
            }
            
            catch(NoSuchElementException endOfFile) 
            {
               endFile = true;  //end of File
            }
         } 
      }
      
      catch (FileNotFoundException e)
      {
         System.out.println("that is a FAKE file!!! rude.");
      }
      
      scan.close(); // close scanner  
   } // end constructor

   public StackInt replaceBooks(StackInt<String> section)
   {
       
      while (!buffer.empty())
      {
         section.push(buffer.pop()); // replace the displaced books
      }
      return section;
   }





   public boolean searchFiction(String book)
   {
      boolean found = false;
      while (!fiction.empty() && found != true)
      {
         if (fiction.peek().toLowerCase().equals(book.toLowerCase())) // check to see if the book title matches 
         {
            found = true;
         }
            
         buffer.push(fiction.pop());
      }
         
      replaceBooks(fiction);
            
      if (found == true)
      { 
         return true; }
   
      return false;
   
   }

   public boolean searchNonfiction(String book)
   {
      boolean found = false;
      while (!nonfiction.empty() && found != true)
      {
         if (nonfiction.peek().toLowerCase().equals(book.toLowerCase())) // check to see if the book title matches 
         {
            found = true;
         }
            
         buffer.push(nonfiction.pop());
      }
         
      replaceBooks(nonfiction);
            
      if (found == true)
      { 
         return true; }
   
      return false;
      
   }

   public boolean searchTextbooks(String book)
   {
      
   
      boolean found = false;
      while (!textbooks.empty() && found != true)
      {
         if (textbooks.peek().toLowerCase().equals(book.toLowerCase())) // check to see if the book title matches 
         {
            found = true;
         }
            
         buffer.push(textbooks.pop());
      }
         
      replaceBooks(textbooks);
            
      if (found == true)
      { 
         return true; }
   
      return false;
   
   }

   /**
      method to retrieve the list of books in a section
      @return the full contents of a section
   **/
   public String getBooks(String type)
   {
      String section = type.toLowerCase(); //ignore case of type entered
      StringBuilder contents = new StringBuilder(); 
   
      switch(section)
      {
         case "fiction":
            while (!fiction.empty())
            {
               contents.append(fiction.peek());  // add the string to the list
               contents.append('\n');
               buffer.push(fiction.pop()); //move the book out of the way
            }
         
            replaceBooks(fiction);
            break;
      
      
         case "nonfiction":  // other cases are the same, with their respective sectoins
            while (!nonfiction.empty())
            {
               contents.append(nonfiction.peek());
               contents.append('\n');
               buffer.push(nonfiction.pop());
            }
         
            replaceBooks(nonfiction);
            break;
      
      
         case "textbooks":
            while (!textbooks.empty())
            {
               contents.append(textbooks.peek());
               contents.append('\n');
            
               buffer.push(textbooks.pop());
            }
         
            replaceBooks(textbooks);
            break;
      }// end switch
   
      return "contents of " + section + " section: \n" + contents.toString();
   }

   /**
      method to remove a book if it has been checked out
      @return true or false depending on if the book exists in the section
   **/
   public boolean checkOutBook(String type, String book)
   {
      boolean found = false;
      
      switch(type.toLowerCase())
      {
         case "fiction":
            while (!fiction.empty() && found !=true)
            {
               
               if (fiction.peek().toLowerCase().equals(book.toLowerCase())) // remove book and set found to true if the book is found
               {
                  fiction.pop();
                  found = true;
               }
               
               else
               {
                  buffer.push(fiction.pop());
               }
            }
         
            replaceBooks(fiction);
            break;
      
      
         case "nonfiction":
            while (!nonfiction.empty() && found !=true)
            {
               if (nonfiction.peek().toLowerCase().equals(book.toLowerCase()))
               {
                  nonfiction.pop();
                  found = true;
               }
               
               else
               {
                  buffer.push(nonfiction.pop());
               }
            
            }
         
            replaceBooks(nonfiction);
            break;
            
      
      
         case "textbooks":
            while (!textbooks.empty() && found !=true)
            {
               if (textbooks.peek().toLowerCase().equals(book.toLowerCase()))
               {
                  textbooks.pop();
                  found = true;
               }
               
               else
               {
                  buffer.push(textbooks.pop());
               }
            
            }
         
            replaceBooks(textbooks); 
            break;
      } //end switch
      
      if (found == true)
      { 
         return true; }
      
      return false;
   }


   /**
      method that adds a book to a section when it is being returned, given it doesn't already exist
      @return true or false depending on if the book exists in the section
   **/
   public boolean returnBook(String type, String book)
   {
      boolean exists = false;
      if (searchFiction(book) || searchNonfiction(book) || searchTextbooks(book))
      { 
         return false; }
      
      switch(type.toLowerCase())
      {
         case "fiction":
            if (!searchFiction(book))
            { fiction.push(book); }
            
            break;
      
         case "nonfiction":
            if (!searchNonfiction(book))
            { nonfiction.push(book); }           
            break;
      
         case "textbooks":
            if (!searchTextbooks(book))
            { textbooks.push(book); }
            break; 
      } // end switch
      return true; 
   }


   /**
      method that searches all the sections to see if the library has it
      @return the section that the book is found in, or "not found" if the library doesn't have it
   **/
   public String findBook(String book)
   {
      String section = null;
      
      if(!(searchFiction(book) || searchNonfiction(book) || searchTextbooks(book)))
      { return "not found"; }
      
      
      if(searchFiction(book))
      { section = "fiction"; }
               
      if(searchNonfiction(book))
      { section = "nonfiction"; }
      
      if(searchTextbooks(book))
      { section = "textbooks"; }
      
      return "found in the " + section + " section ";
      
   
   }


} // end class