package object;

public class File {
	private String id;
	private String fileName;
	private String fileContent;
	private int score;
	
	
	public File(){
		this.id = null;
		this.fileName = null;
		this.fileContent = null;
		this.score = -1;
	}
	
	public File(String id, String fileName, String fileContent){
		this.id = null;
		this.fileName = fileName;
		this.fileContent = fileContent;
		this.score = -1;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
