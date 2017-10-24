package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import classes.ThreadClass;
import object.File;
import persistance.DbPersistImpl;

/**
 * Servlet implementation class MarkupServlet
 */
@WebServlet("/MarkupServlet")
@MultipartConfig

public class MarkupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static int firstLoad = 1;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MarkupServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
    
//--------------------------------------------------------------------------------------------------------------------------
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (firstLoad == 1){
			firstLoad++;
			RedirectJSP(request, response);		
		    
		}else{
			String uploadFile = request.getParameter("uploadFile");
			String dateRange = request.getParameter("dateSelector");
			String chosenFile = request.getParameter("fileNames");
			
			if (uploadFile != null) {
				UploadFile(request, response);
			}else if (dateRange != null){
				DateSelector(request, response);
			}else if (chosenFile != null){
				DisplayFileOption(request, response);
			}
		}
	}

	//--------------------------------------------------------------------------------------------------------------------------
	public void UploadFile (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    Part filePart = request.getPart("file");
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	    
	    InputStream fileContent = filePart.getInputStream();
	    File f = new File();
	    
	    if (fileName.contains(".html")){
	    
		    Scanner s = new Scanner(fileContent).useDelimiter("\\A");
		    String fileContentString = s.hasNext() ? s.next() : "";
		    s.close();
		    
		    f.setFileName(fileName);
		    if (fileName.indexOf("_") != -1){
		    	f.setId((fileName).substring(0, fileName.indexOf("_")));
		    }else{
		    	f.setId((fileName).substring(0, fileName.indexOf(".")));
		    }
		    		    
		    fileContentString = fileContentString.replaceAll("'", "\\\\\'");
		    f.setFileContent(fileContentString);
		  	    
		    DbPersistImpl persist = new DbPersistImpl();
		    int process = persist.addFile(f);
		    
		    if (process == 0){//if a previous version of the file is already in the db
		    	do{//try to find an appendable version # to allow submission to db
		    		Random rand = new Random();
		    		f.setId(f.getId() + "(" + Integer.toString(rand.nextInt(50) + 1) + ")");
		    		process = persist.addFile(f);
		    	}while (process == 0);
		    	
		    	 try {
		  		   scoreHTML (request, response, f);
		  		} catch (InterruptedException e) {
		  			e.printStackTrace();
		  		}
		    }
	    }else{
	    	request.setAttribute("errorMsg", "Something went wrong. Please make sure the file is of type .html and upload again.");
		    RedirectJSP(request, response);
	    }
	 }
	
//--------------------------------------------------------------------------------------------------------------------------

	public void DateSelector (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		
		DbPersistImpl persist = new DbPersistImpl();
		List<File> files = persist.retrieveFileBtwnDates(date1, date2);
		
		request.setAttribute("date1", date1);
		request.setAttribute("date2", date2);
		
		if (files.isEmpty()){
			File f = new File();
			f.setFileName("No files to display");
			files.add(f);
		}
		request.setAttribute("fileRange", files);
		RedirectJSP(request, response);
	}
	
//--------------------------------------------------------------------------------------------------------------------------

	private void DisplayFileOption(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String file_id = request.getParameter("fileNames");
		DbPersistImpl persist = new DbPersistImpl();
		List<Integer> scores = persist.retreiveScores(file_id);
		
		request.setAttribute("fileOptionName", file_id);
		request.setAttribute("fileScores", scores);
		
		RedirectJSP(request, response);
	}
	
	
//--------------------------------------------------------------------------------------------------------------------------
		public void scoreHTML(HttpServletRequest request, HttpServletResponse response, File file) throws ServletException, IOException, InterruptedException{
			int finalScore = 0;
			String [] tagName = {"<div>", "<p>", "<h1>", "<h2>", "<html>", "<body>", "<header>", "<footer>", "<font>", "<center>", "<big>", "<strike>", "<tt>", "<frameset>", "<frame>", "<br>", "<title>", "<hr>", "<em>", "<strong>" };
			
			//long startTime = System.currentTimeMillis();
			for (int i=0; i < tagName.length; i++){
				//concurrent processing
				ExecutorService service = Executors.newFixedThreadPool(1);
				Future<Integer> score = service.submit(new ThreadClass(tagName[i], file));
					
				try {
					finalScore += score.get();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				service.shutdown();
				//long endTime = System.currentTimeMillis();
				//System.out.println(tagName[i] + ":  " + Long.toString(endTime-startTime)); 
				}
			
			DbPersistImpl persist = new DbPersistImpl();
			int process = persist.addScore(finalScore, file);
			    
			request.setAttribute("finalscore", finalScore);
			    
			RedirectJSP(request, response);
		}
			
//--------------------------------------------------------------------------------------------------------------------------
	
	public void RedirectJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			DbPersistImpl persist = new DbPersistImpl();
		    
			File highestFile = persist.retrieveHighestScore();
			File lowestFile = persist.retrieveLowestScore();
			List<File> files= persist.allRunsAverage();
			List<String> names = persist.getAllFileNames();
			

			request.setAttribute("high_score", highestFile);
			request.setAttribute("low_score", lowestFile);
			request.setAttribute("files", files);
			request.setAttribute("fileNames", names);

		    
		    request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		

//--------------------------------------------------------------------------------------------------------------------------

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
