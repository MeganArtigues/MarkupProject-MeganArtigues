package classes;

import java.util.concurrent.Callable;

import object.File;
import servlet.MarkupServlet;

public class ThreadClass implements Callable<Integer>{
	private File file;
	private String tagName;
	private int arbitraryScoreValue;
	private volatile int score; 
	
   public ThreadClass(String tagName, File file) {   // constructor
      this.tagName = tagName.toLowerCase();
      this.file = file;
      
      if (tagName.equalsIgnoreCase("<div>")){
    	  	arbitraryScoreValue += 5;
		}else if (tagName.equalsIgnoreCase("<p>")){
			arbitraryScoreValue += 3;
		}else if (tagName.equalsIgnoreCase("<h1>")){
			arbitraryScoreValue += 1;
		}else if (tagName.equalsIgnoreCase("<h2>")){
			arbitraryScoreValue += 1;
		}else if (tagName.equalsIgnoreCase("<html>")) {
			arbitraryScoreValue += 10;
		}else if (tagName.equalsIgnoreCase("<body>")) {
			arbitraryScoreValue += 10;
		}else if (tagName.equalsIgnoreCase("<header>")) {
			arbitraryScoreValue += 5;
		}else if (tagName.equalsIgnoreCase("<footer>")) {
			arbitraryScoreValue += 3;
		}else if (tagName.equalsIgnoreCase("<font>")) {
			arbitraryScoreValue += -2;
		}else if (tagName.equalsIgnoreCase("<center>")) {
			arbitraryScoreValue += -3;
		}else if (tagName.equalsIgnoreCase("<big>")) {
			arbitraryScoreValue += -5;
		}else if (tagName.equalsIgnoreCase("<strike>")) {
			arbitraryScoreValue += -2;
		}else if (tagName.equalsIgnoreCase("<tt>")) {
			arbitraryScoreValue += -2;
		}else if (tagName.equalsIgnoreCase("<frameset>")) {
			arbitraryScoreValue += -5;
		}else if (tagName.equalsIgnoreCase("<frame>")) {
			arbitraryScoreValue += -5;
		}else if (tagName.equalsIgnoreCase("<br>")) {
			arbitraryScoreValue += 1;
		}else if (tagName.equalsIgnoreCase("<title>")) {
			arbitraryScoreValue += 5;
		}else if (tagName.equalsIgnoreCase("<hr>")) {
			arbitraryScoreValue += 2;
		}else if (tagName.equalsIgnoreCase("<em>")) {
			arbitraryScoreValue += -3;
		}else if (tagName.equalsIgnoreCase("<strong>")) {
			arbitraryScoreValue += -4;
		}
   }
 
   public Integer call() {
	  tagName = tagName.substring(0, tagName.indexOf(">"));
	  score = 0;
      String content = file.getFileContent();
      content = content.toLowerCase();
      int tag = content.indexOf(tagName);
      while(tag != -1){

          tag = content.indexOf(tagName , tag + 1);
          score += arbitraryScoreValue;
      }
      
      return score;
   }
}